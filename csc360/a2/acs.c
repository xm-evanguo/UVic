/*
 *  Name: Evan Guo
 *  V#: V00866199
 *  CSC 360 Winter 2018
 */

#include <string.h>
#include <stdio.h>
#include <stdlib.h>
#include <pthread.h>
#include <sys/time.h>
#include "p2.h"

/*
 *  The following 5 varibles represent different mode for error_handler()
 */
#define m_lock 0
#define m_unlock 1
#define c_wait 2
#define c_broadcast 3
#define others 4

//lock for access queue
pthread_mutex_t q_lock = PTHREAD_MUTEX_INITIALIZER;
//lock for waiting customer to send signal to clerk in order to look up queue
pthread_mutex_t signal_lock = PTHREAD_MUTEX_INITIALIZER;

//convar for business class customer
pthread_cond_t bus_convar = PTHREAD_COND_INITIALIZER;
//convar for economy class customer
pthread_cond_t eco_convar = PTHREAD_COND_INITIALIZER;
//convar for waiting customer to send signal to clerk in order to look up queue
pthread_cond_t signal_convar = PTHREAD_COND_INITIALIZER;

//lock for each clerk
pthread_mutex_t c1_lock = PTHREAD_MUTEX_INITIALIZER;
pthread_mutex_t c2_lock = PTHREAD_MUTEX_INITIALIZER;
pthread_mutex_t c3_lock = PTHREAD_MUTEX_INITIALIZER;
pthread_mutex_t c4_lock = PTHREAD_MUTEX_INITIALIZER;
//convar for each clerk
pthread_cond_t c1_convar = PTHREAD_COND_INITIALIZER;
pthread_cond_t c2_convar = PTHREAD_COND_INITIALIZER;
pthread_cond_t c3_convar = PTHREAD_COND_INITIALIZER;
pthread_cond_t c4_convar = PTHREAD_COND_INITIALIZER;

queue *economy;
queue *business;

/*
 *  This 4 varibles are used for recording customer's id that each clerk is serving
 */
int c1_serve = 0;
int c2_serve = 0;
int c3_serve = 0;
int c4_serve = 0;

double start_time = 0;

int main(int argc, char *argv[]) {
    economy = init_queue();
    business = init_queue();
    customer *tmp;
    int totalbus = 0;
    int totaleco = 0;
    int totalcustomer;
    //start to read_input
    if(argc < 2){
        fprintf(stderr, "Error: no input file.\n");
        exit(EXIT_FAILURE);
    }else if(argc > 2){
        fprintf(stderr, "Error: too much arguments.\n");
        exit(EXIT_FAILURE);
    }
    FILE *input = fopen(argv[1], "r");
    char *line = NULL;
    size_t len = 0;
    size_t read;
    int i = 0;
    read = getline(&line, &len, input);
    if(read != -1){
        totalcustomer = atoi(strtok(line, "\n"));
    }else{
        fprintf(stderr, "Error: cannot read input");
    }
    customer **customer_info = (customer **)emalloc(sizeof(customer *) * (totalcustomer + 1));
    while((read = getline(&line, &len, input)) != -1){
        tmp = build_customer(line);
        if(tmp->class == 0){
            totaleco++;
        }else{
            totalbus++;
        }
        customer_info[i] = tmp;
        i++;
    }
    customer_info[i] = NULL;
    if(totalcustomer != totalbus + totaleco){
        fprintf(stderr, "Error: unmatched total number of customer.\n");
        exit(EXIT_FAILURE);
    }

    clerk **clerk_info = build_clerk();
    //start to create_clerk_thread
    pthread_t clerk_list[5];
    int test;
    start_time = get_time();
    for(i = 0; i < 4; i++){
        test = pthread_create(&clerk_list[i], NULL, clerk_function, (void *)clerk_info[i]);
        if(test != 0){
            fprintf(stderr, "Error: fail to creat customer thread.\n");
            exit(EXIT_FAILURE);
        }
    }
    free(clerk_info);

    //start to create_customer_thread
    pthread_t customer_list[totalcustomer + 1];
    for(i = 0; i < totalcustomer; i++){
        test = pthread_create(&customer_list[i], NULL, customer_function, (void *)customer_info[i]);
		if(test != 0){
			fprintf(stderr, "Error: fail to creat customer thread.\n");
			exit(EXIT_FAILURE);
        }
    }
    free(customer_info);

    //wait for all customers exit
    for(i = 0; i<totalcustomer; i++){
        test = pthread_join(customer_list[i], NULL);
        if(test != 0){
            fprintf(stderr, "Error: fail to pthread_join.\n");
            exit(EXIT_FAILURE);
        }
    }
    double end_time = get_time();
    double totalavg = (business->totaltime + economy->totaltime)/totalcustomer;
    double busavg = business->totaltime / totalbus;
    double ecoavg = economy->totaltime / totaleco;
    printf("The average waiting time for all customers in the system is: %.2f seconds. \n", totalavg);
    printf("The average waiting time for all business-class customers is: %.2f seconds. \n", busavg);
    printf("The average waiting time for all economy-class customers is: %.2f seconds. \n", ecoavg);
}

