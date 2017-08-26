create table cidade (
	id int not null auto_increment,
	nome varchar(45) not null,
	cod_ibge int(7) not null,
	dt_criacao timestamp,
	dt_alteracao timestamp,
	uf_id int not null,
	primary key (id),
	foreign key (uf_id) references uf(cod_ibge)
);