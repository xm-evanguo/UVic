/*
 *	Name: Evan Guo
 *	V#: V00866199
 *	CSC 360 Winter 2018
 */

#include <string.h>
#include <limits.h>
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <signal.h>
#include <sys/types.h>
#include "PMan.h"

const char* delimiters = " \n\t";
const char* prompt = "PMan: > ";

/*
 *	Take a file name and commands,
 *	then excute the program by execvp() with commands,
 *	and add the process into a linkedlist
 */
void bg(char **args, list *list){
	pid_t pid = fork();
	if(pid == -1){
		fprintf(stderr, "Error: Fail to fork.\n");
		exit(EXIT_FAILURE);
	}else if (pid == 0){
		execvp(args[0], args);
		fprintf(stderr, "Error: Fail to execvp %s.\n", args[0]);
		exit(EXIT_FAILURE);
	}else{
		sleep(1);
		char *path = realpath(args[0], NULL);
		if(path != NULL){
			node *tmp = new_node(pid, path, 1);
			add_node(tmp, list);
		}
	}
}

/*
 *	Print a list of all the programs currently executing in the background
 */
void bglist(list *list){
	node *cur;
	for(cur = list->head; cur != NULL; cur = cur->next){
		if(cur->state == 1)
			printf("%d: %s\n", cur->pid, cur->path);
	}
	printf("Total background jobs: %d\n", list->size);
}

/*
 *	Send the signal "SIGTERM" to process <pid>
 */
void bgkill(char *command, list *list){
	pid_t pid = (pid_t)atoi(command);
	if(pid == 0){
		fprintf(stderr, "Usage: bgkill <pid>\n");
		return;
	}
	if(remove_node(pid, list) == 1){
		send_signal(pid, SIGTERM);
		sleep(1);
		printf("Process %d has been terminated.\n", pid);
	}
}

/*
 *	Send the signal "SIGSTOP" to process <pid>
 */
void bgstop(char *command, list *list){
	pid_t pid = (pid_t)atoi(command);
	if(pid == 0){
		fprintf(stderr, "Usage: bgstop <pid>\n");
		return;
	}
	node *tmp = search_node(pid, list);
	if(tmp == NULL){
		fprintf(stderr, "Error: Invalid pid %d.\n", pid);
	}else if(tmp->state == 1){
		send_signal(pid, SIGSTOP);
		sleep(1);
		tmp->state = 0;
		list->size--;
	}
}

/*
 *	Send the signal "SIGCONT" to process <pid>
 */
void bgstart(char *command, list *list){
	pid_t pid = (pid_t)atoi(command);
	if(pid == 0){
		fprintf(stderr, "Usage: bgstart <pid>\n");
		return;
	}
	node *tmp = search_node(pid, list);
	if(tmp == NULL){
		fprintf(stderr, "Error: Invalid pid %d.\n", pid);
	}else if(tmp->state == 0){
		send_signal(pid, SIGCONT);
		sleep(1);
		tmp->state = 1;
		list->size++;
	}
}

/*
 *	Print information for process <pid>.
 */
void pstat(char *command, list *list){
	pid_t pid = (pid_t)atoi(command);
	if(search_node(pid, list) == NULL){
		fprintf(stderr, "Error: Process %d does not exist.\n", pid);
		return;
	}
	char path[20];
	path[0] = '\0';
	strcat(path, "/proc/");
	strcat(path, command);
	strcat(path, "/stat");
	FILE *infile = fopen(path, "r");
	char **stat = get_stat(infile);
	float clock_ticks = (float)sysconf(_SC_CLK_TCK);
	char *comm = stat[0];
	char state = *stat[1];
	float utime = atof(stat[2]);
	float stime = atof(stat[3]);
	long int rss = strtol(stat[4], (char **)NULL, 10);
	printf("comm: %s\nstate: %c\nutime: %f\n", comm, state, utime/clock_ticks);
	printf("stime: %f\nrss: %ld\n", stime/clock_ticks, rss);
	free(stat);
	fclose(infile);
	pstatus(command);
}

/*
 *	A helper function for pstat.
 *	Tokenize and return necessary information from /proc/<pid>/stat
 */
