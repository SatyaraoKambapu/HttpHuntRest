package com.rabobank.statementprocessor.exception;

/**
 * Custom Exception to Handle Exceptions in Rabobank Statement Processor
 * 
 * @author skambapu
 * 
 */
public class BusinessOperationException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public BusinessOperationException(String msg) {
		super(msg);
	}

	public BusinessOperationException() {
		super();
	}

	public BusinessOperationException(String msg, Throwable e) {
		super(msg, e);
	}

}
