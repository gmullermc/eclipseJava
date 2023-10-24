import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class GUI implements ActionListener {

	private static JLabel userLabel;
	private static JTextField userText;
	private static JLabel senhaLabel;
	private static JPasswordField senha;
	private static JButton button;
	private static JLabel sucesso;
	
	public static void main(String[] args) {
	
		
		JPanel panel = new JPanel();
		JFrame frame = new JFrame();
		frame.setSize(350, 200);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.add(panel);
		
		panel.setLayout(null);
		
		userLabel = new JLabel("Usuário");
		userLabel.setBounds(10, 20, 80, 25);
		panel.add(userLabel);
		
		userText = new JTextField();
		userText.setBounds(100, 20, 165, 25);
		panel.add(userText);
		
		senhaLabel = new JLabel("Senha");
		senhaLabel.setBounds(10, 50, 80, 25);
		panel.add(senhaLabel);
		
		senha = new JPasswordField();
		senha.setBounds(100, 50, 165, 25);
		panel.add(senha);
		
		button = new JButton("Login");
		button.setBounds(10, 80, 80, 25);
		button.addActionListener(new GUI());
		panel.add(button);
		
		sucesso = new JLabel("");
		sucesso.setBounds(10, 110, 300, 25);
		panel.add(sucesso);
		sucesso.setText("");
		
		frame.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String user = userText.getText();
		String password = senha.getText();
		
		if (user.equals("Guilherme") && password.equals("Muller")) {
			sucesso.setText("Logado com Sucesso!");
		}
	}

}
