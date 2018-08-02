package com.thoughtworks.httphuntrest;

import java.util.ArrayList;
import java.util.List;

public class ToolsSelector {

	public static List<Tool> selectTools(List<Tool> tools, int max_weight) {
		int sum = 0;
		List<Tool> selectedTools = new ArrayList<>();
		for (Tool tool : tools) {
			int w1 = tool.getWeight();
			sum += w1;
			if (sum <= max_weight) {
				selectedTools.add(tool);
			} else {
				sum -= w1;
			}
		}
		return selectedTools;
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