/*
 *  Customer's thread function
 */
void *customer_function(void *info){
    double start_time;
    double end_time;
    int test = 0;
    customer *cur = (customer *)info;
    //sleep until arrive time
    usleep(cur->atime * 100000);
    printf("A customer arrives: customer ID %2d. \n", cur->id);
    //lock access_queue_lock
    error_handler(pthread_mutex_lock(&q_lock), m_lock);
    if(cur->class == 0){
        economy = q_add(cur, economy);
        printf("A customer enters a queue: the queue id %1d, and length of the queue %2d. \n", cur->class, economy->size);
        //signal clerk that queue has customer
        error_handler(pthread_cond_broadcast(&signal_convar), c_broadcast);
        start_time = get_time();
        //wait for clerk's signal
        error_handler(pthread_cond_wait(&eco_convar, &q_lock), c_wait);
        while(c1_serve != cur->id && c2_serve != cur->id && c3_serve != cur->id && c4_serve != cur->id){
            //if customer wake up, check if he is the one been served
            //if not, keep waiting.
            error_handler(pthread_cond_wait(&eco_convar, &q_lock), c_wait);
        }
        end_time = get_time();
        economy->totaltime += end_time - start_time;
    }else{
        business = q_add(cur, business);
        printf("A customer enters a queue: the queue id %1d, and length of the queue %2d. \n", cur->class, business->size);
        //signal clerk that queue has customer
        error_handler(pthread_cond_broadcast(&signal_convar), c_broadcast);
        start_time = get_time();
        //wait for clerk's signal
        error_handler(pthread_cond_wait(&bus_convar, &q_lock), c_wait);
        while(c1_serve != cur->id && c2_serve != cur->id && c3_serve != cur->id && c4_serve != cur->id){
            //if customer wake up, check if he is the one been served
            //if not, keep waiting.
            error_handler(pthread_cond_wait(&bus_convar, &q_lock), c_wait);
        }
        end_time = get_time();
        business->totaltime += end_time - start_time;
    }
    //unlock access_queue_lock
    error_handler(pthread_mutex_unlock(&q_lock), m_unlock);
    //lock clerk_lock, sleep for serve time
    //then signal clerk that work is done, then unlock clerk_lock
    if(c1_serve == cur->id){
        test = pthread_mutex_lock(&c1_lock);
        usleep(cur->stime * 100000);
        test = pthread_cond_signal(&c1_convar);
        test = pthread_mutex_unlock(&c1_lock);
    }else if(c2_serve == cur->id){
        test = pthread_mutex_lock(&c2_lock);
        usleep(cur->stime * 100000);
        test = pthread_cond_signal(&c2_convar);
        test = pthread_mutex_unlock(&c2_lock);
    }else if(c3_serve == cur->id){
        test = pthread_mutex_lock(&c3_lock);
        usleep(cur->stime * 100000);
        test = pthread_cond_signal(&c3_convar);
        test = pthread_mutex_unlock(&c3_lock);
    }else{
        test = pthread_mutex_lock(&c4_lock);
        usleep(cur->stime * 100000);
        test = pthread_cond_signal(&c4_convar);
        test = pthread_mutex_unlock(&c4_lock);
    }
    error_handler(test, others);
    free(cur);
    pthread_exit(NULL);
}

