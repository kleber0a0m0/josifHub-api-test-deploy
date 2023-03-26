package br.edu.josifHubapi.service;
import br.edu.josifHubapi.domain.Trabalho;
import br.edu.josifHubapi.dto.TrabalhoDTO;
import br.edu.josifHubapi.repository.TrabalhoRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor

public class TrabalhoService {
    @Autowired
    private final TrabalhoRepository trabalhoRepository;
    public List<Trabalho> getAll(){
        return trabalhoRepository.findAll();
    }

    public Trabalho insert(TrabalhoDTO trabalhoDTO) {
        Trabalho item = Trabalho.builder()
                .titulo(trabalhoDTO.getTitulo())
                .resumo(trabalhoDTO.getResumo())
                .palavrasChave(trabalhoDTO.getPalavrasChave())
                .area(trabalhoDTO.getArea())
                .autor1(trabalhoDTO.getAutor1())
                .autor2(trabalhoDTO.getAutor2())
                .autor3(trabalhoDTO.getAutor3())
                .autor4(trabalhoDTO.getAutor4())
                .autor5(trabalhoDTO.getAutor5())
                .autor6(trabalhoDTO.getAutor6())
                .autor7(trabalhoDTO.getAutor7())
                .autor8(trabalhoDTO.getAutor8())
                .autor9(trabalhoDTO.getAutor9())
                .autor10(trabalhoDTO.getAutor10())
                .autor11(trabalhoDTO.getAutor11())
                .build();

        return trabalhoRepository.save(item);
    }

    public Optional<Trabalho> findById(Long codigo) {
        return trabalhoRepository.findById(codigo);
    }

    @Transactional
    public Trabalho save(Trabalho trabalho) {
        return trabalhoRepository.save(trabalho);
    }

    @Transactional
    public void delete(Trabalho trabalhos) {
        trabalhoRepository.delete(trabalhos);
    }
}
