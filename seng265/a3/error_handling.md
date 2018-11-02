# Error handling for Assignmen3

The error that have been handled by uvroff_class.py are folloing:

## Not valid value

The commands LW, LM and LS normally follow by number. If there are something else, the program will report an error. For example, in e_input3.txt: 
```
.LW 30
.LS 1Here is a sentence with enough words to require some sort of word wrap. 
```
Since the LS follows by a stringt, the program will report an error like in e_output3.txt:
```
Error on line 2: value for LS is not valid
```
Another example is when the commands are followed by number, but the number is out of bound. Base on the description for assignment 2, LS should not be greater than 2, and LW and LS cannot be smaller than 0. Therefore, if LS follows by -1, like in e_input01.txt, the program will also report a value not valid error. 

## FT not follows by "on" or "off"

The command FT is speical. It follows by on or off. Anything else will report an error. In e_input2.txt:
```
.LW 30
.FT 1
While there    are enough characters   here to
fill
   at least one line, there is
plenty
of
            white space which will cause
a bit of confusion to the reader, yet
the .FT on command means  that
    the original formatting of
the lines
        must be preserved. In essence, the
command .LW is ignored.
```
The FT is followed by "1" in line 2. The program will report an error:
```
Error on line 2: FT must follow with on or off only
```
All of the error test files are in a3/tests
