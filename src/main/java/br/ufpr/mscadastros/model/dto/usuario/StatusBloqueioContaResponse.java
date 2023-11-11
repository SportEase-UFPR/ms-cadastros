package br.ufpr.mscadastros.model.dto.usuario;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.LinkedHashMap;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class StatusBloqueioContaResponse {
    private Long idUsuario;
    private Boolean bloqueada;

    public StatusBloqueioContaResponse(Object response) {
        if (response instanceof LinkedHashMap<?, ?> hm) {
            this.idUsuario = Long.valueOf((Integer) hm.get("idUsuario"));
            this.bloqueada = (Boolean) hm.get("bloqueada");
        }
    }
}
