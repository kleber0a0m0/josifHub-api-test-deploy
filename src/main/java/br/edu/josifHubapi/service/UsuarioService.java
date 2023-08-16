package br.edu.josifHubapi.service;
import br.edu.josifHubapi.domain.Roles;
import br.edu.josifHubapi.domain.Usuario;
import br.edu.josifHubapi.dto.UsuarioCadastroDTO;
import br.edu.josifHubapi.dto.UsuarioRecuperarSenhaDTO;
import br.edu.josifHubapi.enums.RoleName;
import br.edu.josifHubapi.enums.SituacaoUsuario;
import br.edu.josifHubapi.enums.TipoToken;
import br.edu.josifHubapi.repository.RolesRepository;
import br.edu.josifHubapi.repository.UsuarioRepository;
import br.edu.josifHubapi.security.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class UsuarioService {
    public HashMap<String, Object> response = new HashMap<>();

    @Autowired
    private final UsuarioRepository usuarioRepository;

    @Autowired
    TokenService tokenService;

    @Autowired
    MailService mailService;

    @Autowired
    RolesRepository rolesRepository;

    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public Optional<Usuario> getUsuarioByEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }

    public Optional<Usuario> getUsuarioByHashid(String hashid) {
        return usuarioRepository.findByHashid(UUID.fromString(hashid));
    }

    public Usuario update(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    public ResponseEntity<HashMap<String, Object>> cadastrarUsuario(UsuarioCadastroDTO usuarioDTO) {
        if (this.getUsuarioByEmail(usuarioDTO.email()).isPresent()) {
            response.put("erro", true);
            response.put("mensagem", "Já existe um usuário cadastrado com esse e-mail.");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
        }
        String senhaCriptografada = passwordEncoder.encode(usuarioDTO.senha());
        Usuario usuario = new Usuario(usuarioDTO.email(),senhaCriptografada);
        usuario.setSituacao(SituacaoUsuario.PENDENTE_VALIDACAO_EMAIL);
        RoleName roleNome = usuarioDTO.funcao();
        if (roleNome != null) {
            Optional<Roles> role = rolesRepository.findByRoleName(roleNome);
            if (role.isPresent()) {
                List<Roles> roles = new ArrayList<>();
                roles.add(role.get());
                usuario.setRoles(roles);
            } else {
                response.put("erro", true);
                response.put("mensagem", "A função " + roleNome + " não foi encontrada.");
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
            }
        } else {
            response.put("erro", true);
            response.put("mensagem", "A função especificada é inválida.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        var token = tokenService.gerarToken(usuario, TipoToken.CONFIRMACAO_CADASTRO);
        mailService.sendMailConfirmarCadastro(usuario.getEmail(), token);
        System.err.println("token_CONFIRMACAO_CADASTRO[" + usuario.getEmail() + "]: " + token);
        try {
            usuarioRepository.save(usuario);
            response.put("erro", false);
            response.put("mensagem", "Usuário cadastrado com sucesso.");
            return ResponseEntity.ok().body(response);
        }catch (Exception e){
            response.put("erro", true);
            response.put("mensagem", "Erro ao cadastrar usuário.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    public ResponseEntity<HashMap<String,Object>> confirmarCadastro(String token) {
        if (tokenService.isTokenValido(token, TipoToken.CONFIRMACAO_CADASTRO)){
            Optional<Usuario> usuarioOptional = this.getUsuarioByHashid(tokenService.getHashid(token));
            if(usuarioOptional.isEmpty()){
                response.put("erro", true);
                response.put("mensagem", "Usuário não encontrado.");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
            Usuario usuario = usuarioOptional.get();
            usuario.setSituacao(SituacaoUsuario.EMAIL_VALIDADO);
            try {
                this.update(usuario);
            } catch (Exception e) {
                response.put("erro", true);
                response.put("mensagem", "Erro ao confirmar cadastro.");
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
            }
            response.put("erro", false);
            response.put("mensagem", "Email validado com sucesso.");
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
        }
        response.put("erro", true);
        response.put("mensagem", "Email não validado.");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    public ResponseEntity<HashMap<String, Object>> recuperarSenha(String email) {
        Optional<Usuario> usuario = this.getUsuarioByEmail(email);
        try {
            if (usuario.isPresent()) {
                String tokenRecuperacaoSenha = tokenService.gerarToken(usuario.get(), TipoToken.RECUPERACAO_SENHA);
                if (mailService.sendMailRecuperarSenha(usuario.get().getEmail(), tokenRecuperacaoSenha)) {
                    response.put("erro", false);
                    response.put("mensagem", "E-mail de recuperação de senha enviado com sucesso.");
                    return ResponseEntity.ok().body(response);
                } else {
                    response.put("erro", true);
                    response.put("mensagem", "Erro ao enviar e-mail de recuperação de senha.");
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
                }
            } else {
                response.put("erro", true);
                response.put("mensagem", "Nenhum usuário encontrado com esse e-mail.");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }
        } catch (Exception e) {
            response.put("erro", true);
            response.put("mensagem", "Erro ao recuperar senha.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    public ResponseEntity<HashMap<String, Object>> novaSenha(String token, UsuarioRecuperarSenhaDTO usuarioRecuperarSenhaDTO) {
        try {
            Optional<Usuario> usuario = this.getUsuarioByHashid(tokenService.getHashid(token));
            if (usuario.isPresent()) {
                if (tokenService.isTokenValido(token, TipoToken.RECUPERACAO_SENHA)) {
                    usuario.get().setSenha(passwordEncoder.encode(usuarioRecuperarSenhaDTO.senha()));
                    usuario.get().setSituacao(SituacaoUsuario.EMAIL_VALIDADO);
                    if (this.update(usuario.get()) == null) {
                        response.put("erro", true);
                        response.put("mensagem", "Erro ao alterar senha.");
                        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
                    }
                }
                mailService.sendMailNovaSenha(usuario.get().getEmail());
                response.put("erro", false);
                response.put("mensagem", "Senha alterada com sucesso.");
                return ResponseEntity.ok().body(response);
            } else {
                response.put("erro", true);
                response.put("mensagem", "Usuário não encontrado para esse token.");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }
        } catch (Exception e) {
            response.put("erro", true);
            response.put("mensagem", "Erro ao alterar senha.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

}
