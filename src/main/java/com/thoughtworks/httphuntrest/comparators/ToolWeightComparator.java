package com.thoughtworks.httphuntrest.comparators;

import java.util.Comparator;

import com.thoughtworks.httphuntrest.Tool;

public class ToolWeightComparator implements Comparator<Tool> {

	@Override
	public int compare(Tool o1, Tool o2) {
		return o1.getWeight() - o2.getWeight();
	}
}
