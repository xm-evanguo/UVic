/* a4.c
 * CSC Spring 2018
 * 
 * Student name: Evan Guo
 * Student UVic ID:	V00866199
 * Date of completed work: April 6
 *
 *
 * Code provided for Assignment #4
 *
 * Author: Mike Zastre (2018-Mar-25)
 *
 * This skeleton of a C language program is provided to help you
 * begin the programming tasks for A#4. As with the previous
 * assignments, there are "DO NOT TOUCH" sections. You are *not* to
 * modify the lines within these section.
 *
 * You are also NOT to introduce any new program-or file-scope
 * variables (i.e., ALL of your variables must be local variables).
 * YOU MAY, however, read from and write to the existing program- and
 * file-scope variables. Note: "global" variables are program-
 * and file-scope variables.
 *
 * UNAPPROVED CHANGES to "DO NOT TOUCH" sections could result in
 * either incorrect code execution during assignment evaluation, or
 * perhaps even code that cannot be compiled.  The resulting mark may
 * be zero.
 */


/* =============================================
 * ==== BEGINNING OF "DO NOT TOUCH" SECTION ====
 * =============================================
 */

#define F_CPU 16000000UL
#include <avr/io.h>
#include <avr/interrupt.h>
#include <util/delay.h>

#define DELAY1 0.000001
#define DELAY3 0.01

#define PRESCALE_DIV1 8
#define PRESCALE_DIV3 64
#define TOP1 ((int)(0.5 + (F_CPU/PRESCALE_DIV1*DELAY1))) 
#define TOP3 ((int)(0.5 + (F_CPU/PRESCALE_DIV3*DELAY3)))

#define PWM_PERIOD ((long int)500)

volatile long int count = 0;
volatile long int slow_count = 0;


ISR(TIMER1_COMPA_vect) {
	count++;
}


ISR(TIMER3_COMPA_vect) {
	slow_count += 5;
}

/* =======================================
 * ==== END OF "DO NOT TOUCH" SECTION ====
 * =======================================
 */


/* *********************************************
 * **** BEGINNING OF "STUDENT CODE" SECTION ****
 * *********************************************
 */

void led_state(uint8_t LED, uint8_t state) {
	// set DDRL to 0xff
	DDRL = 0xFF;
	// LED = the number of the LED
	// state = state of the LED, 0 = off, 1 = on
	// if the state is 1, use logical AND to set the bit to 0
	// if the state is 0, use logical OR to set the bit to 1
	// default as 0
	switch (LED) {
		case 0:
			if(state == 0){
				PORTL &= 0b01111111;
			}else{
				PORTL |= 0b10000000;
			}
			break;
		case 1:
			if(state == 0){
				PORTL &= 0b11011111;
			}else{
				PORTL |= 0b00100000;
			}
			break;
		case 2:
			if(state == 0){
				PORTL &= 0b11110111;
			}else{
				PORTL |= 0b00001000;
			}
			break;
		case 3:
			if(state == 0){
				PORTL &= 0b11111101;
			}else{
				PORTL |= 0b00000010;
			}
			break;
		default:
			PORTL = 0x00;
			break;
	}

}



void SOS() {
    uint8_t light[] = {
        0x1, 0, 0x1, 0, 0x1, 0,
        0xf, 0, 0xf, 0, 0xf, 0,
        0x1, 0, 0x1, 0, 0x1, 0,
        0x0
    };

    int duration[] = {
        100, 250, 100, 250, 100, 500,
        250, 250, 250, 250, 250, 500,
        100, 250, 100, 250, 100, 250,
        250
    };

	int length = 19;
	
	// use for loop to go through the array
	// if light[i] is 0, set all lights off
	// otherwise, set the specific LED(s) on
	// delay for duration[i] ms
	for(int i = 0; i < length; i++){
		if(light[i] == 0){
			led_state(0, 0);
			led_state(1, 0);
			led_state(2, 0);
			led_state(3, 0);
			_delay_ms(duration[i]);
			continue;
		}
		if(light[i] & 0x1){
			led_state(0, 1);
		}
		if(light[i] & 0x2){
			led_state(1, 1);
		}
		if(light[i] & 0x4){
			led_state(2, 1);
		}
		if(light[i] & 0x8){
			led_state(3, 1);
		}
		_delay_ms(duration[i]);	
	}
}


void glow(uint8_t LED, float brightness) {
	// use holder to store the on time
	// use infinite loop
	// if 0 <= count < holder, turn LED on
	// if holder <= count < PWM_PERIOD, turn LED off
	// if PWM_PERIOD <= count, set count to 0 and turn LED on
	int holder = brightness * PWM_PERIOD;
	DDRL = 0xFF;
	for(;;){
		if(count < holder){
			led_state(LED, 1);
		}else if(count < PWM_PERIOD){
			led_state(LED, 0);
		}else{
			count = 0;
			led_state(LED, 1);
		}
	}
}



