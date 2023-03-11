package br.edu.josifHubapi.controler;

import br.edu.josifHubapi.domain.Area;
import br.edu.josifHubapi.dto.AreaDTO;
import br.edu.josifHubapi.service.AreaService;
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
@RequestMapping(value = "/api/v1/area", produces = MediaType.APPLICATION_JSON_VALUE)
public class AreaControler {
    @Autowired
    AreaService areaService;

    @GetMapping
    public ResponseEntity<List<Area>> findAll() {
        return ResponseEntity.ok(areaService.findAll());
    }

    @PostMapping
    public ResponseEntity<Area> insert(@RequestBody AreaDTO areaDTO) {
        return ResponseEntity.status(HttpStatus.OK).body(areaService.insert(areaDTO));
    }


    @PutMapping("/{codigo}")
    public ResponseEntity<Object> updateArea(@PathVariable(value = "codigo") Long codigo, @RequestBody AreaDTO areaDTO){
        Optional<Area> areaOptional = areaService.findById(codigo);
        if (!areaOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Área não encontrada.");
        }else{
            var areaModel = areaOptional.get();
            BeanUtils.copyProperties(areaDTO,areaModel );
            return ResponseEntity.status(HttpStatus.OK).body(areaService.save(areaModel));
        }
    }

    @DeleteMapping("/{codigo}")
    public ResponseEntity<Object> deleteArea(@PathVariable(value = "codigo") Long codigo){
        try {
            Optional<Area> areaOptional = areaService.findById(codigo);
            if (!areaOptional.isPresent()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Área não encontrada.");
            }
            areaService.delete(areaOptional.get());
            return ResponseEntity.status(HttpStatus.OK).body("Área excluida com sucesso.");
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Erro ao excluir a área! - ["+e.getMessage()+"]");
        }
    }

}
