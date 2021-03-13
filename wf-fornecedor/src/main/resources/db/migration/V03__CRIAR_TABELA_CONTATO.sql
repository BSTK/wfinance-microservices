CREATE TABLE IF NOT EXISTS CONTATO (
    ID              SERIAL       PRIMARY KEY,
    FORNECEDOR_ID   BIGINT       NOT NULL,
    NOME            VARCHAR(100) NOT NULL,
    EMAIL           VARCHAR(100) NOT NULL,
    TELEFONE        VARCHAR(15)  NOT NULL,
    FOREIGN KEY (FORNECEDOR_ID) REFERENCES FORNECEDOR (ID)
);

INSERT INTO CONTATO(ID, FORNECEDOR_ID, NOME, EMAIL, TELEFONE)
VALUES (1, 4, 'Pedro Barbosa', 'pedro.barbosa@gmail.com', '(11) 96655-9988');

INSERT INTO CONTATO(ID, FORNECEDOR_ID, NOME, EMAIL, TELEFONE)
VALUES (2, 4, 'Maria Barbosa', 'maria.barbosa@gmail.com', '(11) 96655-7788');

INSERT INTO CONTATO(ID, FORNECEDOR_ID, NOME, EMAIL, TELEFONE)
VALUES (3, 4, 'Rui Pessoa', 'rui.pesso@gmail.com', '(11) 96688-9988');

INSERT INTO CONTATO(ID, FORNECEDOR_ID, NOME, EMAIL, TELEFONE)
VALUES (4, 4, 'Fernando Tatu', 'fernando.tatu@gmail.com', '(11) 99955-9988');
