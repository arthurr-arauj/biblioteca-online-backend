package br.com.biblioteca.biblioteca.service;

import br.com.biblioteca.biblioteca.model.Livro;
import br.com.biblioteca.biblioteca.model.Usuario;
import br.com.biblioteca.biblioteca.repository.LivroRepository;
import br.com.biblioteca.biblioteca.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LivroService {

    @Autowired
    private LivroRepository livroRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public Livro cadastrarLivro(Livro livro) {
        if (livro.getUsuario() != null && livro.getUsuario().getId() != null) {
            Usuario usuario = usuarioRepository.findById(livro.getUsuario().getId())
                    .orElseThrow(() -> new RuntimeException("Usuário não encontrado com ID: " + livro.getUsuario().getId()));
            livro.setUsuario(usuario);
        }
        return livroRepository.save(livro);
    }

    public Livro buscarLivro(Long id) {
        return livroRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Livro não encontrado com ID: " + id));
    }

    public List<Livro> listarTodosLivros() {
        return livroRepository.findAll();
    }

    public List<Livro> buscarLivrosPorUsuario(Long usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado com ID: " + usuarioId));
        return livroRepository.findByUsuarioId(usuarioId);
    }

    public Livro atualizarLivro(Long id, Livro livro) {
        Livro livroExistente = buscarLivro(id);
        
        livroExistente.setTitulo(livro.getTitulo());
        livroExistente.setAutor(livro.getAutor());
        livroExistente.setGenero(livro.getGenero());
        livroExistente.setAnoPublicacao(livro.getAnoPublicacao());
        livroExistente.setIsbn(livro.getIsbn());
        
        if (livro.getUsuario() != null && livro.getUsuario().getId() != null) {
            Usuario usuario = usuarioRepository.findById(livro.getUsuario().getId())
                    .orElseThrow(() -> new RuntimeException("Usuário não encontrado com ID: " + livro.getUsuario().getId()));
            livroExistente.setUsuario(usuario);
        }
        
        return livroRepository.save(livroExistente);
    }

    public void deletarLivro(Long id) {
        Livro livro = buscarLivro(id);
        livroRepository.delete(livro);
    }
}
