package com.sistemavendastcc.api.model;

import java.math.BigDecimal;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;


public class PedidoInput {
	@NotNull
	private BigDecimal desconto;
	@NotNull
	private Long usuarioId;
	@NotNull
	private Long clienteId;
	
	@Valid
	List<ItemPedidoInput> itensPedido;

	public BigDecimal getDesconto() {
		return desconto;
	}

	public void setDesconto(BigDecimal desconto) {
		this.desconto = desconto;
	}

	public Long getUsuarioId() {
		return usuarioId;
	}

	public void setUsuarioId(Long usuarioId) {
		this.usuarioId = usuarioId;
	}

	public Long getClienteId() {
		return clienteId;
	}

	public void setClienteId(Long clienteId) {
		this.clienteId = clienteId;
	}

	public List<ItemPedidoInput> getItensPedido() {
		return itensPedido;
	}

	public void setItensPedido(List<ItemPedidoInput> itensPedido) {
		this.itensPedido = itensPedido;
	}
	
	
}
