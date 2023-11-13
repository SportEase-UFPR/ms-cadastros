package br.ufpr.mscadastros.service;

import br.ufpr.mscadastros.client.MsComunicacoesClient;
import br.ufpr.mscadastros.emails.TemplateEmails;
import br.ufpr.mscadastros.model.dto.usuario.*;
import br.ufpr.mscadastros.repository.AdministradorRepository;
import br.ufpr.mscadastros.repository.ClienteRepository;
import br.ufpr.mscadastros.security.TokenService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    @Value("${url.recuperacao.senha_cliente}")
    private String urlRecuperacaoSenhaCliente;

    @Value("${url.recuperacao.senha_adm}")
    private String urlRecuperacaoSenhaAdm;

    public static final String USUARIO_NAO_ENCONTRADO = "Usuário não encontrado";
    private final TokenService tokenService;
    private final ClienteRepository clienteRepository;
    private final AdministradorRepository admRepository;
    private final MsComunicacoesClient msComunicacoesClient;

    public UsuarioService(TokenService tokenService, ClienteRepository clienteRepository, AdministradorRepository admRepository, MsComunicacoesClient msComunicacoesClient) {
        this.tokenService = tokenService;
        this.clienteRepository = clienteRepository;
        this.admRepository = admRepository;
        this.msComunicacoesClient = msComunicacoesClient;
    }


    public RecuperarSenhaResponse enviarEmailRecuperacaoSenha(String email) {
        var cliente = clienteRepository.findByEmail(email).orElse(null);
        var adm = admRepository.findByEmail(email).orElse(null);

        if(cliente == null && adm == null) {
            return null;
        }

        if(cliente != null) {
            String tokenRecuperacaoSenha = tokenService.gerarTokenComEmailSemExpiracao(cliente.getIdUsuario().toString(), email);
            msComunicacoesClient.enviarEmail(TemplateEmails.emailRecuperacaoSenha(email, cliente.getNome(),
                    tokenRecuperacaoSenha, urlRecuperacaoSenhaCliente));
        } else {
            String tokenRecuperacaoSenha = tokenService.gerarTokenComEmailSemExpiracao(adm.getIdUsuario().toString(), email);
            msComunicacoesClient.enviarEmail(TemplateEmails.emailRecuperacaoSenha(email, adm.getNome(),
                    tokenRecuperacaoSenha, urlRecuperacaoSenhaAdm));
        }

        return RecuperarSenhaResponse.builder()
                .email(email)
                .mensagem("Email enviado")
                .build();
    }
}
