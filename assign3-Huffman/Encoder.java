//Assignment 3
//Alex Irion, Semur Nabiev

import java.io.*;
import java.util.Random;
import java.util.Scanner;


public class Encoder {

	static String filename = "";  
	static int numOfCharsCreating = 0;
	static String[] alphabet= new String[]{ "a", "b", "c", "d", "e", "f", "g", 
			"h", "i","j", "k", "l", "m", "n", 
			"o", "p", "q", "r", "s", "t", "u", 
			"v", "w", "x", "y", "z"};
	static String[] alphabet_2= new String[alphabet.length*alphabet.length];
	static String[] encoding_1st_level  = new String[26];
	static String[] encoding_2nd_level  = new String[encoding_1st_level.length*encoding_1st_level.length];
	static int divisor = 0;  
	static int divisor_2 = 0;  
	static boolean dbug = false;
	static TreeNode root = null ;

	public static void main(String[] args) {

		if(args.length == 2) {
			filename = args[0];	
			numOfCharsCreating = Integer.parseInt(args[1]); 	 
		}
		else {
			numOfCharsCreating = 2000;
			filename = "Frequency.txt";
		}
		try {
			File inFile = new File(filename);
			Scanner fileScanner = new Scanner (inFile);

			//Read in all of the values from the file. Put in a 
			//frequency array and add up all of the numbers for the divisor
			int [] frequencies = new int[alphabet.length];
			int [] frequencies_2 = new int[frequencies.length*frequencies.length];
			int [] ranges = new int[alphabet.length];
			int [] ranges_2 = new int[ranges.length*ranges.length];
			int index = 0, temp = 0, count =0;

			while(fileScanner.hasNextLine()) {
				if(numOfCharsCreating%2==1) 
					numOfCharsCreating +=1; 
				temp = fileScanner.nextInt();
				frequencies[index]= temp;
				count += temp;
				ranges[index] = count;
				divisor += temp;

				index++; 
			} 
			while(index<alphabet.length) {
				frequencies[index] = -1;  //tells us where the array ends
				index++;
			} 

			//build a priority queue (in order of frequencies) made of TreeNodes
			PriorityQueue<TreeNode> initialPQ_1st_level = initialPut(frequencies,0);

			//build a huffman tree based upon that priority queue. 
			PriorityQueue<TreeNode> oneElement = buildHuffmanTree(initialPQ_1st_level);

			//Get the last element in the priority queue (should be the root to the entire tree) and dequeue it

			TreeNode one = oneElement.dequeue();
			root = one;

			//Find the new encoding based on the Huffman tree, put codes in "encoding" array
			getEncoding(one, frequencies,0);

			//Create files level 1
			//last input is the mode
			createTextFile(numOfCharsCreating, ranges, frequencies, "testText",0);
			int bitsActuallyWritten1 = createEncodedFile("testText.enc1",0);
			decodeTextFile("testText.enc1", "testText.dec1");

			printEncoding(0);

			temp = 0;
			index = 0;
			for(int i=0;i<frequencies.length;i++) { 
				for(int r=0;r<frequencies.length;r++) { 

					alphabet_2[index] = alphabet[i]+alphabet[r];  
					int freq = frequencies[r]*frequencies[i];  

					boolean block = frequencies[r]==-1 || frequencies[i]==-1;
					if(!block){ 

						frequencies_2[index] = freq; 
						temp += freq;
						divisor_2 = temp;
						ranges_2[index] = temp;

					}
					index++;
				}
			}    
			while(index<alphabet.length) {
				frequencies[index] = -1;  //tells us where the array ends
				index++;
			}

			PriorityQueue<TreeNode> initialPQ_2nd_level = initialPut(frequencies_2,1);
			PriorityQueue<TreeNode> twoElement = buildHuffmanTree(initialPQ_2nd_level);
			TreeNode two = twoElement.dequeue();
			root = two;
			getEncoding(two, frequencies_2,1);

			//Create files level 2 
			//last input is the mode 
			int bitsActuallyWritten2 = createEncodedFile("testText.enc2",1);
			decodeTextFile("testText.enc2", "testText.dec2"); 
			printEncoding(1);


			//Measure the effiecieny of encodeing (compare with entrophy)
			//Determine the entropy of the file
			double entropy_1 = calculateEntropy(frequencies,divisor,0); 
			double entropy_2 = calculateEntropy(frequencies_2,divisor_2,1); 
			printEfficiency(frequencies, bitsActuallyWritten1, bitsActuallyWritten2,entropy_1,entropy_2 ); 

			//make it work for 2 letters in a row
			fileScanner.close();
		}
		catch(FileNotFoundException e) {
			System.out.println("Problem finding the file. " + e);
		}
	}

