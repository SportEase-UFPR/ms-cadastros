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

    @Value("${api.security.token.user.secret}")
    private String userSecret;

    @Value("${api.security.token.apigateway.secret}")
    private String apiGatewaySecret;

    @Value("${api.security.token.mscadastro.secret}")
    private String msCadastroSecret;

    @Value("${api.gateway.issuer}")
    private String apiGatewayIssuer;

    @Value("${api.user.issuer}")
    private String apiUserIssuer;

    @Value("${api.mscadastro.issuer}")
    private String msCadastroIssuer;

    @Value("${api.mslocacoes.issuer}")
    private String msLocacoesIssuer;

    @Value("${api.security.token.mslocacoes.secret}")
    private String msLocacoesSecret;

    public String gerarTokenComEmailSemExpiracao(String idUsuario, NivelAcesso nivelAcesso, String email) {
        var algoritmo = Algorithm.HMAC256(userSecret);
        return JWT.create()
                .withIssuer(apiUserIssuer)
                .withSubject(idUsuario)
                .withClaim("userProfile", nivelAcesso.toString())
                .withClaim("email", email)
                .sign(algoritmo);
    }

    public String gerarTokenComEmailSemExpiracao(String idUsuario, String email) {
        var algoritmo = Algorithm.HMAC256(userSecret);
        return JWT.create()
                .withIssuer(apiUserIssuer)
                .withSubject(idUsuario)
                .withClaim("email", email)
                .sign(algoritmo);
    }

    public void validarToken(String tokenJWT) {
        var tokenFormatado = removerPrefixoToken(tokenJWT);
        try {
            var algoritmo = Algorithm.HMAC256(apiGatewaySecret);
            JWT.require(algoritmo)
                    .withIssuer(apiGatewayIssuer)
                    .build()
                    .verify(tokenFormatado);
        } catch (JWTVerificationException ex) {
            log.error(ex.getMessage());
            throw new TokenInvalidoException("Token JWT inválido ou expirado");
        }
    }

    public String gerarTokenMsCadastro() {
        var algoritmo = Algorithm.HMAC256(msCadastroSecret);
        return JWT.create()
                .withIssuer(msCadastroIssuer)
                .withSubject(msCadastroIssuer)
                .withExpiresAt(dataExpiracao(20)) //data da expiração
                .sign(algoritmo); //assinatura

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

    public void validarTokenApiMsLocacoes(String tokenApi) {
        var tokenFormatado = removerPrefixoToken(tokenApi);
        try {
            var algoritmo = Algorithm.HMAC256(msLocacoesSecret);
            JWT.require(algoritmo)
                    .withIssuer(msLocacoesIssuer)
                    .build()
                    .verify(tokenFormatado);
        } catch (JWTVerificationException ex) {
            log.error(ex.getMessage());
            throw new TokenInvalidoException("Token JWT inválido ou expirado");
        }
    }
}
