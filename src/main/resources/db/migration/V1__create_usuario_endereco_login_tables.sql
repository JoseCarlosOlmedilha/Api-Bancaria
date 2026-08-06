CREATE TABLE usuario (
    id BIGINT NOT NULL AUTO_INCREMENT,
    nome VARCHAR(255),
    email VARCHAR(255),
    cpf VARCHAR(255),
    telefone VARCHAR(255),
    data_nascimento VARCHAR(255),
    sexo VARCHAR(255),
    status_usuario VARCHAR(255),
    PRIMARY KEY (id),
    UNIQUE KEY uk_usuario_email (email),
    UNIQUE KEY uk_usuario_cpf (cpf)
);

CREATE TABLE login (
    id BIGINT NOT NULL AUTO_INCREMENT,
    username VARCHAR(255),
    password VARCHAR(255),
    usuario_id BIGINT,
    PRIMARY KEY (id),
    UNIQUE KEY uk_login_username (username),
    UNIQUE KEY uk_login_usuario_id (usuario_id),
    CONSTRAINT fk_login_usuario FOREIGN KEY (usuario_id) REFERENCES usuario (id)
);

CREATE TABLE endereco (
    id BIGINT NOT NULL AUTO_INCREMENT,
    rua VARCHAR(255),
    numero VARCHAR(255),
    bairro VARCHAR(255),
    cidade VARCHAR(255),
    cep VARCHAR(255),
    usuario_id BIGINT,
    PRIMARY KEY (id),
    UNIQUE KEY uk_endereco_usuario_id (usuario_id),
    CONSTRAINT fk_endereco_usuario FOREIGN KEY (usuario_id) REFERENCES usuario (id)
);
