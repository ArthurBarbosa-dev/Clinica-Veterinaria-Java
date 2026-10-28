package view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import controller.AnimalController;
import controller.ConsultaController;
import controller.DonoController;

public class TelaPrincipal extends JFrame {

    private DonoController tutorController;
    private AnimalController animalController;
    private ConsultaController consultaController;

    public TelaPrincipal(DonoController tutorController, AnimalController animalController, ConsultaController consultaController) {
        this.tutorController = tutorController;
        this.animalController = animalController;
        this.consultaController = consultaController;

        configurarJanela();
        criarComponentes();
    }

    private void configurarJanela() {
        setTitle("Clínica Veterinária");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    private void criarComponentes() {
        JLabel titulo = new JLabel("Sistema da Clínica Veterinária", JLabel.CENTER);

        JButton btnDonos = new JButton("Donos");
        JButton btnAnimais = new JButton("Animais");
        JButton btnConsultas = new JButton("Consultas");

        JPanel painel = new JPanel();
        painel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));

        painel.add(btnDonos);
        painel.add(btnAnimais);
        painel.add(btnConsultas);

        // Ações dos botões ativadas e conectadas
        btnDonos.addActionListener(e -> abrirTelaDonos());
        btnAnimais.addActionListener(e -> abrirTelaAnimais());
        btnConsultas.addActionListener(e -> abrirTelaConsultas());

        getContentPane().add(titulo, BorderLayout.NORTH);
        getContentPane().add(painel, BorderLayout.CENTER);
    }

    private void abrirTelaDonos() {
        TelaDonos tela = new TelaDonos(tutorController);
        tela.setVisible(true);
    }

    private void abrirTelaAnimais() {
        TelaAnimais tela = new TelaAnimais(animalController, tutorController);
        tela.setVisible(true);
    }

    private void abrirTelaConsultas() {
        TelaConsulta tela = new TelaConsulta(consultaController, animalController);
        tela.setVisible(true);
    }
}