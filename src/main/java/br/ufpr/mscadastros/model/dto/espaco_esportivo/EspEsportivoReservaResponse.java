package br.ufpr.mscadastros.model.dto.espaco_esportivo;

import br.ufpr.mscadastros.model.entity.EspacoEsportivo;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class EspEsportivoReservaResponse {
    private Long id;
    private String nome;


    public EspEsportivoReservaResponse(EspacoEsportivo ee) {
        this.id = ee.getId();
        this.nome = ee.getNome();
    }
}
