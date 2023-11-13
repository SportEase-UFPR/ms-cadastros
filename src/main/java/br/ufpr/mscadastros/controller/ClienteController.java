package br.ufpr.mscadastros.controller;

import br.ufpr.mscadastros.exceptions.EntityNotFoundException;
import br.ufpr.mscadastros.model.dto.cliente.*;
import br.ufpr.mscadastros.model.dto.cliente.ClienteAlterarEmailRequest;
import br.ufpr.mscadastros.model.dto.cliente.ClienteAlterarEmailResponse;
import br.ufpr.mscadastros.repository.ClienteRepository;
import br.ufpr.mscadastros.security.TokenService;
import br.ufpr.mscadastros.service.ClienteService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins ="*")
@RestController
@RequestMapping("/clientes")
public class ClienteController {
    private final ClienteService clienteService;
    private final ClienteRepository repository;
    private final TokenService tokenService;

    public ClienteController(ClienteService clienteService, ClienteRepository repository, TokenService tokenService) {
        this.clienteService = clienteService;
        this.repository = repository;
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

    @GetMapping("/via-ms/{clienteId}")
    public ResponseEntity<ClienteBuscaResponse> buscarClientePorIdViaMs(@PathVariable Long clienteId,
                                                                        @RequestHeader("AuthorizationApi") String token) {
        tokenService.validarTokenMs(token);
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

    @GetMapping("/buscar-emails-clientes/via-ms")
    public ResponseEntity<List<BuscarEmailsClientesResponse>> buscarEmailsClientesViaMs(
            @RequestHeader("AuthorizationApi") String token) {
        tokenService.validarTokenMs(token);
        return ResponseEntity.status(HttpStatus.OK).body(clienteService.buscarEmailsClientes());
    }

    @PostMapping("/buscar-lista-nomes")
    public ResponseEntity<List<NomeClienteResponse>> buscarNomesClientes(@RequestBody List<Long> request,
                                                                       @RequestHeader("AuthorizationApi") String token) {
        tokenService.validarTokenMs(token);
        return ResponseEntity.status(HttpStatus.OK).body(clienteService.buscarNomesClientes(request));
    }

    @GetMapping("/buscar-ids")
    public ResponseEntity<List<Long>> buscarIdsClientes(@RequestHeader("AuthorizationApi") String token) {
        tokenService.validarTokenMs(token);
        return ResponseEntity.status(HttpStatus.OK).body(clienteService.buscarIdsClientes());
    }

    @GetMapping("/detalhes")
    public ResponseEntity<List<ClienteBuscaDetalhadaResponse>> buscarDetalhesCliente() {
        return ResponseEntity.status(HttpStatus.OK).body(clienteService.buscarDetalhesCliente());
    }

}

