package br.edu.josifHubapi.service;

import br.edu.josifHubapi.domain.Avaliador;
import br.edu.josifHubapi.dto.AvaliadorDTO;
import br.edu.josifHubapi.repository.AvaliadorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AvaliadorService {

    @Autowired
    private final AvaliadorRepository avaliadorRepository;


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
                .areaAtuacao(avaliadorDTO.getAreaAtuacao())
                .titulacao(avaliadorDTO.getTitulacao())
                .build();
        return avaliadorRepository.save(item);
    }

    public List<Avaliador> getAll(){
        return avaliadorRepository.findAll();
    }

    public Boolean existsAvaliadorByCpf(String cpf){
        return avaliadorRepository.existsAvaliadorByCpf(cpf);
    }
}
