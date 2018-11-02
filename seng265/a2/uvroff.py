#!/usr/bin/env python

# Name: Evan Guo
# VNumber: V00866199
# SENG265 Summer 2018

import sys
import fileinput
import string

#main program
def main():
	global wordCount
	LW = 0
	FT = 1
	LM = 0
	LS = 0
	newLine_check = 0
	for line in fileinput.input():
		if newLine_check:
			print_newLine(LS)
			newLine_check = 0
		if line[0] == ".":
			value = value_calculate(line)
			if line[2] == "W":
				LW = format_LW(line, value, LW)
			elif line[2] == "T":
				FT = value
			elif line[2] == "M":
				LM = format_LM(line, value, LM)
			elif line[2] == "S":
				LS = format_LS(line, value, LS)
		elif line[0] == "\n":
			if wordCount:
				print()
			print_newLine(LS)
			reset_wordCount()
			newLine_check = 1;
		else:
			printing(line, LW, FT, LM, LS)
	if newLine_check != 1:
		print()

#return LW after change
def format_LW(line, value, LW):
	if line[4] == "+":
		LW += value
	elif line[4] == "-":
		if LW - value > 0:
			LW -= value
		else:
			LW = 0
	else:
		LW = value
	return LW

#return LM after change
def format_LM(line, value, LM):
	if line[4] == "+":
		LM += value
	elif line[4] == "-":
		if LM - value > 0:
			LM -= value
		else:
			LM = 0
	else:
		LM = value
	return LM

#return LS after change
def format_LS(line, value, LS):
	if line[4] == "+":
		LS += value
	elif line[4] == "-":
		if LS-value > 0:
			LS -= value
		else:
			LS = 0
	else:
		LS = value
	return LS

#calculate value for formatting
def value_calculate(line):
	if line[2] == "T":
		if line[5] == "n":
			return 1
		else:
			return 0
	if line[4].isdigit():
		value = int(line[4])
		if line[5].isdigit():
			value = value * 10 + int(line[5])
	elif line[4] == "+" or line[4] == "-":
		value = int(line[5])
		if line[6].isdigit():
			value = value * 10 + int(line[6])
	return value

#print output
def printing(line, LW, FT, LM, LS):
	global wordCount
	if LW == 0:
		FT = 0
	if FT:
		if wordCount == 0 and LM:
			print_indent(LM)
		line = line.strip();
		data = [word for word in line.split(" ") if len(word)]
		for word in data:
			print_space(word, LW, LM, LS)
			wordCount += len(word)
			if wordCount > LW:
				reset_wordCount()
				print_newLine(LS)
				print_indent(LM)
				print(word, end='')
			elif wordCount == LW:
				print(word, end='')
				reset_wordCount()
				print_newLine(LS)
				print_indent(LM)
			else:
				print(word, end='')
	else:
		no_format(line)	

#reset the wordCount to 0 and print a new line
def reset_wordCount():
	global wordCount
	wordCount = 0
	print()	

#print linespacing
def print_newLine(LS):
	for x in range(LS):
		print()

#print space between words
def print_space(word, LW, LM, LS):
	global wordCount
	if wordCount and wordCount != LM:
		if wordCount + len(word) + 1 <= LW:
			print(" ", end = '')
			wordCount+=1
		else:
			reset_wordCount()
			print_newLine(LS)
			print_indent(LM)

#print indent
def print_indent(LM):
	global wordCount
	for x in range(LM):
		print(" ", end='')
	wordCount = LM

#print without formatting
def no_format(line):
	global wordCount
	line = line.split("\n")
	if wordCount:
		print()
	wordCount = 1
	for x in line:
		print(x, end='')

if __name__ == '__main__':
	wordCount = 0
	main()
