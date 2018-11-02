Name: Evan Guo
V#: V00866199
CSC 360 Winter 2018

PMan is a program used to allow program(s) start in background while continue reading user input.

PMan has the following commands:
  bg <file name> [argument(s)]: PMan will execute the file with argument(s).
  bglist: PMan will return all the processes that are executing in the background.
  bgkill <pid>: PMan will terminate the process <pid>.
  bgstop <pid>: PMan will temporarily stop the process <pid>.
  bgstart <pid>: PMan will re-start the process <pid>.
  pstat <pid>: PMan will return the information of the process <pid>.

Helper program:
  The package also includes two helper programs, inf.c and args.c, which are provided by Dr.Kui Wu.

How to use PMan:
  Use "make clean" to delete the files "PMan", "inf", "args" and "*.exe" under current directory.
  Use "make" to compile PMan, inf and args.
  After compiling, entre "./PMan" to start the program.
