package br.edu.josifHubapi.security;

import br.edu.josifHubapi.domain.Usuario;
import br.edu.josifHubapi.enums.TipoToken;
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

    Integer VALIDADE_TOKEN_REC_SENHA_HORAS = 1;
    Integer VALIDADE_TOKEN_CONFIRMACAO_EMAIL_HORAS = 1;


    //TODO adicionar secret no aplicattion.properties com o @Value + variavel de ambiente
    @Value("${api.security.token.secret}")
    private String secret;

    public String gerarToken(Usuario usuario, TipoToken tipoToken) {
        if (tipoToken.equals(TipoToken.CONFIRMACAO_CADASTRO)) {
            try {
                var algoritmo = Algorithm.HMAC256(secret);
                return JWT.create()
                        .withIssuer("josifHub-api")
                        .withSubject(usuario.getEmail())
                        .withClaim("hashid", usuario.getHashid().toString())
                        .withClaim("tipo", TipoToken.CONFIRMACAO_CADASTRO.toString())
                        .withExpiresAt(dataExpiracao(VALIDADE_TOKEN_CONFIRMACAO_EMAIL_HORAS))
                        .sign(algoritmo);
            } catch (JWTCreationException exception) {
                throw new RuntimeException("Erro ao gerar token jwt", exception);

            }
        }
        if (tipoToken.equals(TipoToken.RECUPERACAO_SENHA)) {
            try {
                var algoritmo = Algorithm.HMAC256(secret);
                return JWT.create()
                        .withIssuer("josifHub-api")
                        .withSubject(usuario.getEmail())
                        .withClaim("hashid", usuario.getHashid().toString())
                        .withClaim("tipo", TipoToken.RECUPERACAO_SENHA.toString())
                        .withExpiresAt(dataExpiracao(VALIDADE_TOKEN_REC_SENHA_HORAS))
                        .sign(algoritmo);
            } catch (JWTCreationException exception) {
                throw new RuntimeException("Erro ao gerar token jwt", exception);
            }
        }
        if (tipoToken.equals(TipoToken.LOGIN)) {
            try {
                var algoritmo = Algorithm.HMAC256(secret);
                return JWT.create()
                        .withIssuer("josifHub-api")
                        .withSubject(usuario.getEmail())
                        .withClaim("hashid", usuario.getHashid().toString())
                        .withClaim("tipo", TipoToken.LOGIN.toString())
                        .withExpiresAt(dataExpiracao(VALIDADE_TOKEN_LOGIN_HORAS))
                        .sign(algoritmo);
            } catch (JWTCreationException exception) {
                throw new RuntimeException("Erro ao gerar token jwt", exception);
            }
        }
        throw new RuntimeException("Erro ao gerar token jwt");
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

    public boolean isTokenValido(String token, TipoToken tipoToken) {
        if (tipoToken.equals(TipoToken.CONFIRMACAO_CADASTRO)) {
            try {
                var algoritmo = Algorithm.HMAC256(secret);
                JWT.require(algoritmo)
                        .withIssuer("josifHub-api")
                        .withClaim("tipo", TipoToken.CONFIRMACAO_CADASTRO.toString())
                        .withClaim("hashid", getSubject(token))
                        .build()
                        .verify(token);
                return true;
            } catch (JWTVerificationException exception) {
                System.err.println(exception.getMessage());
                return false;
            }

        }
        return false;
    }

    public String getEmail(String token) {
        try {
            var algoritmo = Algorithm.HMAC256(secret);
            return JWT.require(algoritmo)
                    .withIssuer("josifHub-api")
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTVerificationException exception) {
            throw new RuntimeException("Token JWT inválido ou expirado!");
        }
    }

    public String getHashid(String token) {
        try {
            var algoritmo = Algorithm.HMAC256(secret);
            return JWT.require(algoritmo)
                    .withIssuer("josifHub-api")
                    .build()
                    .verify(token)
                    .getClaim("hashid").asString();
        } catch (JWTVerificationException exception) {
            throw new RuntimeException("Token JWT inválido ou expirado!");
        }
    }


}