package br.edu.josifHubapi.repository;

import br.edu.josifHubapi.domain.Avaliador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


public interface AvaliadorRepository extends JpaRepository<Avaliador, Long> {

    Boolean existsAvaliadorByCpf(String cpf);
    Avaliador findAvaliadorByCpf(String cpf);


}
