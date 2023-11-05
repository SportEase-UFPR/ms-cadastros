package br.ufpr.mscadastros.model.dto.espaco_esportivo;

import br.ufpr.mscadastros.model.dto.esporte.EsporteResponse;
import br.ufpr.mscadastros.model.entity.EspacoEsportivo;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class EspEsportivoBuscaResponse {
    private Long id;
    private String nome;
    private String descricao;
    private String localidade;
    private String piso;
    private String dimensoes;
    private Short capacidade;
    private Boolean disponivel;
    private LocalTime horaAbertura;
    private LocalTime horaFechamento;
    private LocalTime periodoLocacao;
    private Integer maxLocacaoDia;
    private List<EsporteResponse> listaEsportes = new ArrayList<>();
    private Double mediaAvaliacoes;
    private String imagemBase64;

    public EspEsportivoBuscaResponse(EspacoEsportivo ee) {
        this.id = ee.getId();
        this.nome = ee.getNome();
        this.descricao = ee.getDescricao();
        this.localidade = ee.getLocalidade();
        this.piso = ee.getPiso();
        this.dimensoes = ee.getDimensoes();
        this.capacidade = ee.getCapacidade();
        this.disponivel = ee.getDisponivel();
        this.horaAbertura = ee.getHoraAbertura();
        this.horaFechamento = ee.getHoraFechamento();
        this.periodoLocacao = ee.getPeriodoLocacao();
        this.maxLocacaoDia = ee.getMaxLocacaoDia();
        this.imagemBase64 = Base64.getEncoder().encodeToString(ee.getImagemBase64());
        ee.getListaEsportes().forEach(esporte -> listaEsportes.add(new EsporteResponse(esporte)));

        if (ee.getMediaAvaliacao() != null) {
            //arredonda pra 1 casa decimal
            var mediaAvaliacao = ee.getMediaAvaliacao();
            mediaAvaliacoes = Math.round(mediaAvaliacao * 10.0) / 10.0;
        }
    }
}
