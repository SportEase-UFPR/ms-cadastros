package br.ufpr.mscadastros.model.dto.usuario;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class AlterarSenhaRequest {
    @NotBlank(message = "O token é obrigatório")
    private String tokenUsuario;

    @NotBlank(message = "A nova senha é obrigatória")
    private String novaSenha;
}
