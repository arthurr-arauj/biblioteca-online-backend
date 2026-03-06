package br.com.biblioteca.biblioteca.service;

import br.com.biblioteca.biblioteca.model.Usuario;
import br.com.biblioteca.biblioteca.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public Usuario criarUsuario(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    public Usuario buscarUsuario(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado com ID: " + id));
    }

    public List<Usuario> listarUsuarios() {
        return usuarioRepository.findAll();
    }

    public Usuario atualizarUsuario(Long id, Usuario usuario) {
        Usuario usuarioExistente = buscarUsuario(id);
        usuarioExistente.setNome(usuario.getNome());
        return usuarioRepository.save(usuarioExistente);
    }

    public void deletarUsuario(Long id) {
        Usuario usuario = buscarUsuario(id);
        usuarioRepository.delete(usuario);
    }
}
