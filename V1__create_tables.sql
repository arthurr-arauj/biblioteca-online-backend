CREATE TABLE usuarios (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(150) NOT NULL
);

CREATE TABLE livros (
    id SERIAL PRIMARY KEY,
    titulo VARCHAR(200) NOT NULL,
    autor VARCHAR(150) NOT NULL,
    genero VARCHAR(100),
    ano_publicacao INTEGER,
    isbn VARCHAR(20) UNIQUE,
);

CREATE TABLE usuario_livro (
    id SERIAL PRIMARY KEY,
    usuario_id INTEGER NOT NULL,
    livro_id INTEGER NOT NULL,
    status VARCHAR(30) NOT NULL,
    avaliacao INTEGER,
    data_inicio DATE,
    data_fim DATE,

    CONSTRAINT fk_usuario_livro_usuario

    CONSTRAINT fk_usuario_livro_livro
        FOREIGN KEY (livro_id)
        REFERENCES livros(id)
);
