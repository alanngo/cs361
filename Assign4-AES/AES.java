/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */ 

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 *	Alex Irion and Semur Nabiev
 */
public class AES {

	/**
	 * @param args the command line arguments
	 */
	static String filename = "plaintext.txt";
	static boolean encode = true; //true for encrypt, false for decrypt 
	static int state[][] = new int[4][4];
	static int key[][] = new int[4][8]; //32 byte size
	static int keyExtended[][] = new int[4][60]; //240 byte size needed, but 256 used.

	static String keyText = "key.txt";
	static String inFile; 

	/*
    Number of columns (32-bit words) comprising the State. For this
    standard, Nb = 4. (Also see Sec. 6.3.)
	 */
	static int nb = 4;
	/*
    Number of 32-bit words comprising the Cipher Key. For this
    standard, Nk = 4, 6, or 8. (Also see Sec. 6.3.)
	 */
	static int nk = 8;
	/*
    Number of rounds, which is a function of Nk and Nb (which is
    fixed). For this standard, Nr = 10, 12, or 14. (Also see Sec. 6.3.) 
	 */
	static int nr = 14;

	public static final int[][] sbox = {
			{
				0x63, 0x7c, 0x77, 0x7b, 0xf2, 0x6b, 0x6f, 0xc5, 0x30, 0x01, 0x67, 0x2b, 0xfe, 0xd7, 0xab, 0x76
			}, {
				0xca, 0x82, 0xc9, 0x7d, 0xfa, 0x59, 0x47, 0xf0, 0xad, 0xd4, 0xa2, 0xaf, 0x9c, 0xa4, 0x72, 0xc0
			}, {
				0xb7, 0xfd, 0x93, 0x26, 0x36, 0x3f, 0xf7, 0xcc, 0x34, 0xa5, 0xe5, 0xf1, 0x71, 0xd8, 0x31, 0x15
			}, {
				0x04, 0xc7, 0x23, 0xc3, 0x18, 0x96, 0x05, 0x9a, 0x07, 0x12, 0x80, 0xe2, 0xeb, 0x27, 0xb2, 0x75
			}, {
				0x09, 0x83, 0x2c, 0x1a, 0x1b, 0x6e, 0x5a, 0xa0, 0x52, 0x3b, 0xd6, 0xb3, 0x29, 0xe3, 0x2f, 0x84
			}, {
				0x53, 0xd1, 0x00, 0xed, 0x20, 0xfc, 0xb1, 0x5b, 0x6a, 0xcb, 0xbe, 0x39, 0x4a, 0x4c, 0x58, 0xcf
			}, {
				0xd0, 0xef, 0xaa, 0xfb, 0x43, 0x4d, 0x33, 0x85, 0x45, 0xf9, 0x02, 0x7f, 0x50, 0x3c, 0x9f, 0xa8
			}, {
				0x51, 0xa3, 0x40, 0x8f, 0x92, 0x9d, 0x38, 0xf5, 0xbc, 0xb6, 0xda, 0x21, 0x10, 0xff, 0xf3, 0xd2
			}, {
				0xcd, 0x0c, 0x13, 0xec, 0x5f, 0x97, 0x44, 0x17, 0xc4, 0xa7, 0x7e, 0x3d, 0x64, 0x5d, 0x19, 0x73
			}, {
				0x60, 0x81, 0x4f, 0xdc, 0x22, 0x2a, 0x90, 0x88, 0x46, 0xee, 0xb8, 0x14, 0xde, 0x5e, 0x0b, 0xdb
			}, {
				0xe0, 0x32, 0x3a, 0x0a, 0x49, 0x06, 0x24, 0x5c, 0xc2, 0xd3, 0xac, 0x62, 0x91, 0x95, 0xe4, 0x79
			}, {
				0xe7, 0xc8, 0x37, 0x6d, 0x8d, 0xd5, 0x4e, 0xa9, 0x6c, 0x56, 0xf4, 0xea, 0x65, 0x7a, 0xae, 0x08
			}, {
				0xba, 0x78, 0x25, 0x2e, 0x1c, 0xa6, 0xb4, 0xc6, 0xe8, 0xdd, 0x74, 0x1f, 0x4b, 0xbd, 0x8b, 0x8a
			}, {
				0x70, 0x3e, 0xb5, 0x66, 0x48, 0x03, 0xf6, 0x0e, 0x61, 0x35, 0x57, 0xb9, 0x86, 0xc1, 0x1d, 0x9e
			}, {
				0xe1, 0xf8, 0x98, 0x11, 0x69, 0xd9, 0x8e, 0x94, 0x9b, 0x1e, 0x87, 0xe9, 0xce, 0x55, 0x28, 0xdf
			}, {
				0x8c, 0xa1, 0x89, 0x0d, 0xbf, 0xe6, 0x42, 0x68, 0x41, 0x99, 0x2d, 0x0f, 0xb0, 0x54, 0xbb, 0x16
			}
	};
	public static final int[][] invsbox = {
			{
				0x52, 0x09, 0x6a, 0xd5, 0x30, 0x36, 0xa5, 0x38, 0xbf, 0x40, 0xa3, 0x9e, 0x81, 0xf3, 0xd7, 0xfb
			}, {
				0x7c, 0xe3, 0x39, 0x82, 0x9b, 0x2f, 0xff, 0x87, 0x34, 0x8e, 0x43, 0x44, 0xc4, 0xde, 0xe9, 0xcb
			}, {
				0x54, 0x7b, 0x94, 0x32, 0xa6, 0xc2, 0x23, 0x3d, 0xee, 0x4c, 0x95, 0x0b, 0x42, 0xfa, 0xc3, 0x4e
			}, {
				0x08, 0x2e, 0xa1, 0x66, 0x28, 0xd9, 0x24, 0xb2, 0x76, 0x5b, 0xa2, 0x49, 0x6d, 0x8b, 0xd1, 0x25
			}, {
				0x72, 0xf8, 0xf6, 0x64, 0x86, 0x68, 0x98, 0x16, 0xd4, 0xa4, 0x5c, 0xcc, 0x5d, 0x65, 0xb6, 0x92
			}, {
				0x6c, 0x70, 0x48, 0x50, 0xfd, 0xed, 0xb9, 0xda, 0x5e, 0x15, 0x46, 0x57, 0xa7, 0x8d, 0x9d, 0x84
			}, {
				0x90, 0xd8, 0xab, 0x00, 0x8c, 0xbc, 0xd3, 0x0a, 0xf7, 0xe4, 0x58, 0x05, 0xb8, 0xb3, 0x45, 0x06
			}, {
				0xd0, 0x2c, 0x1e, 0x8f, 0xca, 0x3f, 0x0f, 0x02, 0xc1, 0xaf, 0xbd, 0x03, 0x01, 0x13, 0x8a, 0x6b
			}, {
				0x3a, 0x91, 0x11, 0x41, 0x4f, 0x67, 0xdc, 0xea, 0x97, 0xf2, 0xcf, 0xce, 0xf0, 0xb4, 0xe6, 0x73
			}, {
				0x96, 0xac, 0x74, 0x22, 0xe7, 0xad, 0x35, 0x85, 0xe2, 0xf9, 0x37, 0xe8, 0x1c, 0x75, 0xdf, 0x6e
			}, {
				0x47, 0xf1, 0x1a, 0x71, 0x1d, 0x29, 0xc5, 0x89, 0x6f, 0xb7, 0x62, 0x0e, 0xaa, 0x18, 0xbe, 0x1b
			}, {
				0xfc, 0x56, 0x3e, 0x4b, 0xc6, 0xd2, 0x79, 0x20, 0x9a, 0xdb, 0xc0, 0xfe, 0x78, 0xcd, 0x5a, 0xf4
			}, {
				0x1f, 0xdd, 0xa8, 0x33, 0x88, 0x07, 0xc7, 0x31, 0xb1, 0x12, 0x10, 0x59, 0x27, 0x80, 0xec, 0x5f
			}, {
				0x60, 0x51, 0x7f, 0xa9, 0x19, 0xb5, 0x4a, 0x0d, 0x2d, 0xe5, 0x7a, 0x9f, 0x93, 0xc9, 0x9c, 0xef
			}, {
				0xa0, 0xe0, 0x3b, 0x4d, 0xae, 0x2a, 0xf5, 0xb0, 0xc8, 0xeb, 0xbb, 0x3c, 0x83, 0x53, 0x99, 0x61
			}, {
				0x17, 0x2b, 0x04, 0x7e, 0xba, 0x77, 0xd6, 0x26, 0xe1, 0x69, 0x14, 0x63, 0x55, 0x21, 0x0c, 0x7d
			}
	};


