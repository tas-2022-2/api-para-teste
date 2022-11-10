CREATE TABLE pessoas (
	id   UUID         NOT NULL PRIMARY KEY,
	cpf  VARCHAR(11)  NOT NULL UNIQUE,
	nome VARCHAR(100) NOT NULL
);

CREATE TABLE doacoes (
  id         INTEGER      NOT NULL PRIMARY KEY AUTO_INCREMENT,
	pessoas_id UUID         NOT NULL REFERENCES pessoas(id),
	categoria  INTEGER      NOT NULL,
	descricao  VARCHAR(100) NOT NULL,
	situacao   INTEGER      NOT NULL
);