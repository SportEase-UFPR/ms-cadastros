package br.ufpr.mscadastros.model.dto.cliente;

import br.ufpr.mscadastros.model.entity.Cliente;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class BuscarEmailsClientesResponse {
    private Long idCliente;
    private String nomeCliente;
    private String emailCliente;

    public BuscarEmailsClientesResponse(Cliente cliente) {
        this.idCliente = cliente.getId();
        this.nomeCliente = cliente.getNome();
        this.emailCliente = cliente.getEmail();
    }
}
