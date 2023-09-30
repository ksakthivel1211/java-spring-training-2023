package cdw.springTraining.gatekeeper.customException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler
    public ResponseEntity<ErrorResponse> handleException(GateKeepingCustomException exception)
    {
        ErrorResponse errorResponse =new ErrorResponse();
        errorResponse.setStatus(HttpStatus.BAD_REQUEST.value());
        errorResponse.setMessage(exception.getMessage());
        errorResponse.setTimeStamp(System.currentTimeMillis());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