char **get_stat(FILE *infile){
	char *line = NULL;
	size_t len = 0;
	getline(&line, &len, infile);
	char **array = (char **)emalloc(6 * sizeof(char *));
	char *tmp = strtok(line, " ");
	int counter = 1;
	while(tmp != NULL){
		if(counter == 2){
			array[0] = tmp;
		}else if (counter == 3){
			array[1] = tmp;
		}else if(counter == 14){
			array[2] = tmp;
		}else if(counter == 15){
			array[3] = tmp;
		}else if(counter == 24){
			array[4] = tmp;
			break;
		}
		counter++;
		tmp = strtok(NULL, " ");
	}
	return array;
}

/*
 *	A helper function for pstat.
 *	Print the information for process <pid> from /proc/<pid>/status
 */
void pstatus(char *command){
	char path[20];
	path[0] = '\0';
	strcat(path, "/proc/");
	strcat(path, command);
	strcat(path, "/status");
	FILE *infile = fopen(path, "r");
	char **status = get_status(infile);
	int voluntary = atoi(status[0]);
	int nonvoluntary = atoi(status[1]);
	printf("VOLUNTARY_CTXT_SWITCHES: %d\n", voluntary);
	printf("NONVOLUNTARY_CTXT_SWITCHES: %d\n", nonvoluntary);
	free(status);
	fclose(infile);
}

/*
 *	A helper function for pstatus.
 *	Tokenize and return necessary information from /proc/<pid>/status
 */
char **get_status(FILE *infile){
	char *line = NULL;
	size_t len = 0;
	size_t read;
	char **array = (char **)emalloc(3 * sizeof(char *));
	char* voluntary = "voluntary_ctxt_switches:";
    char* nonvoluntary = "nonvoluntary_ctxt_switches:";
	while((read = getline(&line, &len, infile)) != -1){
		char *tmp = strtok(line, delimiters);
		if(tmp != NULL){
			if(strncmp(tmp, voluntary, strlen(voluntary)) == 0){
				tmp = strtok(NULL, delimiters);
				array[0] = tmp;
			}else if(strncmp(tmp, nonvoluntary, strlen(nonvoluntary)) == 0){
				tmp = strtok(NULL, delimiters);
				array[1] = tmp;
				break;
			}
		}
	}
	return array;
}

/*
 *	Send a specific signal to process <pid>
 */
void send_signal(pid_t pid, int signal){
	if(kill(pid, signal) == -1){
		fprintf(stderr, "Error: Fail to send signal.\n");
		exit(EXIT_FAILURE);
	}
}

/*
 *	Identify user input and pass required parameter(s) to function
 */
void identify(char **args, list *list){
	if(args[0] == NULL){
		free(args);
		return;
	}
	if(strcmp(args[0], "bg") == 0){
		if(args[1] == NULL){
			fprintf(stderr, "Usage: bg <file name> [argument(s)]\n");
		}else{
			bg(args+1, list);
		}
	}else if(strcmp(args[0], "bglist") == 0){
		bglist(list);
	}else if(strcmp(args[0], "bgkill") == 0){
		if(args[2] != NULL || args[1] == NULL){
			fprintf(stderr, "Usage: bgkill <pid>\n");
		}else{
			bgkill(args[1], list);
		}
	}else if(strcmp(args[0], "bgstop") == 0){
		if(args[2] != NULL || args[1] == NULL){
			fprintf(stderr, "Usage: bgstop <pid>\n");
		}else{
			bgstop(args[1], list);
		}
	}else if(strcmp(args[0], "bgstart") == 0){
		if(args[2] != NULL || args[1] == NULL){
			fprintf(stderr, "Usage: bgstart <pid>\n");
		}else{
			bgstart(args[1], list);
		}
	}else if(strcmp(args[0], "pstat") == 0){
		if(args[2] != NULL || args[1] == NULL){
			fprintf(stderr, "Usage: pstat <pid>\n");
		}else{
			pstat(args[1], list);
		}
	}else{
		fprintf(stderr, "Error: %s command not found.\n", args[0]);
		return;
	}
	free(args);
}

/*
 *	Tokenize user input to a char **
 */
