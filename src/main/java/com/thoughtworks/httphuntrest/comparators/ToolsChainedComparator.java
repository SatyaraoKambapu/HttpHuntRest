package com.thoughtworks.httphuntrest.comparators;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import com.thoughtworks.httphuntrest.Tool;

public class ToolsChainedComparator implements Comparator<Tool> {

	private List<Comparator<Tool>> listComparators;

	@SafeVarargs
	public ToolsChainedComparator(Comparator<Tool>... comparators) {
		this.listComparators = Arrays.asList(comparators);
	}

	@Override
	public int compare(Tool o1, Tool o2) {
		for (Comparator<Tool> comparator : listComparators) {
			int result = comparator.compare(o1, o2);
			if (result != 0) {
				return result;
			}
		}
		return 0;
	}

}
