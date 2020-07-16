package com.dal.catmeclone.exceptionhandler;

public class UserDefinedException extends Exception {

    private static final long serialVersionUID = 1L;
    String message;

    public UserDefinedException() {
        super();
    }

    public UserDefinedException(String arg0) {
        super(arg0);
        message = arg0;
    }

    public UserDefinedException(Throwable arg0) {
        super(arg0);
    }

    public UserDefinedException(String arg0, Throwable arg1) {
        super(arg0, arg1);
    }

    public UserDefinedException(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
        super(arg0, arg1, arg2, arg3);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }

    public void setMessage(String msz) {
        message = msz;
    }

    @Override
    public String toString() {
        return message;
    }

}
