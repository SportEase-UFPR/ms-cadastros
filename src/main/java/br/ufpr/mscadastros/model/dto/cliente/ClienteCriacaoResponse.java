package br.ufpr.mscadastros.model.dto.cliente;

import br.ufpr.mscadastros.model.entity.Cliente;
import br.ufpr.mscadastros.model.enums.NivelAcesso;
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
public class ClienteCriacaoResponse {
    private Long id;
    private String nome;
    private String email;
    private String cpf;
    private Boolean alunoUFPR;
    private String grr;
    private NivelAcesso nivelAcesso;

    public ClienteCriacaoResponse(Cliente cliente) {
        this.id = cliente.getId();
        this.nome = cliente.getNome();
        this.email = cliente.getEmail();
        this.cpf = cliente.getCpf();
        this.alunoUFPR = cliente.getAlunoUFPR();
        this.grr = cliente.getGrr();
        this.nivelAcesso = NivelAcesso.ROLE_CLIENTE;
    }
}