	public static final int[] rcon = {
			0x8d, 0x01, 0x02, 0x04, 0x08, 0x10, 0x20, 0x40, 0x80, 0x1b, 0x36, 0x6c, 0xd8, 0xab, 0x4d, 0x9a,
			0x2f, 0x5e, 0xbc, 0x63, 0xc6, 0x97, 0x35, 0x6a, 0xd4, 0xb3, 0x7d, 0xfa, 0xef, 0xc5, 0x91, 0x39,
			0x72, 0xe4, 0xd3, 0xbd, 0x61, 0xc2, 0x9f, 0x25, 0x4a, 0x94, 0x33, 0x66, 0xcc, 0x83, 0x1d, 0x3a,
			0x74, 0xe8, 0xcb, 0x8d, 0x01, 0x02, 0x04, 0x08, 0x10, 0x20, 0x40, 0x80, 0x1b, 0x36, 0x6c, 0xd8,
			0xab, 0x4d, 0x9a, 0x2f, 0x5e, 0xbc, 0x63, 0xc6, 0x97, 0x35, 0x6a, 0xd4, 0xb3, 0x7d, 0xfa, 0xef,
			0xc5, 0x91, 0x39, 0x72, 0xe4, 0xd3, 0xbd, 0x61, 0xc2, 0x9f, 0x25, 0x4a, 0x94, 0x33, 0x66, 0xcc,
			0x83, 0x1d, 0x3a, 0x74, 0xe8, 0xcb, 0x8d, 0x01, 0x02, 0x04, 0x08, 0x10, 0x20, 0x40, 0x80, 0x1b,
			0x36, 0x6c, 0xd8, 0xab, 0x4d, 0x9a, 0x2f, 0x5e, 0xbc, 0x63, 0xc6, 0x97, 0x35, 0x6a, 0xd4, 0xb3,
			0x7d, 0xfa, 0xef, 0xc5, 0x91, 0x39, 0x72, 0xe4, 0xd3, 0xbd, 0x61, 0xc2, 0x9f, 0x25, 0x4a, 0x94,
			0x33, 0x66, 0xcc, 0x83, 0x1d, 0x3a, 0x74, 0xe8, 0xcb, 0x8d, 0x01, 0x02, 0x04, 0x08, 0x10, 0x20,
			0x40, 0x80, 0x1b, 0x36, 0x6c, 0xd8, 0xab, 0x4d, 0x9a, 0x2f, 0x5e, 0xbc, 0x63, 0xc6, 0x97, 0x35,
			0x6a, 0xd4, 0xb3, 0x7d, 0xfa, 0xef, 0xc5, 0x91, 0x39, 0x72, 0xe4, 0xd3, 0xbd, 0x61, 0xc2, 0x9f,
			0x25, 0x4a, 0x94, 0x33, 0x66, 0xcc, 0x83, 0x1d, 0x3a, 0x74, 0xe8, 0xcb, 0x8d, 0x01, 0x02, 0x04,
			0x08, 0x10, 0x20, 0x40, 0x80, 0x1b, 0x36, 0x6c, 0xd8, 0xab, 0x4d, 0x9a, 0x2f, 0x5e, 0xbc, 0x63,
			0xc6, 0x97, 0x35, 0x6a, 0xd4, 0xb3, 0x7d, 0xfa, 0xef, 0xc5, 0x91, 0x39, 0x72, 0xe4, 0xd3, 0xbd,
			0x61, 0xc2, 0x9f, 0x25, 0x4a, 0x94, 0x33, 0x66, 0xcc, 0x83, 0x1d, 0x3a, 0x74, 0xe8, 0xcb, 0x8d
	};

