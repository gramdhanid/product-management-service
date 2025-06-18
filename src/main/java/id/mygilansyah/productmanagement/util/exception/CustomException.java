package id.mygilansyah.productmanagement.util.exception;

public class CustomException extends Exception {

    private static final long serialVersionUID = 1L;
    private final ErrorCode errorCode;
    private final Integer httpStatusCode;

    public CustomException(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
        this.httpStatusCode = 500;
    }
}
