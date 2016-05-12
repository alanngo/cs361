UTEID: aji272; sn9755;
FIRSTNAME: Alex; Semur;
LASTNAME: Irion; Nabiev;
CSACCOUNT: alex10; snabiev;
EMAIL: alexirion10@gmail.com;  semur.nabiev@gmail.com;

[Program 3] [Description]
Everything runs throught the Encoder.java file.  The priorityQueue and Huffman tree classes were built by me, with modifications from Mike Scotts class.  The encoder file reads the Frequency.txt file and computes the entropy for both 1st level and 2nd level (2 letter combinations).  We generate a testText file by using the probabilities of each letter, which is then encoded into 1's and 0's  (ascii), which is then finally mapped back to the origional text.  By building a Huffman tree (ordered based on the frequency) we were able to build a more efficient encoding scheme than the naive encoding.  We found that the 2 level encoding was slightly more efficient than the first level, but not by much.

To compile our program, you need to use "javac *.java". To run our program, you need to use "java Encoder Frequency k”

[Finish]
Program is complete.

[Test Cases]
[Input of test 1][command line]
java Encoder Frequency.txt 2000


[Output of test 1]
##########################################
############# ENCODING SCHEME ############
##########################################
Character       Code
a               1
b               011
c               00
d               010
##########################################

##########################################
############# ENCODING SCHEME ############
##########################################
Character       Code
aa              1
ab              0101
ac              000
ad              01111
ba              0110
bb              011101111
bc              01110101
bd              011101001
ca              001
cb              01110110
cc              0111001
cd              01110000
da              0100
db              011101110
dc              01110001
dd              011101000
##########################################


Entropy               level 1: 0.7206795673749244
Bits actually written level 1: 2359
Entropy               level 2: 0.7206795673749242
Bits actually written level 2: 1654
Entropy improvement: 2.220446049250313E-14%
Bit ratio improvement: 42.62394195888754%

   
[Input of test 2] [command line]
java Encoder Frequency.txt 20

[Output of test 2]
##########################################                                                                                            
############# ENCODING SCHEME ############                                                                                            
##########################################                                                                                            
Character       Code                                                                                                                  
a               1                                                                                                                     
b               011                                                                                                                   
c               00                                                                                                                    
d               010                                                                                                                   
##########################################                                                                                            
                                                                                                                                      
##########################################                                                                                            
############# ENCODING SCHEME ############                                                                                            
##########################################                                                                                            
Character       Code                                                                                                                  
aa              1                                                                                                                     
ab              0101                                                                                                                  
ac              000                                                                                                                   
ad              01111                                                                                                                 
ba              0110                                                                                                                  
bb              011101111                                                                                                             
bc              01110101                                                                                                              
bd              011101001                                                                                                             
ca              001                                                                                                                   
cb              01110110                                                                                                              
cc              0111001                                                                                                               
cd              01110000                                                                                                              
da              0100                                                                                                                  
db              011101110                                                                                                             
dc              01110001                                                                                                              
dd              011101000                                                                                                             
##########################################                                                                                            
                                                                                                                                      

Entropy               level 1: 0.7206795673749244
Bits actually written level 1: 21
Entropy               level 2: 0.7206795673749242
Bits actually written level 2: 12
Entropy improvement: 2.220446049250313E-14%
Bit ratio improvement: 75.0%


[Input of test 3] [command line]
java Encoder Frequency.txt 4000

[Output of test 3]
##########################################
############# ENCODING SCHEME ############
##########################################
Character       Code
a               1
b               011
c               00
d               010
##########################################

##########################################
############# ENCODING SCHEME ############
##########################################
Character       Code
aa              1
ab              0101
ac              000
ad              01111
ba              0110
bb              011101111
bc              01110101
bd              011101001
ca              001
cb              01110110
cc              0111001
cd              01110000
da              0100
db              011101110
dc              01110001
dd              011101000
##########################################


Entropy               level 1: 0.7206795673749244
Bits actually written level 1: 4757
Entropy               level 2: 0.7206795673749242
Bits actually written level 2: 3383
Entropy improvement: 2.220446049250313E-14%
Bit ratio improvement: 40.614838900384264%


[Input of test 4] [command line]
java Encoder Frequency.txt 10000

[Output of test 4]
##########################################
############# ENCODING SCHEME ############
##########################################
Character       Code
a               1
b               011
c               00
d               010
##########################################

##########################################
############# ENCODING SCHEME ############
##########################################
Character       Code
aa              1
ab              0101
ac              000
ad              01111
ba              0110
bb              011101111
bc              01110101
bd              011101001
ca              001
cb              01110110
cc              0111001
cd              01110000
da              0100
db              011101110
dc              01110001
dd              011101000
##########################################


Entropy               level 1: 0.7206795673749244
Bits actually written level 1: 4757
Entropy               level 2: 0.7206795673749242
Bits actually written level 2: 3383
Entropy improvement: 2.220446049250313E-14%
Bit ratio improvement: 40.614838900384264%

