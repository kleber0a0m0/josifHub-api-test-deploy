package br.edu.josifHubapi.service;

import br.edu.josifHubapi.domain.Avaliador;
import br.edu.josifHubapi.domain.Trabalhos;
import br.edu.josifHubapi.dto.AvaliadorDTO;
import br.edu.josifHubapi.dto.TrabalhoDTO;
import br.edu.josifHubapi.repository.AreaRepository;
import br.edu.josifHubapi.repository.AvaliadorRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

@Service
public class AvaliadorService {

    @Autowired
    AvaliadorRepository avaliadorRepository;

    public List<Avaliador> getAll(){
        return avaliadorRepository.findAll();
    }

    public Avaliador insert(AvaliadorDTO avaliadorDTO) {
        if(existsAvaliadorByCpf(avaliadorDTO.getCpf())) {
            return avaliadorRepository.findAvaliadorByCpf(avaliadorDTO.getCpf());
        }
        Avaliador item = Avaliador.builder()
                .nome(avaliadorDTO.getNome())
                .email(avaliadorDTO.getEmail())
                .senha(avaliadorDTO.getSenha())
                .telefone(avaliadorDTO.getTelefone())
                .cpf(avaliadorDTO.getCpf())
                .lattes(avaliadorDTO.getLattes())
                .areaFormacao(avaliadorDTO.getAreaFormacao())
                .instituicao(avaliadorDTO.getInstituicao())
                //.areaAtuacao(avaliadorDTO.getAreaAtuacao())
                .titulacao(avaliadorDTO.getTitulacao())
                .build();
        return avaliadorRepository.save(item);
    }



    public Boolean existsAvaliadorByCpf(String cpf){
        return avaliadorRepository.existsAvaliadorByCpf(cpf);
    }

    public Optional<Avaliador> findById(Long codigo) {
        return avaliadorRepository.findById(codigo);
    }

    @Transactional
    public void delete(Avaliador avaliador) {
        avaliadorRepository.delete(avaliador);
    }

    @Transactional
    public Avaliador save(Avaliador avaliador) {
        return avaliadorRepository.save(avaliador);
    }
}
