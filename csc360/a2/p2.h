/*
 * Name: Evan Guo
 * Student Number: V00866199
 * CSC 360 Winter 2018
 */

 #ifndef _P2_H_
 #define _P2_H_

typedef struct customer customer;
struct customer{
    int id;
    int class;
    int atime;
    int stime;
    customer *next;
};

typedef struct queue queue;
struct queue{
    customer *head;
    int size;
    double totaltime;
};

typedef struct clerk clerk;
struct clerk{
    int id;
};

void error_handler(int test, int mode);
void *customer_function(void *info);
void *clerk_function(void *info);
customer *build_customer(char *line);
clerk **build_clerk();
queue *q_add(customer *ptr, queue *q);
queue *q_remove(queue *q);
int q_peak(queue *q);
queue *init_queue();
customer *init_customer();
clerk *init_clerk(int theid);
void *emalloc(size_t size);
double get_time();

#endif
