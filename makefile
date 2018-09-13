CC=javac
GC=gcc

target: newfolder sud2sat sud2sat_v2 sat2sud anotherSolver

newfolder:
	mkdir -p tmp output

sud2sat:
	$(CC) sud2sat.java

sat2sud:
	$(CC) sat2sud.java

sud2sat_v2:
	$(CC) sud2sat_v2.java

anotherSolver: satz213/satz.c
	$(GC) satz213/satz.c -w -o satz213/satz

basic:
	./multsuds.sh tests/basic50.txt

extend1:
	./multsuds.sh tests/top95.txt

extend2:
	./singlesud_v2.sh tests/easy.txt

extend3:
	./satz213.sh tests/easy.txt

clean:
	/bin/rm -f *.class *.txt *.sol timetable
	/bin/rm -f -r tmp output
	/bin/rm -f ./satz213/satz ./satz213/timetable
