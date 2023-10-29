package br.ufpr.mscadastros.model.dto.usuario;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Builder
public class RecuperarSenhaResponse {
    private String email;
    private String mensagem;
}
