package br.edu.josifHubapi.controller;
import br.edu.josifHubapi.dto.UsuarioCadastroDTO;
import br.edu.josifHubapi.dto.UsuarioRecuperarSenhaDTO;
import br.edu.josifHubapi.security.TokenService;
import br.edu.josifHubapi.service.MailService;
import br.edu.josifHubapi.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;

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

    @PostMapping
    public ResponseEntity<HashMap<String, Object>> cadastrarUsuario(@RequestBody UsuarioCadastroDTO usuarioDTO) {
        return usuarioService.cadastrarUsuario(usuarioDTO);
    }

    @PostMapping(value = "/confirmar-cadastro/{token}")
    public ResponseEntity<HashMap<String, Object>> confirmarCadastro(@PathVariable("token") String token) {
        return usuarioService.confirmarCadastro(token);
    }

    @PostMapping(value = "/recuperar-senha/{email}")
    public ResponseEntity<HashMap<String, Object>> recuperarSenha(@PathVariable("email") String email) {
        return usuarioService.recuperarSenha(email);
    }

    @PostMapping(value = "/nova-senha/{token}")
    public ResponseEntity<HashMap<String, Object>> novaSenha(@PathVariable("token") String token, @RequestBody UsuarioRecuperarSenhaDTO usuarioRecuperarSenhaDTO) {
        return usuarioService.novaSenha(token, usuarioRecuperarSenhaDTO);
    }
}
