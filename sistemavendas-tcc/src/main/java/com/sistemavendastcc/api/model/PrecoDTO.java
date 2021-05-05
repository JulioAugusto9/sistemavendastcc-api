package com.sistemavendastcc.api.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.sistemavendastcc.domain.model.Preco;

public class PrecoDTO {
	private LocalDate dataCriacao;
	private BigDecimal preco;

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
	
	public PrecoDTO(LocalDate dataCriacao, BigDecimal preco) {
		super();
		this.dataCriacao = dataCriacao;
		this.preco = preco;
	}

	public static PrecoDTO from(Preco preco) {
		return new PrecoDTO(preco.getDataCriacao(), preco.getPreco());
	}
}
