package com.dal.catmeclone.exceptionhandler;

import java.io.PrintStream;
import java.io.PrintWriter;

public class DuplicateEntityException extends Exception {

    private static final long serialVersionUID = 8461874431183399030L;

    public DuplicateEntityException(String arg0) {
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
    public void setStackTrace(StackTraceElement[] stackTrace) {

        super.setStackTrace(stackTrace);
    }

    @Override
    public synchronized Throwable initCause(Throwable cause) {

        return super.initCause(cause);
    }

    @Override
    public void printStackTrace() {

        super.printStackTrace();
    }

    @Override
    public void printStackTrace(PrintStream s) {

        super.printStackTrace(s);
    }

    @Override
    public void printStackTrace(PrintWriter s) {

        super.printStackTrace(s);
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
    public int hashCode() {

        return super.hashCode();
    }

}
