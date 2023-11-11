package br.ufpr.mscadastros.model.dto.cliente;

import br.ufpr.mscadastros.model.dto.locacao.EstatisticasReservaResponse;
import br.ufpr.mscadastros.model.entity.Cliente;
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
public class ClienteBuscaDetalhadaResponse {
    private Long id;
    private String nome;
    private String email;
    private String cpf;
    private String grr;
    private Boolean alunoUFPR;
    private Integer totalReservas;
    private Integer totalReservasSolicitadas;
    private Integer totalReservasCanceladas;
    private Integer totalReservasAprovadas;
    private Integer totalReservasNegadas;
    private Integer totalReservasFinalizadas;
    private Integer totalReservasEncerradas;


    public ClienteBuscaDetalhadaResponse(Cliente cliente, EstatisticasReservaResponse estatisticasReserva) {
        this.id = cliente.getId();
        this.nome = cliente.getNome();
        this.email = cliente.getEmail();
        this.cpf = cliente.getCpf();
        this.grr = cliente.getGrr();
        this.alunoUFPR = cliente.getAlunoUFPR();
        this.totalReservas = estatisticasReserva.getTotalReservas();
        this.totalReservasSolicitadas = estatisticasReserva.getTotalReservasSolicitadas();
        this.totalReservasCanceladas = estatisticasReserva.getTotalReservasCanceladas();
        this.totalReservasAprovadas = estatisticasReserva.getTotalReservasAprovadas();
        this.totalReservasNegadas = estatisticasReserva.getTotalReservasNegadas();
        this.totalReservasFinalizadas = estatisticasReserva.getTotalReservasFinalizadas();
        this.totalReservasEncerradas = estatisticasReserva.getTotalReservasEncerradas();
    }
}
