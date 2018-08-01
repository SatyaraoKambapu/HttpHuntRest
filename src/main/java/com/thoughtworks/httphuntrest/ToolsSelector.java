package com.thoughtworks.httphuntrest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ToolsSelector {

	public static void sortToolsByWeight(List<Tool> tools) {
		Collections.sort(tools, new Comparator<Tool>() {
			public int compare(Tool o1, Tool o2) {
				return (int) (o1.getWeight() - o2.getWeight());
			}
		});
	}

	public static List<Tool> selectTools(List<Tool> tools, double max_weight) {
		int sum = 0;
		List<Tool> selectedTools = new ArrayList<>();
		for (Tool tool : tools) {
			double w1 = tool.getWeight();
			sum += w1;
			if (sum <= max_weight && (sum - w1) <= w1) {
				selectedTools.add(tool);
			}
		}
		return selectedTools;
	}

	public static void sortToolsByValue(List<Tool> tools) {
		Collections.sort(tools, new Comparator<Tool>() {
			public int compare(Tool o1, Tool o2) {
				return (int) (o2.getValue() - o1.getValue());
			}
		});
	}

	public static String[] sortedToolNames(List<Tool> tools) {
		String[] names = new String[tools.size()];
		int index = 0;
		for (Tool tool : tools) {
			names[index++] = tool.getName();
		}
		return names;
	}
}
