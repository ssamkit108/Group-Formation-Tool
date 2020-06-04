package com.dal.catmeclone.exceptionhandler;

import java.io.PrintStream;
import java.io.PrintWriter;

public class FileRelatedException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public FileRelatedException(String arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public synchronized Throwable fillInStackTrace() {
		// TODO Auto-generated method stub
		return super.fillInStackTrace();
	}

	@Override
	public synchronized Throwable getCause() {
		// TODO Auto-generated method stub
		return super.getCause();
	}

	@Override
	public String getLocalizedMessage() {
		// TODO Auto-generated method stub
		return super.getLocalizedMessage();
	}

	@Override
	public String getMessage() {
		// TODO Auto-generated method stub
		return super.getMessage();
	}

	@Override
	public StackTraceElement[] getStackTrace() {
		// TODO Auto-generated method stub
		return super.getStackTrace();
	}

	@Override
	public synchronized Throwable initCause(Throwable arg0) {
		// TODO Auto-generated method stub
		return super.initCause(arg0);
	}

	@Override
	public void printStackTrace() {
		// TODO Auto-generated method stub
		super.printStackTrace();
	}

	@Override
	public void printStackTrace(PrintStream arg0) {
		// TODO Auto-generated method stub
		super.printStackTrace(arg0);
	}

	@Override
	public void printStackTrace(PrintWriter arg0) {
		// TODO Auto-generated method stub
		super.printStackTrace(arg0);
	}

	@Override
	public void setStackTrace(StackTraceElement[] arg0) {
		// TODO Auto-generated method stub
		super.setStackTrace(arg0);
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return super.toString();
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return super.clone();
	}

	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		return super.equals(obj);
	}

	@Override
	protected void finalize() throws Throwable {
		// TODO Auto-generated method stub
		super.finalize();
	}

	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return super.hashCode();
	}

	
}
