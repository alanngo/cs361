Projects for CS 361–Intro to Security
Each must run from the command line

======================================================================================================
– Project One: BLP System

Implement a simple security system following BLP simple security and the star-property.

compile with: javac *.java  
run with: java SecureSystem instructionList

======================================================================================================
– Project Two: Covert Channel

Updated project one to implement a covert channel within a "secure system." Added CREATE, DESTROY, and RUN instructions for users (subjects) to allow a high-level user to communicate with a low-level user via bit stream.

compile with: javac *.java
run with: java CovertChannel inputfilename  

replacing "inputfilename" with a file of which contents you want transferred over the channel.

======================================================================================================
– Project Three: Huffman encryption

Computes the entropy of a language based on the frequency of symbols in the language using the Huffman algorithm

======================================================================================================
– Project Four: AES Encryption

My implemention the AES-128 algorithm.

compile with: javac *.java  
run with: java AES option keyFile inputFile

Where "keyFile" is the key file, "inputFile" names a file containing lines of plaintext, and option is either "e" or "d" for encryption and decryption, respectively. keyFile contains a single line of 32 hex characters, which represents a 128-bit key. The inputFile should have 32 hex characters per line.

======================================================================================================
– Project Five: Password Cracker
Implemented a dictionary attack password cracker.

compile with:

javac *.java  
run with: java PasswordCrack inputFile1 inputFile1

Where "inputFile1" is the dictionary file, and "inputFile2" is the list of encrypted passwords.