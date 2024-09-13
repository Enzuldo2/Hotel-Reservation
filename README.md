# Reserva de quartos de Hotel
### Como Rodar o Programa para teste na maquina
Baixe o Xamp para iniciar o BD. baixe em [https://www.apachefriends.org/pt_br/download.html](https://www.apachefriends.org/pt_br/download.html).
usando como MySQL como Banco de dados, usando a ferramenta XAMP para gerir o banco, local do BD : [http://localhost/phpmyadmin/index.php?route=/sql&db=hotel&table=client&pos=0](http://localhost/phpmyadmin/index.php?route=/database).
Va para o link do BD após dar Start no Apache e MySQL. Crie um novo sistema dando New na esquerda superior e  crie as tabelas como estão no arquivo de DAO.
IMPORTANTE, o sistema é feito utilizando o id como chave primária e ela é auto Incrementada pelo próprio BD, ATIVE ESSA OPÇÃO NA HORA DE CRIAR A TABELA.
depois de criar as tabelas necessárias no BD, você pode rodar o programa e ele funcionara perfeitamente.
### Sobre
usando como MySQL como Banco de dados, usando a ferramenta XAMP para gerir o banco, local do BD : [http://localhost/phpmyadmin/index.php?route=/sql&db=hotel&table=client&pos=0](http://localhost/phpmyadmin/index.php?route=/database)
O sistema manda email avisando que a reserva foi feita com sucesso, ou que uma vaga que o cliente queria esta disponivel.
Estamos utilizando Padrões de Projeto para esse sistema.
Código tem 3 interfaces, uma para cliente não logado, uma para cliente logado e uma pro Admin.
Admin seria a pessoa que fica no balcão do hotel, sistema extremamente simples de coordenar e consegue falcimente gerir um hotel.

### Desing Patterns
- Factory
- Bridge
- Observer
- Singleton

### Architecture
- Arquitetura MVC

### SonarGraph
- Manitence Level = 81,71.
- Propagation cost = 28,13.
- Lines of Code = 2400.
- Cycle groups = 2.
- Maior ciclo de 4 elementos (Packege View).
- Complex Code = 1,99.
- Relative Entanglement = 6,99.

### Gmail Api
- Na classe GmailService estou logando na minha conta do gmail feita para enviar email aos clientes.
- Você deve criar sua própria API para mandar os emails, caso não queira não tem problema o código funciona sem.

### Conceitos
- Associação Binaria com Navegabilidade
    - Relação de dependência.
    - A classe na qual sai a seta poderá disparar metodos em objetos da classe que receberá a seta.
    - A reciproca não é verdadeira.

### Diagrama de classes
Esta no arquivo Astah-UML em imagens_projeto
porem segue foto do diagrama:
[Foto do diagrama](https://github.com/Enzuldo2/Hotel-Reservation/blob/main/Imagens_projeto/diagrama_atual.jpeg)



