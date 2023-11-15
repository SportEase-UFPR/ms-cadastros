package br.ufpr.mscadastros.emails;

import br.ufpr.mscadastros.model.dto.email.CriacaoEmailRequest;

public class TemplateEmails {

    private TemplateEmails(){/*vazio..*/}

    public static CriacaoEmailRequest emailAtivacaoConta(String email, String nome, String tokenAtivacao, String urlBase) {
        var linkAtivacao = urlBase + "?token=" + tokenAtivacao;
        var assunto = "Ative Sua Conta no SportEase!";
        var mensagem = """
                <html><body>
                    <h2>Olá %s,</h2>
                    <p>Bem-vindo ao SportEase! </p>
                    <p>Para ativar sua conta e começar a utilizar os nossos serviços, clique no botão abaixo:</p>
                    <div style="text-align: center;">
                      <a href='%s' style="background-color: #007bff;
                      text-decoration: none;
                      color: #ffffff !important;
                      border-radius: 10px;
                      padding: 15px 20px;
                      font-weight: 500;
                      margin: 20px;
                      font-size: 18px;">Ativar Minha Conta</a>
                    </div>
                    <p>Se o botão acima não funcionar, você também pode copiar e colar o seguinte link em seu navegador:</p>
                    <p><a href='%s'>%s</a></p>
                    <p style="margin: 0;">Atenciosamente,</p>
                    <p style="margin: 0;">A Equipe SportEase.</p>
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
                    <h2>Olá %s,</h2>
                    <p>Recebemos uma solicitação de recuperação de senha para a sua conta no SportEase.</p>
                    <p>Para redefinir sua senha, clique no botão abaixo:</p>
                    <div style="text-align: center;">
                      <a href='%s' style="background-color: #007bff;
                      text-decoration: none;
                      color: #ffffff !important;
                      border-radius: 10px;
                      padding: 15px 20px;
                      font-weight: 500;
                      margin: 20px;
                      font-size: 18px;">Redefinir Senha</a>
                    </div>
                    <p>Se o botão acima não funcionar, você também pode copiar e colar o seguinte link em seu navegador:</p>
                    <p><a href='%s'>%s</a></p>
                    <p>Se você não solicitou essa recuperação de senha, pode ignorar este e-mail.</p>
                    <p style="margin: 0;">Atenciosamente,</p>
                    <p style="margin: 0;">A Equipe SportEase.</p>
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
                <body>
                        <h2>Olá %s,</h2>
                        <p>Recebemos uma solicitação de alteração de email da sua conta no SportEase.</p>
                        <p>Para validar o novo email, clique no botão abaixo:</p>
                        <div style="text-align: center;">
                              <a href='%s' style="background-color: #007bff;
                              text-decoration: none;
                              color: #ffffff !important;
                              border-radius: 10px;
                              padding: 15px 20px;
                              font-weight: 500;
                              margin: 20px;
                              font-size: 18px;">Validar novo email</a>
                        </div>
                        <p>Se o botão acima não funcionar, você também pode copiar e colar o seguinte link em seu navegador:</p>
                        <p><a href='%s'>%s</a></p>
                        <p style="margin: 0;">Atenciosamente,</p>
                        <p style="margin: 0;">A Equipe SportEase.</p>
                </body>
             </html>""".formatted(nome, linkAtualizacaoEmail, linkAtualizacaoEmail, linkAtualizacaoEmail);

        return CriacaoEmailRequest.builder()
                .assunto(assunto)
                .mensagem(mensagem)
                .email(novoEmail)
                .build();
    }
}
