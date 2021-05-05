package com.sistemavendastcc.domain.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MapKey;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;

@Entity
public class Produto {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String descricao;
	private String TipoUnidade;
	
	@OneToMany(mappedBy = "produto")
	@MapKey(name = "dataCriacao")
	@OrderBy("dataCriacao")
	private SortedMap<LocalDate, Preco> precos;
	
	public Produto() {}
	
	public Produto(Long id, String descricao, String tipoUnidade) {
		super();
		this.id = id;
		this.descricao = descricao;
		TipoUnidade = tipoUnidade;
	}
	
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
	
	public SortedMap<LocalDate, Preco> getPrecos() {
		return precos;
	}

	public List<Preco> getPrecosAsList() {
		return new ArrayList<Preco>(precos.values());
	}
	public void setPrecos(SortedMap<LocalDate, Preco> precos) {
		this.precos = precos;
	}
	public BigDecimal getPrecoAtual() {
		if (precos == null) return new BigDecimal(0);
		SortedMap<LocalDate, Preco> precoslocal = precos;
		if (!precoslocal.getClass().equals(TreeMap.class)) {
			precoslocal = new TreeMap<LocalDate, Preco>(precos);
		}
		Preco precoAtual = (((TreeMap<LocalDate, Preco>)precoslocal).floorEntry(LocalDate.now())).getValue();
		return precoAtual.getPreco();
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Produto other = (Produto) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
}
