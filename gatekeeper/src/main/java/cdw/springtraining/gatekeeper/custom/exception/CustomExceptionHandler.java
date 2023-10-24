package cdw.springtraining.gatekeeper.custom.exception;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author sakthivel
 * CustomExceptionHandler constructs the error response
 */
@RestControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

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
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        List<ValidationError> errors = new ArrayList<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            ValidationError validationError = new ValidationError(error.getField(), error.getDefaultMessage());
            errors.add(validationError);
        }
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<List<ValidationError>> handleValidationException(ConstraintViolationException ex) {
        List<ValidationError> errors = new ArrayList<>();

        for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
            ValidationError error = new ValidationError(
                    violation.getPropertyPath().toString(),
                    violation.getMessage()
            );
            errors.add(error);
        }

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> HandleException(Exception exception){

        SimpleDateFormat dateformat = new SimpleDateFormat("dd-MMM-yyyy hh:mm:ss aa");
        ErrorResponse errorResponse =new ErrorResponse();
        errorResponse.setStatus(HttpStatus.BAD_REQUEST.value());
        errorResponse.setMessage(exception.getMessage());
        String datetime = dateformat.format(new Date().getTime());
        errorResponse.setTimeStamp(datetime);
        return new ResponseEntity<>(errorResponse,HttpStatus.BAD_REQUEST);
    }

}
