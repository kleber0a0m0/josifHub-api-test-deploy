package br.edu.josifHubapi.repository;

import br.edu.josifHubapi.domain.AreaAvaliadores;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AreaAvaliadoresRepository extends JpaRepository<AreaAvaliadores, Long> {

    Boolean existsAreaAvaliadoresByCodigoAvaliadorAndCodigoArea(String codigoAvaliador, String codigoArea);
    List<AreaAvaliadores> findAllByCodigoAvaliador(String codigoAvaliador);

}
