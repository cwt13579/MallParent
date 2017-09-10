package com.iec.core.app.exception;

public class ServiceConflictException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3866526528482023357L;
	private String conflictService1;
	private String conflictService2;

	public ServiceConflictException(String arg0) {
		super(arg0);
	}
	
	public ServiceConflictException(String conflictService1,
			String conflictService2) {
		this.conflictService1 = conflictService1;
		this.conflictService2 = conflictService2;
	}

	public ServiceConflictException(String conflictService1,
			String conflictService2,String arg0) {
		super(arg0);
		this.conflictService1 = conflictService1;
		this.conflictService2 = conflictService2;
	}

	public String getMessage() {
		return "[" + conflictService1 + "] and " + "[" + conflictService2
				+ "] conflict! " + super.getMessage();
	}
}
