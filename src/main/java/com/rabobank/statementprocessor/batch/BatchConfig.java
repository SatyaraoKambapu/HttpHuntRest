package com.rabobank.statementprocessor.batch;

import java.io.File;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.xml.StaxEventItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

import com.rabobank.statementprocessor.common.SupportedFileType;
import com.rabobank.statementprocessor.entity.CustomerRecord;
import com.rabobank.statementprocessor.entity.CustomerRecords;

@Configuration
@EnableBatchProcessing
public class BatchConfig {
	@Autowired
	private JobBuilderFactory jobBuilderFactory;

	@Autowired
	private StepBuilderFactory stepBuilderFactory;

	@Value("${filepath}")
	String filepath;

	@SuppressWarnings("unused")
	@Bean
	public Job readFilesJob() {
		JobBuilder builder = jobBuilderFactory.get("readFilesJob").incrementer(
				new RunIdIncrementer());
		File inputFile = new File(filepath);

		if (inputFile.isFile()
				&& inputFile.getName().endsWith(
						SupportedFileType.CSV.getFileType())) {
			return builder.start(step1()).build();
		} else if (inputFile.isFile()
				&& inputFile.getName().endsWith(
						SupportedFileType.XML.getFileType())) {
			return builder.start(step2()).build();
		}
		return null;
	}

	@Bean
	public Step step1() {
		return stepBuilderFactory.get("step1")
				.<CustomerRecord, CustomerRecord> chunk(20).reader(reader())
				.writer(writer()).build();
	}

	@Bean
	public Step step2() {
		return stepBuilderFactory.get("step2")
				.<CustomerRecords, CustomerRecords> chunk(20)
				.reader(xmlFileItemReader()).writer(writerForCollection())
				.build();
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Bean
	public FlatFileItemReader<CustomerRecord> reader() {
		// Create reader instance
		FlatFileItemReader<CustomerRecord> reader = new FlatFileItemReader<CustomerRecord>();

		// Set input file location
		reader.setResource(new FileSystemResource(filepath));

		// Set number of lines to skips. Use it if file has header rows.
		reader.setLinesToSkip(1);

		// Configure how each line will be parsed and mapped to different values
		reader.setLineMapper(new DefaultLineMapper() {
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
		return reader;
	}

	@Bean
	ItemReader<CustomerRecords> xmlFileItemReader() {
		StaxEventItemReader<CustomerRecords> xmlFileReader = new StaxEventItemReader<>();
		xmlFileReader.setResource(new FileSystemResource(filepath));
		xmlFileReader.setFragmentRootElementName("records");
		Jaxb2Marshaller recordsMarshaller = new Jaxb2Marshaller();
		recordsMarshaller.setClassesToBeBound(CustomerRecords.class);
		xmlFileReader.setUnmarshaller(recordsMarshaller);
		return xmlFileReader;
	}

	@Bean
	public CsvConsoleWriter<CustomerRecord> writer() {
		return new CsvConsoleWriter<CustomerRecord>();
	}

	@Bean
	public XmlConsoleWriter<CustomerRecords> writerForCollection() {
		return new XmlConsoleWriter<CustomerRecords>();
	}
}
