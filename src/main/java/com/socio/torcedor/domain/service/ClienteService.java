package com.socio.torcedor.domain.service;

import java.util.ArrayList;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.socio.torcedor.exception.EntidadeNaoEncontradaException;
import com.socio.torcedor.client.CampanhaClient;
import com.socio.torcedor.domain.model.Campanha;
import com.socio.torcedor.domain.model.Cliente;

import com.socio.torcedor.domain.repository.ClienteRepository;
import com.socio.torcedor.exception.EntidadeEmUsoException;

/**
 * Classe que representa o dominio do Cliente, ou seja, faz parte das regras de
 * negocio de um cliente em si. Aqui vamos manipular nossas regras de negocio
 * definidas.
 * 
 * @author Alexandre
 */

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	private CampanhaClient client;
	
	
	public Cliente gravarNovoCliente(Cliente cliente) {

		try {

			List<Campanha> campanhas = client.listarCampanhasPorClube(cliente.getClube().getId());

			if (isEmailValido(cliente.getEmail())) {
				Cliente campanhaCliente = clienteRepository.findClienteByEmail(cliente.getEmail());
				if (campanhas != null) {
					campanhaCliente.setCampanhas(novaListaCampanha(campanhas));
				}				
				atualizarClienteExistente(campanhaCliente.getId(), campanhaCliente);
				throw new EntidadeEmUsoException("Email já cadastrado anteriormente " + campanhaCliente.getId());
			}
			cliente.setCampanhas(campanhas);
			return clienteRepository.save(cliente);
		} catch (EntidadeEmUsoException e) {
			throw new EntidadeEmUsoException("Email ja existente.");
		}
	}

	public void deletarCliente(long id) {
		try {

			Cliente cliente = clienteRepository.findById(id).orElseThrow(
					() -> new EntidadeNaoEncontradaException("Não existe cliente cadastrado com id %d " + id));

			clienteRepository.deleteById(cliente.getId());

		} catch (EmptyResultDataAccessException e) {
			throw new EntidadeNaoEncontradaException(String.format("Não existe cozinha com id %d ", id));
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(String.format("Cozinha com o codigo %d não pode ser removida", id));
		}
	}
	
	public List<Cliente> listarClientes() {
		
		
		return clienteRepository.findAll();

	}

	public Cliente atualizarDadosCliente(long id, Cliente cliente) {
		try {

			Cliente putCliente = clienteRepository.findClienteById(id);
			Cliente clienteAtualizado = null;
			if (putCliente != null) {
				BeanUtils.copyProperties(cliente, putCliente, "id");
				clienteAtualizado = clienteRepository.save(putCliente);
			}
			return clienteAtualizado;
		} catch (EntidadeNaoEncontradaException e) {
			throw new EntidadeNaoEncontradaException(
					String.format("Erro ao atualizar campanha para o " + "cliente %d ", id));
		}

	}

	private void atualizarClienteExistente(long id, Cliente cliente) {
		try {

			Cliente putCliente = clienteRepository.findClienteById(id);

			if (putCliente != null) {
				BeanUtils.copyProperties(cliente, putCliente, "id");
				clienteRepository.atualizarClienteCampanha(putCliente);
			}

		} catch (EntidadeNaoEncontradaException e) {
			throw new EntidadeNaoEncontradaException(
					String.format("Erro ao atualizar campanha para o " + "cliente %d ", id));
		}

	}

	/*
	 * Houve a necessidade de criar esse metodo auxiliar devido ao erro
	 * UnsupportedOperationException
	 */
	private List<Campanha> novaListaCampanha(List<Campanha> campanhas) {
		List<Campanha> nova = new ArrayList<>();
		//if (campanhas != null ) {
			for (Campanha campanha : campanhas) {
				nova.add(campanha);
		//	}
			
		}
	//	return null;
			return nova;
		
	}

	private boolean isEmailValido(String email) {

		Cliente existeCliente = clienteRepository.validarEmailCliente(email);
		if (existeCliente != null) {
			return true;
		}
		return false;
	}

}
