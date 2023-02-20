package br.edu.josifHubapi.controler;
import br.edu.josifHubapi.domain.Trabalhos;
import br.edu.josifHubapi.dto.TrabalhoDTO;
import br.edu.josifHubapi.service.TrabalhosService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/trabalhos", produces = MediaType.APPLICATION_JSON_VALUE)
public class TrabalhosControler {

    @Autowired
    TrabalhosService trabalhoService;

    @GetMapping
    public ResponseEntity<List<Trabalhos>> findAll() {
        return ResponseEntity.ok(trabalhoService.getAll());
    }

    @PostMapping
    public ResponseEntity<Trabalhos> insert(@RequestBody TrabalhoDTO trabalhoDTO) {
        return ResponseEntity.ok(trabalhoService.insert(trabalhoDTO));
    }




}
