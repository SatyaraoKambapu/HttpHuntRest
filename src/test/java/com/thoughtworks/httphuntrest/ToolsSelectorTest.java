package com.thoughtworks.httphuntrest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import com.thoughtworks.httphuntrest.comparators.ToolValueComparator;
import com.thoughtworks.httphuntrest.comparators.ToolWeightComparator;
import com.thoughtworks.httphuntrest.comparators.ToolsChainedComparator;

public class ToolsSelectorTest {

	@Before
	public void setUp() {

	}

	@Test
	public void testSelectTools() {
		List<Tool> tools = new ArrayList<>();
		Tool t1 = new Tool();
		t1.setWeight(1);
		t1.setName("knife");
		t1.setValue(80);
		tools.add(t1);
		Tool t2 = new Tool();
		t2.setName("guns");
		t2.setWeight(5);
		t2.setValue(90);
		tools.add(t2);
		Tool t3 = new Tool();
		t3.setName("rope");
		t3.setWeight(8);
		t3.setValue(600);
		tools.add(t3);
		Tool t4 = new Tool();
		t4.setName("water");
		t4.setWeight(8);
		t4.setValue(700);
		tools.add(t4);
		Collections.sort(tools, new ToolsChainedComparator(
				new ToolValueComparator(), new ToolWeightComparator()));
		List<Tool> toolsSelected = ToolsSelector.selectTools(tools, 15);
		String[] names = ToolsSelector.sortedToolNames(toolsSelected);
		Assert.assertEquals("water", names[0]);
		Assert.assertEquals("guns", names[1]);
		Assert.assertEquals("knife", names[2]);
	}

}
