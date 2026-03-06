package br.com.biblioteca.biblioteca.model;

import jakarta.persistence.*;

@Entity
@Table(name = "livros")
public class Livro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "titulo", nullable = false, length = 200)
    private String titulo;

    @Column(name = "autor", nullable = false, length = 150)
    private String autor;

    @Column(name = "genero", length = 100)
    private String genero;

    @Column(name = "ano_publicacao")
    private Integer anoPublicacao;

    @Column(name = "isbn", unique = true, length = 20)
    private String isbn;

    // Construtor vazio (obrigatório para o Spring)
    public Livro() {}

    // Construtor limpo (sem o usuário)
    public Livro(String titulo, String autor, String genero, Integer anoPublicacao, String isbn) {
        this.titulo = titulo;
        this.autor = autor;
        this.genero = genero;
        this.anoPublicacao = anoPublicacao;
        this.isbn = isbn;
    }

    // Apenas Getters e Setters do Livro
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getAutor() { return autor; }
    public void setAutor(String autor) { this.autor = autor; }

    public String getGenero() { return genero; }
    public void setGenero(String genero) { this.genero = genero; }

    public Integer getAnoPublicacao() { return anoPublicacao; }
    public void setAnoPublicacao(Integer anoPublicacao) { this.anoPublicacao = anoPublicacao; }

    public String getIsbn() { return isbn; }
    public void setIsbn(String isbn) { this.isbn = isbn; }
}