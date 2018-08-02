package com.thoughtworks.httphuntrest;

import java.util.Arrays;

/**
 * Input Request for Stage3 in the Game.
 * 
 * @author skambapu
 * 
 */
public class HttpHuntRequestStageIII {

	public HttpHuntReqSubStageIII[] toolsSortedOnUsage;

	public HttpHuntReqSubStageIII[] getToolsSortedOnUsage() {
		return toolsSortedOnUsage;
	}

	public void setToolsSortedOnUsage(
			HttpHuntReqSubStageIII[] toolsSortedOnUsage) {
		this.toolsSortedOnUsage = toolsSortedOnUsage;
	}

	@Override
	public String toString() {
		return "HttpHuntRequestStageIII [toolsSortedOnUsage="
				+ Arrays.toString(toolsSortedOnUsage) + "]";
	}

}
