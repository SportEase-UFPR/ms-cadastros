package br.ufpr.mscadastros.model.dto.adm;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class AdmAlterarNomeRequest {
    @NotBlank(message = "O nome é obrigatório")
    private String novoNome;
}
