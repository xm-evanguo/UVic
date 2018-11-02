CC=gcc
# CFLAGS=-c -g -Wall -DDEBUG
CFLAGS=-c -g -Wall -DDEBUG -std=c99
LDFLAGS=

all: uvroff2 driver

uvroff2: uvroff2.o formatter.o
	$(CC) $(LDFLAGS) uvroff2.o formatter.o -o uvroff2

driver: driver.o formatter.o
	$(CC) $(LDFLAGS) driver.o formatter.o -o driver

driver.o: driver.c formatter.h
	$(CC) $(CFLAGS) driver.c

uvroff2.o: uvroff2.c formatter.h
	$(CC) $(CFLAGS) uvroff2.c

formatter.o: formatter.c formatter.h
	$(CC) $(CFLAGS) formatter.c

clean:
	/bin/rm -f uvroff2 driver uvroff2.o driver.o formatter.o

test:
	./uvroff2 /home/zastre/seng265/assign2/in00.txt
	./uvroff2 /home/zastre/seng265/assign2/in01.txt | diff /home/zastre/seng265/assign2/out01.txt -
	./uvroff2 /home/zastre/seng265/assign2/in02.txt | diff /home/zastre/seng265/assign2/out02.txt -
	./uvroff2 /home/zastre/seng265/assign2/in03.txt | diff /home/zastre/seng265/assign2/out03.txt -
	./uvroff2 /home/zastre/seng265/assign2/in04.txt | diff /home/zastre/seng265/assign2/out04.txt -
	./uvroff2 /home/zastre/seng265/assign2/in05.txt | diff /home/zastre/seng265/assign2/out05.txt -
	./uvroff2 /home/zastre/seng265/assign2/in06.txt | diff /home/zastre/seng265/assign2/out06.txt -
	./uvroff2 /home/zastre/seng265/assign2/in07.txt | diff /home/zastre/seng265/assign2/out07.txt -
	./uvroff2 /home/zastre/seng265/assign2/in08.txt | diff /home/zastre/seng265/assign2/out08.txt -
	./uvroff2 /home/zastre/seng265/assign2/in09.txt | diff /home/zastre/seng265/assign2/out09.txt -
	./uvroff2 /home/zastre/seng265/assign2/in10.txt | diff /home/zastre/seng265/assign2/out10.txt -
	./uvroff2 /home/zastre/seng265/assign2/in11.txt | diff /home/zastre/seng265/assign2/out11.txt -
	./uvroff2 /home/zastre/seng265/assign2/in12.txt | diff /home/zastre/seng265/assign2/out12.txt -
	./uvroff2 /home/zastre/seng265/assign2/in13.txt | diff /home/zastre/seng265/assign2/out13.txt -
	./uvroff2 /home/zastre/seng265/assign2/in14.txt | diff /home/zastre/seng265/assign2/out14.txt -
	./uvroff2 /home/zastre/seng265/assign2/in15.txt | diff /home/zastre/seng265/assign2/out15.txt -
	./uvroff2 /home/zastre/seng265/assign2/in16.txt | diff /home/zastre/seng265/assign2/out16.txt -
	./uvroff2 /home/zastre/seng265/assign2/in17.txt | diff /home/zastre/seng265/assign2/out17.txt -
	./uvroff2 /home/zastre/seng265/assign2/in18.txt | diff /home/zastre/seng265/assign2/out18.txt -
	./uvroff2 /home/zastre/seng265/assign2/in19.txt | diff /home/zastre/seng265/assign2/out19.txt -
	./uvroff2 /home/zastre/seng265/assign2/in20.txt | diff /home/zastre/seng265/assign2/out20.txt -
	cat /home/zastre/seng265/assign2/in00.txt | ./uvroff2
	cat /home/zastre/seng265/assign2/in01.txt | ./uvroff2 | diff /home/zastre/seng265/assign2/out01.txt -
	cat /home/zastre/seng265/assign2/in02.txt | ./uvroff2 | diff /home/zastre/seng265/assign2/out02.txt -
	cat /home/zastre/seng265/assign2/in03.txt | ./uvroff2 | diff /home/zastre/seng265/assign2/out03.txt -
	cat /home/zastre/seng265/assign2/in04.txt | ./uvroff2 | diff /home/zastre/seng265/assign2/out04.txt -
	cat /home/zastre/seng265/assign2/in05.txt | ./uvroff2 | diff /home/zastre/seng265/assign2/out05.txt -
	cat /home/zastre/seng265/assign2/in06.txt | ./uvroff2 | diff /home/zastre/seng265/assign2/out06.txt -
	cat /home/zastre/seng265/assign2/in07.txt | ./uvroff2 | diff /home/zastre/seng265/assign2/out07.txt -
	cat /home/zastre/seng265/assign2/in08.txt | ./uvroff2 | diff /home/zastre/seng265/assign2/out08.txt -
	cat /home/zastre/seng265/assign2/in09.txt | ./uvroff2 | diff /home/zastre/seng265/assign2/out09.txt -
	cat /home/zastre/seng265/assign2/in10.txt | ./uvroff2 | diff /home/zastre/seng265/assign2/out10.txt -
	cat /home/zastre/seng265/assign2/in11.txt | ./uvroff2 | diff /home/zastre/seng265/assign2/out11.txt -
	cat /home/zastre/seng265/assign2/in12.txt | ./uvroff2 | diff /home/zastre/seng265/assign2/out12.txt -
	cat /home/zastre/seng265/assign2/in13.txt | ./uvroff2 | diff /home/zastre/seng265/assign2/out13.txt -
	cat /home/zastre/seng265/assign2/in14.txt | ./uvroff2 | diff /home/zastre/seng265/assign2/out14.txt -
	cat /home/zastre/seng265/assign2/in15.txt | ./uvroff2 | diff /home/zastre/seng265/assign2/out15.txt -
	cat /home/zastre/seng265/assign2/in16.txt | ./uvroff2 | diff /home/zastre/seng265/assign2/out16.txt -
	cat /home/zastre/seng265/assign2/in17.txt | ./uvroff2 | diff /home/zastre/seng265/assign2/out17.txt -
	cat /home/zastre/seng265/assign2/in18.txt | ./uvroff2 | diff /home/zastre/seng265/assign2/out18.txt -
	cat /home/zastre/seng265/assign2/in19.txt | ./uvroff2 | diff /home/zastre/seng265/assign2/out19.txt -
	cat /home/zastre/seng265/assign2/in20.txt | ./uvroff2 | diff /home/zastre/seng265/assign2/out20.txt -


