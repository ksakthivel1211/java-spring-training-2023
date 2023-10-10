package cdw.springTraining.gatekeeper.customException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author sakthivel
 * CustomExceptionHandler constructs the error response
 */
@RestControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(GateKeepingCustomException.class)
    public ResponseEntity<ErrorResponse> handleException(GateKeepingCustomException exception)
    {
        ErrorResponse errorResponse =new ErrorResponse();
        errorResponse.setStatus(exception.getHttpStatus().value());
        errorResponse.setMessage(exception.getMessage());
        errorResponse.setTimeStamp(System.currentTimeMillis());
        return  new ResponseEntity<>(errorResponse,HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
