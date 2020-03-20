package com.socio.torcedor.infrastructure.repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.socio.torcedor.domain.model.Cliente;
import com.socio.torcedor.domain.repository.ClienteRepositoriesQueries;
import com.socio.torcedor.domain.repository.ClienteRepository;

@Repository
public class ClienteRepositoryImpl implements ClienteRepositoriesQueries{
	
	@PersistenceContext
	private EntityManager em;

	
	@Override
	public Cliente validarEmailCliente(String email) {
		try {
			
		
		CriteriaBuilder builder = em.getCriteriaBuilder();

		CriteriaQuery<Cliente> criteria = builder.createQuery(Cliente.class);

		Root<Cliente> root = criteria.from(Cliente.class);
		
		criteria.where(builder.equal(root.get("email"), email));
		
		TypedQuery<Cliente> query = em.createQuery(criteria);
		
		
		return query.getSingleResult();
		
		} catch (NoResultException e) {
			return null;
		}
		
		
		
	}

	@Transactional
	@Override
	public void atualizarClienteCampanha(Cliente cliente) {
		
		em.merge(cliente);
		
	}
	
	
}
