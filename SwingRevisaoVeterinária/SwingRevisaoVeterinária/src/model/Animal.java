package model;

public class Animal {
	//Atributos
	String nome;
	String especie;
	String raca;
	Dono dono;
	int id;
	//Construtor para atribuir valores aos atributos
	public Animal(int id,String nome, String especie, String raca, Dono dono) { 
		this.nome = nome;
		this.especie = especie;
		this.raca = raca;
		this.dono = dono;
		this.id = id;
	}
	
	//Gets & Sets
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getEspecie() {
		return especie;
	}
	public void setEspecie(String especie) {
		this.especie = especie;
	}
	public String getRaca() {
		return raca;
	}
	public void setRaca(String raca) {
		this.raca = raca;
	}
	public Dono getDono() {
		return dono;
	}
	public void setDono(Dono dono) {
		this.dono = dono;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	//Esse metodo é para apresentar o nome e espécie
	@Override
	public String toString() {
		return nome + " ("+especie+")";
	}

}
