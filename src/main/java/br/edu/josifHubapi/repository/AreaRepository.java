package br.edu.josifHubapi.repository;

import br.edu.josifHubapi.domain.Area;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface AreaRepository extends JpaRepository<Area, Long> {

    Optional<Area> findAreaByCodigoArea(String codigoArea);
}
