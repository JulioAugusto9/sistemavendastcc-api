package com.sistemavendastcc.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sistemavendastcc.domain.model.PessoaJuridica;
import com.sistemavendastcc.domain.repository.PessoaJuridicaRepository;

@RestController
@CrossOrigin
@RequestMapping("/clientes")
public class ClienteController {
	
	@Autowired
	PessoaJuridicaRepository pessoaJuridicaRepo;
	
	@GetMapping
	List<PessoaJuridica> listar() {
		return pessoaJuridicaRepo.findAll();
	}
	
	
}
