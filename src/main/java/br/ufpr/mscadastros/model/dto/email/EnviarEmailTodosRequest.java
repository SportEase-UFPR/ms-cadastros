package br.ufpr.mscadastros.model.dto.email;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EnviarEmailTodosRequest {
    @NotBlank(message = "assunto é obrigatório")
    private String assunto;
    @NotBlank(message = "corpo é obrigatório")
    private String corpo;
}
