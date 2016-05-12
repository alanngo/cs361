import java.util.Scanner;

public class InstructionObject {

	String subjectName = "";
	String objectName = "";
	int value = 0;
	boolean goodSyntax = true;
	boolean isRead = false;
	boolean isWrite = false;
	Subject sub = null; 
	Object obj = null;
	boolean sleep = false;

	/*
	Set up
	 */
	InstructionObject(){

	}
	/*
	Enumerate instruction types
	 */
	public enum InstructionType {
		read,
		write,
		badInstruction
	}

	/*
	Read each line of text and parse it into either READ, WRITE, or BADINSTRUCTION type
	 */
	public InstructionObject(String lineText) {

		String nextValue;
		Scanner parse = new Scanner(lineText);
		nextValue = parse.next();
		boolean wrongInput = false;

		//Parses lines that start is "READ"
		if(nextValue.equals("read")) {
			isRead=true;
			InstructionType type = InstructionType.read;
			if(parse.hasNext()) {
				nextValue = parse.next();
				if(nextValue instanceof String) 
					subjectName = nextValue; 
				else
					wrongInput = true;
			}
			else 
				wrongInput = true;
			if(parse.hasNext()){
				nextValue = parse.next();
				if(nextValue instanceof String) 
					objectName = nextValue;
				else
					wrongInput = true;
			}
			else 
				wrongInput = true;
		}

		//Parses lines that start with "WRITE"
		else if(nextValue.equals("write")) {
			isWrite = true;
			InstructionType type = InstructionType.write;

			if(parse.hasNext()) {
				nextValue = parse.next();
				if(nextValue instanceof String) 
					subjectName = nextValue;
				else
					wrongInput = true;
			}
			else 
				wrongInput = true;

			if(parse.hasNext()){
				nextValue = parse.next();
				if(nextValue instanceof String)
					objectName = nextValue;
				else
					wrongInput = true;
			}
			else {
				wrongInput = true;
			}
			if(parse.hasNextInt()) {
				value = parse.nextInt();
			}
			else {
				wrongInput = true;
			} 
		}  
		else if(nextValue.equals("sleep")) {
			sleep = true;
		}
		//first word isn't read or write, bad instruction
		else {
			wrongInput = true;  
		}
		if(parse.hasNext())
			wrongInput = true;

		goodSyntax = !wrongInput; 
	}
}
