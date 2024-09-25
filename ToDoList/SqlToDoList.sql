CREATE DATABASE todolist;
USE todolist;
CREATE TABLE standard (
	id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(50) NOT NULL,
    sobrenome VARCHAR(50) NOT NULL,
    email VARCHAR (50) NOT NULL,
    senha VARCHAR (10) NOT NULL
);

CREATE TABLE premium (
	id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    fk_id_standard INT NOT NULL,
    FOREIGN KEY (fk_id_standard) REFERENCES standard (id)
);

CREATE TABLE tarefas (
	id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(50),
    descricao VARCHAR(100),
    is_finalizada BOOLEAN DEFAULT FALSE, 
    fk_id_standard INT,
    fk_id_premium INT DEFAULT NULL,
    FOREIGN KEY (fk_id_standard) REFERENCES standard (id),
    FOREIGN KEY (fk_id_premium) REFERENCES premium (fk_id_standard)
);

CREATE TABLE pagamentos (
	id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    valor DOUBLE NOT NULL,
    data_pagamento DATE NOT NULL,
    fk_id INT NOT NULL,
    FOREIGN KEY (fk_id) REFERENCES premium (fk_id_standard)
);

INSERT INTO premium (fk_id_standard) values (5);

DELETE FROM standard WHERE id >= 5;
DELETE FROM premium WHERE fk_id_standard >= 5;
DELETE FROM pagamentos WHERE fk_id >= 6;
DELETE FROM tarefas WHERE tarefas.id >= 5;

SELECT * FROM standard;
SELECT * FROM premium;
SELECT * FROM pagamentos;
SELECT * FROM tarefas;


INSERT INTO standard (
	nome, sobrenome, email, senha
) VALUES (
	"Bruno", "Zambuze", "bruno@hotmail.com", "13022004"
);

INSERT INTO standard (
	nome, sobrenome, email, senha
) VALUES (
	"Marcelo", "Santana", "marcelo@hotmail.com", "123456789"
);

INSERT INTO premium (
	fk_id_standard
) VALUES (
	6
);

INSERT INTO pagamentos (
	valor, data_pagamento, fk_id
) VALUES (
	19.90, '2024-10-09', 6
);

INSERT INTO tarefas (
	nome, descricao, fk_id_standard
) VALUES (
	"Lavar a louça", "preciso lavar a louça", 3
);

INSERT INTO tarefas (
	nome, descricao, fk_id_premium
) VALUES (
	"Corrigir as provas", "preciso corrigir as provas", 4
);

SELECT tarefas.id, tarefas.nome, tarefas.descricao, tarefas.is_finalizada FROM tarefas INNER JOIN premium ON 
tarefas.fk_id_premium = premium.fk_id_standard;

SELECT tarefas.id, tarefas.nome, tarefas.descricao, tarefas.is_finalizada FROM tarefas INNER JOIN standard ON
tarefas.fk_id_standard = standard.id;

SELECT standard.nome, standard.sobrenome, standard.email, standard.senha FROM standard INNER JOIN premium ON premium.fk_id_standard = standard.id WHERE standard.id = 51;

SELECT pagamentos.valor, pagamentos.data_pagamento FROM pagamentos INNER JOIN premium ON pagamentos.fk_id = premium.fk_id_standard WHERE pagamentos.fk_id = 6;

SELECT tarefas.nome, tarefas.descricao, tarefas.is_finalizada FROM tarefas INNER JOIN standard ON tarefas.fk_id_standard = standard.id WHERE standard.id = 58;

DELETE FROM tarefas WHERE id = 14;

UPDATE tarefas SET is_finalizada = true WHERE id = 20;

SELECT*FROM tarefas;

INSERT INTO tarefas (nome, descricao, fk_id_standard, fk_id_premium) VALUES ("teste", "teste", null, 72);