	final static int[] LogTable = {
			0, 0, 25, 1, 50, 2, 26, 198, 75, 199, 27, 104, 51, 238, 223, 3,
			100, 4, 224, 14, 52, 141, 129, 239, 76, 113, 8, 200, 248, 105, 28, 193,
			125, 194, 29, 181, 249, 185, 39, 106, 77, 228, 166, 114, 154, 201, 9, 120,
			101, 47, 138, 5, 33, 15, 225, 36, 18, 240, 130, 69, 53, 147, 218, 142,
			150, 143, 219, 189, 54, 208, 206, 148, 19, 92, 210, 241, 64, 70, 131, 56,
			102, 221, 253, 48, 191, 6, 139, 98, 179, 37, 226, 152, 34, 136, 145, 16,
			126, 110, 72, 195, 163, 182, 30, 66, 58, 107, 40, 84, 250, 133, 61, 186,
			43, 121, 10, 21, 155, 159, 94, 202, 78, 212, 172, 229, 243, 115, 167, 87,
			175, 88, 168, 80, 244, 234, 214, 116, 79, 174, 233, 213, 231, 230, 173, 232,
			44, 215, 117, 122, 235, 22, 11, 245, 89, 203, 95, 176, 156, 169, 81, 160,
			127, 12, 246, 111, 23, 196, 73, 236, 216, 67, 31, 45, 164, 118, 123, 183,
			204, 187, 62, 90, 251, 96, 177, 134, 59, 82, 161, 108, 170, 85, 41, 157,
			151, 178, 135, 144, 97, 190, 220, 252, 188, 149, 207, 205, 55, 63, 91, 209,
			83, 57, 132, 60, 65, 162, 109, 71, 20, 42, 158, 93, 86, 242, 211, 171,
			68, 17, 146, 217, 35, 32, 46, 137, 180, 124, 184, 38, 119, 153, 227, 165,
			103, 74, 237, 222, 197, 49, 254, 24, 13, 99, 140, 128, 192, 247, 112, 7
	};

