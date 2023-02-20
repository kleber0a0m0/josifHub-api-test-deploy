package br.edu.josifHubapi.repository;

import br.edu.josifHubapi.domain.Trabalhos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrabalhosRepository extends JpaRepository<Trabalhos, Long> {


}
