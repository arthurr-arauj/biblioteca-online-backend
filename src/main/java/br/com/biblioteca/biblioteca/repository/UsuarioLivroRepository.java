package br.com.biblioteca.biblioteca.repository;

import br.com.biblioteca.biblioteca.model.UsuarioLivro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioLivroRepository extends JpaRepository<UsuarioLivro, Long> {
    List<UsuarioLivro> findByUsuarioId(Long usuarioId);

    Optional<UsuarioLivro> findByUsuarioIdAndLivroId(Long usuarioId, Long livroId);
}

