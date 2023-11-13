package br.ufpr.mscadastros.client;

import br.ufpr.mscadastros.model.dto.usuario.StatusBloqueioContaResponse;
import br.ufpr.mscadastros.security.TokenService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class ApiGatewayClient {

    @Value("${url.api.gateway}")
    private String urlApiGateway;

    private final RestTemplate restTemplate;
    private final TokenService tokenService;

    public ApiGatewayClient(RestTemplate restTemplate, TokenService tokenService) {
        this.restTemplate = restTemplate;
        this.tokenService = tokenService;
    }

    private HttpHeaders gerarCabecalho() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("AuthorizationApi", tokenService.gerarTokenMs());
        return headers;
    }

    public void excluirUsuarioPorId(Long idUsuario) {
        String url = urlApiGateway + "/usuarios/" + idUsuario;
        HttpHeaders headers = gerarCabecalho();
        restTemplate.exchange(url, HttpMethod.DELETE, new HttpEntity<>(headers), Object.class);
    }

    public List<StatusBloqueioContaResponse> buscarStatusBloqueioContas() {
        String url = urlApiGateway + "/usuarios/buscar-status-bloqueio-contas";
        HttpHeaders headers = gerarCabecalho();
        var response = restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(headers), new ParameterizedTypeReference<List<Object>>() {}).getBody();

        var listaStatus = new ArrayList<StatusBloqueioContaResponse>();
        assert response != null;
        response.forEach(obj -> listaStatus.add(new StatusBloqueioContaResponse(obj)));

        return listaStatus;
    }
}
