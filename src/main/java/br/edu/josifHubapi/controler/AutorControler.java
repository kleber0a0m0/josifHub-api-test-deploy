package br.edu.josifHubapi.controler;

import br.edu.josifHubapi.domain.Autor;
import br.edu.josifHubapi.dto.AvaliadorDTO;
import br.edu.josifHubapi.service.AutorService;
import br.edu.josifHubapi.service.AvaliadorService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/autor", produces = MediaType.APPLICATION_JSON_VALUE)
public class AutorControler {

    @Autowired
    AutorService autorService;

    @GetMapping
    public ResponseEntity<List<Autor>> findAll() {
        return ResponseEntity.ok(autorService.getAll());
    }

}