	final static int[] AlogTable = {
			1, 3, 5, 15, 17, 51, 85, 255, 26, 46, 114, 150, 161, 248, 19, 53,
			95, 225, 56, 72, 216, 115, 149, 164, 247, 2, 6, 10, 30, 34, 102, 170,
			229, 52, 92, 228, 55, 89, 235, 38, 106, 190, 217, 112, 144, 171, 230, 49,
			83, 245, 4, 12, 20, 60, 68, 204, 79, 209, 104, 184, 211, 110, 178, 205,
			76, 212, 103, 169, 224, 59, 77, 215, 98, 166, 241, 8, 24, 40, 120, 136,
			131, 158, 185, 208, 107, 189, 220, 127, 129, 152, 179, 206, 73, 219, 118, 154,
			181, 196, 87, 249, 16, 48, 80, 240, 11, 29, 39, 105, 187, 214, 97, 163,
			254, 25, 43, 125, 135, 146, 173, 236, 47, 113, 147, 174, 233, 32, 96, 160,
			251, 22, 58, 78, 210, 109, 183, 194, 93, 231, 50, 86, 250, 21, 63, 65,
			195, 94, 226, 61, 71, 201, 64, 192, 91, 237, 44, 116, 156, 191, 218, 117,
			159, 186, 213, 100, 172, 239, 42, 126, 130, 157, 188, 223, 122, 142, 137, 128,
			155, 182, 193, 88, 232, 35, 101, 175, 234, 37, 111, 177, 200, 67, 197, 84,
			252, 31, 33, 99, 165, 244, 7, 9, 27, 45, 119, 153, 176, 203, 70, 202,
			69, 207, 74, 222, 121, 139, 134, 145, 168, 227, 62, 66, 198, 81, 243, 14,
			18, 54, 90, 238, 41, 123, 141, 140, 143, 138, 133, 148, 167, 242, 13, 23,
			57, 75, 221, 124, 132, 151, 162, 253, 28, 36, 108, 180, 199, 82, 246, 1
	};


