#!/bin/bash

set -e

echo
echo "Translate a single sudoku to DIMACS formula:"
echo "java sud2sat < $1"
java sud2sat < $1

echo
echo "Solving SAT with minisat"
echo "minisat sud2satout.txt sat2sud.txt"
echo
minisat sud2satout.txt sat2sud.txt & wait

# Print solved sudoku
echo
echo "Translate output to sudoku"
echo "java sat2sud < sat2sud.txt"
echo
java sat2sud < sat2sud.txt
