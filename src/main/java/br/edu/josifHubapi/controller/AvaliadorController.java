package br.edu.josifHubapi.controller;

import br.edu.josifHubapi.domain.AreaAvaliadores;
import br.edu.josifHubapi.dto.AreaAvaliadoresDTO;
import br.edu.josifHubapi.dto.AvaliadorDTO;
import br.edu.josifHubapi.service.AvaliadorService;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/avaliadores", produces = MediaType.APPLICATION_JSON_VALUE)
public class AvaliadorController {
    @Autowired
    private AvaliadorService avaliadorService;

    @GetMapping
    public ResponseEntity<List<AvaliadorDTO>> buscarTodosAvaliadores() {
        List<AvaliadorDTO> avaliadoresDTO = avaliadorService.getAllAvaliadores();
        return ResponseEntity.ok(avaliadoresDTO);
    }

    @PostMapping
    public ResponseEntity<AvaliadorDTO> criarAvaliador(@RequestBody AvaliadorDTO avaliadorDTO) {
        AvaliadorDTO novoAvaliadorDTO = avaliadorService.criarAvaliador(avaliadorDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoAvaliadorDTO);
    }

    @PostMapping("/areas")
    @ResponseBody
    public ResponseEntity<?> adicionarAreaAoAvaliador(@RequestBody AreaAvaliadoresDTO areaAvaliadoresDTO) {
        try {
            AreaAvaliadores areaAvaliadores = avaliadorService.adicionarAreaAoAvaliador(areaAvaliadoresDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(areaAvaliadores);
        } catch (EntityNotFoundException ex) {
            Map<String, Object> errorResponse = new HashMap<>();
              errorResponse.put("status", HttpStatus.NOT_FOUND.value());
              errorResponse.put("error", HttpStatus.NOT_FOUND.getReasonPhrase());
              errorResponse.put("message", ex.getMessage());
              errorResponse.put("path", "/api/v1/avaliadores/areas");
              errorResponse.put("timestamp", System.currentTimeMillis());
              errorResponse.put("redirect", "https://http.cat/404");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
        } catch(EntityExistsException ex) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("status", HttpStatus.BAD_REQUEST.value());
            errorResponse.put("error", HttpStatus.BAD_REQUEST.getReasonPhrase());
            errorResponse.put("message", ex.getMessage());
            errorResponse.put("path", "/api/v1/avaliadores/areas");
            errorResponse.put("timestamp", System.currentTimeMillis());
            errorResponse.put("redirect", "https://http.cat/400");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }
    @GetMapping("/areas/{id}")
    public ResponseEntity<List<AreaAvaliadores>> buscarAvaliadorPorId(@PathVariable String id) {
        List<AreaAvaliadores> areaAvaliadores = avaliadorService.getAllAvaliadoresById(id);
        return ResponseEntity.ok(areaAvaliadores);
    }



}

