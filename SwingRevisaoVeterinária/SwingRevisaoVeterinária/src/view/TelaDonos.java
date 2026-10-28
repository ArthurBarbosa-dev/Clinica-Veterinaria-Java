package view;

import java.util.List;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.JButton; 
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import controller.DonoController;
import model.Dono;


public class TelaDonos extends JFrame {
	//controladora dos donos para permitir cadastro
    private DonoController donoController;

    //declaração dos componentes
    private JTextField txtNome;
    private JTextField txtTelefone;
    
    //botões
    private JButton btnCadastrar;
    private JButton btnExcluir;

    //declaração da tabela e o modelo que ela segue
    private JTable tabela;
    private DefaultTableModel modelo;

    public TelaDonos(DonoController donoController) {

        this.donoController = donoController;

        configurarJanela(); 
        criarComponentes();
        atualizarTabela();
    }

    private void configurarJanela() {

        setTitle("Cadastro de Tutores");

        setSize(600, 400);
        
        //encerra o processo da tela ao fechar mas não derruba a aplicação
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        setLocationRelativeTo(null);
    }

    private void criarComponentes() {

    		//painel do formulário de cadastro
        JPanel painelFormulario = new JPanel();

        painelFormulario.setLayout(new GridLayout(2, 2, 10, 10));
        //layout de grid, os dois primeiros argumentos representam as dimensões da matriz de compopnentes
        //os dois ultimos o espaçamento entre os componentes
        
        //campos para o preenchimento de dados
        JLabel lblNome =new JLabel("Nome:");
        txtNome =new JTextField();

        JLabel lblTelefone =new JLabel("Telefone:");
        txtTelefone =new JTextField();

        painelFormulario.add(lblNome);
        painelFormulario.add(txtNome);
        painelFormulario.add(lblTelefone);
        painelFormulario.add(txtTelefone);

        // botão de caaastro e seu action listener
        btnCadastrar = new JButton("Cadastrar");
        btnCadastrar.addActionListener(e -> cadastrarTutor());
        
        //botão de excluir
        btnExcluir = new JButton("Excluir");
        btnExcluir.addActionListener(e -> excluirTutor());
        
        //painel inferior
        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        painelBotoes.add(btnCadastrar);
        painelBotoes.add(btnExcluir);
        
        //Modelo que serve de base para a criação da tabela
        String[] colunas = {"ID","Nome","Telefone"};
        modelo =new DefaultTableModel(colunas,0);

        // Tabela e o Scrollpane, que permite que ela possa ser scrollada conforme seu tamanho
        tabela =new JTable(modelo);
        JScrollPane scroll =new JScrollPane(tabela);

        //Adição dos componentes na tela
        add(painelFormulario, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);
        add(painelBotoes, BorderLayout.SOUTH);
    }

    private void cadastrarTutor() {

        String nome =txtNome.getText();
        String telefone = txtTelefone.getText();

        if (nome.trim().isEmpty() || telefone.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Preencha o nome e o telefone!");
            return;
        }
        
        // Cadastra através do Controller e retorna o objeto do dono cadastrado
        Dono dono = donoController.cadastrar(nome,telefone);
        // Adiciona o tutor na tabela
        modelo.addRow(new Object[] {dono.getId(),dono.getNome(),dono.getTelefone()});

        //pop-up de confirmação de sucesso
        JOptionPane.showMessageDialog( this,"Dono cadastrado com sucesso!");

        // Limpa os campos após operação
        txtNome.setText("");
        txtTelefone.setText("");

        //Seleciona o campo de nome para já inserir novos dados
        txtNome.requestFocus();
    }
    
    private void atualizarTabela() {
    	modelo.setRowCount(0);
    	List<Dono> donos = donoController.listar();
    	if(donos != null) {
    		for(Dono d : donos) {
    			modelo.addRow(new Object[] {d.getId(), d.getNome(), d.getTelefone()});
    		}
    	}
    }
    
    private void excluirTutor() {
    	int linhaSelecionada = tabela.getSelectedRow();
    	
    	if(linhaSelecionada == -1) {
    		JOptionPane.showMessageDialog(this, "Selecione um tutor na tabela para excluir.");
    		return;
    	}
    	
    	int id = (int) modelo.getValueAt(linhaSelecionada, 0); // não entendi essa forma de declarar o tipo da variavel usando parenteses "(int)"
    	
    	int confirmacao = JOptionPane.showConfirmDialog(this, "Tem certeza que deseja excluir esse dono?");
    	if(confirmacao == JOptionPane.YES_OPTION) {
    		donoController.excluir(id);
    		atualizarTabela();
    		JOptionPane.showMessageDialog(this, "Dono excluído com sucesso!");
    	}
    }
}