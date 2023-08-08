package br.edu.josifHubapi.controller;

import br.edu.josifHubapi.domain.Autor;
import br.edu.josifHubapi.domain.Usuario;
import br.edu.josifHubapi.dto.ConfirmacaoCadastroDTO;
import br.edu.josifHubapi.dto.UsuarioCadastroDTO;
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

import java.util.List;

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

    @GetMapping
    public ResponseEntity<List<Usuario>> findAll() {
        return ResponseEntity.ok(usuarioService.findAll());
    }

    @PostMapping
    public ResponseEntity<Usuario> insert(@RequestBody UsuarioCadastroDTO usuarioDTO) {

        if (usuarioService.getUsuarioByEmail(usuarioDTO.email()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        var tokenConfirmacaoCadastro = tokenService.gerarTokenConfirmacaoCadastro(usuarioDTO);

        String senhaCriptografada = passwordEncoder.encode(usuarioDTO.senha());

        Usuario usuario = new Usuario(usuarioDTO.email(),senhaCriptografada,tokenConfirmacaoCadastro);
        mailService.sendMailConfirmarCadastro(usuario.getEmail(), tokenConfirmacaoCadastro);

        return ResponseEntity.ok(usuarioService.insert(usuario));
    }

    @PostMapping(value = "/confirmar-cadastro")
    public ResponseEntity<Usuario> validarEmail(@RequestBody ConfirmacaoCadastroDTO confirmacaoCadastroDTO) {
        Usuario usuario = usuarioService.getUsuarioByTokenConfirmacaoCadastro(confirmacaoCadastroDTO.token()).get();
        if (usuario.getTokenConfirmacaoCadastro().equals(confirmacaoCadastroDTO.token())) {
            usuario.setSituacao(SituacaoUsuario.EMAIL_VALIDADO);
            usuario.setTokenConfirmacaoCadastro(null);
            return ResponseEntity.ok(usuarioService.insert(usuario));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
}
