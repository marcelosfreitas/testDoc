create table conta (
	id int not null auto_increment,
	nome varchar(45) not null,
	dt_criacao timestamp,
	dt_alteracao timestamp,
	primary key (id)
);