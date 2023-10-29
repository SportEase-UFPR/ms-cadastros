package br.ufpr.mscadastros.model.dto.cliente;

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
public class ClienteBuscaResponse {
    private Long id;
    private String nome;
    private String email;
    private String cpf;
    private String grr;

    public ClienteBuscaResponse(Cliente cliente) {
        this.id = cliente.getId();
        this.nome = cliente.getNome();
        this.email = cliente.getEmail();
        this.cpf = cliente.getCpf();
        this.grr = cliente.getGrr();
    }
}
