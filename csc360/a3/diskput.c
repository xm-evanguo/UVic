/*
 *  Name: Evan Guo
 *  V#: V00866199
 *  CSC 360 Winter 2018
 */

#include <stdio.h>
#include <stdlib.h>
#include <stdint.h>
#include <string.h>
#include <ctype.h>
#include <sys/mman.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <unistd.h>
#include <time.h>
#include <fcntl.h>

char **pathtok(char *path);
void *emalloc(size_t size);
int finddirectory(char *blockptr, char **directory, int flc, int n);
char *getfilename(char **directory);
int freesizeofdisk(char *blockptr);
int fatpacking(char *blockptr, int entry);
void copyfile(char *blockptr, char *infileptr, int flc, int filesize, char *filename);
void fatunpacking(char *blockptr, int entry, int value);
int findfreesector(char *blockptr);
void directoryupdate(char *blockptr, int directoryflc, int fileflc, char *filename, int filesize, time_t filetime);

/*
 *  calculate the physical location of the entry
 */
int fatpacking(char *blockptr, int entry){
	int high;
	int low;
	if ((entry % 2) == 0) {
		high = blockptr[512 + ((3*entry) / 2) + 1]&0x0F;
		low = blockptr[512 + ((3*entry) / 2)]&0xFF;
		return (high << 8) + low;
	}else{
		high = blockptr[512 + (int)((3*entry) / 2)]&0xF0;
		low = blockptr[512 + (int)((3*entry) / 2) + 1]&0xFF;
		return (high >> 4) + (low << 4);
	}
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

/*
 *  return file name
 */
char *getfilename(char **directory){
	char **filename;
	char *prv;
    for(filename = directory; *filename != NULL; filename++){
		prv = *filename;
	}
	return prv;
}

/*
 *  find the directory
 *  if the directory array only contain 1 element, return flc
 *  if the directory exist, return the first logical cluster of that directory
 *  if the directory doesn't exist, print error infomation and exit
 */
int finddirectory(char *blockptr, char **directory, int flc, int n){
    if(directory[n+1] == NULL){
        return flc;
    }
    int i;
    char *name = (char *)emalloc(sizeof(char) * 9);
    char *tmpptr = blockptr + (flc + 31) * 512;
	while(tmpptr[0] != 0x00){
		if((tmpptr[11]&0x18) == 0x10 && tmpptr[0] != 0xE5){
            for(i = 0; i < 8; i++){
                if(tmpptr[i] == ' '){
                    break;
                }
                name[i] = tmpptr[i];
            }
            name[i] = '\0';
            if(strncmp(name, directory[n], strlen(directory[n])) == 0){
                flc = tmpptr[26]+(tmpptr[27]<<8);
                return(finddirectory(blockptr, directory, flc, n+1));
            }
		}
		tmpptr += 32;
	}
    return 0;
}

/*
 *  tokenize the given pathtok
 *  capitalize the directory name and keep the last element,
 *  which is the file name, in lowercase
 */
char **pathtok(char *path){
    char *tmppath = path;
    char **directory = (char **)emalloc(sizeof(char *)*10);
    int i, j;
    char *word = strtok(tmppath, "/");
    i = 0;
    while(word != NULL){
        j = 0;
        while(word[j]){
            word[j] = toupper(word[j]);
            j++;
        }
        directory[i] = word;
        i++;
        word = strtok(NULL, "/");
    }
    if(directory[0] == NULL){
        fprintf(stderr, "Usage: ./diskput <IMA file> <file path>");
        exit(EXIT_FAILURE);
    }
	j = 0;
	word = directory[i-1];
	while(word[j]){
		word[j] = tolower(word[j]);
		j++;
	}
	directory[i-1] = word;
    directory[i] = NULL;
    return directory;
}

/*
 *  calculate the free size of the disk and return
 */
int freesizeofdisk(char *blockptr){
    int bytespersector = blockptr[11] + (blockptr[12] << 8);
    int i;
    int freesector = 0;
    for(i = 2; i < 2849; i++){
        int entry = fatpacking(blockptr, i);
        if(entry == 0x00){
            freesector++;
        }
    }
    return freesector*bytespersector;
}

/*
 *  copy the input file data to disk
 */
void copyfile(char *blockptr, char *infileptr, int directoryflc, int filesize, char *filename){
	int entry, nextentry, i;
	int cur = 0;
	entry = findfreesector(blockptr);
	while(filesize > 0){
		char *tmpptr = blockptr + (entry + 31) * 512;
		for(i = 0; i< 512; i++){
			tmpptr[i] = infileptr[cur + i];
			filesize--;
			if(filesize == 0){
				fatunpacking(blockptr, entry, 0xFFF);
				break;
			}
		}
		cur += 512;
		fatunpacking(blockptr, entry, 0x69);
		nextentry = findfreesector(blockptr);
		fatunpacking(blockptr, entry, nextentry);
		entry = nextentry;
	}
}

/*
 *  set the FAT entry
 */
void fatunpacking(char *blockptr, int entry, int value){
	if((entry % 2) == 0){
		blockptr[512 + ((3*entry) / 2) + 1] = (value >> 8) & 0x0F;
		blockptr[512 + ((3*entry) / 2)] = value & 0xFF;
	}else{
		blockptr[512 + (int)((3*entry) / 2)] = (value << 4) & 0xF0;
		blockptr[512 + (int)((3*entry) / 2) + 1] = (value >> 4) & 0xFF;
	}
}

/*
 *  find the next free sector and return the entry of it
 */
int findfreesector(char *blockptr){
    int i;
    for(i = 2; i < 2849; i++){
        int entry = fatpacking(blockptr, i);
        if(entry == 0x00){
            return i;
        }
    }
	fprintf(stderr, "Error: Cannot find free sector.\n");
	exit(EXIT_FAILURE);
}

/*
 *  update the new file infomation at directory
 */
void directoryupdate(char *blockptr, int directoryflc, int fileflc, char *filename, int filesize, time_t filetime){
	char *tmpptr = blockptr + (directoryflc + 31) * 512 + 64;
	int i;
    struct tm *info;
    info = gmtime(&filetime);
	while(tmpptr[0] != 0x00 && tmpptr[0] != 0xE5){
		tmpptr += 32;
	}
	char *word = strtok(filename, ".");
	for(i = 0; i < strlen(word) && i < 8; i++){
		word[i] = toupper(word[i]);
		tmpptr[i] = word[i];
	}
	word = strtok(NULL, ".");
	for(i = 0; i < strlen(word) && i < 3; i++){
		word[i] = toupper(word[i]);
		tmpptr[i+8] = word[i];
	}
    tmpptr[14] = ((info->tm_min&0X07)<<5);
	tmpptr[15] = ((info->tm_min&0X38)>>3) + ((info->tm_hour&0X1F)<<3);
    tmpptr[16] = (info->tm_mday&0X1F) + (((info->tm_mon+1)&0X07)<<5);
    tmpptr[17] = (((info->tm_mon+1)&0X08)>>3) + (((info->tm_year-80)&0X7F)<<1);
    tmpptr[22] = tmpptr[14];
    tmpptr[23] = tmpptr[15];
    tmpptr[24] = tmpptr[16];
    tmpptr[25] = tmpptr[17];
    tmpptr[26] = (fileflc&0x0F);
	tmpptr[27] = ((fileflc&0xF0)>>8);
	tmpptr[28] = (filesize&0x000000FF);
	tmpptr[29] = ((filesize&0x0000FF00)>>8);
	tmpptr[30] = ((filesize&0x00FF0000)>>16);
	tmpptr[31] = ((filesize&0xFF000000)>>24);
}

int main(int argc, char *argv[]){
    char *blockptr;
    int fd, fd2, directoryflc, fileflc, freesize, filesize;
    struct stat sb, sb2;
	char **directory;
	if(argc < 3){
		fprintf(stderr, "Usage: ./diskput <disk image file> <file name>\n");
		exit(EXIT_FAILURE);
	}
    fd = open(argv[1], O_RDWR);
    if(fd < 0){
        fprintf(stderr, "File not found.\n");
        exit(EXIT_FAILURE);
    }
    fstat(fd, &sb);
    blockptr = mmap(NULL, sb.st_size, PROT_READ | PROT_WRITE, MAP_SHARED, fd, 0);
    directory = pathtok(argv[2]);
	char *filename = getfilename(directory);
	fd2 = open(filename, O_RDONLY);
	if(fd2 < 0){
		fprintf(stderr, "File not found.\n");
		munmap(blockptr, sb.st_size);
		close(fd2);
		exit(EXIT_FAILURE);
	}
	fstat(fd2, &sb2);
	directoryflc = -12;
    if(directory[1] != NULL){
        directoryflc = finddirectory(blockptr, directory, -12, 0);
        if(directoryflc == 0){
            fprintf(stderr, "The directory not found.\n");
				munmap(blockptr, sb.st_size);
			close(fd);
			close(fd2);
            exit(EXIT_FAILURE);
        }
    }
	freesize = freesizeofdisk(blockptr);
	filesize = sb2.st_size;
	if(freesize < filesize){
		fprintf(stderr, "No enough free space in the disk image.\n");
		munmap(blockptr, sb.st_size);
		close(fd);
		close(fd2);
		exit(EXIT_FAILURE);
	}
	char *infileptr = mmap(NULL, sb2.st_size, PROT_READ, MAP_SHARED, fd2, 0);
	fileflc = findfreesector(blockptr);
	copyfile(blockptr, infileptr, directoryflc, filesize, filename);
	directoryupdate(blockptr, directoryflc, fileflc, filename, filesize, sb2.st_mtime);
	munmap(blockptr, sb2.st_size);
	munmap(blockptr, sb.st_size);
    close(fd);
    close(fd2);
	return 0;
}
