package model;

public class Dono extends Pessoa{ //herda de Pessoa

	public Dono(int id,String nome, String telefone) { //Construtor
		super(id, nome, telefone); //super é porque herda os atributos de pessoa?
		
	}
	
    @Override 
    public String toString() { //metodo que retorna o nome, mas me parece que poderia ser mais simples. invés de tudo isso e override
        return nome;
    }
}
