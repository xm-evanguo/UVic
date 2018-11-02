#!/bin/bash

printf "Input testing file: $(basename $1)\n"

mkdir -p tmp/$(basename $1).sudoku
split -d -e -l 1 $1 tmp/$(basename $1).sudoku/

mkdir -p output/$(basename $1).sols
for file in tmp/$(basename $1).sudoku/*; do
  printf "\rSolving sudoku #$(basename $file)"
  ./singlesud.sh $file > output/$(basename $1).sols/$(basename $file).sol
done

printf "\nAll sudoku solved, see output at out/$(basename $1).sol\n"
cat output/$(basename $1).sols/* > output/$(basename $1).sol
