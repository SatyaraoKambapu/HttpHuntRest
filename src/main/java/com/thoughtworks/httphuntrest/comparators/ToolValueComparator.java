package com.thoughtworks.httphuntrest.comparators;

import java.util.Comparator;

import com.thoughtworks.httphuntrest.Tool;

public class ToolValueComparator implements Comparator<Tool> {
	@Override
	public int compare(Tool o1, Tool o2) {
		return o2.getValue() - o1.getValue();
	}
}
