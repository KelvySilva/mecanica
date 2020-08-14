#Api de Mecânica

### Projeto de exemplo para API de sistema de gerenciamento de mecânica.

###**Rodando a API**

1. Faça o clone do projeto:
`git clone https://github.com/KelvySilva/mecanica`

2. Navegue até a raiz do projeto, onde haverá um arquivo chamado **docker-compose.yml** e execute o comando pelo cmd:
`docker-compose up -d` 
isso criara um container no docker rodando um postgres pré-configurado para conexão com a aplicação.

3. Baixe as dependências do projeto utilizando o MAVEN com o comenado:
`mvn clean install` ou use sua IDE pra que ela execute o procedimento.

4. Agora use a ide para rodar a aplicação

### Dicas
Começe cadastrando *funcionários* antes de tudo, e depois cadastre *cliente* antes de cadastrar um *veículo*, e assim
poderá dar sequencia com o cadastro das *ordens de serviço* e por fim *orçamentos*   


