package br.edu.josifHubapi.service;

import br.edu.josifHubapi.domain.Autor;
import br.edu.josifHubapi.domain.Avaliador;
import br.edu.josifHubapi.dto.AvaliadorDTO;
import br.edu.josifHubapi.repository.AutorRepository;
import br.edu.josifHubapi.repository.AvaliadorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AutorService {
    @Autowired
    private final AutorRepository autorRepository;

    public List<Autor> getAll(){
        return autorRepository.findAll();
    }

}
