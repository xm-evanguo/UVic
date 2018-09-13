# SAT sudoku

A SAT solver for sudoku puzzles
### Team Members

| Name | Student # |
|------|-----------|
| Evan(Ximing) Guo | V00866199 |
| Zijian Chen | V00867494 |
| Shuwen Li |  V00024025 |
| Wendy(Xiaole) Tan | V00868734 |

# Final Report include
- Final report includes basic 50 test cases and top 95 test cases and the final result with time comprehension analysis

# Getting started

 #### Requirements
- Ubuntu 18.04 LTS 64bit
- Java (version 1.8.0)
- gcc (version 4.8.5)
- Minisat (sudo apt install minisat -y)

 #### Input and Output
- The results will be placed in the ```output/``` directory.
- All tests file are in the ```tests/``` file 

# Makefile
#### Make clean
First to delete all class files, all text files, all .sol files, timetable, output and tmp folders
```sh
make clean
```
#### Make target
To compile java and c files before run the code
```sh
make target
```

# Basic 50 tests 
To solve basic tests you can use the ```makefile``` file
 ```sh
 make basic
 ```
Once you do ```make basic``` you will get all 50 solusions in one text file at ```output/basic50.txt.sol```, and 95 text files for each sudoku solution at ```output/basic50.txt.sols```
#### Relative files: 
- tests/basic50.txt
- sud2sat.java
- sat2sud.java

# Top 95 tests 
To solve advence tests from a file you can use the ```makefile``` file
```sh
make extend1
```
Once you do ```make extend1``` you will get all 95 solusions in one text file at ```output/top95.txt.sol```, and 95 text files for each sudoku solution at ```output/top95.txt.sols```
#### Relative files: 
- tests/top95.txt
- sud2sat.java
- sat2sud.java

# Changed minimal encoding
To solve minimal encoding from a file you can use the ```makefile``` file
```sh
make extend2
```
The ```make extend2``` is a advence test case of changed minimal encoding and the output will be showed by stdout.
#### Relative files: 
- tests/easy.txt
- sud2sat_v2.java
- sat2sud.java

# Another SATsolver
To solve the problem use another SATsolover from the www.cs.ubc.ca/˜hoos/SATLIB/index-ubc.html by ```(Satz213 (new version) (contributed by Chu-Min Li))``` modify by group member (Evan Guo) edited some errors.
- Changed CLK_TCK to CLOCKS_PER_SEC
```sh
make extend3
```
The make extend3 is use another SATsolver compile and result will be showed by stdout, and they will be timetable record all history.
#### Relative files: 
- tests/easy.txt
- satz213/satz.c
- sat2sud.java

#### Compile the tests
If you'd like to manually run a translator you can perform the actions in ```makefile``` by hand(eg. make testbasic, extend1...).

```bash
# To clean up all the javac files and txt files
make clean
# To make targer to create the javac files
make target
# To compile the basic 50 tests
make basic
# To compile the top 95 tests
make extend1
# To compile the chanded minial edcoding
make extend2
# To compile the Another SATsolver
make extend3
```

# Todo       

#### Basic
 * [x] Write translator
 * [x] Finish basic 50 tests
 * [x] Write report
 * [x] Created Makefile

#### Advanced

 * [x] Finish top 95 tests
 * [x] Finish the minimal encoding
 * [x] Finish use Another SATsolver
 * [x] Try at least one alternate to the minimal encoding
