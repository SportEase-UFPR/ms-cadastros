package br.ufpr.mscadastros.controller;

import br.ufpr.mscadastros.exceptions.EntityNotFoundException;
import br.ufpr.mscadastros.model.dto.adm.*;
import br.ufpr.mscadastros.service.AdmService;
import br.ufpr.mscadastros.repository.AdministradorRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/adm")
public class AdmController {

    private final AdmService admService;
    private final AdministradorRepository repository;

    public AdmController(AdmService admService, AdministradorRepository repository) {
        this.admService = admService;
        this.repository = repository;
    }

    @PostMapping("/alterar-email-solicitacao")
    //Envia uma mensagem de confirmação para o novo email
    public ResponseEntity<AdmAlterarEmailSolicitacaoResponse> alterarEmailSolicitacao
            (@RequestBody @Valid AdmAlterarEmailSolicitacaoRequest request,
             @RequestHeader("AuthorizationUser") String token) {
        return ResponseEntity.status(HttpStatus.OK).body(admService.alterarEmailSolicitacao(request, token));
    }

    @PutMapping("/alterar-email")
    public ResponseEntity<AdmAlterarEmailResponse> alterarEmail(@RequestBody @Valid AdmAlterarEmailRequest request) {
        return ResponseEntity.status(HttpStatus.OK).body(admService.alterarEmail(request));
    }

    @PutMapping("/alterar-nome")
    public ResponseEntity<AdmAlterarNomeResponse> alterarNome
            (@RequestBody @Valid AdmAlterarNomeRequest request,
             @RequestHeader("AuthorizationUser") String token) {
        return ResponseEntity.status(HttpStatus.OK).body(admService.alterarNome(request, token));
    }

    @PostMapping
    public ResponseEntity<AdmCriacaoResponse> criarAdm(@Valid @RequestBody AdmCriacaoRequest adm) {
        return ResponseEntity.status(HttpStatus.CREATED).body(admService.criarAdm(adm));
    }

    @GetMapping
    public ResponseEntity<List<AdmBuscaResponse>> listarAdms() {
        return ResponseEntity.status(HttpStatus.OK).body(admService.listarAdms());
    }

    @GetMapping("/{admId}")
    public ResponseEntity<AdmBuscaResponse> buscarAdmPorId(@PathVariable Long admId) {
        return ResponseEntity.status(HttpStatus.OK).body(admService.buscarAdmPorId(admId));
    }

    @GetMapping("/adm-logado")
    public ResponseEntity<AdmBuscaResponse> buscarAdmPorIdUsuario(@RequestHeader("AuthorizationUser") String token) {
        return ResponseEntity.status(HttpStatus.OK).body(admService.buscarAdmPorIdUsuario(token));
    }

    @DeleteMapping("/{admId}")
    public ResponseEntity<AdmExclusaoResponse> excluirAdmPorId(@PathVariable Long admId) {
        return ResponseEntity.status(HttpStatus.OK).body(admService.excluirAdmPorId(admId));
    }

    @PutMapping
    public ResponseEntity<AdmAlteracaoResponse> editarDadosAdm(@RequestBody @Valid AdmAlteracaoRequest request, @RequestHeader("AuthorizationUser") String token) {
        return ResponseEntity.status(HttpStatus.OK).body(admService.editarDadosAdm(token, request));
    }

    @GetMapping("/buscar-id-por-id-usuario/{idUsuario}")
    public ResponseEntity<Long> buscarIdPorIdUsuario(@PathVariable Long idUsuario) {
        var adm = repository.findByIdUsuario(idUsuario)
                .orElseThrow(() -> new EntityNotFoundException("Adm não encontrado"));
        return ResponseEntity.status(HttpStatus.OK).body(adm.getId());

    }
}