char **tokenize(char *line){
	int args_size = 10;
	char **args = (char **)calloc(args_size, sizeof(char *));
	if(args == NULL){
		fprintf(stderr, "Error: Fail to calloc.\n");
		exit(EXIT_FAILURE);
	}
	char *tmp = strtok(line, delimiters);
	int i = 0;
	while(tmp){
		args[i] = (char *)calloc(strlen(tmp)+1, sizeof(char));
		if(args[i] == NULL){
			fprintf(stderr, "Error: Fail to calloc.\n");
			exit(EXIT_FAILURE);
		}
		strncpy(args[i], tmp, strlen(tmp));
		tmp = strtok(NULL, delimiters);
		i++;
		if(i >= args_size - 1){
			args = (char **)realloc(args, sizeof(char *) * args_size+10);
			if(args == NULL){
				fprintf(stderr, "Error: Fail to realloc.\n");
				exit(EXIT_FAILURE);
			}
			args_size = args_size+10;
			printf("%d", args_size);
		}
	}
	args[i] = NULL;
	return args;
}

/*
 *	An error handler for malloc
 */
void *emalloc(size_t size){
	void *ptr;
	ptr = malloc(size);
	if(ptr == NULL){
		fprintf(stderr, "Error: Fail to malloc.\n");
		exit(EXIT_FAILURE);
	}
	return ptr;
}

/*
 *	Read a line from stdin, check process status,
 *	and pass the line to identify()
 */
void read_input(list *list){
	char *line = NULL;
	size_t len = 0;
	size_t read;
	read = getline(&line, &len, stdin);
	if(read == 0)
		return;
	update_status(list);
	identify(tokenize(line), list);
}

/*
 *	Update process status.
 *	Notify user if the process in linkedlist has been terminated
 *	by command outside of PMan.
 */
void update_status(list *list){
	int status;
	pid_t pid;
	while((pid = waitpid(-1, &status, WNOHANG)) > 0){
		if(search_node(pid, list) != NULL){
			remove_node(pid, list);
			printf("Process %d has been terminated.\n", pid);
		}
	}
}

/*
 *	Initialize a node
 */
node *init_node(){
	node *tmp = (node *)emalloc(sizeof(node));
	return tmp;
}

/*
 * Initialize a list
 */
list *init_list(){
	list *tmp = (list *)emalloc(sizeof(list));
	tmp->head = init_node();
	tmp->size = 0;
	return tmp;
}

/*
 * Build a new node
 */
node *new_node(pid_t pid, char *path, int state){
	node *new = (node *)emalloc(sizeof(node));
	new->next = NULL;
	new->pid = pid;
	new->path = path;
	new->state = state;
	return new;
}

/*
 * Add a node into list
 */
void add_node(node *new, list *list){
	if(list->size == 0){
		list->head = new;
		list->size++;
		return;
	}
	int i;
	node *cur;
	for(i = 1, cur = list->head; i < list->size; i++, cur = cur->next);
	cur->next = new;
	list->size++;
}

/*
 * Search a node from list.
 * Return the node if found, else return NULL
 */
node *search_node(pid_t pid, list *list){
	node *cur = list->head;
	for(;cur != NULL; cur = cur->next){
		if(cur->pid == pid){
			return cur;
		}
	}
	return NULL;
}

/*
 * Remove a node from list.
 * Return 1 if success, else return 0
 */
int remove_node(pid_t pid, list *list){
	node *prv = list->head;
	node *cur = NULL;
	if(prv->next != NULL)
			cur = prv->next;
	if(prv->pid == pid){
		if(cur != NULL){
			if(prv->state == 1){
				list->size--;
			}
			list->head = cur;
		}else{
			free(list);
			list = init_list();
		}
		free(prv);
		return 1;
	}
	for(; cur != NULL; prv = cur, cur = cur->next){
		if(cur->pid == pid){
			if(cur->next == NULL){
				prv->next = NULL;
			}else{
				prv->next = cur->next;
			}
			if(cur->state == 1){
				list->size--;
			}
			free(cur);
			return 1;
		}
	}
	fprintf(stderr, "Error: Invalid pid %d.\n", pid);
	return 0;
}

int main() {
	list *list = init_list();
	for(;;){
		printf("%s", prompt);
		read_input(list);
	}
}