	private static void createTextFile(int numOfCharsCreating, int [] ranges, 
			int [] frequencies, String fileName, int mode) {

		//        System.out.println("##########################################");
		//        System.out.println("####### GENERATING RANDOM NUMBERS ########");
		//        System.out.println("##########################################");
		PrintWriter writer;
		try {
			writer = new PrintWriter(fileName);
			Random rn = new Random();
			boolean inRange;
			int randomNumber = 0;

			//create 'numOfCharsCreating' # of chars input from command line
			for(int i=0; i<numOfCharsCreating; i++) {
				inRange=false;
				if(mode==0)
					randomNumber = rn.nextInt(divisor);
				else
					randomNumber = rn.nextInt(divisor_2);
				//loops through ranges array to find if the random value is in the range

				for(int index = 0; index < ranges.length && frequencies[index] !=-1 && !inRange; index++) {
					//if inRange, write that character. it should always be in range? right?
					if(randomNumber < ranges[index]) { 

						if(mode==0){
							writer.print(alphabet[index] + ""); 
							//                            System.out.println(alphabet[index]+" "+randomNumber);
							inRange = true;

						}else{
							writer.print(alphabet_2[index] + ""); 
							//                            System.out.println(alphabet_2[index]+" "+randomNumber);
							inRange = true;
						}
					}
				}
			}
			writer.close();
		} 
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		//        System.out.println("##########################################");
		//        System.out.println();
	}

	//uses the text in the testText.txt file and creates testText.enc1
	private static int createEncodedFile(String fileName, int mode) {

		PrintWriter writer;
		int countBitsWritten =0;
		try {
			Scanner s = new Scanner(new File("testText"));
			writer = new PrintWriter(fileName);
			String temp = "";
			boolean found;

			while(s.hasNext()) {

				String line = s.nextLine();
				for (int a = 0; a < line.length(); a++) {

					temp = line.charAt(a) + "";
					found = false;

					if(mode==0){

						temp = line.charAt(a) + "";
						found = false;
						for(int i =0; i<alphabet.length && !found; i++) {

							if(temp.equals(alphabet[i])) {
								//                                System.out.println(alphabet[i]+" : "+encoding_1st_level[i]);

								writer.print(encoding_1st_level[i]);
								found=true;
								countBitsWritten += encoding_1st_level[i].length();
							}
						}
					}
					else{

						temp = line.charAt(a) +""+ line.charAt(a+1);
						found = false;
						for(int i =0; i<alphabet_2.length && !found; i++) {

							if(temp.equals(alphabet_2[i])) {
								//                                System.out.println(alphabet_2[i]+" : "+encoding_2nd_level[i]);

								writer.print(encoding_2nd_level[i]);
								found=true;
								countBitsWritten += encoding_2nd_level[i].length();
							}
						} 
						a++;
					}
				}

			}
			s.close();
			writer.close();
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		} 
		return countBitsWritten;
	}

	private static void decodeTextFile(String scanFile, String writeFile) {

		TreeNode temp = root;
		PrintWriter writer;
		try {
			Scanner s = new Scanner(new File(scanFile));
			writer = new PrintWriter(writeFile);

			String chunk = ""; 

			while(s.hasNextLine()) {

				String line = s.nextLine(); 
				for (int counter = 0; counter < line.length(); counter++) {

					if(temp.isLeaf()){
						writer.print(temp.myValue);
						chunk = "";
						temp = root;
						counter--;
					}else if(line.charAt(counter) == '0') {
						chunk += line.charAt(counter) + "";   
						temp = temp.getLeft(); 
					}else if(line.charAt(counter) == '1') {
						chunk += line.charAt(counter) + "";   
						temp = temp.getRight();
					} 
				} 
				if(temp.isLeaf()){ 
					writer.print(temp.myValue); 
				}
			} 
			s.close();
			writer.close();
		} 
		catch (FileNotFoundException e) {
			e.printStackTrace();
		} 
	}

	//Recursive method that traverses the Huffman tree to get all of the 
	//encodings and put them in an array
	private static void getEncoding(TreeNode node, int[] frequencies,int mode) {
		encodingHelper(node,frequencies,"",mode);  
	}

