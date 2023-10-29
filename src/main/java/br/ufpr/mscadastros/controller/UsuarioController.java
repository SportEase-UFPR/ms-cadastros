package br.ufpr.mscadastros.controller;

import br.ufpr.mscadastros.model.dto.usuario.*;
import br.ufpr.mscadastros.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins ="*")
@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping("/email-recuperacao-senha")
    public ResponseEntity<RecuperarSenhaResponse> enviarEmailRecuperacaoSenha(@RequestBody @Valid RecuperarSenhaRequest recuperarSenhaRequest) {
        return ResponseEntity.status(HttpStatus.OK).body(usuarioService.enviarEmailRecuperacaoSenha(recuperarSenhaRequest.getEmail()));
    }

}

