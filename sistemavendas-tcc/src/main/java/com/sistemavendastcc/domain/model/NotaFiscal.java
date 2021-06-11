package com.sistemavendastcc.domain.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class NotaFiscal {
	@Id
	private Long id;
	private Long cfop;
	private String natOp;
	private BigDecimal uniao;
	private BigDecimal icms;
	private BigDecimal subTriIcms;
	private BigDecimal preco;
	private LocalDate dataInclusao;
	
	public BigDecimal getValPagar() {
		return preco.add( preco.multiply(calcTotalTributos()) )
				.setScale(2, BigDecimal.ROUND_HALF_EVEN);
	}
	
	private BigDecimal calcTotalTributos() {
		var trib = new ArrayList<BigDecimal>();
		trib.add(uniao);
		trib.add(icms);
		trib.add(subTriIcms);
		return trib.stream()
				.map(num -> num.divide(new BigDecimal(100)))
				.reduce(new BigDecimal(0), (acc, num) -> acc.add(num))
				.setScale(4, BigDecimal.ROUND_HALF_EVEN);
	}
	
	public BigDecimal getTotalTributos() {
		return calcTotalTributos()
				.multiply(new BigDecimal(100))
				.setScale(2, BigDecimal.ROUND_HALF_EVEN);
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getCfop() {
		return cfop;
	}
	public void setCfop(Long cfop) {
		this.cfop = cfop;
	}
	public String getNatOp() {
		return natOp;
	}
	public void setNatOp(String natOp) {
		this.natOp = natOp;
	}
	public BigDecimal getUniao() {
		return uniao.setScale(2, BigDecimal.ROUND_HALF_EVEN);
	}
	public void setUniao(BigDecimal uniao) {
		this.uniao = uniao;
	}
	public BigDecimal getIcms() {
		return icms.setScale(2, BigDecimal.ROUND_HALF_EVEN);
	}
	public void setIcms(BigDecimal icms) {
		this.icms = icms;
	}
	public BigDecimal getSubTriIcms() {
		return subTriIcms.setScale(2, BigDecimal.ROUND_HALF_EVEN);
	}
	public void setSubTriIcms(BigDecimal subTriIcms) {
		this.subTriIcms = subTriIcms;
	}

	public BigDecimal getPreco() {
		return preco.setScale(2, BigDecimal.ROUND_HALF_EVEN);
	}

	public void setPreco(BigDecimal preco) {
		this.preco = preco;
	}

	public LocalDate getDataInclusao() {
		return dataInclusao;
	}

	public void setDataInclusao(LocalDate dataInclusao) {
		this.dataInclusao = dataInclusao;
	}
	
	
}
