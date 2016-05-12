
//assignment 1
//aji272; sn9755;
//Alex Irion; Semur Nabiev;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class CovertChannel {

	static int low = SecurityLevel.LOW;
	static int high = SecurityLevel.HIGH;

	// test.txt has the text we are using to test
	static String filename = "testInput.txt";
	static boolean verbose = false;

	// Array Lists that store the objects and subjects
	static ArrayList<Object> objList = new ArrayList<Object>();
	static ArrayList<Subject> subList = new ArrayList<Subject>();

	public static void main(String[] args) {

		if (args.length == 1)
			filename = args[0];
		else if (args.length >= 2) {
			filename = args[1];
			verbose = args[0].equals("v");
		}
		try {
			// StopWatch timing = new StopWatch();
			// timing.start();

			// add Subjects Hal/Lyle and Object Obj to the system
			Subject sub1 = new Subject("Lyle", low);
			Subject sub2 = new Subject("Hal", high);
			Object obj = new Object("Obj", low);
			subList.add(sub1);
			subList.add(sub2);

			// System.out.println();

			// Set up input/output files
			File inFile = new File(filename);
			File outFile = new File(filename + ".out");
			File logFile = new File("log");
			Scanner fileScanner = new Scanner(inFile);
			PrintWriter pWriter = new PrintWriter(outFile);
			PrintWriter logWriter = new PrintWriter(logFile);

			// loops over every line, parse The file by line
			String lineText;
			while (fileScanner.hasNextLine()) {
				lineText = fileScanner.nextLine();

				//create an array in get Binary method (puts each letter into binary)
				byte bytes[] = getBinary(lineText);

				// loop over each array index (each array index holds 1 byte in binary representing 1 letter
				for (int i = 0; i < bytes.length; i++) {
					// array parsing each byte into 8 seperate bits
					int[] writeChar = new int[8];
					int val = bytes[i];

					String str = "";
					for (int b = 0; b < 8; b++) {

						int temp = ((val & 128) == 0 ? 0 : 1);
						val <<= 1;

						// HAL performs actions based on if he is sending a 1bit or 0 bit
						// if the bit being sent is a 0, RUN HAL and CREATE HALOBJ
						// else if a 1 is being sent, RUN HAL
						if (temp == 0) {
							objList.add(obj);
						}

						InstructionObject inObj = new InstructionObject(temp, objList, subList);

						// Lyle collects 8 bits (based on what he reads from
						// "READ LYLE OBJ") to write char to out file
						writeChar[b] = inObj.encodedBit;
						str += writeChar[b];

						if (verbose) {
							logWriter.print(inObj.commands.toString());
						}
						// if there are 8 bits, write the letter, reset the
						// array
						if (b == 7) {
							// System.out.println(str+" | "+Byte.parseByte(str,
							// 2));
							byte bytez = Byte.valueOf(str, 2);
							// create an print writer for writing
							// convert the 8 bit byte to a char
							char ch = (char) (bytez);
							// Write the char seen collected by Lyle to the
							// output file
							pWriter.print(ch);
						}
					}
				}
				pWriter.println();
			}
			// timing.stop();
			// System.out.println();
			// System.out.println(timing.toString());
			pWriter.close();
			fileScanner.close();
			logWriter.close();
		} catch (FileNotFoundException e) {
			System.out.println("Problem finding the file. " + e);
		}
	}

	// Helper method to convert the lineText string into a byte array (1 byte/8
	// bits per array index)
	static public byte[] getBinary(String s) {
		byte[] bytes = s.getBytes();
		StringBuilder binary = new StringBuilder();
		for (byte b : bytes) {
			int val = b;
			for (int i = 0; i < 8; i++) {
				binary.append((val & 128) == 0 ? 0 : 1);
				val <<= 1;
			}
			binary.append(' ');
		}
		// System.out.println("'" + s + "' to binary: " + binary);
		return bytes;
	}
}
