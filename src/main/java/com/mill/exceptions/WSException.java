package com.mill.exceptions;

public class WSException extends Exception{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8194230614376501208L;
	
	private int errorCode;
	
	public WSException(int errorCode, String message)
	{
		super(message);
		this.errorCode = errorCode;
	}

	public int getErrorCode()
	{
		return errorCode;
	}

	public void setErrorCode(int errorCode)
	{
		this.errorCode = errorCode;
	}

}
