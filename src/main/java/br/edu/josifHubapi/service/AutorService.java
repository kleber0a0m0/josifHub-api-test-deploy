package br.edu.josifHubapi.service;

import br.edu.josifHubapi.domain.Autor;
import br.edu.josifHubapi.domain.Trabalho;
import br.edu.josifHubapi.dto.AutorDTO;
import br.edu.josifHubapi.repository.AutorRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

    public Autor teste(Autor autor){

        if (autor != null) {
            Optional<Autor> autorOptional = this.findById(autor.getCodigo());

            if (!autorOptional.isPresent()) {
                System.out.println("Autor não encontrado");
            }

            Autor autorAntigo = autorOptional.get();

            autorAntigo.setNome(autor.getNome());
            autorAntigo.setEmail(autor.getEmail());
            autorAntigo.setSobrenome(autor.getSobrenome());
            autorAntigo.setPais(autor.getPais());
            autorAntigo.setInstituicao(autor.getInstituicao());
            autorAntigo.setResumoBiografico(autor.getResumoBiografico());
            autorAntigo.setORCID(autor.getORCID());

            return autorAntigo;
        }
        return null;
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
