package br.edu.josifHubapi.controller;

import br.edu.josifHubapi.domain.Usuario;
import br.edu.josifHubapi.dto.AutenticacaoDTO;
import br.edu.josifHubapi.security.TokenDadosJWT;
import br.edu.josifHubapi.security.TokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class AutenticacaoController {

    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private TokenService tokenService;

    @PostMapping
    public ResponseEntity efetuarLogin(@RequestBody @Valid AutenticacaoDTO autenticacaoDTO) {
        var token = new UsernamePasswordAuthenticationToken(autenticacaoDTO.email(), autenticacaoDTO.senha());
        var autentication = manager.authenticate(token);

        if (autentication.getPrincipal() instanceof org.springframework.security.core.userdetails.User) {
            User user = (org.springframework.security.core.userdetails.User) autentication.getPrincipal();
            Usuario usuario = new Usuario(user);
            var tokenJwt = tokenService.gerarToken(usuario);
            return ResponseEntity.ok(new TokenDadosJWT(tokenJwt));
        } else {
            return ResponseEntity.badRequest().build();
        }
    }
}