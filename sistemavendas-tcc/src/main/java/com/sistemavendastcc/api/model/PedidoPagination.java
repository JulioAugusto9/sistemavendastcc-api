package com.sistemavendastcc.api.model;

import java.util.List;


public class PedidoPagination {
	private Long totalPages;
	private List<PedidoDTO> pedidos;
	
	public Long getTotalPages() {
		return totalPages;
	}
	public void setTotalPages(Long totalPages) {
		this.totalPages = totalPages;
	}
	public List<PedidoDTO> getPedidos() {
		return pedidos;
	}
	public void setPedidos(List<PedidoDTO> pedidos) {
		this.pedidos = pedidos;
	}
	
}
