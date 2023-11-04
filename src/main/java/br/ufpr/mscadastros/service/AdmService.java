package br.ufpr.mscadastros.service;

import br.ufpr.mscadastros.client.ApiGatewayClient;
import br.ufpr.mscadastros.exceptions.ConflictException;
import br.ufpr.mscadastros.exceptions.EntityNotFoundException;
import br.ufpr.mscadastros.model.dto.adm.*;
import br.ufpr.mscadastros.model.entity.Administrador;
import br.ufpr.mscadastros.model.enums.NivelAcesso;
import br.ufpr.mscadastros.repository.AdministradorRepository;
import br.ufpr.mscadastros.security.TokenService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AdmService {

    public static final String ADM_NAO_ENCONTRADO = "Adm não encontrado";
    private final TokenService tokenService;
    private final AdministradorRepository administradorRepository;
    private final EmailService emailService;

    private final ApiGatewayClient apiGatewayClient;

    @Value("${url.alteracao.email_adm}")
    private String urlAlteracaoEmailAdm;

    @Value("${url.ativacao.conta_adm}")
    private String urlAtivacaoContaAdm;

    @PersistenceContext
    private EntityManager entityManager;


    public AdmService(TokenService tokenService, AdministradorRepository administradorRepository, EmailService emailService, ApiGatewayClient apiGatewayClient) {
        this.tokenService = tokenService;
        this.administradorRepository = administradorRepository;
        this.emailService = emailService;
        this.apiGatewayClient = apiGatewayClient;
    }

    public AdmAlterarEmailSolicitacaoResponse alterarEmailSolicitacao(AdmAlterarEmailSolicitacaoRequest request, String token) {
        //recuperar idUsuario do token e adm pelo id do usuário
        String idUsuario = tokenService.getSubject(token);
        Administrador adm = administradorRepository.findByIdUsuario(Long.parseLong(idUsuario))
                .orElseThrow(() -> new EntityNotFoundException(ADM_NAO_ENCONTRADO));

        //Gerar token
        String tokenAlteracaoEmail = tokenService.gerarTokenComEmailSemExpiracao(idUsuario,
                NivelAcesso.ROLE_ADM, request.getNovoEmail());

        //enviar email
        emailService.enviarEmailConfirmacaoNovoEmail(request.getNovoEmail(), adm.getNome(), tokenAlteracaoEmail, urlAlteracaoEmailAdm);

        return AdmAlterarEmailSolicitacaoResponse.builder()
                .mensagem("Mensagem de alteração de email enviada para " + request.getNovoEmail())
                .build();
    }

    public AdmAlterarEmailResponse alterarEmail(AdmAlterarEmailRequest request) {
        //recuperar do id do usuário do token
        String idUsuario = tokenService.getSubject(request.getTokenUsuarioComNovoEmail());

        //recuperar novo email do token
        String novoEmail = tokenService.getIssuer(request.getTokenUsuarioComNovoEmail(), "email");

        //recuperar adm
        Administrador adm = administradorRepository.findByIdUsuario(Long.parseLong(idUsuario))
                .orElseThrow(() -> new EntityNotFoundException(ADM_NAO_ENCONTRADO));

        //atualizar adm
        adm.setEmail(novoEmail);
        administradorRepository.save(adm);

        return AdmAlterarEmailResponse.builder()
                .idConta(idUsuario)
                .novoEmail(novoEmail)
                .build();

    }

    public AdmAlterarNomeResponse alterarNome(AdmAlterarNomeRequest request, String token) {
        //recuperar idUsuario e adm
        String idUsuario = tokenService.getSubject(token);
        Administrador adm = administradorRepository.findByIdUsuario(Long.parseLong(idUsuario))
                .orElseThrow(() -> new EntityNotFoundException(ADM_NAO_ENCONTRADO));

        //atualizar nome
        adm.setNome(request.getNovoNome());
        administradorRepository.save(adm);

        return AdmAlterarNomeResponse.builder().novoNome(request.getNovoNome()).build();
    }

    public AdmCriacaoResponse criarAdm(AdmCriacaoRequest adm) {
        if(Boolean.TRUE.equals(administradorRepository.existsByEmail(adm.getEmail()))) {
            throw new ConflictException("Email já cadastrado");
        }
        if(Boolean.TRUE.equals(administradorRepository.existsByCpf(adm.getCpf()))) {
            throw new ConflictException("Cpf já cadastrado");
        }

        //Criação do novo adm
        Administrador novoAdm = new Administrador(adm);
        administradorRepository.save(novoAdm);

        //Gerar token de ativação de conta
        String tokenAtivacaoConta = tokenService.gerarTokenComEmailSemExpiracao(adm.getIdUsuario().toString(), NivelAcesso.ROLE_ADM, adm.getEmail());

        //Envio de email de ativação de conta
        emailService.enviarEmailAtivacaoConta(adm.getEmail(), adm.getNome(), tokenAtivacaoConta, urlAtivacaoContaAdm);

        return new AdmCriacaoResponse(novoAdm);
    }

    public List<AdmBuscaResponse> listarAdms() {
        List<Administrador> listaAdms = administradorRepository.findAll();
        if (listaAdms.isEmpty()) {
            throw new EntityNotFoundException("Não há administradores cadastrados");
        }

        List<AdmBuscaResponse> response = new ArrayList<>();
        listaAdms.forEach(adm -> response.add(new AdmBuscaResponse(adm)));
        return response;
    }

    public AdmBuscaResponse buscarAdmPorId(Long admId) {
        Administrador adm = administradorRepository.findById(admId)
                .orElseThrow(() -> new EntityNotFoundException("Administrador não cadastrado"));

        return new AdmBuscaResponse(adm);
    }

    @Transactional
    public AdmExclusaoResponse excluirAdmPorId(Long admId) {
        Administrador adm = administradorRepository.findById(admId)
                .orElseThrow(() -> new EntityNotFoundException("Administrador não cadastrado"));

        //Excluir adm
        administradorRepository.delete(adm);

        //Excluir usuário associado ao adm
        apiGatewayClient.excluirUsuarioPorId(adm.getIdUsuario());

        return AdmExclusaoResponse.builder().msg("Administrador excluído com sucesso").build();
    }


    public AdmAlteracaoResponse editarDadosAdm(String token, AdmAlteracaoRequest request) {
        //recuperar do id do usuário do token
        String idUsuario = tokenService.getSubject(token);

        //Recuperar Adm
        Administrador adm = administradorRepository.findByIdUsuario(Long.parseLong(idUsuario))
                .orElseThrow(() -> new EntityNotFoundException("adm não encontrado"));

        if(Boolean.TRUE.equals(administradorRepository.existsByEmail(request.getEmail())) && !adm.getEmail().equals(request.getEmail())) {
            throw new ConflictException("Email já cadastrado");
        }

        //altera o nome, caso ele seja passado na requisição
        if (StringUtils.isNotBlank(request.getNome())) {
            adm.setNome(request.getNome());
        }

        administradorRepository.save(adm);

        //Para alterar o email, é necessário validar ele, clicando no link enviado para o novo email
        if (StringUtils.isNotBlank(request.getEmail()) && !adm.getEmail().equals(request.getEmail())) {
            String tokenAlteracaoEmail = tokenService.gerarTokenComEmailSemExpiracao(idUsuario, NivelAcesso.ROLE_ADM, request.getEmail());
            emailService.enviarEmailConfirmacaoNovoEmail(request.getEmail(), adm.getNome(), tokenAlteracaoEmail, urlAlteracaoEmailAdm);

            return AdmAlteracaoResponse.builder()
                    .idAdm(adm.getId())
                    .mensagem("Dados do Adm alterado e mensagem enviada para " + request.getEmail())
                    .build();
        }

        return AdmAlteracaoResponse.builder()
                .idAdm(adm.getId())
                .mensagem("Dados do Adm foram atualizados com sucesso")
                .build();
    }

    public AdmBuscaResponse buscarAdmPorIdUsuario(String token) {
        String idUsuario = tokenService.getSubject(token);

        Administrador adm = administradorRepository.findByIdUsuario(Long.parseLong(idUsuario))
                .orElseThrow(() -> new EntityNotFoundException(ADM_NAO_ENCONTRADO));

        return new AdmBuscaResponse(adm);
    }
}
