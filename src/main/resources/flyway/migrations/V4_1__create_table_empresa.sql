create table empresa (
	id int not null auto_increment,
	cnpj varchar(14) not null,
	razao varchar(45) not null,
	dt_criacao timestamp,
	dt_alteracao timestamp,
	conta_id int not null,
	primary key(id),
	foreign key (conta_id) references conta(id)
);