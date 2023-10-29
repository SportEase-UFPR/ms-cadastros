package br.ufpr.mscadastros.controller;

import br.ufpr.mscadastros.model.dto.esporte.EsporteExclusaoResponse;
import br.ufpr.mscadastros.model.dto.esporte.EsporteRequest;
import br.ufpr.mscadastros.model.dto.esporte.EsporteResponse;
import br.ufpr.mscadastros.service.EsporteService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins ="*")
@RestController
@RequestMapping("/esportes")
public class EsporteController {

    private final EsporteService esporteService;

    public EsporteController(EsporteService esporteService) {
        this.esporteService = esporteService;
    }

    @PostMapping
    public ResponseEntity<EsporteResponse> criarEsporte(@Valid @RequestBody EsporteRequest esporte) {
        return ResponseEntity.status(HttpStatus.CREATED).body(esporteService.criarEsporte(esporte));
    }

    @GetMapping
    public ResponseEntity<List<EsporteResponse>> listarEsportes() {
        return ResponseEntity.status(HttpStatus.OK).body(esporteService.listarEsportes());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<EsporteExclusaoResponse> excluirEsporte(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(esporteService.excluirEsporte(id));
    }

}
