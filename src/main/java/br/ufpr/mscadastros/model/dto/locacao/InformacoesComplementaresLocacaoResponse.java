package br.ufpr.mscadastros.model.dto.locacao;

import br.ufpr.mscadastros.model.entity.Cliente;
import br.ufpr.mscadastros.model.entity.EspacoEsportivo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;


@AllArgsConstructor
@Getter
@Builder
public class InformacoesComplementaresLocacaoResponse {
    private Long idLocacao;

    private Long idEspacoEsportivo;
    private String nomeEspacoEsportivo;
    private String localidadeEspacoEsportivo;

    private Long idCliente;
    private String nomeCliente;
    private String cpfCliente;
    private Boolean alunoUFPR;
    private String emailCliente;
    private String grr;

    public InformacoesComplementaresLocacaoResponse(EspacoEsportivo espEsport, Cliente cliente, Long idLocacao) {
        this.idLocacao = idLocacao;
        this.idEspacoEsportivo = espEsport.getId();
        this.nomeEspacoEsportivo = espEsport.getNome();
        this.localidadeEspacoEsportivo = espEsport.getLocalidade();
        this.idCliente = cliente.getId();
        this.nomeCliente = cliente.getNome();
        this.cpfCliente = cliente.getCpf();
        this.alunoUFPR = cliente.getAlunoUFPR();
        this.emailCliente = cliente.getEmail();
        this.grr = cliente.getGrr();
    }
}
