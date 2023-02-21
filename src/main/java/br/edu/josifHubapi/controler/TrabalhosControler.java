package br.edu.josifHubapi.controler;
import br.edu.josifHubapi.domain.Trabalhos;
import br.edu.josifHubapi.dto.TrabalhoDTO;
import br.edu.josifHubapi.service.TrabalhosService;
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
        return ResponseEntity.status(HttpStatus.OK).body(trabalhoService.insert(trabalhoDTO));
    }

    @PutMapping("/{codigo}")
    public ResponseEntity<Object> updateTrabalho(@PathVariable(value = "codigo") Long codigo, @RequestBody TrabalhoDTO trabalhoDTO){
        Optional<Trabalhos> trabalhoOptional = trabalhoService.findById(codigo);
        if (!trabalhoOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Trabalho não encontrado.");
        }else{
            var trabalhoModel = trabalhoOptional.get();
            BeanUtils.copyProperties(trabalhoDTO,trabalhoModel );
            return ResponseEntity.status(HttpStatus.OK).body(trabalhoService.save(trabalhoModel));
        }
    }

    @DeleteMapping("/{codigo}")
    public ResponseEntity<Object> deleteTrabalho(@PathVariable(value = "codigo") Long codigo){
        try {
            Optional<Trabalhos> trabalhoOptional = trabalhoService.findById(codigo);
            if (!trabalhoOptional.isPresent()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Trabalho não encontrado.");
            }
            trabalhoService.delete(trabalhoOptional.get());
            return ResponseEntity.status(HttpStatus.OK).body("Trabalho excluido com sucesso.");
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Erro ao excluir o trabalho! - ["+e.getMessage()+"]");
        }
    }




}
