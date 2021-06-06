package com.sistemavendastcc.api.model;

import java.util.List;

import com.sistemavendastcc.domain.model.Cliente;

public class ClientePagination {
	Long totalPages;
	List<Cliente> clientes;
	
	
	public Long getTotalPages() {
		return totalPages;
	}
	public void setTotalPages(Long totalPages) {
		this.totalPages = totalPages;
	}
	public List<Cliente> getClientes() {
		return clientes;
	}
	public void setClientes(List<Cliente> clientes) {
		this.clientes = clientes;
	}
	
	
}
