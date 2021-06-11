package com.sistemavendastcc.domain.model;

public class Empresa {
	private String nome;
	private String endereco1;
	private String endereco2;
	private String telefone;
	private String CNPJ;
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getEndereco1() {
		return endereco1;
	}
	public void setEndereco1(String endereco1) {
		this.endereco1 = endereco1;
	}
	public String getEndereco2() {
		return endereco2;
	}
	public void setEndereco2(String endereco2) {
		this.endereco2 = endereco2;
	}
	public String getCNPJ() {
		return CNPJ;
	}
	public void setCNPJ(String cNPJ) {
		CNPJ = cNPJ;
	}
	public String getTelefone() {
		return telefone;
	}
	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}
	
	
}