	public static void main(String[] args) {
		String outputFile = "";

		//        for command line inputs
		//        encode = args[0].equals("e");
		//        filename = args[2];
		//        
		//        if (encode) {
		//            inFile = filename + "";
		//        	outputFile = filename + ".enc";
		//        }
		//        else {
		//            inFile = filename + ".enc";
		//            outputFile = filename + ".dec";
		//        }
		//        
		//        keyText = args[1];

		encode=true;
		keyText = "key.txt";
		inFile = "plaintext.txt";
		outputFile = inFile + ".enc";


		try {
			//Set up input/output files
			File inputFile = new File(inFile);
			File keyFile = new File(keyText); 

			Scanner lineScanner = new Scanner(inputFile);
			Scanner keyScanner = new Scanner(keyFile);
			PrintWriter pWriter = new PrintWriter(outputFile);

			//given the key in a text file, use the algorithm to expand the key
			String keyString = "";

			while (keyScanner.hasNextLine()) {
				keyString += keyScanner.nextLine();
			}
			expandKey(keyString);

			//loops over every line, parse The file by line
			String lineText;
			String finishedText;
			while (lineScanner.hasNextLine()) {
				lineText = lineScanner.nextLine();
				//loop over each array index (each array index holds 1 byte in 
				//binary representing 1 letter
				if (lineText.length() == 32) {
					//PARSE THE PLAIN TEXT INTO "state" ARRAY
					readText(lineText);
					if (encode) {
						encodeFile();
					} else {
						decodeFile();
					}
					if(!encode)
						refractorState();

					finishedText = getSyphertext(state,1);  
					pWriter.print(finishedText);

					if(!encode)
						System.out.println("The decryption of the ciphertext:\n"+finishedText);
				}
			}
			pWriter.close();
			lineScanner.close();
		} catch (FileNotFoundException e) {
			System.out.println("Problem finding the file. " + e);
		}
	}

	/////////////////////////////// /////////////////////////////// /////////////////////////////// ///////////////////////////////


	//EXPAND THE KEY
	public static void expandKey(String keyString) {
		int counter = 0;
		int[] tempArray = new int[32];
		//encode everything in integer notation since java is not built for easy memory management

		for (int i = 0; i < keyString.length(); i += 2) {

			String temp = keyString.charAt(i) + "" + keyString.charAt(i + 1);
			tempArray[counter] = getInt(temp);
			counter++;
		}

		counter = 0;
		for (int r = 0; r < key.length; r++) {
			for (int c = 0; c < key[0].length; c++) {

				key[r][c] = tempArray[counter];

				counter++;
				if (counter == key[0].length)
					counter = 0;
			}

		}

		counter = 0;
		for (int r = 0; r < keyExtended.length; r++) {
			for (int c = 0; c < keyExtended[0].length; c++) {

				if (r < key.length && c < key[0].length) {
					keyExtended[r][c] = key[r][c];
				} else
					keyExtended[r][c] = 0;
				counter++;
			}
		}

		counter = 8;
		while (counter < 60) {

			int[] temp = new int[4];
			int colToRotate = counter - 1;
			temp[0] = keyExtended[0][colToRotate];
			temp[1] = keyExtended[1][colToRotate];
			temp[2] = keyExtended[2][colToRotate];
			temp[3] = keyExtended[3][colToRotate];

			if (counter % 8 == 0) {

				temp = subBytes(rotWord(temp));
				for (int g = 0; g < temp.length; g++) {

					int rconValue = rcon[(counter / 8)];

					if (g != 0)
						rconValue = 0;
					temp[g] = temp[g] ^ rconValue;
				}
			} 
			else if (counter % 8 == 4) {
				temp = subBytes(temp);
			}

			for (int i = 0; i < temp.length; i++) {
				int current = 0;

				current = keyExtended[i][counter - 8] ^ temp[i];
				keyExtended[i][counter] = current;
			}

			counter++;
		}

		System.out.println();

	} 

	public static String getSyphertext(int[][] keyExtended, int mode) {

		String str = ""; 
		for (int r = 0; r < keyExtended.length; r++) {
			for (int c = 0; c < keyExtended[0].length; c++) {

				if (mode == 1)
					str += getHex(keyExtended[r][c]+"");
				else
					str += keyExtended[r][c]+"";
			} 
		}
		return str;
	}

	public static void refractorState() {

		int[][] temp = new int[state.length][state[0].length];
		for (int r = 0; r < state.length; r++) {
			for (int c = 0; c < state[0].length; c++) {
				temp[c][r] = state[r][c];
			}
		}
		for (int r = 0; r < state.length; r++) {
			for (int c = 0; c < state[0].length; c++) {
				state[r][c] = temp[r][c];
			}
		}  
	}

	public static void printSyphertext(int[][] keyExtended, int mode) {

		if(encode)
			System.out.println("The ciphertext:");
		else
			System.out.println("The plaintext :");
		for (int r = 0; r < keyExtended.length; r++) {
			for (int c = 0; c < keyExtended[0].length; c++) {

				if (mode == 1)
					System.out.print(getHex(keyExtended[r][c] + "") + " ");
				else
					System.out.print(keyExtended[r][c] + " ");
			}
			System.out.println();
		}
	}

