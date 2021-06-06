package com.sistemavendastcc.domain.model;

import org.springframework.security.core.GrantedAuthority;

public class Role implements GrantedAuthority {
	private static final long serialVersionUID = 1L;
	
	private String nomeRole;
	
	public Role(String nome) {
		this.nomeRole = nome;
	}

	public String getNome() {
		return nomeRole;
	}

	public void setNome(String nome) {
		this.nomeRole = nome;
	}

	@Override
	public String getAuthority() {
		return this.nomeRole;
	}
	
	
}
