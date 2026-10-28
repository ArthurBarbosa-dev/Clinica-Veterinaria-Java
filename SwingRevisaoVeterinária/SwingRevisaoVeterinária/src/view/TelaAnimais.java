package view;
//importações awt (biblioteca gráfica original do java) e swing (modernização do awt)
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
//importações dos controllers e models 
import controller.AnimalController;
import controller.DonoController;
import model.Animal;
import model.Dono;

public class TelaAnimais extends JFrame {

    private JTextField txtNome, txtEspecie, txtRaca; //caixa de texto que será preenchida
    private JComboBox<Dono> cbDono; //menu suspenso com o nome dos tutores
    private JTable tabela; // interface gráfica da tabela do DefaultTableModel
    private DefaultTableModel modelo; //é a matriz com a tabela propriamente dita
    private AnimalController animalController;
    private DonoController donoController;
    private JButton btnExcluir;

    public TelaAnimais(AnimalController animalController, DonoController donoController) { //construtor da classe
        this.animalController = animalController;
        this.donoController = donoController;

        setTitle("Cadastro de Animais");
        setSize(500, 450);
        setLocationRelativeTo(null); //centraliza a janela no meio da tela
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); //fecha apenas a TelaAnimais e libera a memória, nao encerra o programa todo de uma vez
        setLayout(new BorderLayout()); //regra de organização da tela. "N S L O centro"

        // Painel do Formulario
        JPanel painelFormulario = new JPanel(new GridLayout(5, 2, 5, 5)); //5 linhas, 2 colunas, 5pixels vert e 5 pixels horizontal
        painelFormulario.add(new JLabel("Nome:")); //Titulo da caixa de texto Nome
        txtNome = new JTextField(); //oq for preenchido na caixa de texto será guardado na variavel txtNome
        painelFormulario.add(txtNome); //vai ser salvo no formulário (como se o formulário não é uma arraylist?)

        painelFormulario.add(new JLabel("Espécie:"));
        txtEspecie = new JTextField();
        painelFormulario.add(txtEspecie);

        painelFormulario.add(new JLabel("Raça:"));
        txtRaca = new JTextField();
        painelFormulario.add(txtRaca);

        painelFormulario.add(new JLabel("Dono:"));
        cbDono = new JComboBox<>();
        painelFormulario.add(cbDono);

        JButton btnCadastrar = new JButton("Cadastrar");
        painelFormulario.add(btnCadastrar); //quando apertar o botão ele vai guardar as informações das caixas de texto acima no formulário

        // Tabela
        modelo = new DefaultTableModel(new Object[]{"ID", "Nome", "Espécie", "Raça", "Dono"}, 0); //tabela de informações mesmo (matriz). (0 é a quantidade inicial de linhas)
        tabela = new JTable(modelo); //JTable é a interface gráfica da janela
        JScrollPane scroll = new JScrollPane(tabela);
        
        //Painel inferior com botão excluir
        JPanel painelInferior = new JPanel(new FlowLayout(FlowLayout.CENTER));
        btnExcluir = new JButton("Excluir animal selecionado");
        painelInferior.add(btnExcluir);

        add(painelFormulario, BorderLayout.NORTH); //o painel de formulario que ja foi cadastrado aparecerá na janela na parte superior ("NORTH")
        add(scroll, BorderLayout.CENTER);
        add(painelInferior, BorderLayout.SOUTH);

        // Ações
        btnCadastrar.addActionListener(e -> cadastrarAnimal()); //"e" = ActionEvent, expressão lambda, quando o usuário clica no btn
        btnExcluir.addActionListener(e -> excluirAnimal());

        // Carregar dados iniciais
        carregarDonosNoComboBox(); //Donos ja foram cadastrados antes dos animais, ok, mas oq é ComboBox e por que só donos tem isso?
        atualizarTabela();
    }

    private void carregarDonosNoComboBox() {
        cbDono.removeAllItems(); //removeu tudo do menu suspenso para não duplicar itens (garante que as informações do menu suspenso sempre estejam atualizadas)
        List<Dono> donos = donoController.listar();
        for (Dono d : donos) {
            cbDono.addItem(d); // inserindo novamente donos um a um no menu suspenso
        }
    }

    private void cadastrarAnimal() {
        String nome = txtNome.getText();
        String especie = txtEspecie.getText();
        String raca = txtRaca.getText();
        Dono donoSelecionado = (Dono) cbDono.getSelectedItem();

        if (donoSelecionado == null) { //garantia de que sempre haja um dono
            JOptionPane.showMessageDialog(this, "Cadastre um dono primeiro!");
            return;
        }

        Animal animal = animalController.cadastrar(nome, especie, raca, donoSelecionado);

        modelo.addRow(new Object[]{ //adicionou a linha com as informações cadastradas
            animal.getId(),
            animal.getNome(),
            animal.getEspecie(),
            animal.getRaca(),
            animal.getDono().getNome()
        });

        txtNome.setText(""); //limpa a caixa de texto
        txtEspecie.setText(""); //limpa a caixa de texto
        txtRaca.setText(""); //limpa a caixa de texto
        JOptionPane.showMessageDialog(this, "Animal cadastrado com sucesso!"); 
    }

    private void atualizarTabela() {
        modelo.setRowCount(0); //redefine o numero de linhas para 0
        for (Animal a : animalController.listar()) { //busca a linha atualizada no banco de dados
            modelo.addRow(new Object[]{ //a tabela é redesenhada novamente linha por linha
                a.getId(),
                a.getNome(),
                a.getEspecie(),
                a.getRaca(),
                a.getDono().getNome()
            });
        }
    }
    
    private void excluirAnimal() {
    	int linhaSelecionada = tabela.getSelectedRow();
    	
    	if(linhaSelecionada == -1) {
    		JOptionPane.showMessageDialog(this, "Selecione um tutor na tabela para excluir");
    		return;
    	}
    	
    	int id = (int) modelo.getValueAt(linhaSelecionada, 0);
    	
    	int confirmacao = JOptionPane.showConfirmDialog(this, "Tem certeza que deseja excluir esse animal?");
    	if(confirmacao == JOptionPane.YES_OPTION) {
    		animalController.excluir(id);
    		atualizarTabela();
    		JOptionPane.showMessageDialog(this, "Animal excluído com sucesso!");
    	}
    }
    
}