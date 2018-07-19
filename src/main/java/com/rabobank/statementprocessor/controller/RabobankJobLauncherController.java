package com.rabobank.statementprocessor.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Job Launcher in Spring batch
 * 
 * @author skambapu
 * 
 */
@RestController
@EnableScheduling
public class RabobankJobLauncherController {
	@Autowired
	JobLauncher jobLauncher;

	@Autowired
	Job job;

	Logger logger = LoggerFactory.getLogger(this.getClass());

	@RequestMapping("/launchjob")
	public String handle() throws Exception {

		String msg = "No Job Done Yet";
		try {
			JobParameters jobParameters = new JobParametersBuilder().addLong(
					"time", System.currentTimeMillis()).toJobParameters();
			jobLauncher.run(job, jobParameters);
			msg = "Job Done. Please check the results in console.";
		} catch (Exception e) {
			logger.error(e.getMessage());
			msg = e.getMessage();
		}

		return msg;
	}

	@RequestMapping("/")
	public String doBatchProcecss() throws Exception {
		return handle();
	}
}
