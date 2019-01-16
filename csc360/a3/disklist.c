/*
 *  Name: Evan Guo
 *  V#: V00866199
 *  CSC 360 Winter 2018
 */

#include <stdio.h>
#include <stdlib.h>
#include <stdint.h>
#include <string.h>
#include <sys/mman.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <fcntl.h>
#include <unistd.h>

void printinfo(char *tmpptr);
void findsub(char *blockptr, char *parentname);
char *renaming(char *blockptr, char *name);
char *root(char *blockptr);
void printroot(char *blockptr, char *parentname);
void printsub(char *blockptr, char* name, int flc);
void *emalloc(size_t size);

int max_length = 20;

/*
 *  An error handler for malloc
 */
void *emalloc(size_t size){
	void *ptr;
	ptr = malloc(size);
	if(ptr == NULL){
		fprintf(stderr, "Error: fail to malloc.\n");
		exit(EXIT_FAILURE);
	}
	return ptr;
}

/*
 *  Add current directory name after parent name
 *	Use to build full path
 */
char *renaming(char *blockptr, char *name){
	int i, tmp;
	tmp = strlen(name);
	while(tmp + 10 > max_length){
		name = (char *)realloc(name, max_length + 11);
		if(name == NULL){
			fprintf(stderr, "Error: fail to realloc.\n");
			exit(EXIT_FAILURE);
		}
		max_length += 11;
	}
	name[tmp] = '/';
	for(i = 0, tmp+=1; i < 8; i++, tmp++){
		if(blockptr[i] == ' '){
			name[tmp] = '\0';
			break;
		}
		name[tmp] = blockptr[i];
	}
	name[tmp+1] = '\0';
	return name;
}

/*
 *  return root name (disk label)
 */
char *root(char *blockptr){
    char *rootname = (char *)emalloc(sizeof(char)*21);
    int i;
    if(blockptr[43] == ' '){
        char *tmpptr = blockptr + 512 * 19;
        while(tmpptr[11] != 8){
            tmpptr += 32;
        }
        for(i = 0; i < 8; i++){
			if(tmpptr[i] == ' '){
				rootname[i] = '\0';
				break;
			}
            rootname[i] = tmpptr[i];
        }
    }else{
        for(i = 0; i < 8; i++){
			if(blockptr[i] == ' '){
				rootname[i] = '\0';
				break;
			}
            rootname[i] = blockptr[i+43];
        }
    }
    rootname[i+1] = '\0';
    return rootname;
}

/*
 *  print all the files and subdirectories under root
 */
void printroot(char *blockptr, char *parentname){
    char *tmpptr = blockptr + 512*19;
    while(tmpptr[0] != 0x00){
        if(tmpptr[0] != 0xE5 && tmpptr[11] != 0x0F && (tmpptr[11]&0x08) == 0){
			printinfo(tmpptr);
        }
        tmpptr += 32;
    }
	findsub(blockptr, parentname);
}

/*
 *  print all the files and subdirectories under current subdirectory
 */
void printsub(char *blockptr, char* name, int flc){
	int hassub = 0;
	printf("%s\n==================\n", name);
	char *tmpptr = blockptr + (flc + 31) * 512 + 64;
	while(tmpptr[0] != 0x00){
        if(tmpptr[0] != 0xE5 && tmpptr[11] != 0x0F && (tmpptr[11]&0x08) == 0){
			printinfo(tmpptr);
			if((tmpptr[11]&0x10) != 0){
				hassub = 1;
			}
        }
        tmpptr += 32;
    }
	if(hassub){
		tmpptr = blockptr + (flc + 31) * 512 + 64;
		findsub(tmpptr, name);
	}
}

/*
 *  print all the files and subdirectories under current subdirectory
 */
void findsub(char *blockptr, char *parentname){
	char *tmpptr = blockptr + 512*19;
	while(tmpptr[0] != 0x00){
		if((tmpptr[11]&0x10) != 0 && tmpptr[0] != 0xE5 && tmpptr[26] + (tmpptr[27]<<4) > 1){
			parentname = renaming(tmpptr, parentname);
			printsub(blockptr, parentname, tmpptr[26]+(tmpptr[27]<<8));
		}
		tmpptr += 32;
	}
}

/*
 *  print the infomation of the file/directory
 */
void printinfo(char *tmpptr){
	char *name = (char *)emalloc(sizeof(char) * 21);
	char *size = (char *)emalloc(sizeof(char) * 11);
	char type;
	int year, month, day, hour, min, i, j;
	for(i = 0; i < 8; i++){
		if(tmpptr[i] == ' '){
			break;
		}
		name[i] = tmpptr[i];
	}
	name[i] = '\0';
	if((tmpptr[11]&0x10) != 0){
		type = 'D';
		if(tmpptr[26] + (tmpptr[27]<<4) < 2){
			free(name);
			free(size);
			return;
		}
	}else{
		type = 'F';
		i = strlen(name);
		name[i] = '.';
		for(i+=1, j = 8; j < 11; i++, j++){
			name[i] = tmpptr[j];
		}
	}
	for(i = strlen(name); i < 20; i++){
		name[i] = ' ';
	}
	name[i] = '\0';

	sprintf(size, "%d", (tmpptr[28]&0xFF) + ((tmpptr[29]&0xFF)<<8) + ((tmpptr[30]&0xFF)<<16) + ((tmpptr[31]&0xFF)<<24));
	for(i = strlen(size); i < 10; i++){
		size[i] = ' ';
	}
	size[10] = '\0';
	year = ((tmpptr[17]&0xFE)>>1) + 1980;
	month = ((tmpptr[16]&0xE0)>>5) + ((tmpptr[17]&0x01)<<3);
	day = (tmpptr[16]&0x1F);
	hour = ((tmpptr[15]&0xF8)>>3);
	min = ((tmpptr[14]&0xE0)>>5) + ((tmpptr[15]&0x07)<<3);
	printf("%c %s %s %d-%02d-%02d %02d:%02d\n", type, size, name, year, month, day, hour, min);
	free(name);
	free(size);
}

int main(int argc, char *argv[]){
    char *blockptr;
    int fd;
    struct stat sb;
	if(argc < 2){
        fprintf(stderr, "Usage: ./disklist <disk image file>\n");
        exit(EXIT_FAILURE);
    }
    fd = open(argv[1], O_RDONLY);
	if(fd < 0){
        fprintf(stderr, "Error: fail to open file\n");
        exit(EXIT_FAILURE);
    }
    fstat(fd, &sb);

    blockptr = mmap(NULL, sb.st_size, PROT_WRITE, MAP_PRIVATE, fd, 0);
    char *rootname = root(blockptr);
    printf("%s\n", rootname);
    printf("==================\n");
    printroot(blockptr, rootname);
    free(rootname);
	munmap(blockptr, sb.st_size);
	close(fd);
    return 0;
}
