package br.edu.josifHubapi.controller;

import br.edu.josifHubapi.domain.Autor;
import br.edu.josifHubapi.domain.Usuario;
import br.edu.josifHubapi.dto.ConfirmacaoCadastroDTO;
import br.edu.josifHubapi.dto.UsuarioCadastroDTO;
import br.edu.josifHubapi.dto.UsuarioRecuperarSenhaDTO;
import br.edu.josifHubapi.enums.SituacaoUsuario;
import br.edu.josifHubapi.security.TokenService;
import br.edu.josifHubapi.service.MailService;
import br.edu.josifHubapi.service.UsuarioService;
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

    @PostMapping
    public ResponseEntity<HashMap> insert(@RequestBody UsuarioCadastroDTO usuarioDTO) {
        HashMap<String, Object> response = new HashMap<>();

        if (usuarioService.getUsuarioByEmail(usuarioDTO.email()).isPresent()) {
            response.put("erro", true);
            response.put("mensagem", "Já existe um usuário cadastrado com esse e-mail.");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
        }

        String senhaCriptografada = passwordEncoder.encode(usuarioDTO.senha());

        Usuario usuario = new Usuario(usuarioDTO.email(),senhaCriptografada);
        var token = tokenService.gerarTokenConfirmacaoCadastro(usuario);
        mailService.sendMailConfirmarCadastro(usuario.getEmail(), token);

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
        HashMap<String, Object> response = new HashMap<>();

        if(token == null || token.isEmpty()){
            response.put("erro", true);
            response.put("mensagem", "Token não informado.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        System.out.println(tokenService.getSubject(token));
        System.out.println(tokenService.getClaim(token, "hashid"));

        Usuario usuario = usuarioService.getUsuarioByTokenConfirmacaoCadastro(token).get();

        if (usuario.getTokenConfirmacaoCadastro().equals(token)) {
            usuario.setSituacao(SituacaoUsuario.EMAIL_VALIDADO);
            usuario.setTokenConfirmacaoCadastro(null);
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

//    @PostMapping(value = "/recuperar-senha/{email}")
//    public ResponseEntity<Usuario> recuperarSenha(@PathVariable("email") String email) {
//        Usuario usuario = usuarioService.getUsuarioByEmail(email).get();//TODO: tratar o optional
//
//        if (usuario.getEmail().equals(email)) {
//            String tokenRecuperacaoSenha = tokenService.gerarTokenRecuperacaoSenha(usuario);
//            usuario.setTokenRecuperacaoSenha(tokenRecuperacaoSenha);
//            mailService.sendMailRecuperarSenha(usuario.getEmail(), tokenRecuperacaoSenha);
//            usuarioService.update(usuario);
//            return ResponseEntity.ok().build();
//        }
//
//        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
//    }

//    @PostMapping(value = "/nova-senha/{token}")
//    public ResponseEntity<Usuario> novaSenha(@PathVariable("token") String token,@RequestBody UsuarioRecuperarSenhaDTO usuarioRecuperarSenhaDTO) {
//        Optional<Usuario> usuarioOptional = usuarioService.getUsuarioByTokenRecuperacaoSenha(token);
//
//        if (usuarioOptional.isEmpty()) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
//        }
//
//        var usuario = usuarioOptional.get();
//        usuario.setSituacao(SituacaoUsuario.EMAIL_VALIDADO);
//
//        if (usuario.getTokenRecuperacaoSenha().equals(token)) {
//            usuario.setSenha(passwordEncoder.encode(usuarioRecuperarSenhaDTO.senha()));
//            usuario.setTokenRecuperacaoSenha(null);
//            return ResponseEntity.ok().build();
//        }
//        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
//    }
}
