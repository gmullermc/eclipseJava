import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.Border;

public class mainMenu implements ActionListener{
	static JComponent nomeProblema;
	static JTextField nomeTexto;
	static JTextArea textoProblema;
	static JButton button;
	static JButton buttonDrop;
	static JLabel sucesso;
	static JLabel cabecalho;
	static JLabel descProblema;
	static Statement statement;
	static Connection connection;
	
	public static void main(String[] args) {
		configurarDb();
		configurarJanela();											
	}
			
	private static void configurarDb()
	{
		connection = null;
		try {
			connection = DriverManager.getConnection("jdbc:sqlite:base.db");
				statement = connection.createStatement();
				statement.setQueryTimeout(30);
				
				
				statement.executeUpdate("DROP TABLE IF EXISTS Problemas");
				statement.executeUpdate("CREATE TABLE IF NOT EXISTS Problemas (nome, problema)");
				
			} catch(SQLException e) {
				System.err.println(e.getMessage());
		}
	}
	private static void configurarJanela()
	{
		JPanel panel = new JPanel();
		JFrame frame = new JFrame();
		frame.setSize(800, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.add(panel);
	
		panel.setLayout(null);
		
		cabecalho = new JLabel("Bem-Vindo(a) ao Sistema de Reclamações da ETE Gil Rodrigues");
		cabecalho.setBounds(200, 20, 500, 25);
		panel.add(cabecalho);
					
		nomeProblema = new JLabel("Problema");
		nomeProblema.setBounds(10, 60, 125, 25);
		panel.add(nomeProblema);
		
		nomeTexto = new JTextField();
		nomeTexto.setBounds(100, 60, 165, 25);
		panel.add(nomeTexto);
		
		descProblema = new JLabel("Descrição");
		descProblema.setBounds(10, 100, 100, 25);
		panel.add(descProblema);
		
		textoProblema = new JTextArea();
		textoProblema.setBounds(100, 100, 300, 100);
		Border border = BorderFactory.createLineBorder(Color.black);
		textoProblema.setBorder(BorderFactory.createCompoundBorder(border, BorderFactory.createEmptyBorder(10, 10, 10, 10)));
		textoProblema.append("");
		textoProblema.append("\n");
		panel.add(textoProblema);
		
		button = new JButton("Enviar");
		button.setBounds(10, 250, 80, 25);
		button.addActionListener(new mainMenu());
		panel.add(button);
		

	}
	@Override
	public void actionPerformed(ActionEvent e) {
		
		String user = nomeTexto.getText();
		String prob = textoProblema.getText();
		System.out.println("Problema: " + user);
		System.out.println("Descrição do Problema: " + prob);
		try {
			statement.executeUpdate("INSERT INTO Problemas (nome, problema) VALUES ('"+user+"', '"+prob+"' )");
			ResultSet rs = statement.executeQuery("SELECT * FROM Problemas");
			  while(rs.next()) {
			    // Ler os dados inseridos			   
			   
			  }
		} catch (SQLException e1) {
			
			e1.printStackTrace();
		}
		
		
	}
}