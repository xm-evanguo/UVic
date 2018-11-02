/*
 * Name: Evan Guo
 * Student Number: V00866199
 * SENG 265 Summer 2018
 */

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "formatter.h"

#define DEFAULT_BUFLEN 80

const char *delimiters = " \n";
format *f;

//formatting input from file/stdin
char **format_file(FILE *infile) {
	char *line = NULL;
	size_t len = 0;
	size_t read;
	char **ptr = NULL;
	f = newFormat();
	while((read = getline(&line, &len, infile)) != -1){
		ptr = theMain(line, ptr);
	}
	if(f->nLine == 0 && f->wordCount == 0){
		free(f);
		return ptr;	
	}
	f->nLine += 1;
	ptr = sizeCheck(ptr);
	ptr[f->nLine] = NULL;
	free(line);
	free(f);
	return ptr;
}

//formatting input from array
char **format_lines(char **lines, int num_lines) {
	char **result = NULL;
	f = newFormat();
	f->max = num_lines;
	int i;
	for(i = 0; i < num_lines; i++){
		char *line = (char *)calloc(strlen(lines[i]), sizeof(char*));
		strncpy(line, lines[i], strlen(lines[i]));
		result = theMain(line, result);
		free(line);
	}
	free(f);
	return result;
}

//main
char **theMain(char* line, char** strptr){
	if(f->wordCount == -1){
		strptr = printNewLine(strptr);
		strptr = resetWordCount(strptr);
	}
	if(*line == 46){
		formatting(line+1);
	}else if(*line == 10){
		if(f->FT == 1 && f->LW != 0){
			if(f->wordCount != 0)
				strptr = resetWordCount(strptr);
			strptr = printNewLine(strptr);		
			f->wordCount = -1;
		}else{
			strptr = print(line, strptr);	
		}
	}else{
		if(strptr == NULL){
			strptr = (char **)calloc(f->max, sizeof(char**));
		}
		strptr = print(line, strptr);
	}
	return strptr;
}

//change the value of commands
void formatting(char *line){
	char *tmp = (char *)calloc(3, sizeof(char *));
	strncpy(tmp, line, 2);
	int value = calculate(line);
	if(strcmp(tmp, "LW") == 0){
		if((int)*(line+3) == 43){
			f->LW += value;
		}else if((int)*(line+3) == 45){
			f->LW -= value;
		}else{
			f->LW = value;
		}
	}else if(strcmp(tmp, "FT") == 0){
		if(value == 110){					//if the value is 'n' for on
			f->FT = 1;
		}else{								//if the value is 'f' for off
			f->FT = 0;
		}
	}else if(strcmp(tmp, "LM") == 0){
		if((int)*(line+3) == 43){
			f->LM += value;
		}else if((int)*(line+3) == 45){
			f->LM -= value;
		}else{
			f->LM = value;
		}
	}else if(strcmp(tmp, "LS") == 0){
		if((int)*(line+3) == 43){
			f->LS += value;
		}else if((int)*(line+3) == 45){
			f->LS -= value;
		}else{
			f->LS = value;
		}
	}
	free(tmp);
}

//calculate the value of format, return value
int calculate(char *line){
	int tmp = 0;
	int value = (int)*(line+3);		
	if(value < 48){
		value = (int)*(line+4) - 48;		
		tmp = (int)*(line+5) - 48;
		if(tmp >= 0 && tmp <= 9)
			value = value*10 + tmp;
	}else if(value == 111){
		value = (int)*(line+4);
	}else{
		value = value - 48;
		tmp = (int)*(line+4) - 48;	
		if(tmp >= 0 && tmp <= 9)		
			value = value*10 + tmp;	
	}
	return value;
}

//add string to array, return a char**
char **print(char *line, char **strptr){
	if(f->LW == 0 || f->FT == 0){
		strptr[f->nLine] = (char *)calloc(strlen(line), sizeof(char*));
		strptr = noFormat(line, strptr);
		return strptr;
	}
	char *word = NULL;
	if(strptr[f->nLine] == NULL){
			strptr[f->nLine] = (char *)calloc(f->LW+1, sizeof(char*));
	}
	if(f->wordCount == 0 && f->LM != 0){
		strptr = printIndent(strptr);
	}
	word = strtok(line, delimiters);
	if(word != NULL && f->wordCount != f->LM){
		strptr = printSpace(word, strptr);
	}
	while(word != NULL){
		f->wordCount += strlen(word);
		if(f->wordCount > f->LW){
			strptr = resetWordCount(strptr);
			strptr = printNewLine(strptr);
			strptr = printIndent(strptr);
			strcat(strptr[f->nLine], word);
			f->wordCount += strlen(word);
		}else if(f->wordCount == f->LW){
			strcat(strptr[f->nLine], word);
			strptr = resetWordCount(strptr);
			strptr = printNewLine(strptr);
			strptr = printIndent(strptr);
		}else{
			strcat(strptr[f->nLine], word);
		}
		word = strtok(NULL, delimiters);
		if(word != NULL && f->wordCount != f->LM){
			strptr = printSpace(word, strptr);
		}
	}
	return strptr;
}

//add string to array that doesn't require formatting
char **noFormat(char *line, char **strptr){
	f->wordCount = 0;
	if(*line == 10){
		strcat(strptr[f->nLine], "");
		f->nLine += 1;
		return strptr;
	}
	char* string = strtok(line, "\n");
	strncpy(strptr[f->nLine], string, strlen(string));
	f->nLine += 1;
	strptr = sizeCheck(strptr);
	return strptr;
}

//print newline
char **printNewLine(char **strptr){
	int i;
	for(i = 0; i < f->LS; i++){
		f->nLine += 1;
		strptr = sizeCheck(strptr);
		strptr[f->nLine] = (char *)calloc(f->LW+1, sizeof(char*));
	}	
	return strptr;
}

//print indent
char **printIndent(char **strptr){
	char* space = " ";
	int i;
	for(i = 0; i < f->LM; i++){
		strcat(strptr[f->nLine], space);	
	}
	f->wordCount = f->LM;
	return strptr;
}

//print space between word
char **printSpace(char *word, char **strptr){
	if(f->wordCount + strlen(word) + 1 <= f->LW){
		char* tmp = " ";
		strcat(strptr[f->nLine], tmp);
		f->wordCount += 1;
	}else{
		strptr = resetWordCount(strptr);
		strptr = printNewLine(strptr);
		strptr = printIndent(strptr);
	}
	return strptr;
}

//set word count to 0, add 1 to nLine
char **resetWordCount(char **strptr){
	f->wordCount = 0;
	f->nLine += 1;
	strptr = sizeCheck(strptr);
	strptr[f->nLine] = (char *)calloc(f->LW+1, sizeof(char*));
	return strptr;
}

//record format commands
format *newFormat(){
	format *tmp = (format *)malloc(sizeof(format));
	tmp->LW = 0;
	tmp->FT = 1;
	tmp->LM = 0;
	tmp->LS = 0;
	tmp->wordCount = 0;
	tmp->max = DEFAULT_BUFLEN;
	return tmp;
}

//check the size of the array. realloc if doesn't have enough size
char **sizeCheck(char **strptr){
	if(f->nLine > f->max - 2){
		char **tmpstrptr = realloc(strptr, sizeof(strptr) * (f->max+DEFAULT_BUFLEN));
		if(tmpstrptr){
			strptr = tmpstrptr;
			f->max += DEFAULT_BUFLEN;
		}else{
			printf("Error in sizeCheck: fail to realloc");
			exit(1);		
		}
	}
	return strptr;	
}

