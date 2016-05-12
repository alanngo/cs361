UTEID: aji272; sn9755;
FIRSTNAME: Alex; Semur;
LASTNAME: Irion; Nabiev;
CSACCOUNT: alex10; snabiev;
EMAIL: alexirion10@gmail.com;  semur.nabiev@gmail.com;


[Description]
The Java files used includes: CovertChannel, InstructionObject, ReferenceMonitor, SecurityLevel, Object, Subject, and StopWatch.
The file will be read in by the main function in CovertChannel.  CovertChannel parses each line of the input file into a character array, which is then parsed into a bit array.  Hal runs different commands based on whether he needs to transmit a 0 or 1 (either creating an object or not).  InstructionObject does the setup for which commands should be run and determines which subject they should be run for.  After this is done, LYLE performs some commands and reads the value from the object in the ReferenceMonitor (either 0 or 1).  After this action is repeated enough times to form a byte/8 bits, Lyle writes the character to the inputFile.out from the CovertChannel class.


To complie our program, you need to use "javac *.java". To run our program, you need to use "java CovertChannel testInput"
If the verbose call is used, the output is printed to the “log” file


[Machine Information]
Macbook Air 2013
1.3 GHz Intel Core i5


[Finished all requirements.]


[Source Description]  
Pride and Prejudice 
(http://www.gutenberg.org/cache/epub/1342/pg1342.txt) (project Gutenberg)

Metamorphosis (project Gutenberg)
(http://www.gutenberg.org/cache/epub/5200/pg5200.txt)

testInput (Instructions from assignment 1 description page)
(http://www.cs.utexas.edu/~zhaos/cs361/program2/README.txt) 

testInput (README.txt instruction page)
(https://www.cs.utexas.edu/~byoung/cs361/assignment1-nonthreaded-zhao.html)


[Results Summary]
[No.]	[DocumentName] 		[Size] 	 	[Time (ms)]	[Bandwidth (bytes/ms)]
1	Pride and Prejudice	704000 bytes	2282		302
2	Metamorphosis		139000 bytes	567		245 
3	testInput		77 bytes	33		2.33
4	testInput2		407 bytes	55		7.4 
