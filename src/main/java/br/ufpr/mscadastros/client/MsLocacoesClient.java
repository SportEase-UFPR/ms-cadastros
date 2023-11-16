package br.ufpr.mscadastros.client;

import br.ufpr.mscadastros.model.dto.locacao.EstatisticasReservaResponse;
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
public class MsLocacoesClient {

    @Value("${url.ms.locacoes}")
    private String urlMsLocacoes;

    private final RestTemplate restTemplate;
    private final TokenService tokenService;

    public MsLocacoesClient(RestTemplate restTemplate, TokenService tokenService) {
        this.restTemplate = restTemplate;
        this.tokenService = tokenService;
    }

    private HttpHeaders gerarCabecalho() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("AuthorizationApi", tokenService.gerarTokenMs());
        return headers;
    }

    public List<EstatisticasReservaResponse> buscarEstatisticasReserva() {
        String url = urlMsLocacoes + "/locacoes/estatisticas-reserva";
        HttpHeaders headers = gerarCabecalho();
        var response = restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(headers), new ParameterizedTypeReference<List<Object>>() {}).getBody();

        var listaEstatisticas = new ArrayList<EstatisticasReservaResponse>();
        assert response != null;
        response.forEach(obj -> listaEstatisticas.add(new EstatisticasReservaResponse(obj)));

        return listaEstatisticas;
    }

    public void encerrarReservasFuturas(Long idEspacoEsportivo) {
        String url = urlMsLocacoes + "/locacoes/encerrar-reservas-futuras/" + idEspacoEsportivo;
        HttpHeaders headers = gerarCabecalho();
        restTemplate.exchange(url, HttpMethod.PUT, new HttpEntity<>(headers), Object.class);
    }
}
