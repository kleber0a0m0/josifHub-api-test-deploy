package br.edu.josifHubapi.service;

import br.edu.josifHubapi.domain.Area;
import br.edu.josifHubapi.domain.AreaAvaliadores;
import br.edu.josifHubapi.domain.Avaliador;
import br.edu.josifHubapi.dto.AreaAvaliadoresDTO;
import br.edu.josifHubapi.dto.AvaliadorDTO;
import br.edu.josifHubapi.repository.AreaAvaliadoresRepository;
import br.edu.josifHubapi.repository.AreaRepository;
import br.edu.josifHubapi.repository.AvaliadorRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AvaliadorService {

    @Autowired
    AvaliadorRepository avaliadorRepository;

    @Autowired
    AreaRepository areaRepository;

    @Autowired
    AreaAvaliadoresRepository areaAvaliadoresRepository;

    public Boolean existsAvaliadorByCpf(String cpf){
        return avaliadorRepository.existsAvaliadorByCpf(cpf);
    }


    public AvaliadorDTO criarAvaliador(AvaliadorDTO avaliadorDTO) {
        Avaliador avaliador = mapearDTOParaAvaliador(avaliadorDTO);
        Avaliador novoAvaliador = avaliadorRepository.save(avaliador);

        return mapearAvaliadorParaDTO(novoAvaliador);
    }

    private AvaliadorDTO mapearAvaliadorParaDTO(Avaliador avaliador) {
        AvaliadorDTO avaliadorDTO = new AvaliadorDTO();
        avaliadorDTO.setCodigo(avaliador.getCodigoAvaliador());
        avaliadorDTO.setNome(avaliador.getNome());
        avaliadorDTO.setCpf(avaliador.getCpf());
        avaliadorDTO.setEmail(avaliador.getEmail());
        avaliadorDTO.setTelefone(avaliador.getTelefone());
        avaliadorDTO.setLattes(avaliador.getLattes());
        avaliadorDTO.setInstituicao(avaliador.getInstituicao());
        avaliadorDTO.setTitulacao(avaliador.getTitulacao());
        avaliadorDTO.setSenha(avaliador.getSenha());

        return avaliadorDTO;
    }

    private Avaliador mapearDTOParaAvaliador(AvaliadorDTO avaliadorDTO) {
        Avaliador avaliador = new Avaliador();
        avaliador.setNome(avaliadorDTO.getNome());
        avaliador.setEmail(avaliadorDTO.getEmail());
        avaliador.setCpf(avaliadorDTO.getCpf());
        avaliador.setTelefone(avaliadorDTO.getTelefone());
        avaliador.setLattes(avaliadorDTO.getLattes());
        avaliador.setInstituicao(avaliadorDTO.getInstituicao());
        avaliador.setTitulacao(avaliadorDTO.getTitulacao());
        avaliador.setAreaFormacao(avaliadorDTO.getAreaFormacao());
        avaliador.setSenha(avaliadorDTO.getSenha());
        // Mapear outros campos do avaliador...

        return avaliador;
    }
    public AreaAvaliadores adicionarAreaAoAvaliador(AreaAvaliadoresDTO areaAvaliadoresDTO) {
        Avaliador avaliador = avaliadorRepository.findById(Long.valueOf(areaAvaliadoresDTO.getCodigo_avaliador()))
                .orElseThrow(() -> new EntityNotFoundException("Avaliador não encontrado"));
        Area area = areaRepository.findAreaByCodigoArea(areaAvaliadoresDTO.getCodigo_area())
                .orElseThrow(() -> new EntityNotFoundException("Area não encontrada"));
        if(areaAvaliadoresRepository.existsAreaAvaliadoresByCodigoAvaliadorAndCodigoArea(areaAvaliadoresDTO.getCodigo_avaliador(), areaAvaliadoresDTO.getCodigo_area())){
            throw new EntityExistsException("Area já adicionada ao avaliador");
        }

        AreaAvaliadores areaAvaliadores = new AreaAvaliadores();
        areaAvaliadores.setCodigoArea(areaAvaliadoresDTO.getCodigo_area());
        areaAvaliadores.setCodigoAvaliador(areaAvaliadoresDTO.getCodigo_avaliador());

        return areaAvaliadoresRepository.save(areaAvaliadores);
    }

    public List<AvaliadorDTO> getAllAvaliadores() {
        List<Avaliador> avaliadores = avaliadorRepository.findAll();
        List<AvaliadorDTO> avaliadoresDTO = new ArrayList<>();

        for (Avaliador avaliador : avaliadores) {
            AvaliadorDTO avaliadorDTO = mapearAvaliadorParaDTO(avaliador);
            avaliadoresDTO.add(avaliadorDTO);
        }
        return avaliadoresDTO;
    }

    public List<AreaAvaliadores> getAllAvaliadoresById(String id) {
        return areaAvaliadoresRepository.findAllByCodigoAvaliador(id);
    }
}