	//Recursive helper method
	private static void encodingHelper(TreeNode node, int[] frequencies,String str,int mode) {
		if(node.isLeaf()){

			boolean stop = false;
			for(int i=0;i<frequencies.length && !stop && frequencies[i] !=-1;i++){

				if(mode==0 && alphabet[i].equals(node.getValue())){
					encoding_1st_level[i] = str;
					stop = true;
				}else if(mode==1 && alphabet_2[i].equals(node.getValue())){

					encoding_2nd_level[i] = str;
					stop = true;
				}
			}
		}
		else{
			encodingHelper(node.getLeft(),frequencies,str+"0",mode); 
			encodingHelper(node.getRight(),frequencies,str+"1",mode);  
		} 
	}

	//Calculates and prints the entropy based on the frequencies read from the input file
	//Entropy = probability * (log base 2(probability))
	public static double calculateEntropy(int [] frequencies,int div,int mode) {
		double entropy = 0.0;
		double probability = 0.0; 

		for(int i = 0; i < frequencies.length && frequencies[i] != -1; i ++){

			if(frequencies[i] != 0){
				probability = ( (double)frequencies[i] )/div;  
				entropy += probability * Math.log(probability)/Math.log(2); 
			}
		}
		entropy = -entropy;
		if(mode==1)
			entropy /= 2; 
		return entropy;
	}

	private static PriorityQueue<TreeNode> initialPut(int[] frequencies, int mode) {

		PriorityQueue<TreeNode> pq = new PriorityQueue<TreeNode>();
		for(int i = 0; i < frequencies.length && frequencies[i] != -1; i ++) {
			//we need to make a node out with the letter and its corresponding frequency
			TreeNode temp;
			if(mode==0)
				temp = new TreeNode(alphabet[i], frequencies[i]);
			else
				temp = new TreeNode(alphabet_2[i], frequencies[i]);

			if(mode == 1 && temp.myWeight != 0){ 
				pq.enqueue(temp);
			}else if(mode==0){  
				pq.enqueue(temp);
			}
		} 
		return pq;
	}

	//Using the sorted priority queue, build the Huffman tree
	private static PriorityQueue<TreeNode> buildHuffmanTree(PriorityQueue<TreeNode> pq) {

		final int MIN_SIZE = 2;
		final String NON_CHAR_NODE = "1";
		//go through priority queue and make it a single tree
		while(pq.size >= MIN_SIZE){
			TreeNode left = pq.dequeue();
			TreeNode right = pq.dequeue();
			TreeNode temp = new TreeNode(NON_CHAR_NODE, left.getWeight() + right.getWeight());
			temp.setLeft(left);
			temp.setRight(right);	
			pq.enqueue(temp);
		} 

		return pq;
	}

	//Prints the Huffman character Encoding
	public static void printEncoding(int mode) {

		System.out.println("##########################################");
		System.out.println("############# ENCODING SCHEME ############");
		System.out.println("##########################################"); 
		System.out.println("Character\tCode");

		if(mode==0){
			for(int i=0; i<encoding_1st_level.length ; i++) {

				if(mode==0 && encoding_1st_level[i] != null)
					System.out.println(alphabet[i] + "\t\t" + encoding_1st_level[i]); 
			}
		}else{
			for(int i=0; i<encoding_2nd_level.length ; i++) {

				if(mode==1 && encoding_2nd_level[i] != null)
					System.out.println(alphabet_2[i] + "\t\t" + encoding_2nd_level[i]);
			} 
		}


		System.out.println("##########################################");
		System.out.println();
	}

	//Prints the number of bits used in the Naive encoding, and the Huffman encoding
	private static void printEfficiency(int[] frequencies, int bitsActuallyWritten1, int bitsActuallyWritten2,
			double entr1, double entr2) {
		int bitsUsed = 0; 
		for(int i = 0; i < frequencies.length && frequencies[i] != -1; i ++) {
			bitsUsed += frequencies[i]*encoding_1st_level[i].length();
		}
		System.out.println(); 
		System.out.println("Entropy               level 1: " + entr1);
		System.out.println("Bits actually written level 1: " + bitsActuallyWritten1); 
		System.out.println("Entropy               level 2: " + entr2);
		System.out.println("Bits actually written level 2: " + bitsActuallyWritten2);
		System.out.println("Entropy improvement: " + ((entr1/entr2)-1)*100 +"%"); 
		System.out.println("Bit ratio improvement: " + (((double)bitsActuallyWritten1/(double)bitsActuallyWritten2)-1)*100 +"%"); 

		System.out.println();
	}
}


