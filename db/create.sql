drop database if exists sistema_de_agendamentos;

create database sistema_de_agendamentos;

use sistema_de_agendamentos;

create table Usuario(
    id_usuario bigint not null auto_increment PRIMARY KEY,
    nome varchar(50) not null,
    email varchar(50) not null,
    senha varchar(50) not null,
    cpf varchar(11) not null
);

create table Profissional(
    id_profissional bigint not null auto_increment PRIMARY KEY,
    id_usuario bigint not null,
    especialidade varchar(50) not null,
    pdf_data longblob not null,
    FOREIGN KEY(id_usuario) REFERENCES Usuario(id_usuario) ON DELETE CASCADE ON UPDATE CASCADE
);

create table Cliente(
    id_cliente bigint not null auto_increment PRIMARY KEY,
    id_usuario bigint not null,
    sexo enum('MASCULINO', 'FEMININO', 'OUTRO') not null,
    data_nascimento date not null,
    FOREIGN KEY(id_usuario) REFERENCES Usuario(id_usuario) ON DELETE CASCADE ON UPDATE CASCADE
);

create table Agendamento (
    id_agendamento bigint not null auto_increment PRIMARY KEY,
    id_usuario_cliente bigint,
    id_usuario_profissional bigint,
    data date not null,
    horario time not null,
    FOREIGN KEY(id_usuario_cliente) REFERENCES Usuario(id_usuario) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY(id_usuario_profissional) REFERENCES Usuario(id_usuario) ON DELETE CASCADE ON UPDATE CASCADE
);

quit

