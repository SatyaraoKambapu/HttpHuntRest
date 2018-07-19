package com.rabobank.statementprocessor.batch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;

public class BatchJobCompletionListener extends JobExecutionListenerSupport {

	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public void afterJob(JobExecution jobExecution) {
		if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
			logger.info("Batch Job Completed Successfully");
		} else if (jobExecution.getStatus() == BatchStatus.FAILED) {
			logger.error("Batch Job is failed.");
		}

	}

	@Override
	public void beforeJob(JobExecution jobExecution) {
		logger.info("Batch Job Is going to be Executed.");
	}
}
