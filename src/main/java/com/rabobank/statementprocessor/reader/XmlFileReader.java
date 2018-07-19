package com.rabobank.statementprocessor.reader;

import org.springframework.batch.item.xml.StaxEventItemReader;
import org.springframework.core.io.FileSystemResource;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

import com.rabobank.statementprocessor.entity.CustomerRecords;

public class XmlFileReader extends StaxEventItemReader<CustomerRecords> {

	public XmlFileReader(String filepath) {
		this.setResource(new FileSystemResource(filepath));
		this.setFragmentRootElementName("records");
		Jaxb2Marshaller recordsMarshaller = new Jaxb2Marshaller();
		recordsMarshaller.setClassesToBeBound(CustomerRecords.class);
		this.setUnmarshaller(recordsMarshaller);
	}
}
