package br.edu.josifHubapi.controller;

import br.edu.josifHubapi.domain.Avaliador;
import br.edu.josifHubapi.dto.AvaliadorDTO;
import br.edu.josifHubapi.service.AvaliadorService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/avaliadores", produces = MediaType.APPLICATION_JSON_VALUE)
public class AvaliadorController {

    @Autowired
    AvaliadorService avaliadorService;
    @GetMapping
    public List<Avaliador> findAllAvaliador(){
        return avaliadorService.getAll();
    }

    @PostMapping
    public ResponseEntity<Avaliador> insert(@RequestBody AvaliadorDTO avaliadorDTO) {
        return ResponseEntity.ok(avaliadorService.insert(avaliadorDTO));
    }

    @DeleteMapping("/{codigo}")
    public ResponseEntity<Object> deleteAvaliador(@PathVariable(value = "codigo") Long codigo){
        try {
            Optional<Avaliador> avaliadorOptional = avaliadorService.findById(codigo);
            if (avaliadorOptional.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Avaliador não encontrado.");
            }
            avaliadorService.delete(avaliadorOptional.get());
            return ResponseEntity.status(HttpStatus.OK).body("Avaliador excluido com sucesso.");
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Erro ao excluir o Avaliador! - ["+e.getMessage()+"]");
        }
    }

    @PutMapping("/{codigo}")
    public ResponseEntity<Object> updateAvaliador(@PathVariable(value = "codigo") Long codigo, @RequestBody AvaliadorDTO avaliadorDTO){
        Optional<Avaliador> avaliadorOptional = avaliadorService.findById(codigo);
        if (avaliadorOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Avaliador não encontrado.");
        }else{
            var avaliadorModel = avaliadorOptional.get();
            BeanUtils.copyProperties(avaliadorDTO,avaliadorModel );
            return ResponseEntity.status(HttpStatus.OK).body(avaliadorService.save(avaliadorModel));
        }
    }


}
