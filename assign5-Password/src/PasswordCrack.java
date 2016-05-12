import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

//Alex Irion
//Semur Nabiev

public class PasswordCrack {
	static String inFile = "password2.txt";
	static String dictionary = "words.txt";
	final static String characters = "zxcvbnm,./asdfghjkl;'qwertyuiop[]1234567890-=ZXCVBNM<>?ASDFGHJKL:\"QWERTYUIOP{}|!@#$%^&*()_+\\"; //all keyboard characters
	static char [] chars = characters.toCharArray();
	static String [] wordList = new String[413];

	public static void main(String[] args) {
		//command line args
		//java PasswordCrack inputFile1 inputFile2

		//StopWatch
		StopWatch time = new StopWatch();
		time.start();

		//read in the files
		File linuxFile = new File(inFile);
		File wordFile = new File(dictionary);
		try {
			Scanner fileScan = new Scanner(linuxFile);
			Scanner dictScan = new Scanner(wordFile);
			populateWordList(dictScan);


			String lineText;
			String firstName;
			String lastName;
			//			int lastnamePosition;
			String salt;
			String pw;
			String found;
			int countFound =0;
			int countTotal =0;
			while(fileScan.hasNextLine()) {
				countTotal++;
				//SCAN THROUGH EACH LINE, TRY TO CRACK THE PASSWORD FOR EACH PERSON
				//PARSE LINE
				int startIndex = 0;
				lineText = fileScan.nextLine();
				String[] arr = lineText.split(":");

				String name = arr[4];
				firstName = name.split(" ")[0];
				lastName = name.split(" ")[1];


				startIndex = lineText.indexOf(':') +1;
				salt = lineText.substring(startIndex, startIndex+2);
				pw = lineText.substring(startIndex+2, lineText.indexOf(':', lineText.indexOf(':') + 1));

				System.out.println("first name: " + firstName + "\tlast name: " + lastName + "\tsalt: " + salt + "\tpw: " + pw);

				SystemUser user = new SystemUser(firstName, lastName, salt, pw);

				found = crackPW(user);
				if(found != null) {
					//PRINT SOME STUFF
					countFound++;
					System.out.println("password found!!!!");
					System.out.println("Password: " + found);
					System.out.println();
				}
				else {
					System.out.println("NOT FOUND");
					System.out.println();
				}


			}
			System.out.println("# of PW found: " + countFound + "\tOut of: " + countTotal);

			fileScan.close();
			dictScan.close();
		} 
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		time.stop();
		System.out.println("seconds: " +time.time());
	}

	//Puts the dictionary list in an array
	//reserves the 0 index of the array for the userName
	private static void populateWordList(Scanner dictScan) {
		for(int i=2; i<wordList.length; i++) {
			if(dictScan.hasNext()) {
				wordList[i] = dictScan.nextLine();
			}
		}
	}

	private static String crackPW(SystemUser person) {
		wordList[0] = person.firstName;
		wordList[1] = person.lastName;
		jcrypt encrypt = new jcrypt();

		String temp= "";
		int i =0;
		while(wordList[i] != null && i<wordList.length) {
			String word = wordList[i];
			//CALL ALL PERMUTATION METHODS
			temp = mut1(word, person, encrypt, false);
			if(temp!=null)
				return temp;

			temp = mut2(word, person, encrypt, false);
			if(temp!=null)
				return temp;

			temp = mut3(word, person, encrypt, false);
			if(temp!=null)
				return temp;

			temp = mut4(word, person, encrypt, false);
			if(temp!=null)
				return temp;

			temp = mut5(word, person, encrypt, false);
			if(temp!=null)
				return temp;

			temp = mut6(word, person, encrypt, false);
			if(temp!=null)
				return temp;

			temp = mut7(word, person, encrypt, false);
			if(temp!=null)
				return temp;

			temp = mut8(word, person, encrypt, false);
			if(temp!=null)
				return temp;

			temp = mut9(word, person, encrypt, false);
			if(temp!=null)
				return temp;

			temp = mut10(word, person, encrypt, false);
			if(temp!=null)
				return temp;

			temp = mut11(word, person, encrypt, false);
			if(temp!=null)
				return temp;

			temp = mut12(word, person, encrypt, false);
			if(temp!=null)
				return temp;

			//METHOD TO DO DOUBLE WORD MUTATIONS
			//IS THIS NECCESARY?
			//NOT CURRENTLY BEING USED
			//TAKES ABOUT 45 SECONDS TO RUN
			temp = doublesMut(word, person, encrypt);
			if(temp!=null)
				return temp;

			i++;
		}

		//		found = pwMatch(salt+""+pw, encrypt);
		//send in each attempted password to see if it matches
		//System.out.println(encrypt.crypt("(b", "amazing"));

		return null;
	}



