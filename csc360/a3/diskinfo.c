/*
 *  Name: Evan Guo
 *  V#: V00866199
 *  CSC 360 Winter 2018
 */

#include <stdio.h>
#include <stdlib.h>
#include <stdint.h>
#include <sys/mman.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <fcntl.h>
#include <unistd.h>

void OSname(char *blockptr);
void disklabel(char *blockptr);
void sizeofdisk(char *blockptr);
int subdirect(char *blockptr, int flc);
void numoffile(char *blockptr);
void numoffat(char *blockptr);

/*
 *  print OSname
 */
void OSname(char *blockptr){
    int i;
    printf("OS Name: ");
    for(i = 3; i < 9; i++){
        printf("%c", blockptr[i]);
    }
    printf("\n");
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
 *  print disk label
 */
void disklabel(char *blockptr){
    int i;
    printf("Label of the disk: ");
    if(blockptr[43] == ' '){
        char *tmpptr = blockptr + 512 * 19;
        while(tmpptr[11] != 8){
            tmpptr += 32;
        }
        for(i = 0; i < 8; i++){
            printf("%c", tmpptr[i]);
        }
    }else{
        for(i = 43; i < 51; i++){
            printf("%c", blockptr[i]);
        }
    }
    printf("\n");
}

/*
 *  print the total size of the disk and the free size of the disk
 */
void sizeofdisk(char *blockptr){
    int totalsector = blockptr[19] + (blockptr[20] << 8);
    int bytespersector = blockptr[11] + (blockptr[12] << 8);
    printf("Total size of the disk: %d bytes\n", totalsector*bytespersector);
    int i;
    int freesector = 0;
    for(i = 2; i < 2849; i++){
        int entry = fatpacking(blockptr, i);
        if(entry == 0x00){
            freesector++;
        }
    }
    printf("Free size of the disk: %d bytes\n", freesector*bytespersector);
}

/*
 *  calculate the number of files in subdirectory
 */
int subdirect(char *blockptr, int flc){
    char *tmpptr = blockptr + (flc + 31) * 512 + 64;
    int nfile = 0;
    for(;;){
        if(tmpptr[0] == 0x00) break;
        if(tmpptr[0] != 0xE5 && (tmpptr[11]&0x18) == 0 && tmpptr[11] != 0x0F){
            nfile++;
        }else if(tmpptr[11]&&0x08 != 0){
            nfile += subdirect(blockptr, tmpptr[26]+(tmpptr[27]<<8));
        }
        tmpptr += 32;
    }
    return nfile;
}

/*
 *  print the number of files in the disk
 */
void numoffile(char *blockptr){
    char *tmpptr = blockptr + 512 * 19;
    int i;
    int nfile = 0;
    for(i = 19*16; i < 33*16; i++){
        if(tmpptr[0] == 0x00) break;
        if(tmpptr[0] != 0xE5 && (tmpptr[11]&0x18) == 0 && tmpptr[11] != 0x0F){
            nfile++;
        }else if(tmpptr[11]&&0x08 != 0){
            nfile += subdirect(blockptr, tmpptr[26] + (tmpptr[27]<<8));
        }
        tmpptr += 32;
    }
    printf("The number of files in the disk (including all files in the root directory and files in all subdirectories): %d\n", nfile);
}

/*
 *  print the number of FAT copies and secotrs per FAT
 */
void numoffat(char *blockptr){
    printf("Number of FAT copies: %d\n", blockptr[16]);
    printf("Sectors per FAT: %d\n", blockptr[22] + (blockptr[23]<<8));
}

int main(int argc, char *argv[]){
    char *blockptr;
    int fd;
    struct stat sb;
    if(argc < 2){
        fprintf(stderr, "Usage: ./diskinfo <disk image file>\n");
        exit(EXIT_FAILURE);
    }
    fd = open(argv[1], O_RDONLY);
    if(fd < 0){
        fprintf(stderr, "Error: fail to open file.\n");
        exit(EXIT_FAILURE);
    }
    fstat(fd, &sb);

    blockptr = mmap(NULL, sb.st_size, PROT_WRITE, MAP_PRIVATE, fd, 0);

    OSname(blockptr);
    disklabel(blockptr);
    sizeofdisk(blockptr);
    printf("\n==============\n");
    numoffile(blockptr);
    printf("\n=============\n");
    numoffat(blockptr);
    munmap(blockptr, sb.st_size);
    close(fd);
    return 0;
}
