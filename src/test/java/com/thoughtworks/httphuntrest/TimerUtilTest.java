package com.thoughtworks.httphuntrest;

import java.text.ParseException;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

public class TimerUtilTest {

	String s = null;

	@Before
	public void setUp() {
		s = "{\"toolUsage\":[{\"name\":\"knife\",\"useStartTime\":\"2017-01-30 10:00:00\",\"useEndTime\":\"2017-01-30 10:10:00\"},{\"name\":\"guns\",\"useStartTime\":\"2017-01-30 10:15:00\",\"useEndTime\":\"2017-01-30 10:20:00\"},{\"name\":\"guns\",\"useStartTime\":\"2017-01-30 11:00:00\",\"useEndTime\":\"2017-01-30 11:10:00\"},{\"name\":\"knife\",\"useStartTime\":\"2017-01-30 11:10:00\",\"useEndTime\":\"2017-01-30 11:20:00\"},{\"name\":\"rope\",\"useStartTime\":\"2017-01-30 13:00:00\",\"useEndTime\":\"2017-01-30 14:00:00\"}]}";
	}

	@Test
	public void testGetHttpHuntRequestWithTimer() throws ParseException {
		HttpHuntRequestStageIII httpHuntRequestStageIII = TimerUtil
				.getHttpHuntRequest(s);
		Assert.assertEquals("rope",
				httpHuntRequestStageIII.getToolsSortedOnUsage()[0].getName());
		Assert.assertEquals("knife",
				httpHuntRequestStageIII.getToolsSortedOnUsage()[1].getName());
		Assert.assertEquals("guns",
				httpHuntRequestStageIII.getToolsSortedOnUsage()[2].getName());
	}
}
