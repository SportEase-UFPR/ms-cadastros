package br.ufpr.mscadastros.model.dto.espaco_esportivo;


import br.ufpr.mscadastros.model.dto.esporte.EsporteResponse;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class EspEsportivoCriacaoRequest {
    @NotBlank(message = "O nome é obrigatório")
    private String nome;
    private String descricao;
    private String localidade;
    private String piso;
    private String dimensoes;
    private Short capacidade;

    @NotNull(message = "O campo disponivel é obrigatório")
    private Boolean disponivel;

    @NotNull(message = "O campo horaAbertura é obrigatório")
    private LocalTime horaAbertura;

    @NotNull(message = "O campo horaFechamento é obrigatório")
    private LocalTime horaFechamento;

    @NotNull(message = "O campo periodoLocacao é obrigatório")
    private LocalTime periodoLocacao;

    @NotNull(message = "O campo maxLocacaoDia é obrigatório")
    @Positive(message = "maxLocacaoDia deve ser um número positivo")
    private Integer maxLocacaoDia;

    @NotNull(message = "O campo listaEsportes é obrigatório")
    private List<EsporteResponse> listaEsportes = new ArrayList<>();

    private String imagemBase64;


}
