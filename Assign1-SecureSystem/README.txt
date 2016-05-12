UTEID: aji272; sn9755;
FIRSTNAME: Alex; Semur;
LASTNAME: Irion; Nabiev;
CSACCOUNT: alex10; snabiev;
EMAIL: alexirion10@gmail.com;  semur.nabiev@gmail.com;

Explain the program (100+ words):

Program reads instructions from file `instructionList` (input file acn be changed 
by modifying line below)

   //instructionList is the regular input
   //testsList has the test commands
   //extraCreditList is the extra credit command list
   static String filename = "instructionList";

The file will be read by SecureSystem. SecureSystem will also define the objects 
and the subjects. Eeach line will be parsed by the InstructionObject, while accounting
for bad input. COmmands are either write, read, bad syntax or bad command 
(disallowed). This object will be passed by SecureSystem into the ReferenceMonitor. 
There, each instruction will be processed by invoking the Object/Subject class 
methods that modify their respective values. Bad commands will not be processed and
an erro message will appear.

EXTRA CREDIT

Added a `sleep` command that will stop the execution of the instruction list

Tell how much of the program I’ve finished:
Finished all requirements.



Test cases(4+ cases):

write Lyle LObj 5
read Lyle LObj HObj
read Lyle HObj
write Alex 10
write
read Lyle LObj
read Lyle HObj



Results:
 
Reading from file: testsList

lyle writes value 5 to lobj
The current state is: 
   LObj has value: 5
   HObj has value: 0
   Lyle has recently read: 0
   Hal has recently read: 0

Bad Instruction
The current state is: 
   LObj has value: 5
   HObj has value: 0
   Lyle has recently read: 0
   Hal has recently read: 0

lyle reads hobj
The current state is: 
   LObj has value: 5
   HObj has value: 0
   Lyle has recently read: 0
   Hal has recently read: 0

Bad Instruction
The current state is: 
   LObj has value: 5
   HObj has value: 0
   Lyle has recently read: 0
   Hal has recently read: 0

Bad Instruction
The current state is: 
   LObj has value: 5
   HObj has value: 0
   Lyle has recently read: 0
   Hal has recently read: 0

lyle reads lobj
The current state is: 
   LObj has value: 5
   HObj has value: 0
   Lyle has recently read: 5
   Hal has recently read: 0

lyle reads hobj
The current state is: 
   LObj has value: 5
   HObj has value: 0
   Lyle has recently read: 0
   Hal has recently read: 0
