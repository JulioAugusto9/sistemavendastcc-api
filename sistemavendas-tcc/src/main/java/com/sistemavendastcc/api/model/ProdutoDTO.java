package com.sistemavendastcc.api.model;

import java.math.BigDecimal;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.sistemavendastcc.domain.model.Produto;

public class ProdutoDTO {
	private Long id;
	@NotNull
	@NotEmpty
	private String descricao;
	@NotNull
	@NotEmpty
	private String TipoUnidade;
	@NotNull
	private BigDecimal preco;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public String getTipoUnidade() {
		return TipoUnidade;
	}
	public void setTipoUnidade(String tipoUnidade) {
		TipoUnidade = tipoUnidade;
	}
	public BigDecimal getPreco() {
		return preco;
	}
	public void setPreco(BigDecimal preco) {
		this.preco = preco;
	}
	public ProdutoDTO(Long id, String descricao, String tipoUnidade, BigDecimal preco) {
		super();
		this.id = id;
		this.descricao = descricao;
		TipoUnidade = tipoUnidade;
		this.preco = preco;
	}
	
	public static ProdutoDTO from(Produto prod) {
		return new ProdutoDTO(prod.getId(), prod.getDescricao(), prod.getTipoUnidade(), prod.getPrecoAtual());
	}
}
