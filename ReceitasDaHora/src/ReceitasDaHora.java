import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;


public class ReceitasDaHora extends JFrame {
    private JTextField searchField;
    private JList<String> listaReceita;
    private JTextArea recipeDetails;
    private JButton botaoPesquisa;
    private JButton botaoAdicionar;
    private JButton botaoEditar;
    private JButton botaoExcluir;
    private DefaultListModel<String> modeloListaReceita; 

    public ReceitasDaHora() {
        //Cria a janela
        setTitle("Receitas da Hora");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Cria a barra de pesquisa
        JPanel barraDePesquisa = new JPanel();
        searchField = new JTextField(20);
        botaoPesquisa = new JButton("Buscar");
        barraDePesquisa.add(searchField);
        barraDePesquisa.add(botaoPesquisa);

        //Lista de receitas
        modeloListaReceita = new DefaultListModel<>(); // Step 1
        listaReceita = new JList<>(modeloListaReceita); // Step 1
        JScrollPane listaReceitaScrollPane = new JScrollPane(listaReceita);
        

        //Detalhes da receita
        recipeDetails = new JTextArea();
        JScrollPane recipeDetailsScrollPane = new JScrollPane(recipeDetails);
        recipeDetailsScrollPane.setPreferredSize(new Dimension(300, 300));

        
        JPanel buttonPanel = new JPanel();
        botaoAdicionar = new JButton("Adicionar");
        botaoEditar = new JButton("Editar");
        botaoExcluir = new JButton("Deletar");
        buttonPanel.add(botaoAdicionar);
       // buttonPanel.add(botaoEditar);
        buttonPanel.add(botaoExcluir);

        
        add(barraDePesquisa, BorderLayout.NORTH);
        add(listaReceitaScrollPane, BorderLayout.WEST);
        add(recipeDetailsScrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
        
        //Adiciona itens da database na lista
        //Quando tentei editar a base de dados, deu erro em tudo, favor não mexer
        try {
        	Class.forName("org.sqlite.JDBC");
            String nome = "nome";
            String ingredientes = "ingredientes";
            String mododepreparo = "mododepreparo";
            Connection connection = DriverManager.getConnection("jdbc:sqlite:recipes.db");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM recipes");
            while (resultSet.next()) {
                modeloListaReceita.addElement(resultSet.getString("name"));
            }
            connection.close();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        
        
        botaoPesquisa.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String keyword = searchField.getText();

                try {
                    Connection connection = DriverManager.getConnection("jdbc:sqlite:recipes.db");
                    PreparedStatement statement = connection.prepareStatement("SELECT * FROM recipes WHERE name LIKE ?");
                    statement.setString(1, "%" + keyword + "%");
                    ResultSet resultSet = statement.executeQuery();
                    modeloListaReceita.clear();
                    while (resultSet.next()) {
                        modeloListaReceita.addElement(resultSet.getString("name"));
                    }
                    connection.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });
        botaoAdicionar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                AdicionarReceita adder = new AdicionarReceita();
                adder.setVisible(true);
            }
        });

//O botão de edição tá sem funcionalidade, ainda não tive tempo pra implementar o editor, mas a classe tá criada
        
        /*botaoEditar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String receitaSelecionada = listaReceita.getSelectedValue();
                if (receitaSelecionada != null) {
                    EditorDeReceita editor = new EditorDeReceita(receitaSelecionada);
                    editor.setVisible(true);
                }
            }
        });
*/


        botaoExcluir.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String receitaSelecionada = listaReceita.getSelectedValue();
                if (receitaSelecionada == null) {
                    JOptionPane.showMessageDialog(null, "Nenhuma receita selecionada");
                    return;
                }
                int confirmation = JOptionPane.showConfirmDialog(null, "Deseja excluir a receita " + receitaSelecionada + "?");
                if (confirmation != JOptionPane.YES_OPTION) {
                    return;
                }
                try {
                    Connection connection = DriverManager.getConnection("jdbc:sqlite:recipes.db");
                    PreparedStatement statement = connection.prepareStatement("DELETE FROM recipes WHERE name = ?");
                    statement.setString(1, receitaSelecionada);
                    statement.executeUpdate();
                    modeloListaReceita.removeElement(receitaSelecionada);
                    connection.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });



        listaReceita.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {

                String receitaSelecionada = listaReceita.getSelectedValue();

                try {
                    String query = "SELECT ingredients, instructions FROM recipes WHERE name = ?";
                    Connection connection = DriverManager.getConnection("jdbc:sqlite:recipes.db");
                    PreparedStatement statement = connection.prepareStatement(query);
                    statement.setString(1, receitaSelecionada);
                    ResultSet resultSet = statement.executeQuery();

                    if (resultSet.next()) {
                        String ingredients = resultSet.getString("ingredients");
                        String instructions = resultSet.getString("instructions");
                        recipeDetails.setText("Ingredientes:\n" + ingredients + "\n\nModo de Preparo:\n" + instructions);
                    } else {
                        recipeDetails.setText("Nenhuma receita selecionada");
                    }

                    connection.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });

        setVisible(true);
    }

    public void addRecipe(String recipeName) {
        modeloListaReceita.addElement(recipeName);
    }
}
