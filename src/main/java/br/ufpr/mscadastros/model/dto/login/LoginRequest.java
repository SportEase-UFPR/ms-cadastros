package br.ufpr.mscadastros.model.dto.login;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class LoginRequest {
    @NotBlank(message = "O login é obrigatório")
    String login;

    @NotBlank(message = "A senha é obrigatória")
    String senha;
}
