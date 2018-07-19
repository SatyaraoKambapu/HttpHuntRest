package com.rabobank.statementprocessor.reader;

import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.core.io.FileSystemResource;

import com.rabobank.statementprocessor.entity.CustomerRecord;

public class FlatFileReader extends FlatFileItemReader<CustomerRecord> {

	@SuppressWarnings("unchecked")
	public FlatFileReader(String filepath) {
		// Create reader instance

		// Set input file location
		this.setResource(new FileSystemResource(filepath));

		// Set number of lines to skips. Use it if file has header rows.
		this.setLinesToSkip(1);

		// Configure how each line will be parsed and mapped to different values
		this.setLineMapper(new DefaultLineMapper<CustomerRecord>() {
			{
				// 3 columns in each row
				setLineTokenizer(new DelimitedLineTokenizer() {
					{
						setNames(new String[] { "record_referenceId",
								"accountNumber", "description", "startBalance",
								"mutation", "endBalance" });
					}
				});
				// Set values in Employee class
				setFieldSetMapper(new BeanWrapperFieldSetMapper<CustomerRecord>() {
					{
						setTargetType(CustomerRecord.class);
					}
				});
			}
		});
	}
}
