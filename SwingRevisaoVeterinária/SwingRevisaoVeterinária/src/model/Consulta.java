package model;

public class Consulta {
//atributos
    private int id;
    private Animal animal;
    private String data;
    private String motivo;
//construtor
    public Consulta(int id, Animal animal, String data, String motivo) {
        this.id = id;
        this.animal = animal;
        this.data = data;
        this.motivo = motivo;
    }
//Gets & Sets
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public Animal getAnimal() {
        return animal;
    }
    public void setAnimal(Animal animal) {
        this.animal = animal;
    }
    public String getData() {
        return data;
    }
    public void setData(String data) {
        this.data = data;
    }
    public String getMotivo() {
        return motivo;
    }
    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }
}