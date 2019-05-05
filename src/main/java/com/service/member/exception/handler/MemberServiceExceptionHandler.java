package com.service.member.exception.handler;

import com.service.member.model.ErrorResponse;
import com.service.member.exception.MemberNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import java.util.Date;

@ControllerAdvice
public class MemberServiceExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request) {
        ErrorResponse error = new ErrorResponse(new Date(), "Server Error", ex.getLocalizedMessage());
        return new ResponseEntity(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MemberNotFoundException.class)
    public final ResponseEntity<Object> handleMemberNotFoundException(MemberNotFoundException ex, WebRequest request) {
        ErrorResponse error = new ErrorResponse(new Date(), "Member Not Found", ex.getLocalizedMessage());
        return new ResponseEntity(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({ConstraintViolationException.class, ValidationException.class})
    public final ResponseEntity<Object> handleValidationErrors(ConstraintViolationException ex, WebRequest request) {
        ErrorResponse error = new ErrorResponse(new Date(), "Invalid Input", ex.getLocalizedMessage());
        return new ResponseEntity(error, HttpStatus.BAD_REQUEST);
    }

}
