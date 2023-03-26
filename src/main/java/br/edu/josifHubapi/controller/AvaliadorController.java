package br.edu.josifHubapi.controller;

import br.edu.josifHubapi.domain.Area;
import br.edu.josifHubapi.domain.Avaliador;
import br.edu.josifHubapi.dto.AreaDTO;
import br.edu.josifHubapi.dto.AvaliadorDTO;
import br.edu.josifHubapi.service.AreaService;
import br.edu.josifHubapi.service.AvaliadorService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/avaliadores", produces = MediaType.APPLICATION_JSON_VALUE)
public class AvaliadorController {

    @Autowired
    AvaliadorService avaliadorService;

    @Autowired
    AreaService areaService;

    @GetMapping
    public ResponseEntity findAllAvaliador(){
        List<Avaliador> avaliadores = avaliadorService.getAll();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(avaliadores);
    }

    @PostMapping
    public ResponseEntity<ResponseEntity> insert(@RequestBody AvaliadorDTO avaliadorDTO) {
        ResponseEntity avaliadorInserido = avaliadorService.insert(avaliadorDTO);
        return avaliadorInserido;
    }


    @PutMapping("/{codigo}")
    public ResponseEntity<Object> updateAvaliador(@PathVariable(value = "codigo") Long codigo, @RequestBody AvaliadorDTO avaliadorDTO) {
        // Busca o avaliador a ser atualizado pelo código passado na URL
        Optional<Avaliador> avaliadorOptional = avaliadorService.findById(codigo);
        // Cria um conjunto vazio para armazenar as áreas de atuação do avaliador
        Set<Area> areasAtuacao = new HashSet<>();
        // Para cada área enviada pelo cliente, busca a área correspondente no banco de dados e adiciona ao conjunto de áreas do avaliador
        for (AreaDTO areaDTO : avaliadorDTO.getAreasAtuacao()) {
            Optional<Area> area = areaService.findById(areaDTO.getCodigo());
            area.ifPresent(areasAtuacao::add);
        }
        // Verifica se o avaliador foi encontrado
        if (avaliadorOptional.isEmpty()) {
            // Retorna um erro 404 com uma mensagem indicando que o avaliador não foi encontrado
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Avaliador não encontrado.");
        } else {
            // Se o avaliador foi encontrado, copia as propriedades do DTO para a instância do avaliador
            var avaliadorModel = avaliadorOptional.get();
            BeanUtils.copyProperties(avaliadorDTO, avaliadorModel);

            // Define o valor do ID do objeto antes de salvá-lo novamente no banco de dados
            avaliadorModel.setCodigo(codigo);

            // Define as áreas de atuação do avaliador
            avaliadorModel.setAreasAtuacao(areasAtuacao);

            // Salva o avaliador atualizado no banco de dados e retorna um objeto ResponseEntity com o status 200 OK e o corpo contendo o objeto atualizado
            return ResponseEntity.status(HttpStatus.OK).body(avaliadorService.save(avaliadorModel));
        }
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

}
