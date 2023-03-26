package br.edu.josifHubapi.service;

import br.edu.josifHubapi.domain.Area;
import br.edu.josifHubapi.domain.Avaliador;
import br.edu.josifHubapi.dto.AreaDTO;
import br.edu.josifHubapi.dto.AvaliadorDTO;
import br.edu.josifHubapi.repository.AreaRepository;
import br.edu.josifHubapi.repository.AvaliadorRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class AvaliadorService {

    @Autowired
    AvaliadorRepository avaliadorRepository;

    @Autowired
    AreaRepository areaRepository;



    public List<Avaliador> getAll(){
        return avaliadorRepository.findAll();
    }

    public ResponseEntity insert(AvaliadorDTO avaliadorDTO) {
        // Cria uma nova instância de Avaliador a partir do DTO enviado pelo cliente
        Avaliador avaliador = new Avaliador();

        // Copia as propriedades do DTO para a instância de Avaliador usando a biblioteca BeanUtils do Spring
        BeanUtils.copyProperties(avaliadorDTO, avaliador);

        // Cria um conjunto vazio para armazenar as áreas de atuação do avaliador
        Set<Area> areasAtuacao = new HashSet<>();

        // Para cada área enviada pelo cliente, busca a área correspondente no banco de dados e adiciona ao conjunto de áreas do avaliador
        for (AreaDTO areaDTO : avaliadorDTO.getAreasAtuacao()) {
            Optional<Area> area = areaRepository.findById(areaDTO.getCodigo());
            area.ifPresent(areasAtuacao::add);
        }

        // Define as áreas de atuação do avaliador com as áreas obtidas acima
        avaliador.setAreasAtuacao(areasAtuacao);

        // Verifica se o CPF já existe no banco de dados
        if (avaliadorRepository.existsAvaliadorByCpf(avaliadorDTO.getCpf())) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body("O CPF '"+ avaliadorDTO.getCpf() +"' já está cadastrado");
        }

        // Salva o avaliador no banco de dados e retorna a instância salva
        Avaliador avaliadorSalvo = avaliadorRepository.save(avaliador);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(avaliadorSalvo);
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
