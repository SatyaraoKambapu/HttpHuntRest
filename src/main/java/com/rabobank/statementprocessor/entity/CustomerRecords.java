package com.rabobank.statementprocessor.entity;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * This is the class to have multiple of records information. To unmarshal the
 * xml into collections of java objects, we need to have the annotations from
 * package javax.xml.bind.annotation.*.
 * 
 * @author skambapu
 * 
 */
@XmlRootElement(name = "records")
@XmlAccessorType(XmlAccessType.FIELD)
public class CustomerRecords {
	@XmlElement(name = "record")
	List<CustomerRecord> records;

	public void setRecords(List<CustomerRecord> records) {
		this.records = records;
	}

	public List<CustomerRecord> getRecords() {
		return records;
	}

	public void addRecord(CustomerRecord record) {
		if (this.records == null) {
			this.records = new ArrayList<CustomerRecord>();
		}
		this.records.add(record);
	}

}
