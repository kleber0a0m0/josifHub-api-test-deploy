package br.edu.josifHubapi.controler;

import br.edu.josifHubapi.domain.Avaliador;
import br.edu.josifHubapi.dto.AvaliadorDTO;
import br.edu.josifHubapi.service.AvaliadorService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/avaliador", produces = MediaType.APPLICATION_JSON_VALUE)
public class AvaliadorControler {

    @Autowired
    AvaliadorService avaliadorService;

    @GetMapping
    public ResponseEntity<List<Avaliador>> findAll() {
        System.out.println("aqui chegou no get");
        return ResponseEntity.ok(avaliadorService.getAll());
    }
    @GetMapping("/")
    public String hello() {
        return "Hello World";
    }

    @PostMapping
    public ResponseEntity<Avaliador> insert(@RequestBody AvaliadorDTO avaliadorDTO) {
        return ResponseEntity.ok(avaliadorService.insert(avaliadorDTO));
    }


}
