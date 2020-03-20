package com.socio.torcedor.domain.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.socio.torcedor.domain.model.Cliente;

/**
 * Classe que representa o repositorio de do Cliente, esta interface faz foi implementada para gerar consultas auxiliares.
 * @author Alexandre
 */
public interface ClienteRepositoriesQueries {
	
	Cliente validarEmailCliente(String email);
	
	void atualizarClienteCampanha(Cliente cliente);


}
