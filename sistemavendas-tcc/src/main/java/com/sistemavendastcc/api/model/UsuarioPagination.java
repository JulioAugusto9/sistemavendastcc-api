package com.sistemavendastcc.api.model;

import java.util.List;

import com.sistemavendastcc.domain.model.Usuario;

public class UsuarioPagination {
	private Long totalPages;
	private List<Usuario> usuarios;
	
	public Long getTotalPages() {
		return totalPages;
	}
	public void setTotalPages(Long totalPages) {
		this.totalPages = totalPages;
	}
	public List<Usuario> getUsuarios() {
		return usuarios;
	}
	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}
	
	
}
