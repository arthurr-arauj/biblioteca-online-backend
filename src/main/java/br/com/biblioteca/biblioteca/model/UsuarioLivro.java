package br.com.biblioteca.biblioteca.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "usuario_livro")
public class UsuarioLivro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Relacionamento com o Usuário
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    // Relacionamento com o Livro
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "livro_id", nullable = false)
    private Livro livro;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 30)
    private StatusLeitura status;

    @Column(name = "avaliacao")
    private Integer avaliacao;

    @Column(name = "data_inicio")
    private LocalDate dataInicio;

    @Column(name = "data_fim")
    private LocalDate dataFim;

    @Column(name = "resenha", length = 500)
    private String resenha;

    // Construtor vazio obrigatório
    public UsuarioLivro() {}

    // Construtor com parâmetros
    public UsuarioLivro(Usuario usuario, Livro livro, StatusLeitura status) {
        this.usuario = usuario;
        this.livro = livro;
        this.status = status;
    }

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }

    public Livro getLivro() { return livro; }
    public void setLivro(Livro livro) { this.livro = livro; }

    public StatusLeitura getStatus() { return status; }
    public void setStatus(StatusLeitura status) { this.status = status; }

    public Integer getAvaliacao() { return avaliacao; }
    public void setAvaliacao(Integer avaliacao) { this.avaliacao = avaliacao; }

    public LocalDate getDataInicio() { return dataInicio; }
    public void setDataInicio(LocalDate dataInicio) { this.dataInicio = dataInicio; }

    public LocalDate getDataFim() { return dataFim; }
    public void setDataFim(LocalDate dataFim) { this.dataFim = dataFim; }

    public String getResenha() { return resenha; }
    public void setResenha(String resenha) { this.resenha = resenha; }
}