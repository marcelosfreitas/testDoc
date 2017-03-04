package br.com.docrotas.docrotasweb.repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

public abstract class Repository {
	
	@PersistenceContext(type = PersistenceContextType.EXTENDED)
	protected EntityManager manager;

}