package br.com.biblioteca.biblioteca.repository;

import br.com.biblioteca.biblioteca.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    @Query("SELECT l FROM Livro l WHERE l.usuario.id = :usuarioId")
    List<Object> findLivrosByUsuarioId(Long usuarioId);
}