void pulse_glow(uint8_t LED) {
	// use int state as a boolean to determine whether to use holder++ when slow_count != 0 or holder--
	// holder should increase when the program start, and set state to 1
	// when holder == 500, set state to 0 and decrease holder
	// once holder == 0, set state to 1 and start a new cycle	
	int state = 1;
	int holder = 0;
	DDRL = 0xFF;
	for(;;){
		// if slow_count != 0 and state is 1, holder + 1 and set slow_count to 0
		if(slow_count != 0){
			if(state == 1){
				holder++;
				slow_count = 0;
				// if holder >= PWM_PERIOD, set state to 0
				if(holder >= PWM_PERIOD){
					state = 0;
				}
			// if slow_count != 0 and state is 0, holder - 1 and set slow_count to 1
			}else{
				holder--;
				slow_count = 0;
				// if holder <= 0, set state to 1
				if(holder <= 0){
					state = 1;
				}
			}
		}
		// if slow_count == 0, do the same thing as in part 3
		if(count < holder){
			led_state(LED, 1);
		}else if(count < PWM_PERIOD){
			led_state(LED, 0);
		}else{
			count = 0;
			led_state(LED, 0);
		}
	}
}


void light_show() {
	// same idea as in part 2
	uint8_t light[] = {
        0xf, 0x0, 0xf, 0x0, 0xf, 0x0,
        0x6, 0x0, 0x9, 0x0, 0xf, 0x0,
        0xf, 0x0, 0xf, 0x0, 0x9, 0x0,
        0x6, 0x0, 0x8, 0x0, 0xc, 0x0,
		0x6, 0x0, 0x3, 0x0, 0x1, 0x0,
		0x3, 0x0, 0x6, 0x0, 0xc, 0x0,
		0x8, 0x0, 0xc, 0x0, 0x6, 0x0,
		0x3, 0x0, 0x1, 0x0, 0x3, 0x0,
		0x6, 0x0, 0xf, 0x0, 0xf, 0x0,
		0x6, 0x0, 0x6, 0x0, 0x0
    };

    int duration[] = {
        200, 200, 200, 200, 200, 200,
        100, 100, 100, 100, 200, 200,
        200, 200, 200, 200, 100, 100,
        100, 100, 100, 0, 100, 0,
		100, 0, 100, 0, 100, 0,
		100, 0, 100, 0, 100, 0,
		100, 0, 100, 0, 100, 0,
		100, 0, 100, 0, 100, 0,
		100, 100, 200, 200, 200, 200, 
		250, 200, 250, 250, 250
    };

	int length = 59;

	for(int i = 0; i < length; i++){
		if(light[i] == 0){
			led_state(0, 0);
			led_state(1, 0);
			led_state(2, 0);
			led_state(3, 0);
			_delay_ms(duration[i]);
			continue;
		}
		if(light[i] & 0x1){
			led_state(0, 1);
		}
		if(light[i] & 0x2){
			led_state(1, 1);
		}
		if(light[i] & 0x4){
			led_state(2, 1);
		}
		if(light[i] & 0x8){
			led_state(3, 1);
		}
		_delay_ms(duration[i]);	
	}
}


/* ***************************************************
 * **** END OF FIRST "STUDENT CODE" SECTION **********
 * ***************************************************
 */


/* =============================================
 * ==== BEGINNING OF "DO NOT TOUCH" SECTION ====
 * =============================================
 */

int main() {
    /* Turn off global interrupts while setting up timers. */

	cli();

	/* Set up timer 1, i.e., an interrupt every 1 microsecond. */
	OCR1A = TOP1;
	TCCR1A = 0;
	TCCR1B = 0;
	TCCR1B |= (1 << WGM12);
    /* Next two lines provide a prescaler value of 8. */
	TCCR1B |= (1 << CS11);
	TCCR1B |= (1 << CS10);
	TIMSK1 |= (1 << OCIE1A);

	/* Set up timer 3, i.e., an interrupt every 10 milliseconds. */
	OCR3A = TOP3;
	TCCR3A = 0;
	TCCR3B = 0;
	TCCR3B |= (1 << WGM32);
    /* Next line provides a prescaler value of 64. */
	TCCR3B |= (1 << CS31);
	TIMSK3 |= (1 << OCIE3A);


	/* Turn on global interrupts */
	sei();

/* =======================================
 * ==== END OF "DO NOT TOUCH" SECTION ====
 * =======================================
 */


/* *********************************************
 * **** BEGINNING OF "STUDENT CODE" SECTION ****
 * *********************************************
 */

/*
	led_state(0, 1);
	_delay_ms(1000);
	led_state(2, 1);
	_delay_ms(1000);
	led_state(1, 1);
	_delay_ms(1000);
	led_state(2, 0);
	_delay_ms(1000);
	led_state(0, 0);
	_delay_ms(1000);
	led_state(1, 0);
	_delay_ms(1000);
*/


	//SOS();



	//glow(2, 0.01);


	//pulse_glow(0);



	//light_show();

/* ****************************************************
 * **** END OF SECOND "STUDENT CODE" SECTION **********
 * ****************************************************
 */
}
