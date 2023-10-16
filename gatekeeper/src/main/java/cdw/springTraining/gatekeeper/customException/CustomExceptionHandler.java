package cdw.springTraining.gatekeeper.customException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author sakthivel
 * CustomExceptionHandler constructs the error response
 */
@RestControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(GateKeepingCustomException.class)
    public ResponseEntity<ErrorResponse> handleException(GateKeepingCustomException exception)
    {
        SimpleDateFormat dateformat = new SimpleDateFormat("dd-MMM-yyyy hh:mm:ss aa");
        ErrorResponse errorResponse =new ErrorResponse();
        errorResponse.setStatus(exception.getHttpStatus().value());
        errorResponse.setMessage(exception.getMessage());
        String datetime = dateformat.format(new Date().getTime());
        errorResponse.setTimeStamp(datetime);
        return ResponseEntity.status(exception.getHttpStatus()).body(errorResponse);
    }

}
