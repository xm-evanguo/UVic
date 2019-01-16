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
#include <fcntl.h>

int findsub(char *blockptr, char *filename);
void findlocation(char *blockptr, char *filename);
int searchsub(char *blockptr, char *filename, int flc);
void filecopy(char *blockptr, char *tmpptr, char *filename);
int fatpacking(char *blockptr, int entry);
void *emalloc(size_t size);

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
 *  find the location of given file name under root directory
 */
void findlocation(char *blockptr, char *filename){
    char *name = (char *)emalloc(sizeof(char) * 9);
    char *tmpptr = blockptr + 512 * 19;
    int i, hassub, j;
    hassub = 0;
    while(tmpptr[0] != 0x00){
        if((tmpptr[0]&0x10) != 0)
            hassub = 1;
        if(tmpptr[0] != 0xE5 && (tmpptr[11]&0x18) == 0 && tmpptr[11] != 0x0F){
            for(i = 0; i < 8; i++){
        		if(tmpptr[i] == ' ')
        			break;
        		name[i] = tmpptr[i];
        	}
            name[i] = '.';
    		for(i+=1, j = 8; j < 11; i++, j++){
                if(tmpptr[j] == ' ')
        			break;
    			name[i] = tmpptr[j];
    		}
            name[i] = '\0';
			char *uppername = (char *)emalloc(sizeof(char) * strlen(filename) + 1);
			i = 0;
			while(filename[i]){
		        uppername[i] = toupper(filename[i]);
		        i++;
		    }
            if(strncmp(name, uppername, strlen(name)) == 0){
                filecopy(blockptr, tmpptr, filename);
                return;
            }
        }
        tmpptr+= 32;
    }
    if(hassub){
        if(findsub(blockptr, filename)){
            return;
        }
    }
    fprintf(stderr, "File not found\n");
    exit(EXIT_FAILURE);
}

/*
 *  find if there is subdirectory
 *	if there is, pass the location to searchsub()
 */
int findsub(char *blockptr, char *filename){
	char *tmpptr = blockptr + 512*19;
	while(tmpptr[0] != 0x00){
		if((tmpptr[11]&0x18) == 0x10 && tmpptr[0] != 0xE5){
			if(searchsub(blockptr, filename, tmpptr[26]+(tmpptr[27]<<8))){
                return 1;
            }
		}
		tmpptr += 32;
	}
    return 0;
}

/*
 *  find the location of given file name under current directory
 */
int searchsub(char *blockptr, char *filename, int flc){
    char *tmpptr = blockptr + (flc + 31) * 512 + 64;
    char *name = (char *)emalloc(sizeof(char) * 9);
    int i, hassub, j;
    hassub = 0;
    while(tmpptr[0] != 0x00){
        if((tmpptr[0]&0x10) != 0)
            hassub = 1;
        if(tmpptr[0] != 0xE5 && (tmpptr[11]&0x18) == 0 && tmpptr[11] != 0x0F){
            for(i = 0; i < 8; i++){
        		if(tmpptr[i] == ' ')
        			break;
        		name[i] = tmpptr[i];
        	}
            name[i] = '.';
    		for(i+=1, j = 8; j < 11; i++, j++){
                if(tmpptr[j] == ' ')
        			break;
    			name[i] = tmpptr[j];
    		}
            name[i] = '\0';
			i = 0;
			char *uppername = (char *)emalloc(sizeof(char) * strlen(filename) + 1);
			while(filename[i]){
		        uppername[i] = toupper(filename[i]);
		        i++;
		    }
            if(strncmp(name, uppername, strlen(name)) == 0){
                filecopy(blockptr, tmpptr, filename);
                return 1;
            }
        }
        tmpptr+= 32;
    }
    if(hassub){
        if(findsub(blockptr, filename)){
            return 1;
        }
    }
    return 0;
}

/*
 *  copy file data in the disk to the new created file
 */
void filecopy(char *blockptr, char *tmpptr, char *filename){
    int i, size, entry, fd, cur;
    entry = (tmpptr[26]&0xFF) + (tmpptr[27]<<8);
    size = (tmpptr[28]&0xFF) + ((tmpptr[29]&0xFF)<<8) + ((tmpptr[30]&0xFF)<<16) + ((tmpptr[31]&0xFF)<<24);
    fd = open(filename, O_RDWR | O_CREAT, 0666);
	if(fd < 0){
		fprintf(stderr, "Error: fail to create output file.\n");
		exit(EXIT_FAILURE);
	}
    if(lseek(fd, size-1, SEEK_SET) == -1){
		fprintf(stderr, "Error: fail to seek the output file.\n");
		close(fd);
		exit(EXIT_FAILURE);
	}
	if(write(fd, "", 1) == -1){
		fprintf(stderr, "Error: fail to write the output file.\n");
		close(fd);
		exit(EXIT_FAILURE);
	}
    char *outfileptr = mmap(NULL, size, PROT_WRITE, MAP_SHARED, fd, 0);
    cur = 0;
    while(entry != 0xFFF && size > 0){
        tmpptr = blockptr + (entry + 31) * 512;
        for(i = 0; i < 512; i++){
            outfileptr[cur + i] = tmpptr[i];
            size--;
            if(size == 0){
                break;
            }
        }
        cur += 512;
        entry = fatpacking(blockptr, entry);
    }
	munmap(outfileptr, size);
}

int main(int argc, char *argv[]){
    char *blockptr;
    int fd;
    struct stat sb;
	if(argc < 3){
        fprintf(stderr, "Usage: ./diskget <disk image file> <file name>\n");
		close(fd);
        exit(EXIT_FAILURE);
    }
    fd = open(argv[1], O_RDONLY);
    if(fd < 0){
        fprintf(stderr, "Error: fail to open file\n");
		close(fd);
        exit(EXIT_FAILURE);
    }
    fstat(fd, &sb);
    blockptr = mmap(NULL, sb.st_size, PROT_WRITE, MAP_PRIVATE, fd, 0);
    findlocation(blockptr, argv[2]);
	munmap(blockptr, sb.st_size);
	close(fd);
    return 0;
}
