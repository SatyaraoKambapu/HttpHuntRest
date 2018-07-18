package com.rabobank.statementprocessor.reader;

import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.file.transform.LineTokenizer;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;

import com.rabobank.statementprocessor.entity.CustomerRecord;

public class FlatFileReader {

	@Bean
	@StepScope
	public FlatFileItemReader<CustomerRecord> csvCustomerRecordReader(
			String filePath) {
		FlatFileItemReader<CustomerRecord> reader = new FlatFileItemReader<CustomerRecord>();
		reader.setResource(new ClassPathResource(filePath));
		reader.setLinesToSkip(1); // Header skip
		reader.setLineMapper(createRecordLineMapper());
		return reader;
	}

	private LineTokenizer createCustomerRecordLineTokenizer() {
		DelimitedLineTokenizer recordLineTokenizer = new DelimitedLineTokenizer();
		recordLineTokenizer.setDelimiter(",");
		recordLineTokenizer.setNames(new String[] { "record_referenceId",
				"accountNumber", "description", "startBalance", "mutation",
				"endBalance" });
		return recordLineTokenizer;
	}

	private FieldSetMapper<CustomerRecord> createCustomerRecordInformationMapper() {
		BeanWrapperFieldSetMapper<CustomerRecord> customerRecordtInformationMapper = new BeanWrapperFieldSetMapper<>();
		customerRecordtInformationMapper.setTargetType(CustomerRecord.class);
		return customerRecordtInformationMapper;
	}

	private LineMapper<CustomerRecord> createRecordLineMapper() {
		DefaultLineMapper<CustomerRecord> customerRecordLineMapper = new DefaultLineMapper<>();

		LineTokenizer studentLineTokenizer = createCustomerRecordLineTokenizer();
		customerRecordLineMapper.setLineTokenizer(studentLineTokenizer);

		FieldSetMapper<CustomerRecord> customerRecordInformationMapper = createCustomerRecordInformationMapper();
		customerRecordLineMapper
				.setFieldSetMapper(customerRecordInformationMapper);

		return customerRecordLineMapper;
	}
}
