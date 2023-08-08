package br.edu.josifHubapi.security;

import br.edu.josifHubapi.domain.Usuario;
import br.edu.josifHubapi.dto.UsuarioCadastroDTO;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    Integer VALIDADE_TOKEN_LOGIN_HORAS = 48;
    Integer VALIDADE_TOKEN_CONFIRMACAO_EMAIL_HORAS = 1;

    //TODO adicionar secret no aplicattion.properties com o @Value + variavel de ambiente
    @Value("${api.security.token.secret}")
    private String secret;

    public String gerarToken(Usuario usuario) {
        try {
            var algoritmo = Algorithm.HMAC256(secret);
            return JWT.create()
                    .withIssuer("josifHub-api")
                    .withSubject(usuario.getEmail())
                    .withExpiresAt(dataExpiracao(VALIDADE_TOKEN_LOGIN_HORAS))
                    .sign(algoritmo);
        } catch (JWTCreationException exception){
            throw new RuntimeException("Erro ao gerrar token jwt", exception);
        }
    }

    public String gerarTokenConfirmacaoCadastro(UsuarioCadastroDTO usuarioCadastroDTO) {
        try {
            var algoritmo = Algorithm.HMAC256(secret);
            return JWT.create()
                    .withIssuer("josifHub-api")
                    .withSubject(usuarioCadastroDTO.email())
                    .withExpiresAt(dataExpiracao(VALIDADE_TOKEN_CONFIRMACAO_EMAIL_HORAS))
                    .sign(algoritmo);
        } catch (JWTCreationException exception){
            throw new RuntimeException("Erro ao gerrar token jwt", exception);
        }
    }

    private Instant dataExpiracao(Integer horas) {
        return LocalDateTime.now().plusHours(horas).toInstant(ZoneOffset.of("-03:00"));
    }

    public String getSubject(String tokenJWT) {
        try {
            var algoritmo = Algorithm.HMAC256(secret);
            return JWT.require(algoritmo)
                    .withIssuer("josifHub-api")
                    .build()
                    .verify(tokenJWT)
                    .getSubject();
        } catch (JWTVerificationException exception) {
            throw new RuntimeException("Token JWT inválido ou expirado!");
        }
    }
}