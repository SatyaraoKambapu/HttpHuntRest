package com.rabobank.statementprocessor.batch;

import java.util.List;

import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;

import com.rabobank.statementprocessor.entity.CustomerRecord;
import com.rabobank.statementprocessor.reportgen.FailureRecordsReportGenerator;
import com.rabobank.statementprocessor.validator.CustomerRecordValidator;

public class XmlConsoleWriter<CustomerRecords> implements
		ItemWriter<CustomerRecords> {

	@Autowired
	CustomerRecordValidator validator;

	@Override
	public void write(List<? extends CustomerRecords> items) throws Exception {
		CustomerRecords records = items.get(0);
		List<CustomerRecord> list = ((com.rabobank.statementprocessor.entity.CustomerRecords) records)
				.getRecords();
		validator.validate(list);
		FailureRecordsReportGenerator.getInstance()
				.generateFailureRecordsReport(
						validator.getInvalidCustomerRecords());
	}
}
