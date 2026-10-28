package controller;

import java.util.List;
import dao.AnimalDAO;
import model.Animal;
import model.Dono;

public class AnimalController {

    private AnimalDAO animalDAO; //Foi declarada uma variavel do tipo AnimalDAO

    public AnimalController() {
        this.animalDAO = new AnimalDAO(); //especie de construtor 
    }

    public Animal cadastrar(String nome, String especie, String raca, Dono dono) { //metodo para cadastrar animais
        Animal animal = new Animal(0, nome, especie, raca, dono); //não entendi porque o primeiro valor passado foi 0
        return animalDAO.cadastrar(animal); //animalDAO vai receber os dados que foram cadastrados desse animal
    }

    public List<Animal> listar() { //metodo que cria arraylist para usar de consulta todos os animais cadastrados 
        return animalDAO.listar(); //aqui já nao entendi como ele lista, nao mandou nenhum print ou algo do tipo, só escreveu listar
    }

    public void excluir(int id) { //metodo que exclui de animalDAO
        animalDAO.excluir(id); //como exclui já não sei
    }
    
    public void atualizar(Animal animal) {
    	animalDAO.atualizar(animal);
    }
}