package com.dal.catmeclone.exceptionhandler;

import java.io.PrintStream;
import java.io.PrintWriter;

public class FileRelatedException extends Exception {

	private static final long serialVersionUID = 1L;

	public FileRelatedException(String arg0) {
		super(arg0);
	}

	@Override
	public synchronized Throwable fillInStackTrace() {

		return super.fillInStackTrace();
	}

	@Override
	public synchronized Throwable getCause() {

		return super.getCause();
	}

	@Override
	public String getLocalizedMessage() {

		return super.getLocalizedMessage();
	}

	@Override
	public String getMessage() {

		return super.getMessage();
	}

	@Override
	public StackTraceElement[] getStackTrace() {

		return super.getStackTrace();
	}

	@Override
	public synchronized Throwable initCause(Throwable arg0) {

		return super.initCause(arg0);
	}

	@Override
	public void printStackTrace() {

		super.printStackTrace();
	}

	@Override
	public void printStackTrace(PrintStream arg0) {

		super.printStackTrace(arg0);
	}

	@Override
	public void printStackTrace(PrintWriter arg0) {

		super.printStackTrace(arg0);
	}

	@Override
	public void setStackTrace(StackTraceElement[] arg0) {

		super.setStackTrace(arg0);
	}

	@Override
	public String toString() {

		return super.toString();
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {

		return super.clone();
	}

	@Override
	public boolean equals(Object obj) {

		return super.equals(obj);
	}

	@Override
	protected void finalize() throws Throwable {

		super.finalize();
	}

	@Override
	public int hashCode() {

		return super.hashCode();
	}

}
