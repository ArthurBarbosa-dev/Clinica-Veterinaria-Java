package model;

public abstract class Pessoa {
	//atributos
	protected String nome;
	protected String telefone;
	protected int id;
	//construtor
	public Pessoa(int id,String nome, String telefone) {
		this.nome = nome;
		this.telefone = telefone;
		this.id=id;
	}
	//Gets & Sets
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getTelefone() {
		return telefone;
	}
	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

}
