package br.ufpr.mscadastros.service;

import br.ufpr.mscadastros.exceptions.EntityNotFoundException;
import br.ufpr.mscadastros.model.dto.usuario.*;
import br.ufpr.mscadastros.model.entity.Cliente;
import br.ufpr.mscadastros.model.enums.NivelAcesso;
import br.ufpr.mscadastros.repository.ClienteRepository;
import br.ufpr.mscadastros.security.TokenService;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    public static final String USUARIO_NAO_ENCONTRADO = "Usuário não encontrado";
    private final TokenService tokenService;
    private final EmailService emailService;
    private final ClienteRepository clienteRepository;

    public UsuarioService(TokenService tokenService, EmailService emailService, ClienteRepository clienteRepository) {
        this.tokenService = tokenService;
        this.emailService = emailService;
        this.clienteRepository = clienteRepository;
    }


    public RecuperarSenhaResponse enviarEmailRecuperacaoSenha(String email) {
        Cliente cliente = clienteRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("Cliente não encontrado"));

        String tokenRecuperacaoSenha = tokenService.gerarTokenComEmailSemExpiracao(cliente.getIdUsuario().toString(),
                NivelAcesso.ROLE_CLIENTE, email);

        emailService.enviarEmailRecuperacaoSenha(email, cliente.getNome(), tokenRecuperacaoSenha);

        return RecuperarSenhaResponse.builder()
                .email(email)
                .mensagem("Email enviado")
                .build();
    }
}
