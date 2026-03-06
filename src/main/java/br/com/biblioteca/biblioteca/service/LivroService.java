package br.com.biblioteca.biblioteca.service;

import br.com.biblioteca.biblioteca.model.Livro;
import br.com.biblioteca.biblioteca.repository.LivroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class LivroService {

    @Autowired
    private LivroRepository livroRepository;

    // O livro agora é apenas cadastrado no catálogo geral
    public Livro cadastrarLivro(Livro livro) {
        return livroRepository.save(livro);
    }

    public Livro buscarLivro(Long id) {
        return livroRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Livro não encontrado com ID: " + id));
    }

    public List<Livro> listarTodosLivros() {
        return livroRepository.findAll();
    }

    public Livro atualizarLivro(Long id, Livro livro) {
        Livro livroExistente = buscarLivro(id);
        
        livroExistente.setTitulo(livro.getTitulo());
        livroExistente.setAutor(livro.getAutor());
        livroExistente.setGenero(livro.getGenero());
        livroExistente.setAnoPublicacao(livro.getAnoPublicacao());
        livroExistente.setIsbn(livro.getIsbn());
        
        return livroRepository.save(livroExistente);
    }

    public void deletarLivro(Long id) {
        Livro livro = buscarLivro(id);
        livroRepository.delete(livro);
    }
}