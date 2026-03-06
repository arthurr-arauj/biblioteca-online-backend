package br.com.biblioteca.biblioteca.service;

import br.com.biblioteca.biblioteca.dto.UsuarioLivroCreateRequest;
import br.com.biblioteca.biblioteca.dto.UsuarioLivroResponse;
import br.com.biblioteca.biblioteca.dto.UsuarioLivroUpdateRequest;
import br.com.biblioteca.biblioteca.model.Livro;
import br.com.biblioteca.biblioteca.model.StatusLeitura;
import br.com.biblioteca.biblioteca.model.Usuario;
import br.com.biblioteca.biblioteca.model.UsuarioLivro;
import br.com.biblioteca.biblioteca.repository.LivroRepository;
import br.com.biblioteca.biblioteca.repository.UsuarioLivroRepository;
import br.com.biblioteca.biblioteca.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class UsuarioLivroService {

    @Autowired
    private UsuarioLivroRepository usuarioLivroRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private LivroRepository livroRepository;

    public UsuarioLivroResponse adicionarLivroNaEstante(Long usuarioId, UsuarioLivroCreateRequest request) {
        if (request == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Corpo da requisição é obrigatório");
        }
        if (request.getLivroId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "livroId é obrigatório");
        }
        StatusLeitura status = request.getStatus() != null ? request.getStatus() : StatusLeitura.QUERO_LER;
        validarAvaliacao(request.getAvaliacao());

        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado com ID: " + usuarioId));
        Livro livro = livroRepository.findById(request.getLivroId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Livro não encontrado com ID: " + request.getLivroId()));

        UsuarioLivro usuarioLivro = new UsuarioLivro(usuario, livro, status);
        usuarioLivro.setAvaliacao(request.getAvaliacao());
        usuarioLivro.setDataInicio(request.getDataInicio());
        usuarioLivro.setDataFim(request.getDataFim());
        usuarioLivro.setResenha(truncarResenha(request.getResenha()));

        UsuarioLivro salvo = usuarioLivroRepository.save(usuarioLivro);
        return toResponse(salvo);
    }

    public List<UsuarioLivroResponse> listarEstanteDoUsuario(Long usuarioId) {
        // garante 404 se usuário não existir
        usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado com ID: " + usuarioId));

        return usuarioLivroRepository.findByUsuarioId(usuarioId).stream()
                .map(this::toResponse)
                .toList();
    }

    public UsuarioLivroResponse atualizarItemDaEstante(Long usuarioId, Long usuarioLivroId, UsuarioLivroUpdateRequest request) {
        if (request == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Corpo da requisição é obrigatório");
        }
        validarAvaliacao(request.getAvaliacao());

        UsuarioLivro usuarioLivro = usuarioLivroRepository.findById(usuarioLivroId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Item de estante não encontrado com ID: " + usuarioLivroId));

        if (usuarioLivro.getUsuario() == null || usuarioLivro.getUsuario().getId() == null || !usuarioLivro.getUsuario().getId().equals(usuarioId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Item de estante não encontrado para este usuário");
        }

        if (request.getStatus() != null) {
            usuarioLivro.setStatus(request.getStatus());
        }
        if (request.getAvaliacao() != null) {
            usuarioLivro.setAvaliacao(request.getAvaliacao());
        }
        if (request.getDataInicio() != null) {
            usuarioLivro.setDataInicio(request.getDataInicio());
        }
        if (request.getDataFim() != null) {
            usuarioLivro.setDataFim(request.getDataFim());
        }
        if (request.getResenha() != null) {
            usuarioLivro.setResenha(truncarResenha(request.getResenha()));
        }

        UsuarioLivro salvo = usuarioLivroRepository.save(usuarioLivro);
        return toResponse(salvo);
    }

    public void removerItemDaEstante(Long usuarioId, Long usuarioLivroId) {
        UsuarioLivro usuarioLivro = usuarioLivroRepository.findById(usuarioLivroId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Item de estante não encontrado com ID: " + usuarioLivroId));

        if (usuarioLivro.getUsuario() == null || usuarioLivro.getUsuario().getId() == null || !usuarioLivro.getUsuario().getId().equals(usuarioId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Item de estante não encontrado para este usuário");
        }

        usuarioLivroRepository.delete(usuarioLivro);
    }

    private void validarAvaliacao(Integer avaliacao) {
        if (avaliacao == null) return;
        if (avaliacao < 0 || avaliacao > 5) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "avaliacao deve ser entre 0 e 5");
        }
    }

    private String truncarResenha(String resenha) {
        if (resenha == null) return null;
        String r = resenha.trim();
        if (r.isEmpty()) return null;
        if (r.length() <= 500) return r;
        return r.substring(0, 500);
    }

    private UsuarioLivroResponse toResponse(UsuarioLivro entity) {
        UsuarioLivroResponse r = new UsuarioLivroResponse();
        r.setId(entity.getId());

        if (entity.getUsuario() != null) {
            r.setUsuarioId(entity.getUsuario().getId());
        }
        if (entity.getLivro() != null) {
            r.setLivroId(entity.getLivro().getId());
            r.setLivroTitulo(entity.getLivro().getTitulo());
            r.setLivroAutor(entity.getLivro().getAutor());
        }

        r.setStatus(entity.getStatus());
        r.setAvaliacao(entity.getAvaliacao());
        r.setDataInicio(entity.getDataInicio());
        r.setDataFim(entity.getDataFim());
        r.setResenha(entity.getResenha());
        return r;
    }
}

