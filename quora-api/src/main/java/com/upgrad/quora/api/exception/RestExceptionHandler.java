package com.upgrad.quora.api.exception;

import com.upgrad.quora.api.model.ErrorResponse;
import com.upgrad.quora.service.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
//Exception Handling Methods
public class RestExceptionHandler {
    @ExceptionHandler(SignUpRestrictedException.class)
    public ResponseEntity<ErrorResponse> handleSignupRestrictedException(SignUpRestrictedException exc,
                                                                         WebRequest request) {
        return new ResponseEntity<ErrorResponse>(new ErrorResponse().code(exc.getCode()).
                message(exc.getErrorMessage()), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(AuthenticationFailedException.class)
    public ResponseEntity<ErrorResponse> handleAuthenticationFailedException(AuthenticationFailedException exc,
                                                                             WebRequest request) {
        return new ResponseEntity<ErrorResponse>(new ErrorResponse().code(exc.getCode()).
                message(exc.getErrorMessage()), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(SignOutRestrictedException.class)
    public ResponseEntity<ErrorResponse> handleSignOutRestrictedException(SignOutRestrictedException exc,
                                                                          WebRequest request) {
        return new ResponseEntity<ErrorResponse>(new ErrorResponse().code(exc.getCode()).
                message(exc.getErrorMessage()), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFoundException(UserNotFoundException exc,
                                                                     WebRequest request) {
        return new ResponseEntity<ErrorResponse>(new ErrorResponse().code(exc.getCode()).
                message(exc.getErrorMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AuthorizationFailedException.class)
    public ResponseEntity<ErrorResponse> handleAuthorizationFailedException(AuthorizationFailedException exc,
                                                                            WebRequest request) {
        return new ResponseEntity<ErrorResponse>(new ErrorResponse().code(exc.getCode()).
                message(exc.getErrorMessage()), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(InvalidQuestionException.class)
    public ResponseEntity<ErrorResponse> invalidQuestionException(InvalidQuestionException exc, WebRequest request) {
        return new ResponseEntity<ErrorResponse>(new ErrorResponse().code(exc.getCode()).message(exc.getErrorMessage()), HttpStatus.NOT_FOUND);
    }

}