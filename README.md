# Reserva de quartos de Hotel
### Como Rodar o Programa para teste na maquina
Baixe o Xamp para iniciar o BD. baixe em [https://www.apachefriends.org/pt_br/download.html](https://www.apachefriends.org/pt_br/download.html).
usando como MySQL como Banco de dados, usando a ferramenta XAMP para gerir o banco, local do BD : [http://localhost/phpmyadmin/index.php?route=/sql&db=hotel&table=client&pos=0](http://localhost/phpmyadmin/index.php?route=/database).
Va para o link do BD após dar Start no Apache e MySQL. e crie as tabelas como estão no arquivo de DAO.
IMPORTANTE, o sistema é feito utilizando o id como chave primária e ela é auto Incrementada pelo próprio BD, ATIVE ESSA OPÇÃO NA HORA DE CRIAR A TABELA.
depois de criar as tabelas necessárias no BD, você pode rodar o programa e ele funcionara perfeitamente.
### Sobre
usando como MySQL como Banco de dados, usando a ferramenta XAMP para gerir o banco, local do BD : [http://localhost/phpmyadmin/index.php?route=/sql&db=hotel&table=client&pos=0](http://localhost/phpmyadmin/index.php?route=/database)

Estamos utilizando Padrões de Projeto para esse sistema, Como Factory, Singleton , Observer e Brigde. alem de uma Arquitetura MVC.
onde o packege controller seria o nosso packege Serviços.

Utilizamos o SonarGrafh explorer para verificar a manutenabilidade, numero de ciclos e código entrelaçado.
Manitence Level = 81,71.
Propagation cost = 28,13.
lines of Code = 2400.
Cycle groups = 2.
maior ciclo de 4 elementos (Packege View).
complex Code = 1,99.
Relative Entanglement = 6,99.

Código tem 3 interfaces, uma para cliente não logado, uma para cliente logado e uma pro Admin.
Admin seria a pessoa que fica no balcão do hotel, sistema extremamente simples de coordenar e consegue falcimente gerir um hotel.

### Conceitos
- Associação Binaria com Navegabilidade
    - Relação de dependência.
    - A classe na qual sai a seta poderá disparar metodos em objetos da classe que receberá a seta.
    - A reciproca não é verdadeira.

### Diagrama de classes
Esta no arquivo Astah-UML em imagens_projeto
porem segue foto do diagrama:
[Foto do diagrama](https://github.com/Enzuldo2/Hotel-Reservation/blob/main/Imagens_projeto/diagrama_atual.jpeg)



