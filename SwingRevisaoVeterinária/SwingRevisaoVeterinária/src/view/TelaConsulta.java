package view;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import controller.AnimalController;
import controller.ConsultaController;
import model.Animal;
import model.Consulta;

public class TelaConsulta extends JFrame {

    private ConsultaController consultaController;
    private AnimalController animalController;

    private JComboBox<Animal> cbAnimal;
    private JTextField txtData;
    private JTextField txtMotivo;

    private JButton btnCadastrar;
    private JTable tabela;
    private DefaultTableModel modelo;

    public TelaConsulta(ConsultaController consultaController, AnimalController animalController) {
        this.consultaController = consultaController;
        this.animalController = animalController;

        configurarJanela();
        criarComponentes();
        carregarAnimaisNoComboBox();
        atualizarTabela();
    }

    private void configurarJanela() {
        setTitle("Agendamento de Consultas");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    private void criarComponentes() {
        JPanel painelFormulario = new JPanel(new GridLayout(4, 2, 10, 10));

        JLabel lblAnimal = new JLabel("Animal:");
        cbAnimal = new JComboBox<>();

        JLabel lblData = new JLabel("Data:");
        txtData = new JTextField();

        JLabel lblMotivo = new JLabel("Motivo:");
        txtMotivo = new JTextField();

        btnCadastrar = new JButton("Agendar Consulta");

        painelFormulario.add(lblAnimal);
        painelFormulario.add(cbAnimal);
        painelFormulario.add(lblData);
        painelFormulario.add(txtData);
        painelFormulario.add(lblMotivo);
        painelFormulario.add(txtMotivo);
        painelFormulario.add(new JLabel("")); // Espaço de alinhamento
        painelFormulario.add(btnCadastrar);

        btnCadastrar.addActionListener(e -> agendarConsulta());

        String[] colunas = {"ID", "Animal", "Data", "Motivo"};
        modelo = new DefaultTableModel(colunas, 0);
        tabela = new JTable(modelo);
        JScrollPane scroll = new JScrollPane(tabela);

        add(painelFormulario, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);
    }

    private void carregarAnimaisNoComboBox() {
        cbAnimal.removeAllItems();
        List<Animal> animais = animalController.listar();
        for (Animal a : animais) {
            cbAnimal.addItem(a);
        }
    }

    private void agendarConsulta() {
        Animal animalSelecionado = (Animal) cbAnimal.getSelectedItem();
        String data = txtData.getText();
        String motivo = txtMotivo.getText();

        if (animalSelecionado == null) {
            JOptionPane.showMessageDialog(this, "Cadastre e selecione um animal antes de agendar!");
            return;
        }

        if (data.trim().isEmpty() || motivo.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Preencha a data e o motivo!");
            return;
        }

        Consulta consulta = consultaController.cadastrar(animalSelecionado, data, motivo);

        modelo.addRow(new Object[]{
            consulta.getId(),
            consulta.getAnimal().getNome(),
            consulta.getData(),
            consulta.getMotivo()
        });

        JOptionPane.showMessageDialog(this, "Consulta agendada com sucesso!");
        txtData.setText("");
        txtMotivo.setText("");
    }

    private void atualizarTabela() {
        modelo.setRowCount(0);
        List<Consulta> consultas = consultaController.listar();
        if (consultas != null) {
            for (Consulta c : consultas) {
                modelo.addRow(new Object[]{
                    c.getId(),
                    c.getAnimal().getNome(),
                    c.getData(),
                    c.getMotivo()
                });
            }
        }
    }
}