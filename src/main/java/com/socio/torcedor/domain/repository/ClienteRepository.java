package com.socio.torcedor.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.socio.torcedor.domain.model.Cliente;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long>, ClienteRepositoriesQueries{

	Cliente findClienteByEmail(String email);
	Cliente findClienteById(long Id);
	
}
