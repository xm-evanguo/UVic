; a3part4.asm
; CSC 230: Spring 2018
;
; Student name: Evan Guo
; Student ID: V00866199
; Date of completed work: March 26, 2018
;
; *******************************
; Code provided for Assignment #3
;
; Author: Mike Zastre (2018-Mar-08)
; 
; This skeleton of an assembly-language program is provided to help you
; begin with the programming tasks for A#3. As with A#2, there are 
; "DO NOT TOUCH" sections. You are *not* to modify the lines
; within these sections. The only exceptions are for specific
; changes announced on conneX or in written permission from the course
; instructor. *** Unapproved changes could result in incorrect code
; execution during assignment evaluation, along with an assignment grade
; of zero. ****
;
; I have added for this assignment an additional kind of section
; called "TOUCH CAREFULLY". The intention here is that one or two
; constants can be changed in such a section -- this will be needed
; as you try to test your code on different messages.
;


; =============================================
; ==== BEGINNING OF "DO NOT TOUCH" SECTION ====
; =============================================
;
; In this "DO NOT TOUCH" section are:
;
; (1) assembler directives setting up the interrupt-vector table
;
; (2) "includes" for the LCD display
;
; (3) some definitions of constants we can use later in the
;     program
;
; (4) code for initial setup of the Analog Digital Converter (in the
;     same manner in which it was set up for Lab #4)
;     
; (5) code for setting up our three timers (timer1, timer3, timer4)
;
; After all this initial code, your own solution's code may start.
;

.cseg
.org 0
	jmp reset

; location in vector table for TIMER1 COMPA
;
.org 0x22
	jmp timer1

; location in vector table for TIMER4 COMPA
;
.org 0x54
	jmp timer4

.include "m2560def.inc"
.include "lcd_function_defs.inc"
.include "lcd_function_code.asm"

.cseg

; These two constants can help given what is required by the
; assignment.
;
#define MAX_PATTERN_LENGTH 10
#define BAR_LENGTH 6

; All of these delays are in seconds
;
#define DELAY1 0.5
#define DELAY3 0.1
#define DELAY4 0.01


; The following lines are executed at assembly time -- their
; whole purpose is to compute the counter values that will later
; be stored into the appropriate Output Compare registers during
; timer setup.
;

#define CLOCK 16.0e6 
.equ PRESCALE_DIV=1024  ; implies CS[2:0] is 0b101
.equ TOP1=int(0.5+(CLOCK/PRESCALE_DIV*DELAY1))

.if TOP1>65535
.error "TOP1 is out of range"
.endif

.equ TOP3=int(0.5+(CLOCK/PRESCALE_DIV*DELAY3))
.if TOP3>65535
.error "TOP3 is out of range"
.endif

.equ TOP4=int(0.5+(CLOCK/PRESCALE_DIV*DELAY4))
.if TOP4>65535
.error "TOP4 is out of range"
.endif


reset:
	; initialize the ADC converter (which is neeeded
	; to read buttons on shield). Note that we'll
	; use the interrupt handler for timer4 to
	; read the buttons (i.e., every 10 ms)
	;
	ldi temp, (1 << ADEN) | (1 << ADPS2) | (1 << ADPS1) | (1 << ADPS0)
	sts ADCSRA, temp
	ldi temp, (1 << REFS0)
	sts ADMUX, r16


	; timer1 is for the heartbeat -- i.e., part (1)
	;
    ldi r16, high(TOP1)
    sts OCR1AH, r16
    ldi r16, low(TOP1)
    sts OCR1AL, r16
    ldi r16, 0
    sts TCCR1A, r16
    ldi r16, (1 << WGM12) | (1 << CS12) | (1 << CS10)
    sts TCCR1B, temp
	ldi r16, (1 << OCIE1A)
	sts TIMSK1, r16

	; timer3 is for the LCD display updates -- needed for all parts
	;
    ldi r16, high(TOP3)
    sts OCR3AH, r16
    ldi r16, low(TOP3)
    sts OCR3AL, r16
    ldi r16, 0
    sts TCCR3A, r16
    ldi r16, (1 << WGM32) | (1 << CS32) | (1 << CS30)
    sts TCCR3B, temp

	; timer4 is for reading buttons at 10ms intervals -- i.e., part (2)
    ; and part (3)
	;
    ldi r16, high(TOP4)
    sts OCR4AH, r16
    ldi r16, low(TOP4)
    sts OCR4AL, r16
    ldi r16, 0
    sts TCCR4A, r16
    ldi r16, (1 << WGM42) | (1 << CS42) | (1 << CS40)
    sts TCCR4B, temp
	ldi r16, (1 << OCIE4A)
	sts TIMSK4, r16

    ; flip the switch -- i.e., enable the interrupts
    sei

; =======================================
; ==== END OF "DO NOT TOUCH" SECTION ====
; =======================================


; *********************************************
; **** BEGINNING OF "STUDENT CODE" SECTION **** 
; *********************************************

start:
	rcall lcd_init
	
	;initialize part 1
	ldi r16, 1
	sts PULSE, r16

	;initialize part 2 and 4
	ldi r16, 0
	sts BUTTON_CURRENT, r16
	sts BUTTON_PREVIOUS, r16
	sts BUTTON_LENGTH, r16
	
	ldi ZH, high(BUTTON_COUNT)
	ldi ZL, low(BUTTON_COUNT)
	ldi r16, 0x00
	st z+, r16
	st z, r16
	ldi ZH, high(DISPLAY_TEXT)
	ldi ZL, low(DISPLAY_TEXT)
	ldi r16, ' '
	st z+, r16
	st z+, r16
	st z+, r16
	st z+, r16
	st z, r16
	
	;initialize part 4
	ldi ZH, high(DOTDASH_PATTERN)
	ldi ZL, low(DOTDASH_PATTERN)
	ldi r17, ' '
	st z+, r17
	st z+, r17
	st z+, r17
	st z+, r17
	st z+, r17
	st z+, r17
	st z+, r17
	st z+, r17
	st z+, r17
	st z, r17


main_loop:
	;timer 3, if OCF3A is not set, jump back to main_loop
	;if OCF3A is set, reload LCD
	in r16, TIFR3
	sbrs r16, OCF3A
	rjmp main_loop
	
	ldi r16, 1<< OCF3A
	out TIFR3, r16
	
;start to reload LCD
;part 1
set_lcd:	
	;if PULSE is 1, print "<>"
	;else, print space
	lds r16, PULSE
	cpi r16, 0
	breq space
	cpi r16, 1
	breq char

char:
	;set location
	ldi r16, 0
	ldi r17, 14
	push r16
	push r17
	rcall lcd_gotoxy
	pop r17
	pop r16
	;print '<>'
	ldi r16, '<'
	push r16
	rcall lcd_putchar
	pop r16
	
	ldi r16, '>'
	push r16
	rcall lcd_putchar
	pop r16

	rjmp printCount
	
space:
	;set location
	ldi r16, 0
	ldi r17, 14
	push r16
	push r17
	rcall lcd_gotoxy
	pop r17
	pop r16
	;print space
	ldi r16, ' '
	push r16
	rcall lcd_putchar
	pop r16
	ldi r16, ' '
	push r16
	rcall lcd_putchar
	pop r16

;part 2
;print button_count
printCount:
	;get hex number from button_count
	ldi ZH, high(BUTTON_COUNT)
    ldi ZL, low(BUTTON_COUNT)
	ld r17, z+
	ld r16, z
	push r17
	push r16
	;get address from display_text
	ldi r17, high(DISPLAY_TEXT)
    ldi r16, low(DISPLAY_TEXT)
	push r17
	push r16
	;to_decimal_text is provided by Mike Zastre, 2018
	;use to convert hex number to dec and store it into display_text
	rcall to_decimal_text
	pop r16
	pop r16
	pop r16
	pop r16
	
	;print number store in display_text
	ldi ZL, low(DISPLAY_TEXT)
	ldi ZH, high(DISPLAY_TEXT)
	;set location
	ldi r16, 1
	ldi r17, 11
	push r16
	push r17
	rcall lcd_gotoxy
	pop r17
	pop r16
	;print 5 digits
	ld r16, z+	
	push r16
	rcall lcd_putchar
	pop r16

	ld r16, z+	
	push r16
	rcall lcd_putchar
	pop r16

	ld r16, z+	
	push r16
	rcall lcd_putchar
	pop r16

	ld r16, z+	
	push r16
	rcall lcd_putchar
	pop r16
	
	ld r16, z
	push r16
	rcall lcd_putchar
	pop r16

;part 3
;print asterisks when button_current is 1
printAsterisks:
	;if button_current is 0, print spaces
	;otherwise, print asterisks
	lds r18, BUTTON_CURRENT
	cpi r18, 0x00
	breq printSpace
	
	;print asterisks
	;set location
	ldi r16, 1
	ldi r17, 0
	push r16
	push r17
	rcall lcd_gotoxy
	pop r17
	pop r16
	
	;print 6 '*'
	ldi r16, '*'
	push r16
	rcall lcd_putchar
	pop r16
	ldi r16, '*'
	push r16
	rcall lcd_putchar
	pop r16
	ldi r16, '*'
	push r16
	rcall lcd_putchar
	pop r16
	ldi r16, '*'
	push r16
	rcall lcd_putchar
	pop r16
	ldi r16, '*'
	push r16
	rcall lcd_putchar
	pop r16
	ldi r16, '*'
	push r16
	rcall lcd_putchar
	pop r16
	
	rjmp printMorse

;print spaces
printSpace:
	;set location
	ldi r16, 1
	ldi r17, 0
	push r16
	push r17
	rcall lcd_gotoxy
	pop r17
	pop r16
	;print space
	ldi r16, ' '
	push r16
	rcall lcd_putchar
	pop r16
	ldi r16, ' '
	push r16
	rcall lcd_putchar
	pop r16
	ldi r16, ' '
	push r16
	rcall lcd_putchar
	pop r16
	ldi r16, ' '
	push r16
	rcall lcd_putchar
	pop r16
	ldi r16, ' '
	push r16
	rcall lcd_putchar
	pop r16
	ldi r16, ' '
	push r16
	rcall lcd_putchar
	pop r16

;part 4
;print 10 space, dot or dash from dotdash_pattern
printMorse:	
	;set location
	ldi r16, 0
	ldi r17, 0
	push r16
	push r17
	rcall lcd_gotoxy
	pop r17
	pop r16

	ldi ZH, high(DOTDASH_PATTERN)
	ldi ZL, low(DOTDASH_PATTERN)

	ld r16, z+
	push r16
	rcall lcd_putchar
	pop r16
	ld r16, z+
	push r16
	rcall lcd_putchar
	pop r16
	ld r16, z+
	push r16
	rcall lcd_putchar
	pop r16
	ld r16, z+
	push r16
	rcall lcd_putchar
	pop r16
	ld r16, z+
	push r16
	rcall lcd_putchar
	pop r16
	ld r16, z+
	push r16
	rcall lcd_putchar
	pop r16
	ld r16, z+
	push r16
	rcall lcd_putchar
	pop r16
	ld r16, z+
	push r16
	rcall lcd_putchar
	pop r16
	ld r16, z+
	push r16
	rcall lcd_putchar
	pop r16
	ld r16, z
	push r16
	rcall lcd_putchar
	pop r16

	rjmp main_loop
	
timer1:
	;use for part 1
	;if PULSE is 1, change to 0
	;if PULSE is 0, change to 1
	push r16
	lds r16, SREG
	push r16
	lds r16, PULSE
	cpi r16, 1
	breq setSpace
setSymbol:
	ldi r16, 1
	rjmp end
setSpace:
	ldi r16, 0
end:
	sts PULSE, r16
	pop r16
	sts SREG, r16
	pop r16
	reti

; Note there is no "timer3" interrupt handler as we must use this
; timer3 in a polling style within our main program.

;use for part 2, 3 and 4
timer4:
	push r16
	push r17
	push XL
	push XH
	push YL
	push YH
	push ZL
	push ZH
	lds r16, SREG
	push r16

	;900 = 0x0384
	ldi YL, 0x84
	ldi YH, 0x03

	lds	r16, ADCSRA	

	;following 7 lines are from Victoria Li, 2018
	ori r16, 0x40
	sts	ADCSRA, r16
wait:	
	lds r16, ADCSRA
	andi r16, 0x40
	brne wait
	;compare ADC with 0x0384
	;if ADC > 0x0384, no button has been pressed
	lds XL, ADCL
	lds XH, ADCH
	cp XL, YL
	cpc XH, YH
	breq currentPress
	brlo currentPress

;if ADC > 0x0384, no button has been pressed
;set button_current to 0
notPress:
	ldi r16, 0x00
	sts BUTTON_CURRENT, r16
	rjmp checkState

;if ADC <= 0x0384, at least one button has been pressed
;set button_current to 1
currentPress:
	ldi r16, 0x01
	sts BUTTON_CURRENT, r16

;if button_previous is 1, no need to deal with counter
;but need to deal with button_length
;jump to previousPress
checkState:
	lds r17, BUTTON_PREVIOUS
	cpi r17, 0x01
	breq previousPress

	;if button_previous is 0 and button_current is 0
	;nothing need to do, jump to goEnd
	;if button_previous is 0 and button_current is 1
	;go set counter
	lds r16, BUTTON_CURRENT
	cpi r16, 0x00
	breq goEnd

	;if the code reach here, then button_current is 1 and button_previous is 0
	;which means the button just been puushed
	;button_count++
	ldi ZH, high(BUTTON_COUNT)
	ldi ZL, low(BUTTON_COUNT)
	ld r17, Z+
	ld r16, Z
	ldi r18, 0x01
	ldi r19, 0x00
	add r16, r18
	adc r17, r19
	ldi ZH, high(BUTTON_COUNT)
	ldi ZL, low(BUTTON_COUNT)
	st Z+, r17	
	st Z, r16

	;for part 4, set button_length to 1 
 	ldi r16, 0x01
	sts BUTTON_LENGTH, r16
	rjmp goEnd

;when button_previous is 1
previousPress:
	lds r16, BUTTON_CURRENT
	cpi r16, 0x01
	;if button_current is 0, check button_length and convert it to dot or dash
	;else button_length++
	brne checkLength
	;if button_length > 0x15
	;is a dash, no need to increase
	;jump to goEnd
	lds r16, BUTTON_LENGTH
	cpi r16, 0x15
	brsh goEnd
	;if button_length <= 0x14
	;not sure is dot or dash, increase button_length
	inc r16
	sts BUTTON_LENGTH, r16
	rjmp goEnd

;when button_previous is 1 and button_current is 0
;check button_length, if it is greater than 0x14, which is 20, is dash
;otherwise, is dot
checkLength:
	ldi ZH, high(DOTDASH_PATTERN)
 	ldi ZL, low(DOTDASH_PATTERN)
	ldi r17, 0x00
	;find where to put next pattern
counting:
	ld r16, z
	;if current location maps to a space
	;put dot or dash to this location
	cpi r16, ' '
	breq record
	inc ZL
	inc r17
	;if r17 > 10, which means the first 10 spots are not space
	;then no need to input anything, jump to goEnd
	cpi r17, 0x0A
	breq goEnd
	rjmp counting

record:
	;if button_length > 0x14, input a dash
	;otherwise, input a dot
	lds r16, BUTTON_LENGTH
	cpi r16, 0x14
	brlt dot
	breq dot

dash:
	ldi r16, '-'
	st z, r16
	rjmp goEnd

dot:
	ldi r16, '.'
	st z, r16

goEnd:
	;store button_current to button_previous
	lds r16, BUTTON_CURRENT
	sts BUTTON_PREVIOUS, r16

	pop r16
	sts SREG, r16
	pop ZH
	pop ZL
	pop YH
	pop YL
	pop XH
	pop	XL
	pop r17
	pop	r16
    reti

; the function 'to_decimal_text' and comments are provided by Mike Zastre, 2018
; use to convert hex number to dec

; First parameter: 16-bit value for which a
; text representation of its decimal form is to
; be stored.
;
; Second parameter: 16-bit address in data memory
; in which the text representation is to be stored.
to_decimal_text:
    .def countL=r18
    .def countH=r19
    .def factorL=r20
    .def factorH=r21
    .def multiple=r22
    .def pos=r23
    .def zero=r0
    .def ascii_zero=r16
	.set MAX_POS = 5 
    push countH
    push countL
    push factorH
    push factorL
    push multiple
    push pos
    push zero
    push ascii_zero
    push YH
    push YL
    push ZH
    push ZL
    in YH, SPH
    in YL, SPL
    ; fetch parameters from stack frame
    ;
    .set PARAM_OFFSET = 16
    ldd countH, Y+PARAM_OFFSET+3
    ldd countL, Y+PARAM_OFFSET+2
    ; this is only designed for positive
    ; signed integers; we force a negative
    ; integer to be positive.
    ;
    andi countH, 0b01111111
    clr zero
    clr pos
    ldi ascii_zero, '0'
    ; The idea here is to build the text representation
    ; digit by digit, starting from the left-most.
    ; Since we need only concern ourselves with final
    ; text strings having five characters (i.e., our
    ; text of the decimal will never be more than
    ; five characters in length), we begin we determining
    ; how many times 10000 fits into countH:countL, and
    ; use that to determine what character (from ’0’ to
    ; ’9’) should appear in the left-most position
    ; of the string.
    ;
    ; Then we do the same thing for 1000, then
    ; for 100, then for 10, and finally for 1.
    ;
    ; Note that for *all* of these cases countH:countL is
    ; modified. We never write these values back onto
    ; that stack. This means the caller of the function
    ; can assume call-by-value semantics for the argument
    ; passed into the function.
    ;
to_decimal_next:
    clr multiple
to_decimal_10000:
    cpi pos, 0
    brne to_decimal_1000
    ldi factorL, low(10000)
    ldi factorH, high(10000)
    rjmp to_decimal_loop
to_decimal_1000:
    cpi pos, 1
    brne to_decimal_100
    ldi factorL, low(1000)
    ldi factorH, high(1000)
    rjmp to_decimal_loop
to_decimal_100:
    cpi pos, 2
    brne to_decimal_10
    ldi factorL, low(100)
    ldi factorH, high(100)
    rjmp to_decimal_loop

to_decimal_10:
    cpi pos, 3
    brne to_decimal_1
    ldi factorL, low(10)
    ldi factorH, high(10)
    rjmp to_decimal_loop
to_decimal_1:
    mov multiple, countL
    rjmp to_decimal_write
to_decimal_loop:
    inc multiple
    sub countL, factorL
    sbc countH, factorH
    brpl to_decimal_loop
    dec multiple
    add countL, factorL
    adc countH, factorH
to_decimal_write:
    ldd ZH, Y+PARAM_OFFSET+1
    ldd ZL, Y+PARAM_OFFSET+0
    add ZL, pos
    adc ZH, zero
    add multiple, ascii_zero
    st Z, multiple
    inc pos
    cpi pos, MAX_POS
    breq to_decimal_exit
    rjmp to_decimal_next
to_decimal_exit:
    pop ZL
    pop ZH
    pop YL
    pop YH
    pop ascii_zero
    pop zero
    pop pos
    pop multiple
    pop factorL
    pop factorH
    pop countL
    pop countH
    .undef countL
    .undef countH
    .undef factorL
    .undef factorH
    .undef multiple
    .undef pos
    .undef zero
    .undef ascii_zero
    ret


; ***************************************************
; **** END OF FIRST "STUDENT CODE" SECTION ********** 
; ***************************************************


; ################################################
; #### BEGINNING OF "TOUCH CAREFULLY" SECTION ####
; ################################################

; The purpose of these locations in data memory are
; explained in the assignment description.
;

.dseg

PULSE: .byte 1
COUNTER: .byte 2
DISPLAY_TEXT: .byte 16
BUTTON_CURRENT: .byte 1
BUTTON_PREVIOUS: .byte 1
BUTTON_COUNT: .byte 2
BUTTON_LENGTH: .byte 1
DOTDASH_PATTERN: .byte MAX_PATTERN_LENGTH

; ##########################################
; #### END OF "TOUCH CAREFULLY" SECTION ####
; ##########################################
