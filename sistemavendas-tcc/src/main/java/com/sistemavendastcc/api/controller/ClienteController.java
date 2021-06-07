package com.sistemavendastcc.api.controller;

import java.time.LocalDate;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.sistemavendastcc.api.model.ClientePagination;
import com.sistemavendastcc.domain.exception.EntidadeNaoEncontradaException;
import com.sistemavendastcc.domain.model.Cliente;
import com.sistemavendastcc.domain.model.PessoaFisica;
import com.sistemavendastcc.domain.model.PessoaJuridica;
import com.sistemavendastcc.domain.repository.ClienteRepository;
import com.sistemavendastcc.domain.repository.PessoaFisicaRepository;
import com.sistemavendastcc.domain.repository.PessoaJuridicaRepository;

@RestController
@CrossOrigin
@RequestMapping("/clientes")
public class ClienteController {
	
	@Autowired
	PessoaFisicaRepository pessoaFisicaRepo;
	
	@Autowired
	PessoaJuridicaRepository pessoaJuridicaRepo;
	
	@Autowired
	ClienteRepository clienteRepository;
	
	@GetMapping
	public ClientePagination listar(@RequestParam(required = false) String nome,
			@RequestParam(required = false) Integer page,
			@RequestParam(required = false) Integer itemsPerPage) {
		if (nome == null) nome = "";
		if (page == null) page = 0;
		else page--;
		if (itemsPerPage == null) itemsPerPage = 6;
		Long totalPages = clienteRepository.countByNomeContaining(nome);
		totalPages = totalPages / itemsPerPage + (totalPages % itemsPerPage > 0 ? 1 : 0);
		if (totalPages == 0) totalPages = 1L;
		ClientePagination clientePagination = new ClientePagination();
		clientePagination.setTotalPages(totalPages);
		Pageable pageable = PageRequest.of(page, itemsPerPage);
		clientePagination.setClientes(clienteRepository.findByNomeContaining(nome, pageable));
		return clientePagination;
	}
	
	@PostMapping("/pessoafisica")
	@ResponseStatus(HttpStatus.CREATED)
	public Cliente criar(@Valid @RequestBody PessoaFisica cliente) {
		cliente.setDataInclusao(LocalDate.now());
		cliente.setTipo("pessoafisica");
		return pessoaFisicaRepo.save(cliente);
	}
	
	@PutMapping("/pessoafisica/{clienteId}")
	@ResponseStatus(HttpStatus.OK)
	public Cliente alterar(@PathVariable Long clienteId, @Valid @RequestBody PessoaFisica cliente) {
		if (!clienteRepository.existsById(clienteId))
			throw new EntidadeNaoEncontradaException("Cliente não encontrado");
		return pessoaFisicaRepo.save(cliente);
	}
	
	@PostMapping("/pessoajuridica")
	@ResponseStatus(HttpStatus.CREATED)
	public Cliente criar(@Valid @RequestBody PessoaJuridica cliente) {
		cliente.setDataInclusao(LocalDate.now());
		cliente.setTipo("pessoajuridica");
		return pessoaJuridicaRepo.save(cliente);
	}
	
	@PutMapping("/pessoajuridica/{clienteId}")
	@ResponseStatus(HttpStatus.OK)
	public Cliente alterar(@PathVariable Long clienteId, @Valid @RequestBody PessoaJuridica cliente) {
		if (!clienteRepository.existsById(clienteId))
			throw new EntidadeNaoEncontradaException("Cliente não encontrado");
		return pessoaJuridicaRepo.save(cliente);
	}
	
	@GetMapping("/{clienteId}")
	@ResponseStatus(HttpStatus.OK)
	public Cliente achar(@PathVariable Long clienteId) {
		return clienteRepository.findById(clienteId)
				.orElseThrow(() -> new EntidadeNaoEncontradaException("Cliente não encontrado"));
	}	
	
	@DeleteMapping("/{clienteId}")
	@ResponseStatus(HttpStatus.OK) 
	public Long excluir(@PathVariable Long clienteId) {
		if (!clienteRepository.existsById(clienteId))
			throw new EntidadeNaoEncontradaException("Cliente não encontrado");
		clienteRepository.deleteById(clienteId);
		return clienteId;
	}
	
}
