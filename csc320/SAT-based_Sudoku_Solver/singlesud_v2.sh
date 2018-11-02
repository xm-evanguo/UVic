#!/bin/bash

set -e

echo
echo "-------- Extend 2: minimal encoding alternation --------"

echo
echo "Translate a sudoku to DIMACS format with alternated minimal encoding:"
echo "java sud2sat_v2 < $1"
java sud2sat_v2 < $1

# Run SAT solver
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