/*
 *  clerk's thread function
 */
void *clerk_function(void *info){
    double cur_time = 0;
    int serving = 0;
    int test = 0;
    clerk *cur = (clerk *)info;
    for(;;){
        //lock sigal_lock
        error_handler(pthread_mutex_lock(&signal_lock), m_lock);
        while(business->size == 0 && economy->size == 0){
            //wait for customer's signal that queue is not empty
            error_handler(pthread_cond_wait(&signal_convar, &signal_lock), c_wait);
        }
        //unlock signal_lock
        error_handler(pthread_mutex_unlock(&signal_lock), m_unlock);
        //lock access_queue_lock
        error_handler(pthread_mutex_lock(&q_lock), m_lock);
        if(business->size > 0){
            //lock clerk_lock, use global variable to record the serving customer's id
            if(cur->id == 1){
                c1_serve = q_peak(business);
                test = pthread_mutex_lock(&c1_lock);
            }else if(cur->id == 2){
                c2_serve = q_peak(business);
                test = pthread_mutex_lock(&c2_lock);
            }else if(cur->id == 3){
                c3_serve = q_peak(business);
                test = pthread_mutex_lock(&c3_lock);
            }else{
                c4_serve = q_peak(business);
                test = pthread_mutex_lock(&c4_lock);
            }
            error_handler(test, m_lock);
            serving = q_peak(business);
            cur_time = get_time();
            printf("A clerk starts serving a customer: start time %.2f, the customer ID %2d, the clerk ID %1d. \n", cur_time - start_time, serving, cur->id);
            q_remove(business);
            //unlock access_queue_lock
            error_handler(pthread_mutex_unlock(&q_lock), m_unlock);
            //wake customer up
            error_handler(pthread_cond_broadcast(&bus_convar), c_broadcast);
        }else if(economy->size > 0 && business->size == 0){
            //lock clerk_lock, use global variable to record the serving customer's id
            if(cur->id == 1){
                c1_serve = q_peak(economy);
                test = pthread_mutex_lock(&c1_lock);
            }else if(cur->id == 2){
                c2_serve = q_peak(economy);
                test = pthread_mutex_lock(&c2_lock);
            }else if(cur->id == 3){
                c3_serve = q_peak(economy);
                test = pthread_mutex_lock(&c3_lock);
            }else{
                c4_serve = q_peak(economy);
                test = pthread_mutex_lock(&c4_lock);
            }
            error_handler(test, m_lock);
            serving = q_peak(economy);
            q_remove(economy);
            //unlock access_queue_lock
            error_handler(pthread_mutex_unlock(&q_lock), m_unlock);
            cur_time = get_time();
            printf("A clerk starts serving a customer: start time %.2f, the customer ID %2d, the clerk ID %1d. \n", cur_time - start_time, serving, cur->id);
            //wake customer up
            error_handler(pthread_cond_broadcast(&eco_convar), c_broadcast);
        }else{
            //if both queues are empty, unlock access_queue_lock
            pthread_mutex_unlock(&q_lock);
            continue;
        }
        //waiting customer's signal
        if(cur->id == 1){
            test = pthread_cond_wait(&c1_convar, &c1_lock);
        }else if(cur->id == 2){
            test = pthread_cond_wait(&c2_convar, &c2_lock);
        }else if(cur->id == 3){
            test = pthread_cond_wait(&c3_convar, &c3_lock);
        }else{
            test = pthread_cond_wait(&c4_convar, &c4_lock);
        }
        error_handler(test, c_wait);
        cur_time = get_time();
        printf("A clerk finish serving a customer: end time %.2f, the customer ID %2d, the clerk ID %1d. \n", cur_time - start_time, serving, cur->id);
        //finish serving customer, unlock clerk_lock
        if(cur->id == 1){
            test = pthread_mutex_unlock(&c1_lock);
        }else if(cur->id == 2){
            test = pthread_mutex_unlock(&c2_lock);
        }else if(cur->id == 3){
            test = pthread_mutex_unlock(&c3_lock);
        }else{
            test = pthread_mutex_unlock(&c4_lock);
        }
        error_handler(test, m_unlock);
    }
}

