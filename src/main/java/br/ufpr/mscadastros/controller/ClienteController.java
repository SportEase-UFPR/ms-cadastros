package br.ufpr.mscadastros.controller;

import br.ufpr.mscadastros.exceptions.EntityNotFoundException;
import br.ufpr.mscadastros.model.dto.cliente.*;
import br.ufpr.mscadastros.model.dto.cliente.ClienteAlterarEmailRequest;
import br.ufpr.mscadastros.model.dto.cliente.ClienteAlterarEmailResponse;
import br.ufpr.mscadastros.model.dto.email.EnviarEmailRequest;
import br.ufpr.mscadastros.model.dto.email.EnviarEmailResponse;
import br.ufpr.mscadastros.model.dto.email.EnviarEmailTodosRequest;
import br.ufpr.mscadastros.repository.ClienteRepository;
import br.ufpr.mscadastros.security.TokenService;
import br.ufpr.mscadastros.service.ClienteService;
import br.ufpr.mscadastros.service.EmailService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins ="*")
@RestController
@RequestMapping("/clientes")
public class ClienteController {
    private final ClienteService clienteService;
    private final ClienteRepository repository;
    private final EmailService emailService;
    private final TokenService tokenService;

    public ClienteController(ClienteService clienteService, ClienteRepository repository, EmailService emailService, TokenService tokenService) {
        this.clienteService = clienteService;
        this.repository = repository;
        this.emailService = emailService;
        this.tokenService = tokenService;
    }

    @PostMapping
    public ResponseEntity<ClienteCriacaoResponse> criarCliente(@Valid @RequestBody ClienteCriacaoRequest cliente) {
        return ResponseEntity.status(HttpStatus.CREATED).body(clienteService.criarCliente(cliente));
    }

    @GetMapping("/{clienteId}")
    public ResponseEntity<ClienteBuscaResponse> buscarClientePorId(@PathVariable Long clienteId) {
        return ResponseEntity.status(HttpStatus.OK).body(clienteService.buscarClientePorId(clienteId));
    }

    @GetMapping("/cliente-logado")
    public ResponseEntity<ClienteBuscaResponse> buscarDadosClienteLogado(@RequestHeader("AuthorizationUser") String token) {
        return ResponseEntity.status(HttpStatus.OK).body(clienteService.buscarDadosClienteLogado(token));
    }

    @PutMapping
    public ResponseEntity<ClienteAlteracaoResponse> atualizarDadosCliente
            (@Valid @RequestBody ClienteAlteracaoRequest request,
             @RequestHeader("AuthorizationUser") String token) {
        return ResponseEntity.status(HttpStatus.OK).body(clienteService.atualizarDadosCliente(request, token));
    }

    @PutMapping("/alterar-email")
    public ResponseEntity<ClienteAlterarEmailResponse> alterarEmail(@RequestBody @Valid ClienteAlterarEmailRequest alterarEmailRequest) {
        return ResponseEntity.status(HttpStatus.OK).body(clienteService.alterarEmail(alterarEmailRequest));
    }

    @GetMapping("/buscar-id-por-id-usuario/{idUsuario}")
    public ResponseEntity<Long> buscarIdPorIdUsuario(@PathVariable Long idUsuario) {
        var cliente = repository.findByIdUsuario(idUsuario)
                .orElseThrow(() -> new EntityNotFoundException("Cliente não encontrado"));
            return ResponseEntity.status(HttpStatus.OK).body(cliente.getId());

    }

    @GetMapping("/buscar-emails-clientes")
    public ResponseEntity<List<BuscarEmailsClientesResponse>> buscarEmailsClientes() {
        return ResponseEntity.status(HttpStatus.OK).body(clienteService.buscarEmailsClientes());
    }

    @PostMapping("/enviar-email")
    public ResponseEntity<EnviarEmailResponse> enviarEmailClientes(@RequestBody @Valid EnviarEmailRequest request) {
        return ResponseEntity.status(HttpStatus.OK).body(emailService.enviarEmailClientes(request));
    }

    @PostMapping("/enviar-email-todos")
    public ResponseEntity<EnviarEmailResponse> enviarEmailTodosClientes(@RequestBody @Valid EnviarEmailTodosRequest request) {
        List<BuscarEmailsClientesResponse> clientes = clienteService.buscarEmailsClientes();
        List<String> listaEmailsClientes = new ArrayList<>();

        clientes.forEach(cliente -> listaEmailsClientes.add(cliente.getEmailCliente()));

        return ResponseEntity.status(HttpStatus.OK).body(emailService.enviarEmailClientes(EnviarEmailRequest.builder()
                        .assunto(request.getAssunto())
                        .corpo(request.getCorpo())
                        .listaEmails(listaEmailsClientes)
                .build()));
    }

    @PostMapping("/buscar-lista-nomes")
    public ResponseEntity<List<NomeClienteResponse>> buscarNomesClientes(@RequestBody List<Long> request,
                                                                       @RequestHeader("AuthorizationApi") String token) {
        tokenService.validarTokenApiMsLocacoes(token);
        return ResponseEntity.status(HttpStatus.OK).body(clienteService.buscarNomesClientes(request));
    }
}

