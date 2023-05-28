package br.edu.josifHubapi.controller;
import br.edu.josifHubapi.domain.Autor;
import br.edu.josifHubapi.domain.Trabalho;
import br.edu.josifHubapi.dto.TrabalhoDTO;
import br.edu.josifHubapi.service.AutorService;
import br.edu.josifHubapi.service.TrabalhoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/trabalhos", produces = MediaType.APPLICATION_JSON_VALUE)
public class TrabalhoControler {

    @Autowired
    TrabalhoService trabalhoService;

    @Autowired
    AutorService autorService;

    @GetMapping
    public ResponseEntity<List<Trabalho>> findAll() {
        return ResponseEntity.ok(trabalhoService.getAll());
    }

    @PostMapping
    public ResponseEntity<Trabalho> insert(@RequestBody Trabalho trabalho) {
        List<Autor> autores = new ArrayList<>();
        System.out.println(trabalho.getAutor1());
        if(trabalho.getAutor1() != null) {
            autores.add(autorService.save(trabalho.getAutor1()));
        }
        if(trabalho.getAutor2() != null) {
            autores.add(autorService.save(trabalho.getAutor2()));
        }
        if(trabalho.getAutor3() != null) {
            autores.add(autorService.save(trabalho.getAutor3()));
        }
        if(trabalho.getAutor4() != null) {
            autores.add(autorService.save(trabalho.getAutor4()));
        }
        if(trabalho.getAutor5() != null) {
            autores.add(autorService.save(trabalho.getAutor5()));
        }
        if(trabalho.getAutor6() != null) {
            autores.add(autorService.save(trabalho.getAutor6()));
        }
        if(trabalho.getAutor7() != null) {
            autores.add(autorService.save(trabalho.getAutor7()));
        }
        if(trabalho.getAutor8() != null) {
            autores.add(autorService.save(trabalho.getAutor8()));
        }
        if(trabalho.getAutor9() != null) {
            autores.add(autorService.save(trabalho.getAutor9()));
        }
        if(trabalho.getAutor10() != null) {
            autores.add(autorService.save(trabalho.getAutor10()));
        }
        if(trabalho.getAutor11() != null) {
            autores.add(autorService.save(trabalho.getAutor11()));
        }

        trabalho.setAutores(autores);

        Trabalho novoTrabalho = trabalhoService.save(trabalho);
        return ResponseEntity.ok(novoTrabalho);
    }

    @PutMapping("/{codigo}")
    public ResponseEntity<Object> updateTrabalho(@PathVariable("codigo") Long codigo, @RequestBody Trabalho trabalho) {
        Optional<Trabalho> trabalhoOptional = trabalhoService.findById(codigo);

        if (trabalhoOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Trabalho não encontrado.");
        }

        Trabalho trabalhoAntigo = trabalhoOptional.get();

        trabalhoAntigo.setTitulo(trabalho.getTitulo());
        trabalhoAntigo.setResumo(trabalho.getResumo());
        trabalhoAntigo.setPalavrasChave(trabalho.getPalavrasChave());
        trabalhoAntigo.setArea(trabalho.getArea());

        trabalhoAntigo.setAutor1(autorService.teste(trabalho.getAutor1()));
        trabalhoAntigo.setAutor2(autorService.teste(trabalho.getAutor2()));
        trabalhoAntigo.setAutor2(autorService.teste(trabalho.getAutor3()));
        trabalhoAntigo.setAutor2(autorService.teste(trabalho.getAutor4()));
        trabalhoAntigo.setAutor2(autorService.teste(trabalho.getAutor5()));
        trabalhoAntigo.setAutor2(autorService.teste(trabalho.getAutor6()));
        trabalhoAntigo.setAutor2(autorService.teste(trabalho.getAutor7()));
        trabalhoAntigo.setAutor2(autorService.teste(trabalho.getAutor8()));
        trabalhoAntigo.setAutor2(autorService.teste(trabalho.getAutor9()));
        trabalhoAntigo.setAutor2(autorService.teste(trabalho.getAutor10()));
        trabalhoAntigo.setAutor2(autorService.teste(trabalho.getAutor11()));

        trabalhoService.save(trabalhoAntigo);

        return ResponseEntity.ok(trabalhoAntigo);
    }




    @DeleteMapping("/{codigo}")
    public ResponseEntity<Object> deleteTrabalho(@PathVariable(value = "codigo") Long codigo){
        try {
            Optional<Trabalho> trabalhoOptional = trabalhoService.findById(codigo);
            if (trabalhoOptional.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Trabalho não encontrado.");
            }
            trabalhoService.delete(trabalhoOptional.get());
            return ResponseEntity.status(HttpStatus.OK).body("Trabalho excluido com sucesso. POREM OS AUTORES FICARAM CADASTRADOS AINDA_ TODO: FAZER ELES SUMIREM");
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Erro ao excluir o trabalho! - ["+e.getMessage()+"]");
        }
    }




}
