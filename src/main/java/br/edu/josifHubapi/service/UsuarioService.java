package br.edu.josifHubapi.service;

import br.edu.josifHubapi.domain.Area;
import br.edu.josifHubapi.domain.Usuario;
import br.edu.josifHubapi.dto.AreaDTO;
import br.edu.josifHubapi.dto.UsuarioCadastroDTO;
import br.edu.josifHubapi.repository.AreaRepository;
import br.edu.josifHubapi.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UsuarioService {
    @Autowired
    private final UsuarioRepository usuarioRepository;

    public Optional<Usuario> getUsuarioByEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }

    public Optional<Usuario> getUsuarioByHashid(String hashid) {
        return usuarioRepository.findByHashid(UUID.fromString(hashid));
    }
//    public Optional<Usuario> getUsuarioByTokenConfirmacaoCadastro(String token) {
//        return usuarioRepository.findByTokenConfirmacaoCadastro(token);
//    }

//    public Optional<Usuario> getUsuarioByTokenRecuperacaoSenha (String token) {
//        return usuarioRepository.findByTokenRecuperacaoSenha(token);
//    }

    public List<Usuario> findAll(){
        return usuarioRepository.findAll();
    }

    public Usuario insert(Usuario usuario) {
        //TODO: SALVAR A ROLE DO USUARIO
        return usuarioRepository.save(usuario);
    }

    public Usuario update(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }
}
