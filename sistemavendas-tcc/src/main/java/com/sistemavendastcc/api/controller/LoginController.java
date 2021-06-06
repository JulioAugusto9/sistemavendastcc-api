package com.sistemavendastcc.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.sistemavendastcc.api.model.UsuarioInput;
import com.sistemavendastcc.domain.exception.EntidadeNaoEncontradaException;
import com.sistemavendastcc.domain.exception.NegocioException;
import com.sistemavendastcc.domain.model.Usuario;
import com.sistemavendastcc.domain.repository.UsuarioRepository;

@RestController
@CrossOrigin
public class LoginController {
	
	@Autowired
	UsuarioRepository usuarioRepo;
	
	@PostMapping("/login")
	@CrossOrigin
	public Usuario login(@RequestBody UsuarioInput usuarioInput) {
		Usuario usuario = usuarioRepo.findByLogin(usuarioInput.getLogin());
		if (usuario == null) throw new EntidadeNaoEncontradaException("Usuário não encontrado");
		if (!usuario.getSenha().equals(usuarioInput.getSenha())) throw new NegocioException("Senha incorreta");
		return usuario;
	}
}
