package br.ufpr.mscadastros.service;

import br.ufpr.mscadastros.client.ApiGatewayClient;
import br.ufpr.mscadastros.client.MsComunicacoesClient;
import br.ufpr.mscadastros.client.MsLocacoesClient;
import br.ufpr.mscadastros.emails.TemplateEmails;
import br.ufpr.mscadastros.exceptions.ConflictException;
import br.ufpr.mscadastros.exceptions.EntityNotFoundException;
import br.ufpr.mscadastros.model.dto.cliente.*;
import br.ufpr.mscadastros.model.dto.cliente.ClienteAlterarEmailRequest;
import br.ufpr.mscadastros.model.dto.cliente.ClienteAlterarEmailResponse;
import br.ufpr.mscadastros.model.entity.Cliente;
import br.ufpr.mscadastros.model.enums.NivelAcesso;
import br.ufpr.mscadastros.repository.ClienteRepository;
import br.ufpr.mscadastros.security.TokenService;
import jakarta.transaction.Transactional;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class ClienteService {
    public static final String CLIENTE_NAO_ENCONTRADO = "cliente não encontrado";
    private final TokenService tokenService;
    private final ClienteRepository clienteRepository;

    @Value("${url.alteracao.email_cliente}")
    private String urlAlteracaoEmailCliente;

    @Value("${url.ativacao.conta_cliente}")
    private String urlAtivacaoContaCliente;

    private final MsLocacoesClient msLocacoesClient;
    private final ApiGatewayClient apiGatewayClient;
    private final MsComunicacoesClient msComunicacoesClient;



    public ClienteService(TokenService tokenService, ClienteRepository clienteRepository, MsLocacoesClient msLocacoesClient, ApiGatewayClient apiGatewayClient, MsComunicacoesClient msComunicacoesClient) {
        this.tokenService = tokenService;
        this.clienteRepository = clienteRepository;
        this.msLocacoesClient = msLocacoesClient;
        this.apiGatewayClient = apiGatewayClient;
        this.msComunicacoesClient = msComunicacoesClient;
    }

    @Transactional
    public ClienteCriacaoResponse criarCliente(ClienteCriacaoRequest cliente) {
        if(Boolean.TRUE.equals(clienteRepository.existsByEmail(cliente.getEmail()))) {
            throw new ConflictException("E-mail já cadastrado");
        }
        if(Boolean.TRUE.equals(clienteRepository.existsByCpf(cliente.getCpf()))) {
            throw new ConflictException("CPF já cadastrado");
        }

        if(cliente.getAlunoUFPR()) {
            if (Boolean.TRUE.equals(clienteRepository.existsByGrr(cliente.getGrr()))) {
                throw new ConflictException("GRR já cadastrado");
            }
        }


        //Criação do novo cliente
        Cliente novoCliente = new Cliente(cliente);
        clienteRepository.save(novoCliente);

        //Gerar token de ativação de conta
        String tokenAtivacaoConta = tokenService.gerarTokenComEmailSemExpiracao(cliente.getIdUsuario().toString(), NivelAcesso.ROLE_CLIENTE, cliente.getEmail());

        //Envio de email de ativação de conta
        msComunicacoesClient.enviarEmail(TemplateEmails.emailAtivacaoConta(cliente.getEmail(),
                cliente.getNome(), tokenAtivacaoConta, urlAtivacaoContaCliente));

        return new ClienteCriacaoResponse(novoCliente);
    }

    public ClienteBuscaResponse buscarClientePorId(Long id) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(CLIENTE_NAO_ENCONTRADO));
        return new ClienteBuscaResponse(cliente);
    }

    @Transactional
    public ClienteAlteracaoResponse atualizarDadosCliente(ClienteAlteracaoRequest request, String token) {
        //recuperar do id do usuário do token
        String idUsuario = tokenService.getSubject(token);

        //Recuperar Cliente
        Cliente cliente = clienteRepository.findByIdUsuario(Long.parseLong(idUsuario))
                .orElseThrow(() -> new EntityNotFoundException(CLIENTE_NAO_ENCONTRADO));

        if(Boolean.TRUE.equals(clienteRepository.existsByEmail(request.getEmail())) && !cliente.getEmail().equals(request.getEmail())) {
            throw new ConflictException("E-mail já cadastrado");
        }

        if(request.getAlunoUFPR()) {
            if (Boolean.TRUE.equals(clienteRepository.existsByGrr(cliente.getGrr())) && !cliente.getGrr().equals(request.getGrr())) {
                throw new ConflictException("GRR já cadastrado");
            }
        }

        //altera o nome, caso ele seja passado na requisição
        if(StringUtils.isNotBlank(request.getNome())) {
        cliente.setNome(request.getNome());
        }

        //alterar vínculo com a UFPR e grr:
        if(request.getGrr() != null) {
            cliente.setAlunoUFPR(true);
            cliente.setGrr(request.getGrr());
        } else if (request.getAlunoUFPR() != null && Boolean.FALSE.equals(request.getAlunoUFPR())) {
            cliente.setAlunoUFPR(false);
            cliente.setGrr(null);
        }

        clienteRepository.save(cliente);


        //Para alterar o email, é necessário validar ele, clicando no link enviado para o novo email
        if(StringUtils.isNotBlank(request.getEmail()) && !cliente.getEmail().equals(request.getEmail())) {

            String tokenAlteracaoEmail = tokenService.gerarTokenComEmailSemExpiracao(idUsuario, NivelAcesso.ROLE_CLIENTE, request.getEmail());
            msComunicacoesClient.enviarEmail(TemplateEmails.emailConfirmarNovoEmail(request.getEmail(),
                    cliente.getNome(), tokenAlteracaoEmail, urlAlteracaoEmailCliente));


            return ClienteAlteracaoResponse.builder()
                    .mensagem("Dados do cliente alterado e mensagem enviada para " + request.getEmail())
                    .build();
        }


        return ClienteAlteracaoResponse.builder()
                .mensagem("Dados do cliente foram atualizados com sucesso")
                .build();

    }

    public ClienteAlterarEmailResponse alterarEmail(ClienteAlterarEmailRequest request) {
        //recuperar o id e o email do token
        String idUsuario = tokenService.getSubject(request.getTokenUsuarioComNovoEmail());
        String novoEmail = tokenService.getIssuer(request.getTokenUsuarioComNovoEmail(), "email");

        //recuperar cliente
        Cliente cliente = clienteRepository.findByIdUsuario(Long.parseLong(idUsuario))
                .orElseThrow(() -> new EntityNotFoundException(CLIENTE_NAO_ENCONTRADO));

        //atualizar email de Cliente
        cliente.setEmail(novoEmail);
        clienteRepository.save(cliente);

        return ClienteAlterarEmailResponse.builder()
                .idConta(idUsuario)
                .novoEmail(novoEmail)
                .build();

    }

    public ClienteBuscaResponse buscarDadosClienteLogado(String token) {
        String idUsuario = tokenService.getSubject(token);

        Cliente cliente = clienteRepository.findByIdUsuario(Long.parseLong(idUsuario))
                .orElseThrow(() -> new EntityNotFoundException(CLIENTE_NAO_ENCONTRADO));

        return new ClienteBuscaResponse(cliente);
    }

    public List<BuscarEmailsClientesResponse> buscarEmailsClientes() {
        List<Cliente> listaClientes = clienteRepository.findAll();
        if(listaClientes.isEmpty()) {
            throw new EntityNotFoundException("Não há clientes cadastrados");
        }
        List<BuscarEmailsClientesResponse> response = new ArrayList<>();
        listaClientes.forEach(cliente ->
                response.add(new BuscarEmailsClientesResponse(cliente)));
        return response;
    }

    public List<NomeClienteResponse> buscarNomesClientes(List<Long> ids) {
        var response = new ArrayList<NomeClienteResponse>();
        ids.forEach(id -> {
            var cliente = clienteRepository.findById(id);
            cliente.ifPresent(c -> response.add(new NomeClienteResponse(c)));
        });
        return response;
    }

    public List<Long> buscarIdsClientes() {
        var listaClientes = clienteRepository.findAll();
        return listaClientes.stream()
                .map(Cliente::getId)
                .toList();
    }

    public List<ClienteBuscaDetalhadaResponse> buscarDetalhesCliente() {
        var listaClientes = clienteRepository.findAll();
        var estatisticasReservas = msLocacoesClient.buscarEstatisticasReserva();
        var statusBloqueioContas = apiGatewayClient.buscarStatusBloqueioContas();

        var clienteBuscaDetalhadaList = new ArrayList<ClienteBuscaDetalhadaResponse>();

        listaClientes.forEach(cliente -> clienteBuscaDetalhadaList.add(new ClienteBuscaDetalhadaResponse(cliente, estatisticasReservas, statusBloqueioContas)));

        return clienteBuscaDetalhadaList;
    }
}
