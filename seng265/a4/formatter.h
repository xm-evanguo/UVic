/*
 * Name: Evan Guo
 * Student Number: V00866199
 * SENG 265 Summer 2018
 */
 
#ifndef _FORMATTER_H_
#define _FORMATTER_H_

#include <stdio.h>

typedef struct format format;
struct format{
    int LW;
    int FT;
    int LM;
    int LS;
    int wordCount;
    int max;
    int nLine;
};

size_t getline (char **, size_t *, FILE *);
char **format_file(FILE *);
char **format_lines(char **, int);
format *newFormat();
char **theMain(char *, char **);
int calculate(char *);
void formatting(char *);
char **noFormat(char *, char **);
char **print(char *, char **);
char **resetWordCount(char **);
char **printNewLine(char **);
char **printIndent(char **);
char **printSpace(char *, char **);
char **sizeCheck(char **);

#endif
