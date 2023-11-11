package br.ufpr.mscadastros.model.dto.espaco_esportivo;

import br.ufpr.mscadastros.model.dto.esporte.EsporteResponse;
import br.ufpr.mscadastros.model.entity.EspacoEsportivo;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class EspEsportivoReservaResponse {
    private Long id;
    private String nome;
    private List<EsporteResponse> listaEsportes = new ArrayList<>();
    private List<Integer> diasFuncionamento;
    private Short capacidadeMin;
    private Short capacidadeMax;



    public EspEsportivoReservaResponse(EspacoEsportivo ee) {
        this.id = ee.getId();
        this.nome = ee.getNome();
        this.capacidadeMin = ee.getCapacidadeMin();
        this.capacidadeMax = ee.getCapacidadeMax();
        ee.getListaEsportes().forEach(esporte -> listaEsportes.add(new EsporteResponse(esporte)));

        this.diasFuncionamento = Arrays.stream(ee.getDiasFuncionamento().split(","))
                .map(Integer::parseInt)
                .toList();
    }
}
