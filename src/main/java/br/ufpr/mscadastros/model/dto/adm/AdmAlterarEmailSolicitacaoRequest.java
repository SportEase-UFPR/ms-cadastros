package br.ufpr.mscadastros.model.dto.adm;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class AdmAlterarEmailSolicitacaoRequest {
    @NotBlank(message = "O email é obrigatório")
    @Email(message = "O email deve ser válido")
    private String novoEmail;
}
