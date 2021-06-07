package com.sistemavendastcc.api.controller;

import java.time.LocalDate;

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

import com.sistemavendastcc.api.model.UsuarioPagination;
import com.sistemavendastcc.domain.exception.EntidadeNaoEncontradaException;
import com.sistemavendastcc.domain.model.Usuario;
import com.sistemavendastcc.domain.repository.UsuarioRepository;

@RestController
@CrossOrigin
@RequestMapping("/usuarios")
public class UsuarioController {
	
	@Autowired
	UsuarioRepository usuarioRepo;
	
	@GetMapping
	public UsuarioPagination listar(@RequestParam(required = false) String nome, 
			@RequestParam(required = false) Integer page,
			@RequestParam(required = false) Integer itemsPerPage) {
		if (nome == null) nome = "";
		if (page == null) page = 0;
		else page--;
		if (itemsPerPage == null) itemsPerPage = 6;
		Long totalPages = usuarioRepo.countByNomeContaining(nome);
		totalPages = totalPages / itemsPerPage + (totalPages % itemsPerPage > 0 ? 1 : 0);
		if (totalPages == 0) totalPages = 1L;
		UsuarioPagination usuarioPagination = new UsuarioPagination();
		usuarioPagination.setTotalPages(totalPages);
		Pageable pageable = PageRequest.of(page, itemsPerPage);
		usuarioPagination.setUsuarios(usuarioRepo.findByNomeContaining(nome, pageable));
		return usuarioPagination;
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Usuario criar(@Valid @RequestBody Usuario usuario) {
		usuario.setDataInclusao(LocalDate.now());
		return usuarioRepo.save(usuario);
	}
	
	
	@GetMapping("/{usuarioId}")
	@ResponseStatus(HttpStatus.OK)
	public Usuario achar(@PathVariable Long usuarioId) {
		return usuarioRepo.findById(usuarioId)
				.orElseThrow(() -> new EntidadeNaoEncontradaException("Usuário não encontrado"));
	}
	
	@PutMapping("/{usuarioId}")
	@ResponseStatus(HttpStatus.OK)
	public Usuario alterar(@PathVariable Long usuarioId, @Valid @RequestBody Usuario usuario) {
		if (!usuarioRepo.existsById(usuarioId))
			throw new EntidadeNaoEncontradaException("Usuário não encontrado");
		return usuarioRepo.save(usuario);
	}
	
	@DeleteMapping("/{usuarioId}")
	@ResponseStatus(HttpStatus.OK)
	public Long excluir(@PathVariable Long usuarioId) {
		if (!usuarioRepo.existsById(usuarioId))
			throw new EntidadeNaoEncontradaException("Usuário não encontrado");
		usuarioRepo.deleteById(usuarioId);
		return usuarioId;
	}
	
}













