package com.sistemavendastcc.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

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
	List<Cliente> listar(@RequestParam(required = false) String nome) {
		if (nome == null) nome = "";
		return clienteRepository.findByNomeContaining(nome);
	}
	
	@PostMapping("/pessoafisica")
	@ResponseStatus(HttpStatus.CREATED)
	Cliente criar(@RequestBody PessoaFisica cliente) {
		return pessoaFisicaRepo.save(cliente);
	}
	
	@PostMapping("/pessoajuridica")
	@ResponseStatus(HttpStatus.CREATED)
	Cliente criar(@RequestBody PessoaJuridica cliente) {
		return pessoaJuridicaRepo.save(cliente);
	}
	
}