	//    public static void print2DArray(int[][] keyExtended, int mode) {
	//
	//        System.out.println("#######PRINTING 2D ARRAY#####################");
	//        for (int r = 0; r < keyExtended.length; r++) {
	//            for (int c = 0; c < keyExtended[0].length; c++) {
	//
	//                if (mode == 1)
	//                    System.out.print(getHex(keyExtended[r][c] + "") + " \t");
	//                else
	//                    System.out.print(keyExtended[r][c] + " \t");
	//            }
	//            System.out.println();
	//        }
	//        System.out.println("##########################################");
	//    }
	//
	//    public static void print1DArray(int[] keyExtended) {
	//
	//        System.out.println("#######PRINTING 1D ARRAY#####################");
	//        for (int r = 0; r < keyExtended.length; r++) {
	//
	//            System.out.print(keyExtended[r] + " \t");
	//        }
	//        System.out.println("\n##########################################");
	//    }

	public static String getHex(String s) { 
		return String.format("%02X", Integer.parseInt(s)).toString();
	}


	public static int getInt(String s) {
		String digits = "0123456789ABCDEF";
		s = s.toUpperCase();
		int val = 0;
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			int d = digits.indexOf(c);
			val = 16 * val + d;
		}
		return val;
	}

	static String getBinary(String s) {

		int i = Integer.parseInt(s, 16);
		String bin = Integer.toBinaryString(i);
		return bin;
	}

	private static void readText(String lineText) {

		int[] tempArray = new int[16];
		int counter = 0;
		//encode everything in integer notation since java is not built for easy memory management

		for (int i = 0; i < lineText.length(); i += 2) {

			String temp = lineText.charAt(i) + "" + lineText.charAt(i + 1);
			tempArray[counter] = getInt(temp); 
			counter++;
		}

		counter = 0;
		for (int r = 0; r < state.length; r++) {
			for (int c = 0; c < state[0].length; c++) {

				state[c][r] = tempArray[counter]; 
				counter++; 
			}  
		}
	}

	/////////////////////////////// /////////////////////////////// /////////////////////////////// ///////////////////////////////


	//should run 14 rounds
	//input text is represented as a 4 x 4 array of bytes. (128 bits)
	//The key is represented as a 4 x n array of bytes, where n depends on the key size.(256 bits)
	private static void encodeFile() {

		System.out.println("********************ENCODING********************");

		//ROUNDS OF ENCRYPTION
		for (int i = 0; i < 14; i++) {
			//SUBBYTES
			int Col1[] = new int[4];
			int Col2[] = new int[4];
			int Col3[] = new int[4];
			int Col4[] = new int[4];

			//ADD ROUND KEY
			addRoundKey(i);
			printStateText("addRoundKey", i);
			System.out.println(writeStateText()); 

			int colNum = 0;
			for (int row = 0; row < 4; row++) {
				Col1[row] = state[row][0];
				Col2[row] = state[row][1];
				Col3[row] = state[row][2];
				Col4[row] = state[row][3];
			}

			Col1 = subBytes(Col1);
			Col2 = subBytes(Col2);
			Col3 = subBytes(Col3);
			Col4 = subBytes(Col4);

			//put all of the columns back into the state
			for (int z = 0; z < 4; z++) {
				state[z][0] = Col1[z];
				state[z][1] = Col2[z];
				state[z][2] = Col3[z];
				state[z][3] = Col4[z];
			}
			printStateText("subBytes", i);
			System.out.println(writeStateText()); 

			//SHIFT ROWS
			shiftRows();
			printStateText("shiftRows", i);
			System.out.println(writeStateText()); 

			//MIX COLUMNS

			if (i != 13) {
				for (int h = 0; h < 4; h++)
					mixColumn2(h);
				printStateText("mixColumns", i);
				System.out.println(writeStateText()); 
			} 
		}

		addRoundKey(14);
		printStateText("addRoundKey", 14);
		System.out.println(writeStateText());

		System.out.println();
		printSyphertext(state, 1);

	} 

	//IF IN DECODE MODE, DECODE THE CIPHER TEXT USING THE KEY
	private static void decodeFile() {
		System.out.println("******************DECODING****************");

		System.out.println("start: "+writeStateText());

		refractorState(); 

		//ROUNDS OF ENCRYPTION
		for (int i = 14; i > 0; i--) {
			//SUBBYTES
			int Col1[] = new int[4];
			int Col2[] = new int[4];
			int Col3[] = new int[4];
			int Col4[] = new int[4];

			//ADD ROUND KEY
			addRoundKey(i);
			printStateText("addRoundKey", i);
			System.out.println(writeStateText()); 


			//MIX COLUMNS

			if (i != 14) {
				for (int h = 0; h < 4; h++)
					invMixColumn2(h);
				printStateText("invMixColumns", i);
				System.out.println(writeStateText()); 
			}

			//SHIFT ROWS
			invShiftRows();
			printStateText("invShiftRows", i);
			System.out.println(writeStateText()); 

			for (int row = 0; row < 4; row++) {
				Col1[row] = state[row][0];
				Col2[row] = state[row][1];
				Col3[row] = state[row][2];
				Col4[row] = state[row][3];
			}

			Col1 = invSubBytes(Col1);
			Col2 = invSubBytes(Col2);
			Col3 = invSubBytes(Col3);
			Col4 = invSubBytes(Col4);

			//put all of the columns back into the state
			for (int z = 0; z < 4; z++) {
				state[z][0] = Col1[z];
				state[z][1] = Col2[z];
				state[z][2] = Col3[z];
				state[z][3] = Col4[z];
			}
			printStateText("invSubBytes", i);
			System.out.println(writeStateText()); 
		}

		addRoundKey(0);
		printStateText("addRoundKey", 0);
		System.out.println(writeStateText());

		System.out.println();
		printSyphertext(state, 1);
	} 

	/*
    Transformation in the Cipher and Inverse Cipher in which a Round
    Key is added to the State using an XOR operation. The length of a
    Round Key equals the size of the State (i.e., for Nb = 4, the Round
    Key length equals 128 bits/16 bytes). 
	 */
	static void addRoundKey(int offset) {

		int shift = 0;
		if (offset != 0)
			shift = (offset * state.length);

		int g = 0;
		for (int c = shift; c < state.length + shift; c++) {
			for (int r = 0; r < state[0].length; r++) {
				state[r][g] ^= keyExtended[r][c];
			}
			g++;
		} 

	}


	/**
	 * Replaces all elements in the passed array with values in sbox[][].
	 * @param arr Array whose value will be replaced
	 * @return The array who's value was replaced.
	 */
	public static int[] subBytes(int[] arr) {
		for (int j = 0; j < arr.length; j++) {
			int num = arr[j];
			String hexNum = getHex(num + "");

			char l = hexNum.charAt(0);
			char r = hexNum.charAt(1);

			int column = getIntFromHexChar(l);
			int row = getIntFromHexChar(r); 
			int temp = arr[j];
			arr[j] = sbox[column][row]; 
		}
		return arr;
	}

	/*
    Transformation in the Inverse Cipher that is the inverse of
    SubBytes()
	 */
	public static int[] invSubBytes(int[] arr) {

		for(int k = 0; k < arr.length; k++){
			int val = arr[k];
			String column = "";
			String row = "";        
			for (int j = 0; j < sbox.length; j++) {
				for (int i = 0; i < sbox[0].length; i++) {
					int temp  = sbox[j][i];
					if(temp==val){
						column = getHex(j+"").charAt(1)+"";
						row = getHex(i+"").charAt(1)+""; 
					} 
				}
			}  
			arr[k] = getInt(column+row);  
		}
		return arr;
	}

	/*  
    Transformation in the Cipher that processes the State by cyclically
    shifting the last three rows of the State by different offsets. 
	 */
	static void shiftRows() {

		state[1] = rotWord(state[1]);

		state[2] = rotWord(state[2]);
		state[2] = rotWord(state[2]);

		state[3] = rotWord(state[3]);
		state[3] = rotWord(state[3]);
		state[3] = rotWord(state[3]);

	}


	/*
    Transformation in the Inverse Cipher that is the inverse of
    ShiftRows(). 
	 */
	static void invShiftRows() {

		state[1] = invRotWord(state[1]);

		state[2] = invRotWord(state[2]);
		state[2] = invRotWord(state[2]);

		state[3] = invRotWord(state[3]);
		state[3] = invRotWord(state[3]);
		state[3] = invRotWord(state[3]);
	}

	private static int mul(int a, int b) {
		int inda = (a < 0) ? (a + 256) : a;
		int indb = (b < 0) ? (b + 256) : b;

		if ((a != 0) && (b != 0)) {
			int index = (LogTable[inda] + LogTable[indb]);
			int val = (AlogTable[index % 255]);
			return val;
		} else
			return 0;
	}  

	// In the following two methods, the input c is the column number in
	// your evolving state matrix st (which originally contained 
	// the plaintext input but is being modified).  Notice that the state here is defined as an
	// array of bytes.  If your state is an array of integers, you'll have
	// to make adjustments. 

	public static void mixColumn2(int c) { 
		int a[] = new int[4];

		for (int i = 0; i < 4; i++) { 
			a[i] = state[i][c];
		} 
		state[0][c] = (mul(2, a[0]) ^ a[2] ^ a[3] ^ mul(3, a[1]));
		state[1][c] = (mul(2, a[1]) ^ a[3] ^ a[0] ^ mul(3, a[2]));
		state[2][c] = (mul(2, a[2]) ^ a[0] ^ a[1] ^ mul(3, a[3]));
		state[3][c] = (mul(2, a[3]) ^ a[1] ^ a[2] ^ mul(3, a[0]));

	}  

	public static void invMixColumn2(int c) {

		int a[] = new int[4];

		for (int i = 0; i < 4; i++) {

			a[i] = state[i][c];
		}

		state[0][c] = (mul(14, a[0]) ^ mul(11, a[1]) ^ mul(13, a[2]) ^ mul(9, a[3]));
		state[1][c] = (mul(14, a[1]) ^ mul(11, a[2]) ^ mul(13, a[3]) ^ mul(9, a[0]));
		state[2][c] = (mul(14, a[2]) ^ mul(11, a[3]) ^ mul(13, a[0]) ^ mul(9, a[1]));
		state[3][c] = (mul(14, a[3]) ^ mul(11, a[0]) ^ mul(13, a[1]) ^ mul(9, a[2]));

	}  


	/*  
    Function used in the Key Expansion routine that takes a four-byte
    word and performs a cyclic permutation. 
	 */
	static int[] rotWord(int[] arr) {

		int temp = arr[0];
		for (int c = 0; c < 3; c++) {
			arr[c] = arr[c + 1];
		}
		arr[3] = temp; 
		return arr;
	}

	/*  
    Function used in the Key Expansion routine that takes a four-byte
    word and performs a cyclic permutation. 
	 */
	static int[] invRotWord(int[] arr) {

		int temp = arr[3];
		for (int c = 3; c > 0; c--) {
			arr[c] = arr[c - 1];
		}
		arr[0] = temp; 
		return arr;
	}


	/*  
    Function used in the Key Expansion routine that takes a four-byte
    input word and applies an S-box to each of the four bytes to
    produce an output word. 
	 */
	static int[] subWord(int[] arr) {
		int colsArrayNew[] = new int[4];
		for (int i = 0; i < arr.length; i++) //Sub-Byte subroutine
		{
			for (int j = 0; j < arr.length; j++) {
				int hex = arr[i];

				arr[i] = sbox[hex / 16][hex % 16];
			}
		}
		return colsArrayNew;
	}


	public static int getIntFromHexChar(char ch) {
		String c = ch + "";
		c = c.toLowerCase();
		ch = c.charAt(0);

		int temp = 0;
		if (ch >= 48 && ch < 58)
			temp = (int) ch - 48;
		else if (ch == 'a')
			temp = 10;
		else if (ch == 'b')
			temp = 11;
		else if (ch == 'c')
			temp = 12;
		else if (ch == 'd')
			temp = 13;
		else if (ch == 'e')
			temp = 14;
		else if (ch == 'f')
			temp = 15;
		return temp;
	}

	/*
    Print 2D array
	 */
	static void printArray(int arr[][]) {
		System.out.println("=================================");
		for (int r = 0; r < arr.length; r++) {
			for (int c = 0; c < arr[r].length; c++) {
				System.out.println(arr[r][c]);
			}
			System.out.println();
		}
		System.out.println("=================================");
	}

	public static void printStateText(String stage, int round) {
		System.out.println("After " + stage + "(" + round + "):"); 
	}

	public static String writeStateText() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < state[0].length; i++) {
			for (int j = 0; j < state.length; j++) {
				sb.append(getHex("" + state[j][i])); //Byte.parseByte(state[i][j])  ?
			}
		}
		return sb.toString();
	} 
}