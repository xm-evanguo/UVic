/*
 * UVic SENG 265, Summer 2018, A#4
 *
 * This will contain a solution to uvroff2.c. In order to complete the
 * task of formatting a file, it must open the file and pass the result
 * to a routine in formatter.c.
 */
#include <assert.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "formatter.h"

FILE *input;

//Needs to open the file, then pass to formatter.c via format_file If *not* a file, count lines, then pass to formatter.c via format_lines


int main(int argc, char *argv[]) {
	input = fopen(argv[1], "r");
	char** strptr = NULL;
	if(argc == 1){
		strptr = format_file(stdin);	
	}else if (input == NULL) {
		char* my_string = (char*) malloc (sizeof(char)*argc);
		printf("argc = %d\n", argc);
		for (int j = 1; j < argc; j++){
			my_string[j-1] = *argv[j];
		}
		char* super_string = my_string;
		free(my_string);
		strptr = format_lines((char**)super_string, argc);
	}else{
		strptr = format_file(input);
	}
	
	if(strptr != NULL){
		char **printing = NULL;
		for(printing = strptr; *printing != NULL; printing++){
			printf("%s\n", *printing);	
		}
	}
	
	exit(0);
}
