package com.sakthivel.spring.boot.crudDemo.customException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler
    public ResponseEntity<ErrorResponse> handleException(BookingException exc){
        ErrorResponse meetingErrorResponse=new ErrorResponse();
        meetingErrorResponse.setMessage(exc.getMessage());
        meetingErrorResponse.setStatus(HttpStatus.BAD_REQUEST.value());
        meetingErrorResponse.setTimeStamp(System.currentTimeMillis());

        return new ResponseEntity<>(meetingErrorResponse,HttpStatus.BAD_REQUEST);
    }
}