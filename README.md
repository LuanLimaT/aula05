📦 Sistema de Gestão de Vendas

Este projeto é um sistema web para gerenciamento de vendas, em virtudade da Disciplina : *Programação para Web 2*, desenvolvido com Spring Boot, Spring Security, JPA, Thymeleaf e MySQL. Ele permite o cadastro de usuários, autenticação, gerenciamento de clientes (Pessoa Física e Jurídica), produtos, vendas e itens de venda.

🛠️ Tecnologias Utilizadas

Java 17

Spring Boot (MVC, Security, Data JPA)

Hibernate

MySQL

Thymeleaf

Lombok

Maven

🔥 Funcionalidades

✅ Autenticação e autorização com Spring Security e roles (USER e ADMIN)
✅ Cadastro e listagem de clientes (Pessoa Física e Jurídica)
✅ Cadastro, listagem e gerenciamento de produtos
✅ Registro de vendas, vinculando clientes e produtos
✅ Persistência de dados com JPA e MySQL

📂 Estrutura do Projeto

📦 src/main/java/com/example/aula03
 ├── config/               # Configuração do Spring Security e Interceptors
 ├── controller/           # Controladores para lidar com requisições HTTP
 ├── model/entity/         # Entidades JPA (Usuario, Pessoa, Produto, Venda, etc.)
 ├── repository/           # Interfaces de acesso ao banco de dados (JPA Repository)
 ├── service/              # Regras de negócio e lógica do sistema
 ├── view/                 # Templates Thymeleaf para exibição dos dados

🔑 Segurança e Controle de Acesso

A segurança é implementada com Spring Security, onde:

Usuários são cadastrados com BCryptPasswordEncoder.

Permissões são gerenciadas via roles (ROLE_USER e ROLE_ADMIN).

Interceptores verificam acessos a determinadas rotas.

Exemplo de credenciais padrão:

Login

Senha

Permissão

admin

admin

ROLE_ADMIN

user

user

ROLE_USER

🏗️ Configuração e Execução

1️⃣ Clonar o repositório

git clone https://github.com/seu-usuario/seu-repositorio.git
cd seu-repositorio

2️⃣ Configurar o Banco de Dados

Criar um banco no MySQL:

CREATE DATABASE gestao_vendas;

Configurar o arquivo application.properties:

spring.datasource.url=jdbc:mysql://localhost:3306/gestao_vendas
spring.datasource.username=root
spring.datasource.password=12345
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

3️⃣ Executar o projeto

mvn spring-boot:run

Acesse o sistema no navegador: http://localhost:8080

📜 Scripts SQL de Exemplo

Para popular o banco de dados, use o seguinte script:

INSERT INTO produto (descricao, valor) VALUES ('Camisa Preta', 90.00);
INSERT INTO pessoa (tipo, cpf, nome, telefone) VALUES ('F', '076.259.941-39', 'Pedro', '(63) 99297-3497');
INSERT INTO venda (data, pessoa_id) VALUES ('2024-03-18', 1);

🤝 Contribuição

Faça um fork do repositório.

Crie uma branch com sua feature: git checkout -b minha-feature

Commit suas mudanças: git commit -m 'Adiciona nova funcionalidade'

Envie para o repositório remoto: git push origin minha-feature

Abra um Pull Request.

📜 Licença

Este projeto está sob a licença MIT. Sinta-se à vontade para usá-lo e melhorá-lo! 🚀

