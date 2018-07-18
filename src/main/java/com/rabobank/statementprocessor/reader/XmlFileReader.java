package com.rabobank.statementprocessor.reader;

import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.xml.StaxEventItemReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

import com.rabobank.statementprocessor.entity.CustomerRecord;

public class XmlFileReader {

	@Value("${filepath}")
	private String filepath;

	@Bean
	ItemReader<CustomerRecord> xmlFileItemReader() {
		StaxEventItemReader<CustomerRecord> xmlFileReader = new StaxEventItemReader<>();
		xmlFileReader.setResource(new ClassPathResource(filepath));
		xmlFileReader.setFragmentRootElementName("record");

		Jaxb2Marshaller studentMarshaller = new Jaxb2Marshaller();
		studentMarshaller.setClassesToBeBound(CustomerRecord.class);
		xmlFileReader.setUnmarshaller(studentMarshaller);

		return xmlFileReader;
	}
}