	//	prepend a character to the string, e.g., @string;
	public static String mut1(String word, SystemUser person, jcrypt w, boolean isDouble) {
		String temp = "";
		for(int i=0; i< chars.length; i++) {
			temp = chars[i] + word;
			if(pwMatch(temp, person, w)) 
				return temp;
			else if(isDouble)
				return temp;
		}
		return null;
	}

	//	append a character to the string, e.g., string9;
	public static String mut2(String word, SystemUser person, jcrypt w, boolean isDouble) {
		String temp = "";
		for(int i=0; i< chars.length; i++) {
			temp = word + chars[i];
			if(pwMatch(temp, person, w)) 
				return temp;
			else if(isDouble)
				return temp;
		}
		return null;
	}

	//	delete the first character from the string, e.g., tring;
	public static String mut3(String word, SystemUser person, jcrypt w, boolean isDouble) {
		String temp = word.substring(1, word.length());
		if(pwMatch(temp, person, w)) 
			return temp;
		else if(isDouble)
			return temp;
		return null;
	}

	//	delete the last character from the string, e.g., strin;
	public static String mut4(String word, SystemUser person, jcrypt w, boolean isDouble) {
		String temp = word.substring(0, word.length()-1);
		if(pwMatch(temp, person, w)) 
			return temp;
		else if(isDouble)
			return temp;
		return null;
	}

	//	reverse the string, e.g., gnirts;
	public static String mut5(String word, SystemUser person, jcrypt w, boolean isDouble) {
		String temp = "";
		for (int i = word.length() - 1 ; i >= 0 ; i-- ) {
			temp += word.charAt(i);	
		}
		if(pwMatch(temp, person, w)) 
			return temp;
		else if(isDouble)
			return temp;
		return null;
	}

	//	duplicate the string, e.g., stringstring;
	public static String mut6(String word, SystemUser person, jcrypt w, boolean isDouble) {
		String temp = word + "" + word;
		if(pwMatch(temp, person, w)) 
			return temp;
		else if(isDouble)
			return temp;
		return null;
	}

	//	reflect the string, e.g., stringgnirts or gnirtsstring;
	public static String mut7(String word, SystemUser person, jcrypt w, boolean isDouble) {
		String temp = "";
		for (int i = word.length() - 1 ; i >= 0 ; i-- ) {
			temp += word.charAt(i);	
		}
		String temp2 = word +temp;
		if(pwMatch(temp2, person, w)) 
			return temp2;
		else if(isDouble)
			return temp;
		temp = temp+word;
		if(pwMatch(temp, person, w)) 
			return temp;
		else if(isDouble)
			return temp;

		return null;
	}

	//	uppercase the string, e.g., STRING;
	public static String mut8(String word, SystemUser person, jcrypt w, boolean isDouble) {
		String temp = word.toUpperCase();
		if(pwMatch(temp, person, w)) 
			return temp;
		else if(isDouble)
			return temp;
		return null;
	}

	//	lowercase the string, e.g., string;
	public static String mut9(String word, SystemUser person, jcrypt w, boolean isDouble) {
		String temp = word.toLowerCase();
		if(pwMatch(temp, person, w)) 
			return temp;
		else if(isDouble)
			return temp;
		return null;
	}

	//	capitalize the string, e.g., String;
	public static String mut10(String word, SystemUser person, jcrypt w, boolean isDouble) {
		String temp = word.substring(0, 1).toUpperCase() + "" + word.substring(1, word.length());
		if(pwMatch(temp, person, w)) 
			return temp;
		else if(isDouble)
			return temp;
		return null;
	}

	//	ncapitalize the string, e.g., sTRING;
	public static String mut11(String word, SystemUser person, jcrypt w, boolean isDouble) {
		String temp = word.substring(0, 1) + "" + word.substring(1, word.length()).toUpperCase();
		if(pwMatch(temp, person, w)) 
			return temp;
		else if(isDouble)
			return temp;
		return null;
	}

