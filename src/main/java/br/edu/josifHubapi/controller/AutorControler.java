package br.edu.josifHubapi.controller;

import br.edu.josifHubapi.domain.Autor;
import br.edu.josifHubapi.dto.AutorDTO;
import br.edu.josifHubapi.service.AutorService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;
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

    @PostMapping
    public ResponseEntity<Autor> insert(@RequestBody AutorDTO autorDTO) {
        return ResponseEntity.status(HttpStatus.OK).body(autorService.insert(autorDTO));
    }

    @PutMapping("/{codigo}")
    public ResponseEntity<Object> updateAutor(@PathVariable(value = "codigo") Long codigo, @RequestBody AutorDTO autorDTO){
        Optional<Autor> autorOptional = autorService.findById(codigo);
        if (!autorOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Autor não encontrado.");
        }else{
            var autorModel = autorOptional.get();
            BeanUtils.copyProperties(autorDTO,autorModel );
            return ResponseEntity.status(HttpStatus.OK).body(autorService.save(autorModel));
        }
    }

    @DeleteMapping("/{codigo}")
    public ResponseEntity<Object> deleteAutor(@PathVariable(value = "codigo") Long codigo){
        try {
            Optional<Autor> autorOptional = autorService.findById(codigo);
            if (!autorOptional.isPresent()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Autor não encontrado.");
            }
            autorService.delete(autorOptional.get());
            return ResponseEntity.status(HttpStatus.OK).body("Autor excluido com sucesso.");
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Erro ao excluir o Autor! - ["+e.getMessage()+"]");
        }
    }


}
