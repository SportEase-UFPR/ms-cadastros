package br.ufpr.mscadastros.model.dto.espaco_esportivo;

import br.ufpr.mscadastros.model.entity.EspacoEsportivo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EspacoEsportivoSimplificado {
    private Long id;
    private String nome;
    private String localidade;

    public EspacoEsportivoSimplificado(EspacoEsportivo ee) {
        this.id = ee.getId();
        this.nome = ee.getNome();
        this.localidade = ee.getLocalidade();
    }
}
