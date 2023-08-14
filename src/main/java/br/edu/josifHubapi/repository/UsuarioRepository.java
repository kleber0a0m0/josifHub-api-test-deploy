package br.edu.josifHubapi.repository;

import br.edu.josifHubapi.domain.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    //UserDetails findByEmail(String email);
    Optional<Usuario> findByEmail(String email);

    Optional<Usuario> findByHashid(String hashid);
//    Optional<Usuario> findByTokenConfirmacaoCadastro(String token);
//    Optional<Usuario> findByTokenRecuperacaoSenha(String token);


}