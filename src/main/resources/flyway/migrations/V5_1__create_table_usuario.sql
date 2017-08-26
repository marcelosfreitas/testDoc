create table usuario (
	id int not null auto_increment,
	login varchar(20) not null,
	senha varchar(255) not null,
	email varchar(100) not null,
	dt_criacao timestamp,
	dt_alteracao timestamp,	
	conta_id int not null,
	primary key (id),
	foreign key (conta_id) references conta(id)
);