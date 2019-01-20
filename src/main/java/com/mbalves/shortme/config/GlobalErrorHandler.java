package com.mbalves.shortme.config;

import com.mbalves.shortme.domain.exceptions.BadURLException;
import com.mbalves.shortme.domain.ErrorDetails;
import com.mbalves.shortme.domain.exceptions.IdNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@ControllerAdvice
@RestController
public class GlobalErrorHandler extends ResponseEntityExceptionHandler {

    private ResponseEntity<ErrorDetails> buildResponse(String message, String description, HttpStatus status){
        ErrorDetails errorDetails = new ErrorDetails(new Date(),message, description);
        return new ResponseEntity<>(errorDetails, status);
    }

    @ExceptionHandler(IdNotFoundException.class)
    public final ResponseEntity<ErrorDetails> handleUrlNotFoundException(IdNotFoundException ex, WebRequest request) {
        return buildResponse(ex.getMessage(), ex.getId(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BadURLException.class)
    public final ResponseEntity<ErrorDetails> handleBadUrlException(BadURLException ex, WebRequest request) {
        return buildResponse(ex.getMessage(),ex.getBadUrl(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<ErrorDetails> handleOthersExceptions(Exception ex, WebRequest request) {
        return buildResponse(ex.getMessage(),request.getDescription(false), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
