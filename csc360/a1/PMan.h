/*
 * Name: Evan Guo
 * Student Number: V00866199
 * CSC 360 Winter 2018
 */

#ifndef _PMAN_H_
#define _PMAN_H_

/*
    state = 1: program excuting
    state = 0: program stop
*/
typedef struct node node;
struct node{
    node *next;
    char *path;
    pid_t pid;
    int state;
};

typedef struct list list;
struct list{
    node *head;
    int size;
};

void bg(char **args, list *list);
void bglist(list *list);
void bgkill(char *command, list *list);
void bgstop(char *command, list *list);
void bgstart(char *command, list *list);
void pstat(char *commond, list *list);

void identify(char **args, list *list);
char **tokenize (char *line);
void *emalloc(size_t size);
void read_input(list *list);
void send_signal(int pid, int signal);
char **get_stat(FILE *infile);
void pstatus(char *command);
char **get_status(FILE *infile);
void update_status(list *list);

node *init_node();
list *init_list();
node *new_node(pid_t pid, char *path, int state);
node* search_node(pid_t pid, list *list);
void add_node(node *new, list *list);
int remove_node(pid_t pid, list *list);

#endif