/*
 *  Build customer with their information
 */
customer *build_customer(char *line){
    customer *tmp = init_customer();
    char *ptr = strtok(line, ":");
    tmp->id = atoi(ptr);
    ptr = strtok(NULL, ",");
    tmp->class = atoi(ptr);
    if(tmp->class != 1 && tmp->class != 0){
        fprintf(stderr, "Error: Invalid class for customer %d\n", tmp->id);
        exit(EXIT_FAILURE);
    }
    ptr = strtok(NULL, ",");
    tmp->atime = atoi(ptr);
    ptr = strtok(NULL, "\n");
    tmp->stime = atoi(ptr);
    if(tmp->atime < 0 || tmp->stime < 0){
        fprintf(stderr, "Error: Invalid time for customer %d\n", tmp->id);
        exit(EXIT_FAILURE);
    }
    return tmp;
}

/*
 *  Build 4 clerks
 */
clerk **build_clerk(){
    clerk **tmp = (clerk **)emalloc(sizeof(clerk *) * 4);
    int i = 0;
    for(; i < 4; i++){
        tmp[i] = init_clerk(i+1);
    }
    return tmp;
}

/*
 *  Handling non-zero return value from other function.
 *  mode = 0: from pthread_mutex_lock
 *  mode = 1: from pthread_mutex_unlock
 *  mode = 2: from pthread_cond_wait
 *  mode = 3: from pthread_cond_broadcast
 *  mode = 4: from other function
 */
void error_handler(int test, int mode){
    if(test != 0){
        if(mode == 0){
            fprintf(stderr, "Error: fail to pthread_mutex_lock.\n");
        }else if(mode == 1){
            fprintf(stderr, "Error: fail to pthread_mutex_unlock.\n");
        }else if(mode == 2){
            fprintf(stderr, "Error: fail to pthread_cond_wait.\n");
        }else if(mode == 3){
            fprintf(stderr, "Error: fail to pthread_cond_broadcast.\n");
        }else{
            fprintf(stderr, "Error: return value is not 0.\n");
        }
        exit(EXIT_FAILURE);
    }
}

/*
 *  Add customer into queue
 */
queue *q_add(customer *ptr, queue *q){
     if(q->size == 0){
         q->head = ptr;
     }else{
         customer *tmp = q->head;
         while(tmp->next != NULL){
             tmp = tmp->next;
         }
         tmp->next = ptr;
     }
     q->size++;
     return q;
}

/*
 * Remove the first customer in the queue q
 */
queue *q_remove(queue *q){
    if(q->size == 0){
        fprintf(stderr, "Error: removing an empty queue");
        exit(EXIT_FAILURE);
    }else if(q->size == 1){
        q->head = init_customer();
        q->size = 0;
    }else{
        customer *tmp = q->head;
        q->head = tmp->next;
        q->size--;
    }
    return q;
}

/*
 *  Return current time
 */
double get_time(){
    struct timeval cur;
    gettimeofday(&cur,NULL);
    return (cur.tv_sec + (double)cur.tv_usec / 1000000);
}

/*
 *  Return the id of the peak of queue
 */
int q_peak(queue *q){
    if(q->size != 0)
        return q->head->id;
    else
        return -1;
}

/*
 * Initialize a queue
 */
queue *init_queue(){
    queue *tmp = (queue *)emalloc(sizeof(queue));
    tmp->head = init_customer();
    tmp->size = 0;
    tmp->totaltime = 0;
    return tmp;
}

/*
 *  Initialize a customer
 */
customer *init_customer(){
    customer *tmp = (customer *)emalloc(sizeof(customer));
    tmp->id = -1;
    tmp->next = NULL;
    return tmp;
}

/*
 *  Initialize clerk
 */
clerk *init_clerk(int theid){
    clerk *tmp = (clerk *)emalloc(sizeof(clerk));
    tmp->id = theid;
    return tmp;
}

/*
 *  An error handler for malloc
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
