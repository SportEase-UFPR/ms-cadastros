package br.ufpr.mscadastros.model.entity;

import br.ufpr.mscadastros.model.dto.adm.AdmCriacaoRequest;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "tb_administradores")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Administrador {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nome;

    @Column(unique = true, nullable = false, length = 100)
    private String email;

    @Column(unique = true, nullable = false, length = 11)
    private String cpf;

    @Column(unique = true, nullable = false, length = 11)
    private Long idUsuario;

    public Administrador(AdmCriacaoRequest adm) {
        this.nome = adm.getNome();
        this.email = adm.getEmail();
        this.cpf = adm.getCpf();
        this.idUsuario = adm.getIdUsuario();
    }
}
