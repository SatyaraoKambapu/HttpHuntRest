package com.rabobank.statementprocessor.batch;

import java.util.List;

import org.springframework.batch.item.ItemWriter;

import com.rabobank.statementprocessor.reportgen.FailureRecordsReportGenerator;
import com.rabobank.statementprocessor.validator.CustomerRecordValidator;

public class CsvConsoleWriter<CustomerRecord> implements
		ItemWriter<CustomerRecord> {
	@SuppressWarnings("unchecked")
	@Override
	public void write(List<? extends CustomerRecord> listCustomerRecords)
			throws Exception {
		CustomerRecordValidator validator = new CustomerRecordValidator();
		validator
				.validate((List<com.rabobank.statementprocessor.entity.CustomerRecord>) listCustomerRecords);
		FailureRecordsReportGenerator.getInstance()
				.generateFailureRecordsReport(
						validator.getInvalidCustomerRecords());
	}

}
