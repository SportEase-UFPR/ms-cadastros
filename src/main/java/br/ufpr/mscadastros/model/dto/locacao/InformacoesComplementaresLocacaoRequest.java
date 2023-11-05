package br.ufpr.mscadastros.model.dto.locacao;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Getter
@Builder
public class InformacoesComplementaresLocacaoRequest {
    private Long idLocacao;
    private Long idEspacoEsportivo;
    private Long idCliente;
}
