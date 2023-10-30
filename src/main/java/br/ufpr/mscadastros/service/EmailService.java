package br.ufpr.mscadastros.service;

import br.ufpr.mscadastros.exceptions.EmailException;
import br.ufpr.mscadastros.model.dto.email.EnviarEmailRequest;
import br.ufpr.mscadastros.model.dto.email.EnviarEmailResponse;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class EmailService {
    private final JavaMailSender mailSender;

    @Value("${url.ativacao.conta}") //application.yml
    private String urlAtivacaoConta;

    @Value("${url.recuperacao.senha}") //application.yml
    private String urlRecuperacaoSenha;

    @Value("${url.alteracao.email}") //application.yml
    private String urlAlteracaoEmail;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void enviarEmailAtivacaoConta(String email, String nome, String tokenAtivacao) {
        String linkAtivacao = urlAtivacaoConta + "?token=" + tokenAtivacao;

        MimeMessage mensagem = mailSender.createMimeMessage();
        MimeMessageHelper helper;
        try {
            helper = new MimeMessageHelper(mensagem, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());
            helper.setTo(email); //destinatário
            helper.setSubject("Ative Sua Conta no SportEase!"); //assunto
            helper.setText(formatarCorpoEmailAtivacao(nome, linkAtivacao), true); //corpo da msg
        } catch (MessagingException e) {
            throw new EmailException(e.getMessage());
        }
        mailSender.send(mensagem);
    }

    private String formatarCorpoEmailAtivacao(String nome, String linkAtivacao) {
        String corpoEmail = "<html><body>";
        corpoEmail += "<p>Olá " + nome + ",</p>";
        corpoEmail += "<p>Bem-vindo ao SportEase! ";
        corpoEmail += "<p>Para ativar sua conta e começar a utilizar os nossos serviços, clique no botão abaixo:</p>";
        corpoEmail += "<a href='" + linkAtivacao + "' style='display: inline-block; padding: 10px 20px; background-color: #007bff; color: #fff; text-decoration: none;'>Ativar Agora</a>";
        corpoEmail += "<p>Se o botão acima não funcionar, você também pode copiar e colar o seguinte link em seu navegador:</p>";
        corpoEmail += "<p><a href='" + linkAtivacao + "'>" + linkAtivacao + "</a></p>";
        corpoEmail += "<p>Atenciosamente,</p>";
        corpoEmail += "<p>A Equipe SportEase.</p>";
        corpoEmail += "</body></html>";
        return corpoEmail;
    }

    public void enviarEmailRecuperacaoSenha(String email, String nome, String tokenRecuperacaoSenha) {
        String linkAtivacao = urlRecuperacaoSenha + "?token=" + tokenRecuperacaoSenha;

        MimeMessage mensagem = mailSender.createMimeMessage();
        MimeMessageHelper helper;
        try {
            helper = new MimeMessageHelper(mensagem, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());
            helper.setTo(email); //destinatário
            helper.setSubject("Recuperação de senha"); //assunto
            helper.setText(formatarCorpoEmailRecuperacaoSenha(nome, linkAtivacao), true); //corpo da msg
        } catch (MessagingException e) {
            throw new EmailException(e.getMessage());
        }
        mailSender.send(mensagem);
    }

    private String formatarCorpoEmailRecuperacaoSenha(String nome, String linkRecuperacao) {
        String corpoEmail = "<html><body>";
        corpoEmail += "<p>Olá " + nome + ",</p>";
        corpoEmail += "<p>Recebemos uma solicitação de recuperação de senha para a sua conta no SportEase.</p>";
        corpoEmail += "<p>Para redefinir sua senha, clique no botão abaixo:</p>";
        corpoEmail += "<a href='" + linkRecuperacao + "' style='display: inline-block; padding: 10px 20px; background-color: #007bff; color: #fff; text-decoration: none;'>Redefinir Senha</a>";
        corpoEmail += "<p>Se o botão acima não funcionar, você também pode copiar e colar o seguinte link em seu navegador:</p>";
        corpoEmail += "<p><a href='" + linkRecuperacao + "'>" + linkRecuperacao + "</a></p>";
        corpoEmail += "<p>Se você não solicitou essa recuperação de senha, pode ignorar este e-mail.</p>";
        corpoEmail += "<p>Atenciosamente,</p>";
        corpoEmail += "<p>A Equipe SportEase.</p>";
        corpoEmail += "</body></html>";
        return corpoEmail;
    }


    public void enviarEmailConfirmacaoNovoEmail(String novoEmail, String nome, String tokenAlteracaoEmail) {
        String linkAtualizacaoEmail = urlAlteracaoEmail + "?token=" + tokenAlteracaoEmail;
        MimeMessage mensagem = mailSender.createMimeMessage();
        MimeMessageHelper helper;
        try {
            helper = new MimeMessageHelper(mensagem, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());
            helper.setTo(novoEmail); //destinatário
            helper.setSubject("Alteração de email"); //assunto
            helper.setText(formatarCorpoEmailAlteracaoEmail(nome, linkAtualizacaoEmail), true); //corpo da msg
        } catch (MessagingException e) {
            throw new EmailException(e.getMessage());
        }
        mailSender.send(mensagem);
    }

    private String formatarCorpoEmailAlteracaoEmail(String nome, String linkAtualizacaoEmail) {
        String corpoEmail = "<html><body>";
        corpoEmail += "<p>Olá " + nome + ",</p>";
        corpoEmail += "<p>Recebemos uma solicitação de alteração de email da sua conta no SportEase.</p>";
        corpoEmail += "<p>Para validar o novo email, clique no botão abaixo:</p>";
        corpoEmail += "<a href='" + linkAtualizacaoEmail + "' style='display: inline-block; padding: 10px 20px; background-color: #007bff; color: #fff; text-decoration: none;'>Validar novo email</a>";
        corpoEmail += "<p>Se o botão acima não funcionar, você também pode copiar e colar o seguinte link em seu navegador:</p>";
        corpoEmail += "<p><a href='" + linkAtualizacaoEmail + "'>" + linkAtualizacaoEmail + "</a></p>";
        corpoEmail += "<p>Atenciosamente,</p>";
        corpoEmail += "<p>A Equipe SportEase.</p>";
        corpoEmail += "</body></html>";
        return corpoEmail;
    }

    public EnviarEmailResponse enviarEmailClientes(EnviarEmailRequest request) {
        List<String> listaEmails = request.getListaEmails();
        String assunto = request.getAssunto();
        String corpo = request.getCorpo();

        ExecutorService executorService = Executors.newFixedThreadPool(10); //número de threads

        for (String email : listaEmails) {
            executorService.submit(() -> {
                MimeMessage mensagem = mailSender.createMimeMessage();
                MimeMessageHelper helper;
                try {
                    helper = new MimeMessageHelper(mensagem, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());
                    helper.setTo(email); // destinatário
                    helper.setSubject(assunto); // assunto
                    helper.setText(corpo, true); // corpo da mensagem
                } catch (MessagingException e) {
                    throw new EmailException(e.getMessage());
                }
                mailSender.send(mensagem);
            });
        }

        executorService.shutdown();

        while (!executorService.isTerminated()) {/*aguardando...*/}
        return EnviarEmailResponse.builder()
                .mensagem("emails enviados com sucesso")
                .build();
    }
}
