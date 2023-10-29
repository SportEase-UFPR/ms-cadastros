package br.ufpr.mscadastros.model.dto.email;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Getter
@Setter @Builder
@AllArgsConstructor
@NoArgsConstructor
public class EnviarEmailRequest {
    @NotNull(message = "listaEmails é obrigatória")
    private List<String> listaEmails;
    @NotBlank(message = "assunto é obrigatório")
    private String assunto;
    @NotBlank(message = "corpo é obrigatório")
    private String corpo;
}
