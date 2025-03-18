package common.exception;

public class DBException extends Exception{
    private String message;
    private Exception e;

    public DBException(String message, Exception e) {
        this.message = message;
        this.e = e;
    }
}
