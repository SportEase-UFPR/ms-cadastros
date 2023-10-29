package br.ufpr.mscadastros.model.dto.esporte;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class EsporteExclusaoResponse {
    private String mensagem;
}
