package com.devsuperior.dscommerce.controllers.handlers;


import com.devsuperior.dscommerce.Services.exceptions.DataBaseExcepitions;
import com.devsuperior.dscommerce.Services.exceptions.ResourseNotFoundExcepitions;
import com.devsuperior.dscommerce.dto.CustomError;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;

@ControllerAdvice
public class ControllerExceptionHandler {
    @ExceptionHandler(ResourseNotFoundExcepitions.class)
    public ResponseEntity<CustomError> ResourseNotFound(ResourseNotFoundExcepitions e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        CustomError err = new CustomError(Instant.now(), status.value(), e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(DataBaseExcepitions.class)
    public ResponseEntity<CustomError> database(DataBaseExcepitions e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        CustomError err = new CustomError(Instant.now(), status.value(), e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(status).body(err);
    }
}
