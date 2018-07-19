package com.rabobank.statementprocessor.common;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ErrorMessages {

	@Value("${IRRELEVANT_FILE}")
	static String errorMsg_irrilevantType;
	public static final String IRRELEVANT_FILE = errorMsg_irrilevantType;

	/*
	 * public static final String NO_RECORDS_FOUND = PropertiesManager
	 * .getInstance().getProperties().getProperty("NO_RECORDS_FOUND");
	 * 
	 * public static final String NO_FILE_PATH = PropertiesManager.getInstance()
	 * .getProperties().getProperty("NO_FILE_PATH");
	 * 
	 * public static final String CSV_FILE_COLUMNS = PropertiesManager
	 * .getInstance().getProperties().getProperty("CSV_FILE_COLUMNS");
	 * 
	 * public static final String NO_CSV = PropertiesManager.getInstance()
	 * .getProperties().getProperty("NO_CSV");
	 * 
	 * public static final String NO_XML = PropertiesManager.getInstance()
	 * .getProperties().getProperty("NO_XML");
	 */
}
