package br.ufpr.mscadastros.model.dto.adm;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.br.CPF;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class AdmCriacaoRequest {
    @NotBlank(message = "O nome é obrigatório")
    private String nome;

    @Email(message = "O email deve ser válido")
    private String email;

    @CPF(message = "O cpf deve ser válido")
    @NotBlank(message = "O cpf é obrigatório")
    private String cpf;

    @NotNull(message = "idUsuario é obrigatório")
    private Long idUsuario;
}
