package br.edu.josifHubapi.repository;

import br.edu.josifHubapi.domain.Autor;
import br.edu.josifHubapi.domain.Avaliador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AutorRepository extends JpaRepository<Autor, Long> {

}
