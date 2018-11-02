# Name: Evan Guo
# VNumber: V00866199
# SENG265 Summer 2018

import sys
import re

class UVroff:

	def __init__(self, file_name, input_list):
		self.file_name = file_name
		self.input_list = input_list
		self.wordCount = 0
		self.newLine_check = 0
		self.command = {'LW': 0, 'FT': 1, 'LM': 0, 'LS': 0}
		self.the_list = []
		self.string = ""
		self.line_counter = 0

	def get_lines(self):
		if self.file_name != None:
			if self.file_name == "stdin":
				self.read_stdin()
			else:
				self.read_file()
		elif self.input_list != None:
			self.read_list()
		else:
			self.the_list.append("Error: no input")
			return self.the_list
		if self.string != "":
			self.the_list.append(self.string)
			self.string = ""
		return self.the_list

	#read from list
	def read_list(self):
		for line in self.input_list:
			self.line_counter += 1
			self.the_main(line)

	#read from file
	def read_file(self):
		fileptr = open(self.file_name)
		for line in fileptr:
			self.line_counter += 1
			self.the_main(line)
	
	#read from stdin
	def read_stdin(self):
		for line in sys.stdin:
			self.line_counter += 1
			self.the_main(line)

	#main function from assignment 2
	def the_main(self, line):
		if self.newLine_check:
			self.print_newLine()
			self.newLine_check = 0
		if line[0] == ".":
			value = self.value_calculate(line)
			if re.match('^.LW', line):
				self.format_LW(line, value)
			elif re.match('^.FT', line):
				self.format_FT(line)
			elif re.match('^.LM', line):
				self.format_LM(line, value)
			elif re.match('^.LS', line):
				self.format_LS(line, value)
		elif line[0] == "\n":
			if self.wordCount:
				if self.string != "":
					self.the_list.append(self.string)
					self.string = ""
			self.print_newLine()
			self.reset_wordCount()
			self.newLine_check = 1;
		else:
			self.printing(line)
		self.err_handle()
		
	#return LW after change
	def format_LW(self, line, value):
		if re.match('.LW \+?\-?\d\d?$', line):
			if line[4] == "+":
				self.command['LW'] += value
			elif line[4] == "-":
				self.command['LW'] -= value
			else:
				self.command['LW'] = value
		else:
			self.command['LW'] = -1

	#return FT after change
	def format_FT(self, line):
		if re.match('.FT on$', line):
			self.command['FT'] = 1
		elif re.match('.FT off$', line):
			self.command['FT'] = 0
		else:
			self.command['FT'] = -1
	
	#return LM after change
	def format_LM(self, line, value):
		if re.match('.LM \+?\-?\d\d?$', line):
			if line[4] == "+":
				self.command['LM'] += value
			elif line[4] == "-":
				if self.command['LM'] - value > 0:
					self.command['LM'] -= value
				else:
					self.command['LM'] = 0
			else:
				self.command['LM'] = value
		else:
			self.command['LM'] = -1
			
	#return LS after change
	def format_LS(self, line, value):
		if re.match('.LS \+?\-?\d\d?$', line):
			if line[4] == "+":
				self.command['LS'] += value
			elif line[4] == "-":
				self.command['LS'] -= value
			else:
				self.command['LS'] = value
		else:
			self.command['LS'] = -1

	#calculate value for formatting
	def value_calculate(self, line):
		if line[2] == "T":
			if line[5] == "n":
				return 1
			elif line[5] == "f":
				return 0
			else:
				return "abc"
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
	def printing(self, line):
		if self.command['LW'] == 0:
			self.command['FT'] = 0
		if self.command['FT']:
			if self.wordCount == 0 and self.command['LM']:
				self.print_indent()
			line = line.strip()
			data = [word for word in line.split(" ") if len(word)]
			for word in data:
				self.print_space(word)
				self.wordCount += len(word)
				if self.wordCount > self.command['LW']:
					self.reset_wordCount()
					self.print_newLine()
					self.print_indent()
					self.string += word
				elif self.wordCount == self.command['LW']:
					self.string += word
					self.reset_wordCount()
					self.print_newLine()
					self.print_indent()
				else:
					self.string += word
		else:
			self.no_format(line)

	#reset the wordCount to 0 and print a new line
	def reset_wordCount(self):
		self.wordCount = 0
		self.the_list.append(self.string)
		self.string = ""

	#print linespacing
	def print_newLine(self):
		for x in range(self.command['LS']):
			self.the_list.append("")

	#print space between words
	def print_space(self, word):
		if self.wordCount and self.wordCount != self.command['LM']:
			if self.wordCount + len(word) + 1 <= self.command['LW']:
				self.string += " "
				self.wordCount += 1
			else:
				self.reset_wordCount()
				self.print_newLine()
				self.print_indent()

	#print indent
	def print_indent(self):
		for x in range(self.command['LM']):
			self.string += " "
		self.wordCount = self.command['LM']

	#print without formatting
	def no_format(self, line):
		line = line.split("\n")
		for x in line:
			self.string += x
		self.the_list.append(self.string)
		self.string = ""
		
	def err_handle(self):
		if self.command['LS'] < 0 or self.command['LS'] > 2:
			raise ValueError("value for LS on line " + str(self.line_counter) + " is not valid")
		elif self.command['LW'] < 0:
			raise ValueError("value for LW on line " + str(self.line_counter) + " is not valid")
		elif self.command['LM'] < 0:
			raise ValueError("value for LM on line " + str(self.line_counter) + " is not valid")
		elif self.command['FT'] is -1:
			raise ValueError("value for FT on line " + str(self.line_counter) + " is not valid")


		
