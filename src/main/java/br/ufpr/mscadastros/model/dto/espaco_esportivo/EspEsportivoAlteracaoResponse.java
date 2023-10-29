package br.ufpr.mscadastros.model.dto.espaco_esportivo;

import br.ufpr.mscadastros.model.dto.esporte.EsporteResponse;
import br.ufpr.mscadastros.model.entity.EspacoEsportivo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class EspEsportivoAlteracaoResponse {
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
    private String imagemBase64;

    public EspEsportivoAlteracaoResponse(EspacoEsportivo ee) {
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
        ee.getListaEsportes().forEach(esporte -> this.listaEsportes.add(new EsporteResponse(esporte)));
    }
}
