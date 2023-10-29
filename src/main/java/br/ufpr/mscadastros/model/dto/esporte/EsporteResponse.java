package br.ufpr.mscadastros.model.dto.esporte;

import br.ufpr.mscadastros.model.entity.Esporte;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class EsporteResponse {
    private Long id;
    private String nome;

    public EsporteResponse(Esporte esporte) {
        this.id = esporte.getId();
        this.nome = esporte.getNome();
    }

}
