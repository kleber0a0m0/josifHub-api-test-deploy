package br.edu.josifHubapi.service;

import br.edu.josifHubapi.domain.Trabalhos;
import br.edu.josifHubapi.repository.TrabalhosRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
@RequiredArgsConstructor

public class TrabalhosService {
    @Autowired
    private final TrabalhosRepository trabalhoRepository;
    public List<Trabalhos> getAll(){
        return trabalhoRepository.findAll();
    }
}
