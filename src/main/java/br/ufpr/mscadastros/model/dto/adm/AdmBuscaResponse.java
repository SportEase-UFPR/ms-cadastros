package br.ufpr.mscadastros.model.dto.adm;

import br.ufpr.mscadastros.model.entity.Administrador;
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
public class AdmBuscaResponse {
    private Long id;
    private String nome;
    private String cpf;
    private String email;
    private Long idUsuario;

    public AdmBuscaResponse(Administrador adm) {
        this.id = adm.getId();
        this.nome = adm.getNome();
        this.cpf = adm.getCpf();
        this.email = adm.getEmail();
        this.idUsuario = adm.getIdUsuario();
    }
}
