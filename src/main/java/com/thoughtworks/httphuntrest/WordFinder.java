package com.thoughtworks.httphuntrest;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

public class WordFinder {

	public String[] getMatchedStrings(String msg, JSONArray array) {
		List<String> list = new ArrayList<>();
		for (Object str1 : array) {
			String str = (String) str1;
			char[] charArray = str.toCharArray();
			int index = 0;
			int count = 0;
			for (char c : charArray) {
				char[] charsInInputStr = msg.toCharArray();
				int firstCharIndex = msg.indexOf(str.charAt(index));
				if (firstCharIndex == -1) {

				} else {
					if (msg.substring(firstCharIndex).contains(
							String.valueOf(c))) {
						count++;
					}

				}
				index++;
			}
			if (count == str.length()) {
				list.add(str);
			}
		}
		int i = 0;
		String[] resArray = new String[list.size()];
		for (String string : list) {
			resArray[i++] = string;
		}

		return resArray;

	}
}
