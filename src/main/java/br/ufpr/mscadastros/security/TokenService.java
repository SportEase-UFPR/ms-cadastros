package br.ufpr.mscadastros.security;

import br.ufpr.mscadastros.exceptions.TokenInvalidoException;
import br.ufpr.mscadastros.model.enums.NivelAcesso;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Slf4j
@Service
public class TokenService {

    @Value("${user.secret}")
    private String userSecret;

    @Value("${api.gateway.secret}")
    private String apiGatewaySecret;

    @Value("${ms.secret}")
    private String msSecret;

    @Value("${user.issuer}")
    private String userIssuer;

    @Value("${api.gateway.issuer}")
    private String apiGatewayIssuer;

    @Value("${ms.issuer}")
    private String msIssuer;

    public String gerarTokenComEmailSemExpiracao(String idUsuario, NivelAcesso nivelAcesso, String email) {
        var algoritmo = Algorithm.HMAC256(userSecret);
        return JWT.create()
                .withIssuer(userIssuer)
                .withSubject(idUsuario)
                .withClaim("userProfile", nivelAcesso.toString())
                .withClaim("email", email)
                .sign(algoritmo);
    }

    public String gerarTokenComEmailSemExpiracao(String idUsuario, String email) {
        var algoritmo = Algorithm.HMAC256(userSecret);
        return JWT.create()
                .withIssuer(userIssuer)
                .withSubject(idUsuario)
                .withClaim("email", email)
                .sign(algoritmo);
    }

    public String gerarTokenMs() {
        var algoritmo = Algorithm.HMAC256(msSecret);
        return JWT.create()
                .withIssuer(msIssuer)
                .withSubject(msIssuer)
                .withExpiresAt(dataExpiracao(20)) //data da expiração
                .sign(algoritmo); //assinatura
    }

    public void validarTokenApiGateway(String tokenJWT) {
        try {
            var algoritmo = Algorithm.HMAC256(apiGatewaySecret);
            JWT.require(algoritmo)
                    .withIssuer(apiGatewayIssuer)
                    .build()
                    .verify(tokenJWT);
        } catch (JWTVerificationException ex) {
            log.error(ex.getMessage());
            throw new TokenInvalidoException("Token JWT inválido ou expirado");
        }
    }

    public void validarTokenMs(String tokenApi) {
        var tokenFormatado = removerPrefixoToken(tokenApi);
        try {
            var algoritmo = Algorithm.HMAC256(msSecret);
            JWT.require(algoritmo)
                    .withIssuer(msIssuer)
                    .build()
                    .verify(tokenFormatado);
        } catch (JWTVerificationException ex) {
            log.error(ex.getMessage());
            throw new TokenInvalidoException("Token JWT inválido ou expirado");
        }
    }

    public String removerPrefixoToken(String token) {
        return token.replace("Bearer ", "");
    }

    //recupera um issuer do token
    public String getIssuer(String tokenJWT, String issuer) {
        return JWT.decode(tokenJWT).getClaim(issuer).asString();
    }

    //recupera o subject do token
    public String getSubject(String tokenJWT) {
        return JWT.decode(tokenJWT).getSubject();
    }

    private Instant dataExpiracao(Integer minutes) {
        return LocalDateTime.now().plusMinutes(minutes).toInstant(ZoneOffset.of("-03:00"));
    }

}
