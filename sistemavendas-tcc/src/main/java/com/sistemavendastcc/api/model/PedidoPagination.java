package com.sistemavendastcc.api.model;

import java.util.List;

import com.sistemavendastcc.domain.model.Pedido;

public class PedidoPagination {
	private Long totalPages;
	private List<Pedido> pedidos;
	
	public Long getTotalPages() {
		return totalPages;
	}
	public void setTotalPages(Long totalPages) {
		this.totalPages = totalPages;
	}
	public List<Pedido> getPedidos() {
		return pedidos;
	}
	public void setPedidos(List<Pedido> pedidos) {
		this.pedidos = pedidos;
	}
	
	
}
