package br.edu.josifHubapi.exception;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.mail.MailSendException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
@RestController
public class GlobalExceptionHandler {
    HashMap<String, Object> response = new HashMap<>();
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<HashMap> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        response.put("erro", true);
        response.put("mensagem", "O corpo da solicitação está ausente ou não pode ser lido corretamente.");
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<HashMap> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException ex) {
        response.put("erro", true);
        response.put("mensagem", "O método de solicitação '" + ex.getMethod() + "' não é suportado para este endpoint.");
        return new ResponseEntity<>(response, HttpStatus.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler(MailSendException.class)
    public ResponseEntity<Map<String, Object>> handleMailSendException(MailSendException ex) {
        Map<String, Object> response = new HashMap<>();
        response.put("erro", true);
        response.put("mensagem", "Ocorreu um erro ao tentar enviar o e-mail.");
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
