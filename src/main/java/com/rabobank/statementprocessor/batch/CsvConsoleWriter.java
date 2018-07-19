package com.rabobank.statementprocessor.batch;

import java.util.List;

import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;

import com.rabobank.statementprocessor.reportgen.FailureRecordsReportGenerator;
import com.rabobank.statementprocessor.validator.CustomerRecordValidator;

public class CsvConsoleWriter<CustomerRecord> implements
		ItemWriter<CustomerRecord> {

	@Autowired
	CustomerRecordValidator validator;

	@SuppressWarnings("unchecked")
	@Override
	public void write(List<? extends CustomerRecord> listCustomerRecords)
			throws Exception {
		validator
				.validate((List<com.rabobank.statementprocessor.entity.CustomerRecord>) listCustomerRecords);
		FailureRecordsReportGenerator.getInstance()
				.generateFailureRecordsReport(
						validator.getInvalidCustomerRecords());
	}

}
