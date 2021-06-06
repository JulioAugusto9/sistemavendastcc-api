package com.sistemavendastcc.api.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.sistemavendastcc.domain.model.Preco;

public class PrecoDTO {
	private Long id;
	private LocalDate dataCriacao;
	private BigDecimal preco;

	
	
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

	public BigDecimal getPreco() {
		return preco;
	}

	public void setPreco(BigDecimal preco) {
		this.preco = preco;
	}
	
	public PrecoDTO(Long id, LocalDate dataCriacao, BigDecimal preco) {
		super();
		this.id = id;
		this.dataCriacao = dataCriacao;
		this.preco = preco;
	}

	public static PrecoDTO from(Preco preco) {
		return new PrecoDTO(preco.getId(), preco.getDataCriacao(), preco.getPreco());
	}
}
