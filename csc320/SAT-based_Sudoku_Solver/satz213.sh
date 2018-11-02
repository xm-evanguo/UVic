#!/bin/bash

set -e

echo
echo "-------- Extend 3: Another SAT solver --------"

echo
echo "Translate sudoku to DIMACS format:"
echo "java sud2sat < $1"
java sud2sat < $1

echo
echo "Solving SAT with satz213:"
echo "satz213/satz tests/$1"
echo
satz213/satz sud2satout.txt & wait

echo
echo "Translate output to sudoku: "
echo "java sat2sud < satz213/satx.sol"
echo
java sat2sud < satx.sol
