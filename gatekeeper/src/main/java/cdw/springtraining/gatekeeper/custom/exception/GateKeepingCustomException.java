package cdw.springtraining.gatekeeper.custom.exception;

import org.springframework.http.HttpStatus;

/**
 * @author sakthivel
 * GateKeepingCustomException class provides custom runtime exception for the program
 */
public class GateKeepingCustomException extends RuntimeException{

    private final HttpStatus httpStatus;

    public GateKeepingCustomException(String message) {
        this(message,HttpStatus.BAD_REQUEST);
    }

    public GateKeepingCustomException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }


    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
