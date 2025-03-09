INSERT INTO produto (descricao, valor) VALUES ('Calça Jeans', 120.00);
INSERT INTO produto (descricao, valor) VALUES ('Tênis Esportivo', 150.00);
INSERT INTO produto (descricao, valor) VALUES ('Jaqueta de Couro', 250.00);
INSERT INTO produto (descricao, valor) VALUES ('Óculos de Sol', 80.00);
INSERT INTO produto (descricao, valor) VALUES ('Relógio de Pulso', 200.00);
INSERT INTO produto (descricao, valor) VALUES ('Bolsa de Mão', 180.00);
INSERT INTO produto (descricao, valor) VALUES ('Cinto de Couro', 70.00);
INSERT INTO produto (descricao, valor) VALUES ('Chapéu', 50.00);
INSERT INTO produto (descricao, valor) VALUES ('Meias', 20.00);
INSERT INTO produto (descricao, valor) VALUES ('Cachecol', 40.00);
INSERT INTO produto (descricao, valor) VALUES ('Luvas', 30.00);
INSERT INTO produto (descricao, valor) VALUES ('Carteira', 60.00);
INSERT INTO produto (descricao, valor) VALUES ('Mochila', 100.00);
INSERT INTO produto (descricao, valor) VALUES ('Mala de Viagem', 300.00);
INSERT INTO produto (descricao, valor) VALUES ('Fone de Ouvido', 90.00);
INSERT INTO produto (descricao, valor) VALUES ('Carregador Portátil', 70.00);
INSERT INTO produto (descricao, valor) VALUES ('Capa de Celular', 30.00);
INSERT INTO produto (descricao, valor) VALUES ('Livro', 40.00);
INSERT INTO produto (descricao, valor) VALUES ('Caderno', 20.00);
INSERT INTO produto (descricao, valor) VALUES ('Caneta', 10.00);

INSERT INTO pessoa (tipo, cpf, nome, telefone) VALUES ('F', '076.259.941-39', 'Pedro', '(63) 99297-3497');
INSERT INTO pessoa (tipo, cnpj, nome, telefone) VALUES ('J', '12.147.154.0001/11', 'Idea Sistemas', '(63) 2111-0400');

INSERT INTO venda (data, pessoa_id) VALUES ('2025-03-18', 1);
INSERT INTO venda (data, pessoa_id) VALUES ('2025-10-19', 2);

INSERT INTO item_venda (quantidade, produto_id, venda_id) VALUES (2, 1, 1);
INSERT INTO item_venda (quantidade, produto_id, venda_id) VALUES (1, 3, 1);
INSERT INTO item_venda (quantidade, produto_id, venda_id) VALUES (5, 2, 2);


INSERT INTO role (nome) VALUES ('ROLE_ADMIN'), ('ROLE_USER');

INSERT INTO usuario (login, password) VALUES ('admin', '$2a$10$iGIX0LYHa03eoXEs232fC.046eexCmjy5rRGXHAzPoPUm1yUJ67lC'); -- Senha: "admin"
INSERT INTO usuario (login, password) VALUES ('user', '$2a$10$iGIX0LYHa03eoXEs232fC.046eexCmjy5rRGXHAzPoPUm1yUJ67lC');  -- Senha: "123"
--UPDATE usuario SET PASSWORD = '$2a$10$ExemploDeSenhaCriptografada' WHERE login = 'admin'; --Senha "123

INSERT INTO USUARIO_ROLES (USUARIOS_ID, ROLES_ID) VALUES (1, 1); -- Admin recebe papel ADMIN
INSERT INTO USUARIO_ROLES (USUARIOS_ID, ROLES_ID) VALUES (2, 2); -- User recebe papel USER

