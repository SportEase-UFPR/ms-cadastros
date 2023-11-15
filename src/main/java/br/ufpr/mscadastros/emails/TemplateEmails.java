package br.ufpr.mscadastros.emails;

import br.ufpr.mscadastros.model.dto.email.CriacaoEmailRequest;

public class TemplateEmails {

    private TemplateEmails(){/*vazio..*/}
    public static CriacaoEmailRequest emailAtivacaoConta(String email, String nome, String tokenAtivacao, String urlBase) {
        var linkAtivacao = urlBase + "?token=" + tokenAtivacao;
        var assunto = "Ative Sua Conta no SportEase!";
        var mensagem = """
                <html><body>
                    <p>Olá %s,</p>
                    <p>Bem-vindo ao SportEase! </p>
                    <p>Para ativar sua conta e começar a utilizar os nossos serviços, clique no botão abaixo:</p>
                    <a href='%s' style='display: inline-block; padding: 10px 20px; background-color: #007bff; color: #fff; text-decoration: none;'>Ativar Agora</a>
                    <p>Se o botão acima não funcionar, você também pode copiar e colar o seguinte link em seu navegador:</p>
                    <p><a href='%s'>%s</a></p>
                    <p>Atenciosamente,</p>
                    <p>A Equipe SportEase.</p>
                </body></html>
                """.formatted(nome, linkAtivacao, linkAtivacao, linkAtivacao);

        return CriacaoEmailRequest.builder()
                .assunto(assunto)
                .mensagem(mensagem)
                .email(email)
                .build();
    }

    public static CriacaoEmailRequest emailRecuperacaoSenha(String email, String nome, String tokenRecuperacaoSenha, String urlBase) {
        var linkAtivacao = urlBase + "?token=" + tokenRecuperacaoSenha;
        var assunto = "SportEase - Recuperação de senha";
        var mensagem = """
                <html><body>
                    <p>Olá %s,</p>
                    <p>Recebemos uma solicitação de recuperação de senha para a sua conta no SportEase.</p>
                    <p>Para redefinir sua senha, clique no botão abaixo:</p>
                    <a href='%s' style='display: inline-block; padding: 10px 20px; background-color: #007bff; color: #fff; text-decoration: none;'>Redefinir Senha</a>
                    <p>Se o botão acima não funcionar, você também pode copiar e colar o seguinte link em seu navegador:</p>
                    <p><a href='%s'>%s</a></p>
                    <p>Se você não solicitou essa recuperação de senha, pode ignorar este e-mail.</p>
                    <p>Atenciosamente,</p>
                    <p>A Equipe SportEase.</p>
                </body></html>
                """.formatted(nome, linkAtivacao, linkAtivacao, linkAtivacao);

        return CriacaoEmailRequest.builder()
                .assunto(assunto)
                .mensagem(mensagem)
                .email(email)
                .build();
    }

    public static CriacaoEmailRequest emailConfirmarNovoEmail(String novoEmail, String nome, String tokenAlteracaoEmail, String urlBase) {
        var linkAtualizacaoEmail = urlBase + "?token=" + tokenAlteracaoEmail;
        var assunto = "SportEase - Alteração de email";
        var mensagem = """
                             <html>
                <head>
                  <style>
                    .cabecalho {
                      display: flex;
                      align-items: center;
                      justify-content: center;
                    }
                                
                    .titulo {
                      margin-left: 12px;
                    }
                                
                    main {
                      text-align: center;
                    }
                                
                    .container-email {
                      display: flex;
                      justify-content: center;
                    }
                                
                    .btn-email {
                      background-color: #5d5fef;
                      text-decoration: none;
                      color: #ffffff;
                      border-radius: 10px;
                      padding: 15px 20px;
                      font-weight: 500;
                      margin: 10px;
                      font-size: 18px;
                    }
                                
                    .att {
                      margin: 0;
                    }
                  </style>
                </head>
                <body>
                    <header class="cabecalho">
                        <img src="https://i.postimg.cc/vZww9G31/logo-sport-ease.png" alt="logo sport ease ufpr" width="60" />
                        <h1 class="titulo">SportEase</h1>
                    </header>
                                              
                    <main>
                        <h2>Olá %s,</h2>
                        <p>Recebemos uma solicitação de alteração de email da sua conta no SportEase.</p>
                        <p>Para validar o novo email, clique no botão abaixo:</p>
                        <div class="container-email">
                            <a href='%s' class="btn-email">Validar novo email</a>
                        </div>
                        <p>Se o botão acima não funcionar, você também pode copiar e colar o seguinte link em seu navegador:</p>
                        <p><a href='%s'>%s</a></p>
                        <p class="att">Atenciosamente,</p>
                        <p class="att">A Equipe SportEase.</p>
                    </main>
                </body></html>
                """.formatted(nome, linkAtualizacaoEmail, linkAtualizacaoEmail, linkAtualizacaoEmail);

        return CriacaoEmailRequest.builder()
                .assunto(assunto)
                .mensagem(mensagem)
                .email(novoEmail)
                .build();
    }
}
