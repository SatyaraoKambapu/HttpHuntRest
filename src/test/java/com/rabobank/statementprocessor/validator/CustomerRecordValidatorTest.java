package com.rabobank.statementprocessor.validator;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.rabobank.statementprocessor.entity.CustomerRecord;

public class CustomerRecordValidatorTest {
	CustomerRecordValidator customerRecordValidator;
	List<CustomerRecord> records = null;

	@Before
	public void setUp() {
		customerRecordValidator = new CustomerRecordValidator();
	}

	@After
	public void cleanUp() {

	}

	@Test
	public void testEmptyInvalidRecords() {
		Assert.assertTrue(new CustomerRecordValidator()
				.getInvalidCustomerRecords().isEmpty());
	}

	@Test
	public void testAllValidRecords(){
		records = new ArrayList<>();
		CustomerRecord record1 = new CustomerRecord();
		record1.setRecord_referenceId(123);
		record1.setAccountNumber("IBAN54263");
		record1.setDescription("Desc 1");
		record1.setStartBalance(new BigDecimal(100));
		record1.setMutation(new BigDecimal(5));
		record1.setEndBalance(new BigDecimal(105));
		records.add(record1);
		
		records = new ArrayList<>();
		CustomerRecord record2 = new CustomerRecord();
		record2.setRecord_referenceId(1235);
		record2.setAccountNumber("IBAN542634");
		record2.setDescription("Desc 1");
		record2.setStartBalance(new BigDecimal(100));
		record2.setMutation(new BigDecimal(5));
		record2.setEndBalance(new BigDecimal(105));
		records.add(record2);
		customerRecordValidator.validate(records);
		Assert.assertEquals(0, customerRecordValidator
				.getInvalidCustomerRecords().size());
	}
	@Test
	public void testRecordReference() {
		records = new ArrayList<>();
		CustomerRecord record1 = new CustomerRecord();
		record1.setRecord_referenceId(123);
		record1.setAccountNumber("IBAN54263");
		record1.setDescription("Desc 1");
		record1.setStartBalance(new BigDecimal(100));
		record1.setMutation(new BigDecimal(5));
		record1.setEndBalance(new BigDecimal(105));
		records.add(record1);

		CustomerRecord record2 = new CustomerRecord();
		record2.setRecord_referenceId(1234);
		record2.setAccountNumber("IBAN542635");
		record2.setDescription("Desc 2");
		record2.setStartBalance(new BigDecimal(100));
		record2.setMutation(new BigDecimal(5));
		record2.setEndBalance(new BigDecimal(103));
		records.add(record2);

		CustomerRecord record3 = new CustomerRecord();
		record3.setRecord_referenceId(1234);
		record3.setAccountNumber("IBAN5426356");
		record3.setDescription("Desc 3");
		record3.setStartBalance(new BigDecimal(100));
		record3.setMutation(new BigDecimal(5));
		record3.setEndBalance(new BigDecimal(106));
		records.add(record3);
		customerRecordValidator.validate(records);
		Assert.assertEquals(2, customerRecordValidator
				.getInvalidCustomerRecords().size());
	}

	@Test
	public void testEndBalance() {
		records = new ArrayList<>();
		CustomerRecord record1 = new CustomerRecord();
		record1.setRecord_referenceId(123);
		record1.setAccountNumber("IBAN54263");
		record1.setDescription("Desc 1");
		record1.setStartBalance(new BigDecimal(100));
		record1.setMutation(new BigDecimal(5));
		record1.setEndBalance(new BigDecimal(105));
		records.add(record1);

		CustomerRecord record2 = new CustomerRecord();
		record2.setRecord_referenceId(1234);
		record2.setAccountNumber("IBAN542635");
		record2.setDescription("Desc 2");
		record2.setStartBalance(new BigDecimal(100));
		record2.setMutation(new BigDecimal(5));
		record2.setEndBalance(new BigDecimal(103));
		records.add(record2);

		CustomerRecord record3 = new CustomerRecord();
		record3.setRecord_referenceId(1234);
		record3.setAccountNumber("IBAN5426356");
		record3.setDescription("Desc 3");
		record3.setStartBalance(new BigDecimal(100));
		record3.setMutation(new BigDecimal(5));
		record3.setEndBalance(new BigDecimal(106));
		records.add(record3);
		customerRecordValidator.validate(records);
		Assert.assertEquals(2, customerRecordValidator
				.getInvalidCustomerRecords().size());
	}
	
	
}
