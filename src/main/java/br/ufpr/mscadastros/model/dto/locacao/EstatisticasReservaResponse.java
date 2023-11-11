package br.ufpr.mscadastros.model.dto.locacao;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.LinkedHashMap;

@AllArgsConstructor
@Getter
@Builder
public class EstatisticasReservaResponse {
    private Long idCliente;
    private Integer totalReservas;
    private Integer totalReservasSolicitadas;
    private Integer totalReservasCanceladas;
    private Integer totalReservasAprovadas;
    private Integer totalReservasNegadas;
    private Integer totalReservasFinalizadas;
    private Integer totalReservasEncerradas;


    public EstatisticasReservaResponse(Object response) {
        if (response instanceof LinkedHashMap<?, ?> hm) {
            this.idCliente = Long.valueOf((Integer) hm.get("idCliente"));
            this.totalReservas = (Integer) hm.get("totalReservas");
            this.totalReservasSolicitadas = (Integer) hm.get("totalReservasSolicitadas");
            this.totalReservasCanceladas = (Integer) hm.get("totalReservasCanceladas");
            this.totalReservasAprovadas = (Integer) hm.get("totalReservasAprovadas");
            this.totalReservasNegadas = (Integer) hm.get("totalReservasNegadas");
            this.totalReservasFinalizadas = (Integer) hm.get("totalReservasFinalizadas");
            this.totalReservasEncerradas = (Integer) hm.get("totalReservasEncerradas");
        }
    }
}
