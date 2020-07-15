package com.dal.catmeclone.exceptionhandler;

public class UserDefinedSQLException extends Exception {

    private static final long serialVersionUID = 1L;
    String message;

    public UserDefinedSQLException() {
        // TODO Auto-generated constructor stub
    }

    public UserDefinedSQLException(String arg0) {
        super(arg0);
        message = arg0;
    }

    /**
     * @param arg0
     */
    public UserDefinedSQLException(Throwable arg0) {
        super(arg0);
    }

    /**
     * @param arg0
     * @param arg1
     */
    public UserDefinedSQLException(String arg0, Throwable arg1) {
        super(arg0, arg1);
    }

    /**
     * @param arg0
     * @param arg1
     * @param arg2
     * @param arg3
     */
    public UserDefinedSQLException(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
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
