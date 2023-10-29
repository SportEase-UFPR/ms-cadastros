package br.ufpr.mscadastros.model.dto.cliente;


import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.br.CPF;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class ClienteCriacaoRequest {
    @NotBlank(message = "O nome é obrigatório")
    private String nome;

    @Email(message = "O email deve ser válido")
    private String email;

    @CPF(message = "O cpf deve ser válido")
    @NotBlank(message = "O cpf é obrigatório")
    private String cpf;

    @NotNull(message = "alunoUFPR é obrigatório")
    private Boolean alunoUFPR;

    @Pattern(regexp = "GRR\\d{8}", message = "GRR deve seguir o padrão GRR00000000")
    @Nullable
    private String grr;

    @NotNull(message = "idUsuario é obrigatório")
    private Long idUsuario;

}
