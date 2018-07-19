package com.rabobank.statementprocessor.batch;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.rabobank.statementprocessor.common.ErrorMessages;
import com.rabobank.statementprocessor.validator.CustomerRecordValidator;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = { BatchConfig.class, ErrorMessages.class,
		CustomerRecordValidator.class, JobLauncherTestUtils.class })
public class BatchConfigSuccessTest {

	@BeforeClass
	public static void setUp() {
		System.setProperty("filepath",
				"C:\\Users\\skambapu\\Downloads\\assignment\\records.csv");
	}

	@Autowired
	private JobLauncherTestUtils singleJobLauncherTestUtils;

	@Test
	public void testSuccessJob() throws Exception {
		JobExecution jobExecution = singleJobLauncherTestUtils.launchJob();
		Assert.assertEquals(BatchStatus.COMPLETED, jobExecution.getStatus());
	}
}
