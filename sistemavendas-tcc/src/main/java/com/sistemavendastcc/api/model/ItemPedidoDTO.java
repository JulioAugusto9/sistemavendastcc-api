package com.sistemavendastcc.api.model;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import com.sistemavendastcc.domain.model.ItemPedido;

public class ItemPedidoDTO {
	private Long id;
	private BigDecimal preco;
	private Long qtde;
	
	private ProdutoDTO produto;
	
	public static List<ItemPedidoDTO> fromMany(List<ItemPedido> ips) {
		return ips.stream()
				.map((ip) -> ItemPedidoDTO.from(ip))
				.collect(Collectors.toList());
	}
	
	public static ItemPedidoDTO from(ItemPedido ip) {
		return new ItemPedidoDTO(ip.getId(), ip.getPreco(), ip.getQtde(), ProdutoDTO.from(ip.getProduto()));
	}
	
	public ItemPedidoDTO(Long id, BigDecimal preco, Long qtde, ProdutoDTO produto) {
		super();
		this.id = id;
		this.preco = preco;
		this.qtde = qtde;
		this.produto = produto;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public BigDecimal getPreco() {
		return preco;
	}

	public void setPreco(BigDecimal preco) {
		this.preco = preco;
	}

	public Long getQtde() {
		return qtde;
	}

	public void setQtde(Long qtde) {
		this.qtde = qtde;
	}

	public ProdutoDTO getProduto() {
		return produto;
	}

	public void setProduto(ProdutoDTO produto) {
		this.produto = produto;
	}
	
	
}
