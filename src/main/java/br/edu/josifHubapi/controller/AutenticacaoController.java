package br.edu.josifHubapi.controller;

import br.edu.josifHubapi.domain.Usuario;
import br.edu.josifHubapi.dto.AutenticacaoDTO;
import br.edu.josifHubapi.enums.SituacaoUsuario;
import br.edu.josifHubapi.enums.TipoToken;
import br.edu.josifHubapi.security.TokenDadosJWT;
import br.edu.josifHubapi.security.TokenService;
import br.edu.josifHubapi.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/login")
public class AutenticacaoController {

    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private TokenService tokenService;

    @Autowired
    UsuarioService usuarioService;

    @PostMapping
    public ResponseEntity<Object> efetuarLogin(@RequestBody @Valid AutenticacaoDTO autenticacaoDTO) {
        HashMap<String, Object> response = new HashMap<>();

        var token = new UsernamePasswordAuthenticationToken(autenticacaoDTO.email(), autenticacaoDTO.senha());
        var autentication = manager.authenticate(token);

        if (autentication.getPrincipal() instanceof org.springframework.security.core.userdetails.User) {
            User user = (org.springframework.security.core.userdetails.User) autentication.getPrincipal();
            Optional<Usuario> usuario = usuarioService.getUsuarioByEmail(user.getUsername());

            if(usuario.get().getSituacao() == SituacaoUsuario.PENDENTE_VALIDACAO_EMAIL) {
                response.put("erro", true);
                response.put("mensagem", "Email do usuário não foi validado.");
                return ResponseEntity.status(HttpStatus.LOCKED).body(response);

            }

            var tokenJwt = tokenService.gerarToken(usuario.get(), TipoToken.LOGIN);
            return ResponseEntity.ok(new TokenDadosJWT(tokenJwt));
        } else {
            response.put("erro", true);
            response.put("mensagem", "Não foi possível efetuar o login.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }
}