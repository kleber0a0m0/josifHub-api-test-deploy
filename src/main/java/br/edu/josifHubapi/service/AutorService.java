package br.edu.josifHubapi.service;

import br.edu.josifHubapi.domain.Autor;
import br.edu.josifHubapi.domain.Avaliador;
import br.edu.josifHubapi.domain.Trabalhos;
import br.edu.josifHubapi.dto.AutorDTO;
import br.edu.josifHubapi.dto.AvaliadorDTO;
import br.edu.josifHubapi.dto.TrabalhoDTO;
import br.edu.josifHubapi.repository.AutorRepository;
import br.edu.josifHubapi.repository.AvaliadorRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AutorService {
    @Autowired
    private final AutorRepository autorRepository;

    public List<Autor> getAll(){
        return autorRepository.findAll();
    }

    public Optional<Autor> findById(Long codigo) {
        return autorRepository.findById(codigo);
    }

    public Autor insert(AutorDTO autorDTO) {
        Autor item = Autor.builder()
                .nome(autorDTO.getNome())
                .email(autorDTO.getEmail())
                .sobrenome(autorDTO.getSobrenome())
                .ORCID(autorDTO.getORCID())
                .pais(autorDTO.getPais())
                .instituicao(autorDTO.getInstituicao())
                .resumoBiografico(autorDTO.getResumoBiografico())
                .build();

        return autorRepository.save(item);
    }

    @Transactional
    public void delete(Autor autor) {
        autorRepository.delete(autor);
    }

    @Transactional
    public Autor save(Autor autor) {
        return autorRepository.save(autor);
    }


}
