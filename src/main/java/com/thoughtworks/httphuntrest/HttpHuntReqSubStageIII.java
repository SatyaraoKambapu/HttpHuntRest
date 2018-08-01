package com.thoughtworks.httphuntrest;

public class HttpHuntReqSubStageIII {

	public String name;
	public int timeUsedInMinutes;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getTimeUsedInMinutes() {
		return timeUsedInMinutes;
	}

	public void setTimeUsedInMinutes(int timeUsedInMinutes) {
		this.timeUsedInMinutes = timeUsedInMinutes;
	}

	@Override
	public String toString() {
		return "HttpHuntReqSubStageIII [name=" + name + ", timeUsedInMinutes="
				+ timeUsedInMinutes + "]";
	}

}
