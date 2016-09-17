package org.tanner.tutorial.messenger.exception;

public class DataNotFoundException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4832229140252363198L;

	public DataNotFoundException(String message){
		super(message);
	}
}
