package br.edu.josifHubapi.controller;

import br.edu.josifHubapi.domain.Avaliador;
import br.edu.josifHubapi.repository.AreaRepository;
import br.edu.josifHubapi.repository.AvaliadorRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/teste222")
public class ControllerTest {
    private AvaliadorRepository avaliadorRepository;

    private AreaRepository areaRepository;

    public ControllerTest(AvaliadorRepository avaliadorRepository, AreaRepository areaRepository) {
        this.avaliadorRepository = avaliadorRepository;
        this.areaRepository = areaRepository;
    }

    @GetMapping
    public List<Avaliador> findAllAvaliador(){
        return avaliadorRepository.findAll();
    }



}
