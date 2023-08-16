package br.edu.josifHubapi.repository;
import br.edu.josifHubapi.domain.Roles;
import br.edu.josifHubapi.enums.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface RolesRepository extends JpaRepository<Roles, Long> {
    Optional<Roles> findByRoleName(RoleName roleName);
}