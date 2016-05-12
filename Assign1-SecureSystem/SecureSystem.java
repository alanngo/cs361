//assignment 1
//aji272; sn9755;
//Alex Irion; Semur Nabiev;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class SecureSystem {

	static int low  = SecurityLevel.LOW;
	static int high = SecurityLevel.HIGH;

	//instructionList is the regular input
	//testsList has the test commands
	//extraCreditList is the extra credit command list
	static String filename = "instructionList.txt"; 
	// We add two subjects, one high and one low.
	// We add two objects, one high and one low.
	static Subject sub1 = new Subject("Lyle", low);
	static Subject sub2 = new Subject("Hal", high);
	static Object obj1 = new Object("LObj", low);
	static Object obj2 = new Object("HObj", high);

	public static void main(String[] args) {	

		try {
			//parse The file by line
			String lineText;

			System.out.println("Reading from file: "+filename);
			System.out.println();
			Scanner fileScanner = new Scanner (new File(filename));
			//loop over every line
			boolean stop = false;
			while(fileScanner.hasNextLine() && !stop) {

				lineText = fileScanner.nextLine().toLowerCase();
				//create new instruction object from each line of text
				InstructionObject inObj = new InstructionObject(lineText);

				if(inObj.sleep){
					stop = true;
					printSleep();
				}else{

					//Determine which subject and object is being operated on.
					if(inObj.subjectName.equals("lyle")) {
						inObj.sub = sub1;
					}
					else {
						inObj.sub = sub2;
					}
					if(inObj.objectName.equals("lobj")) {
						inObj.obj = obj1; 
					}
					else {
						inObj.obj = obj2;
					}

					ReferenceMonitor ref = new ReferenceMonitor(inObj);

					//Print out results
					printState(inObj);
				}
			}
			fileScanner.close();
		}
		catch(FileNotFoundException e) {
			System.out.println("Problem finding the file. " + e);
		}
	}

	/*
	Prints out the state of LObj, HObj, Lyle, and Hal after each execution
	 */
	public static void printState(InstructionObject inObj) {

		//Prints bad sytax message or action header
		if(!inObj.goodSyntax)
			System.out.println("Bad Instruction");
		else if(inObj.isRead)
			System.out.println(inObj.subjectName + " reads " + inObj.objectName);
		else
			System.out.println(inObj.subjectName + " writes value " + inObj.value+" to "+inObj.objectName);

		System.out.println("The current state is: ");
		System.out.println("   LObj has value: " + obj1.getVal());
		System.out.println("   HObj has value: " + obj2.getVal());
		System.out.println("   Lyle has recently read: " + sub1.getTemp());
		System.out.println("   Hal has recently read: " + sub2.getTemp());
		System.out.println();

	}
	/*
	Prints out the sleeping message
	 */
	public static void printSleep() {
		System.out.println("Going to sleep");
	}

}
