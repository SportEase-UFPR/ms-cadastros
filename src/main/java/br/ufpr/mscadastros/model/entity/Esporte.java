package br.ufpr.mscadastros.model.entity;

import br.ufpr.mscadastros.model.dto.esporte.EsporteResponse;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "tb_esporte")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Esporte {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nome;

    public Esporte(EsporteResponse esporte) {
        this.nome = esporte.getNome();
    }
}
