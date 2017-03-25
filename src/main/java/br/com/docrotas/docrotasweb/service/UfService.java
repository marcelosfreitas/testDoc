package br.com.docrotas.docrotasweb.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.docrotas.docrotasweb.repository.UfRepository;

@Component
public class UfService {
	
	@Autowired
	UfRepository ufRepositorio;
	
//	public Uf salvar (Uf uf) throws ServiceException, RepositoryException {
//		if (uf == null) {
//			throw new ServiceException("Foi passado um objeto Uf nulo");
//		}
//		
//		if (uf.getId() == null) {
//			return ufRepositorio.salvar(uf);
//		} else {
//			return ufRepositorio.atualizar(uf);
//		}
//		
//	}
//	
//	public void excluir (Uf uf) throws ServiceException, RepositoryException {
//		if (uf == null) {
//			throw new ServiceException("Foi passado um objeto Uf nulo");
//		}
//		ufRepositorio.excluir(uf);
//	}
//	
//	public void excluir (Long id) throws ServiceException, RepositoryException {		
//		ufRepositorio.excluir(id);
//	}
}
