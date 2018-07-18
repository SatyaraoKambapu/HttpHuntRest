package com.rabobank.statementprocessor.entity;

import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 
 * This is the class to have single record information. This class will be used
 * for both xml parsing and csv file parsing. To unmarshal the xml into single
 * java object, we need to have the annotations from package
 * javax.xml.bind.annotation.*.
 * 
 * @author skambapu
 * 
 */
@XmlRootElement(name = "record")
@XmlAccessorType(XmlAccessType.FIELD)
public class CustomerRecord {
	@XmlAttribute(name = "reference")
	private long record_referenceId;
	@XmlElement(name = "accountNumber")
	private String accountNumber;
	@XmlElement(name = "description")
	private String description;
	@XmlElement(name = "startBalance")
	private BigDecimal startBalance;
	@XmlElement(name = "mutation")
	private BigDecimal mutation;
	@XmlElement(name = "endBalance")
	private BigDecimal endBalance;

	public long getRecord_referenceId() {
		return record_referenceId;
	}

	public void setRecord_referenceId(long record_referenceId) {
		this.record_referenceId = record_referenceId;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public BigDecimal getStartBalance() {
		return startBalance;
	}

	public void setStartBalance(BigDecimal startBalance) {
		this.startBalance = startBalance;
	}

	public BigDecimal getMutation() {
		return mutation;
	}

	public void setMutation(BigDecimal mutation) {
		this.mutation = mutation;
	}

	public BigDecimal getEndBalance() {
		return endBalance;
	}

	public void setEndBalance(BigDecimal endBalance) {
		this.endBalance = endBalance;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((accountNumber == null) ? 0 : accountNumber.hashCode());
		result = prime * result
				+ ((description == null) ? 0 : description.hashCode());
		result = prime * result
				+ ((endBalance == null) ? 0 : endBalance.hashCode());
		result = prime * result
				+ ((mutation == null) ? 0 : mutation.hashCode());
		result = prime * result
				+ (int) (record_referenceId ^ (record_referenceId >>> 32));
		result = prime * result
				+ ((startBalance == null) ? 0 : startBalance.hashCode());
		return result;
	}

	// Added this for Object comparison.
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CustomerRecord other = (CustomerRecord) obj;
		if (accountNumber == null) {
			if (other.accountNumber != null)
				return false;
		} else if (!accountNumber.equals(other.accountNumber))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (endBalance == null) {
			if (other.endBalance != null)
				return false;
		} else if (!endBalance.equals(other.endBalance))
			return false;
		if (mutation == null) {
			if (other.mutation != null)
				return false;
		} else if (!mutation.equals(other.mutation))
			return false;
		if (record_referenceId != other.record_referenceId)
			return false;
		if (startBalance == null) {
			if (other.startBalance != null)
				return false;
		} else if (!startBalance.equals(other.startBalance))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "CustomerRecord [record_referenceId=" + record_referenceId
				+ ", accountNumber=" + accountNumber + ", description="
				+ description + ", startBalance=" + startBalance
				+ ", mutation=" + mutation + ", endBalance=" + endBalance + "]";
	}
}
