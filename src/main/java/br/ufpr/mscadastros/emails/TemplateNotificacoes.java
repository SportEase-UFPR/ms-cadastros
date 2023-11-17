package br.ufpr.mscadastros.emails;



import br.ufpr.mscadastros.model.dto.notificacao.CriacaoNotificacaoRequest;
import br.ufpr.mscadastros.model.entity.EspacoEsportivo;

public class TemplateNotificacoes {
    private TemplateNotificacoes(){/*vazio..*/}

    public static CriacaoNotificacaoRequest notificacaoEEFicouIndisponivel(EspacoEsportivo espacoEsportivo) {
        var titulo = "O ESPAÇO ESPORTIVO '" + espacoEsportivo.getNome().toUpperCase() + "' FOI DESATIVADO";

        var conteudo = """
                    O espaço esportivo '%s' foi desativado. Portanto não é mais possível solicitar reservas para ele.
                """
                .formatted(espacoEsportivo.getNome());

        return CriacaoNotificacaoRequest.builder()
                .idCliente(-1L)
                .titulo(titulo)
                .conteudo(conteudo)
                .build();
    }

    public static CriacaoNotificacaoRequest notificacaoEEFicouDisponivel(EspacoEsportivo espacoEsportivo) {
        var titulo = "O ESPAÇO ESPORTIVO '" + espacoEsportivo.getNome().toUpperCase() + "' ESTÁ DISPONÍVEL";

        var conteudo = """
                    O espaço esportivo '%s' foi ativado. Agora você já pode solicitar reservas para ele.
                """
                .formatted(espacoEsportivo.getNome());

        return CriacaoNotificacaoRequest.builder()
                .idCliente(-1L)
                .titulo(titulo)
                .conteudo(conteudo)
                .build();
    }

}