package br.ufpr.mscadastros.model.dto.cliente;

import br.ufpr.mscadastros.model.entity.Cliente;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NomeClienteResponse {
    private Long idCliente;
    private String nomeCliente;

    public NomeClienteResponse(Cliente cliente) {
            this.idCliente = cliente.getId();
            this.nomeCliente = cliente.getNome();
    }
}
