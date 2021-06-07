package com.sistemavendastcc.api.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import com.sistemavendastcc.domain.model.Cliente;
import com.sistemavendastcc.domain.model.EstadoPedido;
import com.sistemavendastcc.domain.model.Pedido;
import com.sistemavendastcc.domain.model.Usuario;

public class PedidoDTO {
	private Long id;
	private LocalDate dataCriacao;
	private BigDecimal desconto;
	private EstadoPedido estado;
	Usuario usuario;
	Cliente cliente;
	List<ItemPedidoDTO> itensPedido;
	private BigDecimal precoTotal;
	
	public static List<PedidoDTO> fromMany(List<Pedido> pedidos) {
		return pedidos.stream()
				.map((p) -> PedidoDTO.from(p))
				.collect(Collectors.toList());
	}
	
	public static PedidoDTO from(Pedido pedido) {
		return new PedidoDTO(pedido.getId(),pedido.getDataCriacao(), pedido.getDesconto(), pedido.getEstado(),
				pedido.getUsuario(), pedido.getCliente(), ItemPedidoDTO.fromMany(pedido.getItensPedido()),
				pedido.getPrecoTotal());
	}
	
	public PedidoDTO(Long id, LocalDate dataCriacao, BigDecimal desconto, EstadoPedido estado, Usuario usuario,
			Cliente cliente, List<ItemPedidoDTO> itensPedido, BigDecimal precoTotal) {
		super();
		this.id = id;
		this.dataCriacao = dataCriacao;
		this.desconto = desconto;
		this.estado = estado;
		this.usuario = usuario;
		this.cliente = cliente;
		this.itensPedido = itensPedido;
		this.precoTotal = precoTotal;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public LocalDate getDataCriacao() {
		return dataCriacao;
	}
	public void setDataCriacao(LocalDate dataCriacao) {
		this.dataCriacao = dataCriacao;
	}
	public BigDecimal getDesconto() {
		return desconto;
	}
	public void setDesconto(BigDecimal desconto) {
		this.desconto = desconto;
	}
	public EstadoPedido getEstado() {
		return estado;
	}
	public void setEstado(EstadoPedido estado) {
		this.estado = estado;
	}
	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	public Cliente getCliente() {
		return cliente;
	}
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	public List<ItemPedidoDTO> getItensPedido() {
		return itensPedido;
	}
	public void setItensPedido(List<ItemPedidoDTO> itensPedido) {
		this.itensPedido = itensPedido;
	}

	public BigDecimal getPrecoTotal() {
		return precoTotal;
	}

	public void setPrecoTotal(BigDecimal precoTotal) {
		this.precoTotal = precoTotal;
	}
	
	
}
