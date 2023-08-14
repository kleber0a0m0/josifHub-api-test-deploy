package br.edu.josifHubapi.exception;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@ControllerAdvice
@RestController
public class GlobalExceptionHandler {
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<HashMap> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        HashMap<String, Object> response = new HashMap<>();
        response.put("erro", true);
        response.put("mensagem", "O corpo da solicitação está ausente ou não pode ser lido corretamente.");
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
