package controller;

import java.util.ArrayList;
import java.util.List;
import model.Animal;
import model.Consulta;

public class ConsultaController {
//criacação de arraylist consultas
    private List<Consulta> consultas = new ArrayList<>();
    private int proximoId = 1; //serve como um contador que vai incrementando cada vez que é cadastrado uma consulta

    public Consulta cadastrar(Animal animal, String data, String motivo) { //metodo para cadastrar consultas
        Consulta consulta = new Consulta(proximoId++, animal, data, motivo);
        consultas.add(consulta); //adiciona a consulta cadastrada na arraylist
        return consulta; 
    }

    public List<Consulta> listar() { //metodo para listar as consultas ja cadastradas
        return consultas;
    }
}