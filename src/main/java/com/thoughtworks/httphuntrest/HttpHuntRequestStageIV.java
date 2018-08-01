package com.thoughtworks.httphuntrest;

import java.util.Arrays;

public class HttpHuntRequestStageIV {
	String[] toolsToTakeSorted;

	public String[] getToolsToTakeSorted() {
		return toolsToTakeSorted;
	}

	public void setToolsToTakeSorted(String[] toolsToTakeSorted) {
		this.toolsToTakeSorted = toolsToTakeSorted;
	}

	@Override
	public String toString() {
		return "HttpHuntRequestStageIV [toolsToTakeSorted="
				+ Arrays.toString(toolsToTakeSorted) + "]";
	}

}
