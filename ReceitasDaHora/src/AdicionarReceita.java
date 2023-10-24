import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class AdicionarReceita extends JFrame {
    private JTextField campoNome;
    private JTextArea campoIngredientes;
    private JTextArea campoModoDePreparo;

    public AdicionarReceita() {
        setTitle("Adicionar Receita");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel mainPanel = new JPanel(new BorderLayout());
        JLabel nameLabel = new JLabel("Nome:");
        campoNome = new JTextField(20);
        JLabel ingredientsLabel = new JLabel("Ingredientes:");
        campoIngredientes = new JTextArea();
        JScrollPane ingredientsScrollPane = new JScrollPane(campoIngredientes);
        JLabel instructionsLabel = new JLabel("Modo de Preparo:");
        campoModoDePreparo = new JTextArea();
        JScrollPane instructionsScrollPane = new JScrollPane(campoModoDePreparo);
        JButton saveButton = new JButton("Salvar");

        JPanel inputPanel = new JPanel(new GridLayout(3, 1, 5, 5));
        inputPanel.add(nameLabel);
        inputPanel.add(campoNome);
        inputPanel.add(ingredientsLabel);
        inputPanel.add(ingredientsScrollPane);
        inputPanel.add(instructionsLabel);
        inputPanel.add(instructionsScrollPane);
        mainPanel.add(inputPanel, BorderLayout.CENTER);
        mainPanel.add(saveButton, BorderLayout.SOUTH);
        add(mainPanel);

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                String nome = campoNome.getText();
                String ingredientes = campoIngredientes.getText();
                String mododepreparo = campoModoDePreparo.getText();

                try {
                    Connection conn = DriverManager.getConnection("jdbc:sqlite:recipes.db");
                    Statement stmt = conn.createStatement();
                    String sql = "CREATE TABLE IF NOT EXISTS recipes ("
                            + "id INTEGER PRIMARY KEY,"
                            + "name TEXT,"
                            + "ingredientes TEXT,"
                            + "mododepreparo TEXT)";
                    stmt.executeUpdate(sql);

                    PreparedStatement insertStmt = conn.prepareStatement("INSERT INTO recipes (name, ingredients, instructions) VALUES (?, ?, ?)");
                    insertStmt.setString(1, nome);
                    insertStmt.setString(2, ingredientes);
                    insertStmt.setString(3, mododepreparo);
                    insertStmt.executeUpdate();
                    insertStmt.close();
                    conn.close();
                    JOptionPane.showMessageDialog(null, "Receita salva com sucesso!");
                    dispose();
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(null, "Erro ao salvar a receita: " + e.getMessage());
                }
            }
        });

        setVisible(true);
    }
}
