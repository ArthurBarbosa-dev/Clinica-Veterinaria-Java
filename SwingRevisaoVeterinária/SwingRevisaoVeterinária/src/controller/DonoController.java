package controller;

import java.util.List;
import dao.DonoDAO;
import model.Dono;

public class DonoController {

    private DonoDAO donoDAO;

    public DonoController() { //construtor
        this.donoDAO = new DonoDAO();
    }

    public Dono cadastrar(String nome, String telefone) { //metodo para castrar dono
        Dono dono = new Dono(0, nome, telefone); //0 nesse caso é o placeholder
        return donoDAO.cadastrar(dono); //retorna o dono cadastrado e donoDAO recebe a informação cadastrada 
    }

    public List<Dono> listar() { //metodo para listar donos cadastrados
        return donoDAO.listar(); 
    }

    public void excluir(int id) { //metodo para excluir donos cadastrados
        donoDAO.excluir(id); 
    }
    
    public void atualizar(Dono dono) {
    	donoDAO.atualizar(dono);
    }
}