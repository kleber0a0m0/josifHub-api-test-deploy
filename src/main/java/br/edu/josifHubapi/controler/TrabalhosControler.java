package br.edu.josifHubapi.controler;
import br.edu.josifHubapi.domain.Trabalhos;
import br.edu.josifHubapi.dto.TrabalhoDTO;
import br.edu.josifHubapi.service.TrabalhosService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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


    @DeleteMapping("/{codigo}")
    public ResponseEntity<Object> deleteClient(@PathVariable(value = "codigo") Long codigo){
        try {
            Optional<Trabalhos> trabalhoServiceOptional = trabalhoService.findById(codigo);
            if (!trabalhoServiceOptional.isPresent()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cliente não encontrado.");
            }
            trabalhoService.delete(trabalhoServiceOptional.get());
            return ResponseEntity.status(HttpStatus.OK).body("Trabalho excluido com sucesso.");
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Erro ao excluir o trabalho!");
        }
    }




}
