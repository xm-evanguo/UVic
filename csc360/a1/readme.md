# PMan
PMan is a command line tool in C which uses Linux system call to start, pause, resume, and terminate processes.

#### Commands

```
- bg <file name> [argument(s)]: PMan will execute the file with argument(s).
- bglist: PMan will return all the processes that are executing in the background.
- bgkill <pid>: PMan will terminate the process <pid>.
- bgstop <pid>: PMan will temporarily stop the process <pid>.
- bgstart <pid>: PMan will re-start the process <pid>.
- pstat <pid>: PMan will return the information of the process <pid>.
```

#### ~~Helper program:~~
~~The package also includes two helper programs, inf.c and args.c, which are provided by Dr.Kui Wu.~~

#### How to use PMan
  Use "make clean" to delete the files "PMan", "inf", "args" and "*.exe" under current directory.
  Use "make" to compile PMan, inf and args.
  After compiling, entre "./PMan" to start the program.
