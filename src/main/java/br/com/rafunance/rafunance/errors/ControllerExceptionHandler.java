package br.com.rafunance.rafunance.errors;

import br.com.rafunance.rafunance.errors.models.StandartError;
import br.com.rafunance.rafunance.errors.exceptions.IdNullException;
import br.com.rafunance.rafunance.errors.exceptions.NotFoundException;
import br.com.rafunance.rafunance.errors.exceptions.NullFieldException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ControllerExceptionHandler {
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<StandartError> notFoundExceptionHandler(NotFoundException ex, HttpServletRequest res){
        HttpStatus status = HttpStatus.NOT_FOUND;
        StandartError err = new StandartError(ex.getMessage(), status, status.value(), System.currentTimeMillis());
        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(IdNullException.class)
    public ResponseEntity<StandartError> notFoundExceptionHandler(IdNullException ex, HttpServletRequest res){
        HttpStatus status = HttpStatus.NOT_ACCEPTABLE;
        StandartError err = new StandartError(ex.getMessage(), status, status.value(), System.currentTimeMillis());
        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(NullFieldException.class)
    public ResponseEntity<StandartError> notFoundExceptionHandler(NullFieldException ex, HttpServletRequest res){
        HttpStatus status = HttpStatus.NOT_ACCEPTABLE;
        StandartError err = new StandartError(ex.getMessage(), status, status.value(), System.currentTimeMillis());
        return ResponseEntity.status(status).body(err);
    }

}
