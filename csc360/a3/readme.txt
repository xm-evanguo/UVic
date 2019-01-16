Assignment 3 - csc360 winter 2018 
Evan Guo V00866199

How to compile
Use "make clean" to delete the files "diskinfo", "disklist", "diskget", "diskput", "*.o", and "*.exe" under current directory.
Then use "make" to compile diskinfo.c, disklist.c, diskget.c, diskput.c.

diskinfo.c
Entre "./diskinfo <disk image file>"
The program will print infromation of the disk image file.

disklist.c
Entre "./disklist <disk image file>"
The program will print all files and directories in the disk image file.

diskget.c
Entre "./diskget <disk image file> <file>"
The program will copy the file from disk image file to a new file under current linux directory

diskput.c
Entre "./diskput <disk image file> <file>"
The program will copy the file under current linux directory into the specified directroy in the disk image file.