	//	toggle case of the string, e.g., StRiNg or sTrInG;
	public static String mut12(String word, SystemUser person, jcrypt w, boolean isDouble) {
		char [] temp = word.toCharArray();
		String t = "";
		String build = "";
		for(int i =0; i<word.length(); i++) {
			if(i%2 == 0) {
				t = "" + temp[i];
				build += t.toUpperCase();
			}
			else
				build += "" + temp[i];
		}
		if(pwMatch(build, person, w)) 
			return build;
		else if(isDouble)
			return build;

		build = "";
		for(int j =0; j<word.length(); j++) {
			if(j%2 != 0) {
				t = "" + temp[j];
				build += t.toUpperCase();
			}
			else
				build += "" + temp[j];
		}
		if(pwMatch(build, person, w)) 
			return build;
		else if(isDouble)
			return build;

		return null;
	}



	//Method given the new permutation, checks the jcrypt to see if they match
	public static boolean pwMatch(String text, SystemUser person, jcrypt checker) {
		String enc = jcrypt.crypt(person.salt, text);
		if(enc.equals(person.encField)) {
			return true;
		}
		return false;
	}


	//METHOD TO DO DOUBLE MUTATIONS ON EACH WORD
	//IS THIS REALLY NEEDED?
	//144 POSSIBLE COMBINATIONS
	private static String doublesMut(String word, SystemUser person, jcrypt encrypt) {
		String temp = "";
		//temp = mut1(mut1(word, person, encrypt, true), person, encrypt, false);
		//		temp = mut2(mut4(word, person, encrypt, true), person, encrypt, false);
		//		if(temp != null)
		//			return temp;

		String first = "";
		for(int i=1; i<12; i++) {
			switch (i) {
			case 1: 					
				first = mut1(word, person, encrypt, true);
				break;
			case 2:
				first = mut2(word, person, encrypt, true);
				break;
			case 3: 					
				first = mut3(word, person, encrypt, true);
				break;
			case 4:
				first = mut4(word, person, encrypt, true);
				break;
			case 5: 					
				first = mut5(word, person, encrypt, true);
				break;
			case 6:
				first = mut6(word, person, encrypt, true);
				break;
			case 7: 					
				first = mut7(word, person, encrypt, true);
				break;
			case 8:
				first = mut8(word, person, encrypt, true);
				break;
			case 9: 					
				first = mut9(word, person, encrypt, true);
				break;
			case 10:
				first = mut10(word, person, encrypt, true);
				break;
			case 11: 					
				first = mut11(word, person, encrypt, true);
				break;
			case 12:
				first = mut12(word, person, encrypt, true);
				break;
			}

			for(int j=1; j<12; j++) {
				//outside method
				switch(j) {
				case 1: 					
					temp = mut1(first, person, encrypt, false);
					if(temp != null)
						return temp;
					break;
				case 2:
					temp = mut2(first, person, encrypt, false);
					if(temp != null)
						return temp;
					break;
				case 3: 		
					temp = mut3(first, person, encrypt, false);
					if(temp != null)
						return temp;
					break;
				case 4:
					temp = mut4(first, person, encrypt, false);
					if(temp != null)
						return temp;
					break;
				case 5: 					
					temp = mut5(first, person, encrypt, false);
					if(temp != null)
						return temp;
					break;
				case 6:
					temp = mut6(first, person, encrypt, false);
					if(temp != null)
						return temp;
					break;
				case 7: 					
					temp = mut7(first, person, encrypt, false);
					if(temp != null)
						return temp;
					break;
				case 8:
					temp = mut8(first, person, encrypt, false);
					if(temp != null)
						return temp;
					break;
				case 9: 					
					temp = mut9(first, person, encrypt, false);
					if(temp != null)
						return temp;
					break;
				case 10:
					temp = mut10(first, person, encrypt, false);
					if(temp != null)
						return temp;
					break;
				case 11: 					
					temp = mut11(first, person, encrypt, false);
					if(temp != null)
						return temp;
					break;
				case 12:
					temp = mut12(first, person, encrypt, false);
					if(temp != null)
						return temp;
					break;

				}//close switch
			}//close for inner loop
		}//close outer for loop
		return null;
	}



}
