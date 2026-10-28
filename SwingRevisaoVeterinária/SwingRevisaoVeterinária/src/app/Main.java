package app;

import controller.AnimalController;
import controller.ConsultaController;
import controller.DonoController;
import view.TelaPrincipal;

public class Main {

	public static void main(String[] args) {
	
		DonoController tutorController =new DonoController();
		
		AnimalController animalController =new AnimalController();
		
		ConsultaController consultaController =new ConsultaController();
		TelaPrincipal telaPrincipal =new TelaPrincipal(tutorController,animalController,consultaController);
		telaPrincipal.setVisible(true);
	}
}