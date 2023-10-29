package br.ufpr.mscadastros.model.entity;

import br.ufpr.mscadastros.model.dto.cliente.ClienteCriacaoRequest;
import jakarta.persistence.*;
import lombok.*;

@Entity(name = "tb_clientes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nome;

    @Column(unique = true, nullable = false, length = 100)
    private String email;

    @Column(unique = true, nullable = false, length = 11)
    private String cpf;

    @Column(nullable = false, length = 11, name = "aluno_ufpr")
    private Boolean alunoUFPR;

    @Column(length = 11)
    private String grr;

    @Column(unique = true, nullable = false, length = 11)
    private Long idUsuario;

    public Cliente(ClienteCriacaoRequest cliente) {
        this.nome = cliente.getNome();
        this.email = cliente.getEmail();
        this.cpf = cliente.getCpf();
        this.alunoUFPR = cliente.getAlunoUFPR();
        this.grr = cliente.getGrr();
        this.idUsuario = cliente.getIdUsuario();
    }
}
