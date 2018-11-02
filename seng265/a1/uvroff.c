/*
 * Name: Evan Guo
 * Student Number: V00866199
 * SENG 265 Summer 2018
 */

#include <ctype.h>
#include <stdio.h>
#include <string.h>
#include <stdlib.h>

#define BUFFER_SIZE 134 //132 + 2

int LW = 0;				//record LW
int FT = 1;				//record FT
int LM = 0;				//record LM
int LS = 0;				//record LS
int wordCount = 0;			//record wordCount
const char* delimiters = " \n";		//use for tokenize

void format(char* p, int v);		//use to record LW, FT, LM and LS
void print(char* line);			//use to print contents after formatting
void resetWordCount();			//set wordCount to 0, and print out new line
void origin(char* line);		//if the contents don't need formatting, print them out by origin
void printSpace();			//print out space when LM is not 0
void printNewLine();			//print out new line when LS is not 0

int main()
{
	char buffer[BUFFER_SIZE];				
	int newLineCheck = 0;					//use to check if the last print content is a new line 
	while (fgets(buffer, BUFFER_SIZE - 1, stdin) > 0){	//read stdin line by line
		if(newLineCheck == 1){				
			printNewLine();
			newLineCheck = 0;
		}
		char* pointer = buffer;
		if(*pointer == 46){				//if the first char is '.', record the value after it
			int value = (int)*(pointer+4) - 48;		//record the value
			if(value > 9){					//if is LT, the value for 'o' will be greater than 9
				value = (int)*(pointer+5);		//then record the value for next char
			}else{
				int tmp = (int)*(pointer+5) - 48;	//record the second value
				if(tmp >= 0 && tmp <= 9)		//if the second value isn't a number, ignore it
					value = value*10 + tmp;		//otherwise, record it in value
			}
			format(pointer+1, value);			//record all the format
		}else if(*pointer == 10){			//if the first char is '\n'
			printf("\n");						
			printNewLine();
			resetWordCount();
			newLineCheck = 1;
		}else if(FT == 1){				//if the first char isn't '\n' or '.', is text
			if(LW != 0 || LM != 0 || LS != 0){		//if one of them aren't 0, print with formatting
				print(pointer);
			}else{			
				origin(pointer);			//otherwise, print without formatting
			}
		}else{						//print without formatting if FT == 0
			origin(pointer);		
		}
	}
	if(newLineCheck != 1)
		printf("\n");
	return 0;
}

void format(char* p, int v){
	char tmp[3];				//use to store the second and third char
	strncpy(tmp,p,2);			
	if(strcmp(tmp, "LW") == 0){		
		LW = v;
	}else if(strcmp(tmp, "FT") == 0){	//if the string = "FT"
		if(v == 110){				//if the value is 'n' for on
			FT = 1;
		}else{					//if the value is 'f' for off
			FT = 0;			
		}
	}else if(strcmp(tmp, "LM") == 0){
		LM = v;
	}else if(strcmp(tmp, "LS") == 0){
		LS = v;
	}
}

void print(char* line){
	if(wordCount == 0 && LM != 0){	
		printSpace();
	}
	char* word = strtok(line, delimiters);	
	if(wordCount != LM){
		if(wordCount + 1 + strlen(word) <= LW){ 	//if current wordCount + length of word + 1 < LW
			wordCount++;				//print space
			printf(" ");
		}else{
			resetWordCount();			//otherwise, print new line
			printNewLine();
			printSpace();
		}
	}
	while(word != NULL){
		wordCount += strlen(word);		
		if(wordCount > LW){				//if current wordCount + length of word > LW
			resetWordCount();				//print new line before print word
			printNewLine();
			printSpace();
			printf("%s", word);				
			wordCount = wordCount + strlen(word);		
		}else if(wordCount == LW){			//if current wordCount + length of word = LW
			printf("%s", word);				//print word and then new line
			resetWordCount();
			printNewLine();
			printSpace();
		}else{						//otherwise, print word
			printf("%s", word);			
		}
		word = strtok(NULL, delimiters);
		if(word != NULL && wordCount != LM){		//print space or not
			if(wordCount + strlen(word) + 1 <= LW){
				printf(" ");
				wordCount++;
			}else{
				resetWordCount();
				printNewLine();
				printSpace();
			}
		}
	}
}

void resetWordCount(){
	wordCount = 0;
	printf("\n");
}

void origin(char* line){
	if(wordCount != 0){		//if isn't first line, print new line
		printf("\n");
	}
	wordCount = 1;
	line = strtok(line, "\n");
	printf("%s", line);
}

void printSpace(){
	int i = LM;
	for(;i > 0; i--){		//print LM space
		printf(" ");
	}
	wordCount = LM;
}

void printNewLine(){
	int tmp = 0;
	for(;tmp < LS; tmp++){		//print LS new line
		printf("\n");
	}
}


