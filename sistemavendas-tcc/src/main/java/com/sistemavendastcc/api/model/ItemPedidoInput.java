package com.sistemavendastcc.api.model;

import javax.validation.constraints.NotNull;


public class ItemPedidoInput {
	@NotNull
	private Long qtde;
	
	private Long produtoId;
	
	private Long pedidoId;

	public Long getQtde() {
		return qtde;
	}

	public void setQtde(Long qtde) {
		this.qtde = qtde;
	}

	public Long getProdutoId() {
		return produtoId;
	}

	public void setProdutoId(Long produtoId) {
		this.produtoId = produtoId;
	}

	public Long getPedidoId() {
		return pedidoId;
	}

	public void setPedidoId(Long pedidoId) {
		this.pedidoId = pedidoId;
	}
	
	
}
