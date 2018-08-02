package com.thoughtworks.httphuntrest;

/**
 * Decode the encoded message, which we get it from server.
 * 
 * @author skambapu
 * 
 */
public class Encryption {

	public String encrypt(String msg, int key) {
		char[] charsArray = msg.toCharArray();
		char[] encryptedArray = new char[charsArray.length];
		int index = 0;
		for (char c : charsArray) {
			encryptedArray[index++] = ((char) (c + key));
		}

		return new String(encryptedArray);

	}

	public String decrypt(String encryptedStr, int key) {
		char[] charsArray = encryptedStr.toCharArray();
		char[] decryptedArray = new char[charsArray.length];
		int index = 0;
		for (char c : charsArray) {
			int cint = c;
			int u = (cint - 65);
			int l = (cint - 97);
			if (!Character.isLetter(c)) {
				decryptedArray[index] = c;
			} else if (Character.isUpperCase(c) && u < key) {
				decryptedArray[index] = (char) (90 - (key - u - 1));
			} else if (Character.isLowerCase(c) && l < key) {
				decryptedArray[index] = (char) (122 - (key - l - 1));
			} else {
				decryptedArray[index] = ((char) (c - key));
			}
			index++;
		}
		return new String(decryptedArray);

	}
}