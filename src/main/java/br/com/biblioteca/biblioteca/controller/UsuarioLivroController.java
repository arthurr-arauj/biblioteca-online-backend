package br.com.biblioteca.biblioteca.controller;

import br.com.biblioteca.biblioteca.dto.UsuarioLivroCreateRequest;
import br.com.biblioteca.biblioteca.dto.UsuarioLivroResponse;
import br.com.biblioteca.biblioteca.dto.UsuarioLivroUpdateRequest;
import br.com.biblioteca.biblioteca.service.UsuarioLivroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios/{usuarioId}/estante")
public class UsuarioLivroController {

    @Autowired
    private UsuarioLivroService usuarioLivroService;

    @PostMapping
    public ResponseEntity<UsuarioLivroResponse> adicionarLivro(@PathVariable Long usuarioId, @RequestBody UsuarioLivroCreateRequest request) {
        UsuarioLivroResponse criado = usuarioLivroService.adicionarLivroNaEstante(usuarioId, request);
        return ResponseEntity.ok(criado);
    }

    @GetMapping
    public ResponseEntity<List<UsuarioLivroResponse>> listarEstante(@PathVariable Long usuarioId) {
        List<UsuarioLivroResponse> estante = usuarioLivroService.listarEstanteDoUsuario(usuarioId);
        return ResponseEntity.ok(estante);
    }

    @PutMapping("/{usuarioLivroId}")
    public ResponseEntity<UsuarioLivroResponse> atualizarItem(
            @PathVariable Long usuarioId,
            @PathVariable Long usuarioLivroId,
            @RequestBody UsuarioLivroUpdateRequest request
    ) {
        UsuarioLivroResponse atualizado = usuarioLivroService.atualizarItemDaEstante(usuarioId, usuarioLivroId, request);
        return ResponseEntity.ok(atualizado);
    }

    @DeleteMapping("/{usuarioLivroId}")
    public ResponseEntity<Void> removerItem(@PathVariable Long usuarioId, @PathVariable Long usuarioLivroId) {
        usuarioLivroService.removerItemDaEstante(usuarioId, usuarioLivroId);
        return ResponseEntity.noContent().build();
    }
}

