create table uf (
	cod_ibge int(2) not null,
	descricao varchar(45) not null,
	sigla varchar(2) not null,
	dt_criacao timestamp,
	dt_alteracao timestamp,
	primary key (cod_ibge) 
);