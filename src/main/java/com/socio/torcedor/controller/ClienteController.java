package com.socio.torcedor.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.socio.torcedor.exception.EntidadeNaoEncontradaException;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import com.socio.torcedor.domain.model.Cliente;
import com.socio.torcedor.domain.service.ClienteService;
import com.socio.torcedor.exception.EntidadeEmUsoException;

/**
 * Classe que representa o controlador do Cliente, ou seja, faz parte da representacao de um cliente em si.
 * Aqui vamos manipular nossas requisicoes Https sendo elas:
 * GET, POST, DELETE, PUT.
 * Alem das anotacoes padroes do Spring, estamos atribuindo tambem:
 * Bean Validation, para validacao dos atributos obrigatorios.
 * Anotacoes Swagger para descrever os atributos na documentacao da API
 * @author Alexandre
 */
@Api(tags = "Clientes")
@RestController
@RequestMapping("/cadastro-cliente")
public class ClienteController {

	@Autowired
	private ClienteService clienteService;
	
	@ApiOperation("Cadastra um novo cliente")
	@PostMapping
	public ResponseEntity<?> cadastrarCliente(@ApiParam(name = "id de um Cliente", value = "Representacao de um cliente")
											  @Valid @RequestBody Cliente cliente) {
		try {

			Cliente novocliente = clienteService.gravarNovoCliente(cliente);
			return ResponseEntity.status(HttpStatus.CREATED).body(novocliente);

		} catch (EntidadeEmUsoException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@ApiOperation("Atualiza um novo cliente")
	@PutMapping("/{id}")
	public ResponseEntity<?> atualizarCliente(@ApiParam(name = "id de um Cliente", example = "1") 
											  @PathVariable long id, 
											  @Valid @RequestBody Cliente cliente){
		try {
		Cliente clienteAtualizado = clienteService.atualizarDadosCliente(id, cliente);
		if (clienteAtualizado != null) {
			return ResponseEntity.ok(clienteAtualizado);
		}
		return ResponseEntity.notFound().build();
		
		}catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@ApiOperation("Deleta um cliente")
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deletarCliente(@ApiParam(name = "id de um Cliente", example = "1")
											@PathVariable long id) {

		try {
			clienteService.deletarCliente(id);
			return ResponseEntity.noContent().build();
		}/* catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}*/ catch (EntidadeEmUsoException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		}

	}

	@ApiOperation("Lista todos os clientes")
	@GetMapping
	public List<Cliente> listarClientes() {

		return clienteService.listarClientes();
	}
}
