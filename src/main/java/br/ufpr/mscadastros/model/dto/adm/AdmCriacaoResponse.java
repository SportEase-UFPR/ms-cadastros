package br.ufpr.mscadastros.model.dto.adm;

import br.ufpr.mscadastros.model.entity.Administrador;
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
public class AdmCriacaoResponse {
    private Long id;
    private String nome;
    private String email;
    private String cpf;
    private NivelAcesso nivelAcesso;

    public AdmCriacaoResponse(Administrador adm) {
        this.id = adm.getId();
        this.nome = adm.getNome();
        this.email = adm.getEmail();
        this.cpf = adm.getCpf();
        this.nivelAcesso = NivelAcesso.ROLE_ADM;
    }
}
