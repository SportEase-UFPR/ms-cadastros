package br.ufpr.mscadastros.model.dto.adm;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@Builder
public class AdmAlterarNomeResponse {
    private String novoNome;
}
