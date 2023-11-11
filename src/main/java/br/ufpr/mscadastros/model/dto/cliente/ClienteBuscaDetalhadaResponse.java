package br.ufpr.mscadastros.model.dto.cliente;

import br.ufpr.mscadastros.model.dto.locacao.EstatisticasReservaResponse;
import br.ufpr.mscadastros.model.dto.usuario.StatusBloqueioContaResponse;
import br.ufpr.mscadastros.model.entity.Cliente;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ClienteBuscaDetalhadaResponse {
    private Long id;
    private Long usuarioId;
    private Boolean bloqueada;
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
        this.usuarioId = cliente.getIdUsuario();
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

    public ClienteBuscaDetalhadaResponse(Cliente cliente,
                                         List<EstatisticasReservaResponse> estatisticasReservas,
                                         List<StatusBloqueioContaResponse> statusBloqueioContas) {
        this.id = cliente.getId();
        this.usuarioId = cliente.getIdUsuario();
        this.nome = cliente.getNome();
        this.email = cliente.getEmail();
        this.cpf = cliente.getCpf();
        this.grr = cliente.getGrr();
        this.alunoUFPR = cliente.getAlunoUFPR();

        var estatistica = estatisticasReservas.stream()
                .filter(e -> e.getIdCliente().equals(cliente.getId()))
                .findFirst().orElse(null);
        var status = statusBloqueioContas.stream()
                .filter(s -> s.getIdUsuario().equals(cliente.getIdUsuario()))
                .findFirst().orElse(null);

        assert status != null;
        this.bloqueada = status.getBloqueada();

        if(estatistica != null) {
            this.totalReservas = estatistica.getTotalReservas();
            this.totalReservasSolicitadas = estatistica.getTotalReservasSolicitadas();
            this.totalReservasCanceladas = estatistica.getTotalReservasCanceladas();
            this.totalReservasAprovadas = estatistica.getTotalReservasAprovadas();
            this.totalReservasNegadas = estatistica.getTotalReservasNegadas();
            this.totalReservasFinalizadas = estatistica.getTotalReservasFinalizadas();
            this.totalReservasEncerradas = estatistica.getTotalReservasEncerradas();
        } else {
            this.totalReservas = 0;
            this.totalReservasSolicitadas = 0;
            this.totalReservasCanceladas = 0;
            this.totalReservasAprovadas = 0;
            this.totalReservasNegadas = 0;
            this.totalReservasFinalizadas = 0;
            this.totalReservasEncerradas = 0;
        }

    }
}
