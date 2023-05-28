package br.edu.josifHubapi.service;

import br.edu.josifHubapi.domain.Area;
import br.edu.josifHubapi.dto.AreaDTO;
import br.edu.josifHubapi.repository.AreaRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AreaService {
    @Autowired
    private final AreaRepository areaRepository;

    public List<Area> findAll(){
        return areaRepository.findAll();
    }

    public Area insert(AreaDTO areaDTO) {
        Area item = Area.builder()
                .codigoArea(areaDTO.getCodigo_area())
                .nome(areaDTO.getNome())
                .build();
        return areaRepository.save(item);
    }


}
