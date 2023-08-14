package br.edu.josifHubapi.controller;

import br.edu.josifHubapi.domain.Autor;
import br.edu.josifHubapi.domain.Usuario;
import br.edu.josifHubapi.dto.ConfirmacaoCadastroDTO;
import br.edu.josifHubapi.dto.UsuarioCadastroDTO;
import br.edu.josifHubapi.dto.UsuarioRecuperarSenhaDTO;
import br.edu.josifHubapi.enums.SituacaoUsuario;
import br.edu.josifHubapi.enums.TipoToken;
import br.edu.josifHubapi.security.TokenService;
import br.edu.josifHubapi.service.MailService;
import br.edu.josifHubapi.service.UsuarioService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/usuarios")
public class UserController {

    @Autowired
    UsuarioService usuarioService;

    @Autowired
    MailService mailService;

    @Autowired
    TokenService tokenService;

    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public HashMap<String, Object> response = new HashMap<>();

    @PostMapping
    public ResponseEntity<HashMap> insert(@RequestBody UsuarioCadastroDTO usuarioDTO) {
        if (usuarioService.getUsuarioByEmail(usuarioDTO.email()).isPresent()) {
            response.put("erro", true);
            response.put("mensagem", "Já existe um usuário cadastrado com esse e-mail.");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
        }

        String senhaCriptografada = passwordEncoder.encode(usuarioDTO.senha());

        Usuario usuario = new Usuario(usuarioDTO.email(),senhaCriptografada);
        usuario.setSituacao(SituacaoUsuario.PENDENTE_VALIDACAO_EMAIL);

        var token = tokenService.gerarToken(usuario, TipoToken.CONFIRMACAO_CADASTRO);


        mailService.sendMailConfirmarCadastro(usuario.getEmail(), token);
        System.err.println("token_CONFIRMACAO_CADASTRO: " + token);

        try {
            usuarioService.insert(usuario);
            response.put("erro", false);
            response.put("mensagem", "Usuário cadastrado com sucesso.");
            return ResponseEntity.created(null).body(response);
        }catch (Exception e){
            response.put("erro", true);
            response.put("mensagem", "Erro ao cadastrar usuário.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }

    }

    @PostMapping(value = "/confirmar-cadastro/{token}")
    public ResponseEntity<HashMap> confirmarCadastro(@PathVariable("token") String token) {

       if (tokenService.isTokenValido(token, TipoToken.CONFIRMACAO_CADASTRO)){
           Usuario usuario = usuarioService.getUsuarioByHashid(tokenService.getHashid(token)).get(); //TODO: Alterar para optional
           usuario.setSituacao(SituacaoUsuario.EMAIL_VALIDADO);
           try {
               usuarioService.update(usuario);
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

    @PostMapping(value = "/recuperar-senha/{email}")
    public ResponseEntity<HashMap> recuperarSenha(@PathVariable("email") String email) {
        Optional<Usuario> usuario = usuarioService.getUsuarioByEmail(email);

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

    @PostMapping(value = "/nova-senha/{token}")
    public ResponseEntity<HashMap> novaSenha(@PathVariable("token") String token, @RequestBody UsuarioRecuperarSenhaDTO usuarioRecuperarSenhaDTO) {
        try {
            Optional<Usuario> usuario = usuarioService.getUsuarioByHashid(tokenService.getHashid(token));

            if (usuario.isPresent()) {

                if (tokenService.isTokenValido(token, TipoToken.RECUPERACAO_SENHA)) {
                    usuario.get().setSenha(passwordEncoder.encode(usuarioRecuperarSenhaDTO.senha()));
                    usuario.get().setSituacao(SituacaoUsuario.EMAIL_VALIDADO);

                    if (usuarioService.update(usuario.get()) == null) {
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
