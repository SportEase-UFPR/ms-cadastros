package br.ufpr.mscadastros.model.dto.esporte;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EsporteRequest {
    @NotBlank(message = "O nome é obrigatório")
    private String nome;
}
