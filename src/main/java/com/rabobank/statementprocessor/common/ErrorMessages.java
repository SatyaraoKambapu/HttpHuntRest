package com.rabobank.statementprocessor.common;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "error")
public class ErrorMessages {

	public String irrelevantFile;
	public String norecordsFound;
	public String noFilePath;

	public String getIrrelevantFile() {
		return irrelevantFile;
	}

	public void setIrrelevantFile(String irrelevantFile) {
		this.irrelevantFile = irrelevantFile;
	}

	public String getNorecordsFound() {
		return norecordsFound;
	}

	public void setNorecordsFound(String norecordsFound) {
		this.norecordsFound = norecordsFound;
	}

	public String getNoFilePath() {
		return noFilePath;
	}

	public void setNoFilePath(String noFilePath) {
		this.noFilePath = noFilePath;
	}

}
