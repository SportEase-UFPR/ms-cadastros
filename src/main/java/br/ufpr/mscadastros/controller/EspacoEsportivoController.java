package br.ufpr.mscadastros.controller;

import br.ufpr.mscadastros.model.dto.espaco_esportivo.*;
import br.ufpr.mscadastros.model.dto.locacao.InformacoesComplementaresLocacaoRequest;
import br.ufpr.mscadastros.model.dto.locacao.InformacoesComplementaresLocacaoResponse;
import br.ufpr.mscadastros.repository.EspacoEsportivoRepository;
import br.ufpr.mscadastros.security.TokenService;
import br.ufpr.mscadastros.service.EspacoEsportivoService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins ="*")
@RestController
@RequestMapping("/espacos-esportivos")
@Slf4j
public class EspacoEsportivoController {

    private final EspacoEsportivoService espacoEsportivoService;
    private final TokenService tokenService;
    private final EspacoEsportivoRepository repository;

    public EspacoEsportivoController(EspacoEsportivoService espacoEsportivoService, TokenService tokenService, EspacoEsportivoRepository repository) {
        this.espacoEsportivoService = espacoEsportivoService;
        this.tokenService = tokenService;
        this.repository = repository;
    }

    @PostMapping
    public ResponseEntity<EspEsportivoCriacaoResponse> criarEspacoEsportivo(@Valid @RequestBody EspEsportivoCriacaoRequest ee) {
        return ResponseEntity.status(HttpStatus.CREATED).body(espacoEsportivoService.criarEspacoEsportivo(ee));
    }

    @GetMapping
    public ResponseEntity<List<EspEsportivoBuscaResponse>> listarEspacosEsportivos() {
        return ResponseEntity.status(HttpStatus.OK).body(espacoEsportivoService.listarEspacosEsportivos());
    }

    @GetMapping("/disponiveis")
    public ResponseEntity<List<EspEsportivoReservaResponse>> listarEspacosEsportivosDisponiveis() {
        return ResponseEntity.status(HttpStatus.OK).body(espacoEsportivoService.listarEspacosEsportivosDisponiveis());
    }

    @GetMapping("/{espEsportivoId}")
    public ResponseEntity<EspEsportivoBuscaResponse> buscarEspacoEsportivoPorId(@PathVariable Long espEsportivoId) {
        return ResponseEntity.status(HttpStatus.OK).body(espacoEsportivoService.buscarEspacoEsportivoPorId(espEsportivoId));
    }

    @DeleteMapping("/{espEsportivoId}")
    public ResponseEntity<EspEsportivoExclusaoResponse> excluirEspacoEsportivoPorId(@PathVariable Long espEsportivoId) {
        return ResponseEntity.status(HttpStatus.OK).body(espacoEsportivoService.excluirEspacoEsportivoPorId(espEsportivoId));
    }

    @PutMapping("/{espEsportivoId}")
    public ResponseEntity<EspEsportivoAlteracaoResponse> editarDadosEspacoEsportivo(@RequestBody @Valid EspEsportivoAlteracaoRequest request, @PathVariable Long espEsportivoId) {
        return ResponseEntity.status(HttpStatus.OK).body(espacoEsportivoService.editarEspacoEsportivo(espEsportivoId, request));
    }

    @GetMapping("/existe-por-id/{espEsportivoId}")
    public ResponseEntity<Boolean> existeEEPorId(@PathVariable Long espEsportivoId,
                                                 @RequestHeader("AuthorizationApi") String token) {
        //validar token
        tokenService.validarTokenMs(token);
        return ResponseEntity.status(HttpStatus.OK).body(espacoEsportivoService.existeEEPorId(espEsportivoId));
    }

    @PostMapping("/buscar-inf-complementares-locacao")
    public ResponseEntity<List<InformacoesComplementaresLocacaoResponse>>
    buscarInformacoesComplementaresLocacao(@RequestBody List<InformacoesComplementaresLocacaoRequest> request,
                                           @RequestHeader("AuthorizationApi") String token) {
        //validar token
        tokenService.validarTokenMs(token);
        return ResponseEntity.status(HttpStatus.OK).body(espacoEsportivoService.buscarInformacoesComplementaresLocacao(request));
    }

    @PutMapping("/atualizar-media-avaliacao/{idEspacoEsportivo}")
    public ResponseEntity<Void> atualizarMediaAvaliacaoEspacoEsportivo(@PathVariable Long idEspacoEsportivo,
                                                                       @RequestBody AtualizarMediaAvaliacaoEERequest request,
                                                                       @RequestHeader("AuthorizationApi") String token) {
        tokenService.validarTokenMs(token);
        return ResponseEntity.status(HttpStatus.OK).body(espacoEsportivoService.atualizarMediaAvaliacaoEspacoEsportivo(idEspacoEsportivo, request));
    }

    @PostMapping("/buscar-lista-ee-simplificado")
    public ResponseEntity<List<EspacoEsportivoSimplificado>> buscarEspacoesEsportivosSimplificado(@RequestBody List<Long> request,
                                                                         @RequestHeader("AuthorizationApi") String token) {
        tokenService.validarTokenMs(token);
        return ResponseEntity.status(HttpStatus.OK).body(espacoEsportivoService.buscarEspacoesEsportivosSimplificado(request));
    }
}
