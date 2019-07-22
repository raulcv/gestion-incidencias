package vista;

import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Image;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JSeparator;
import java.awt.Insets;
import java.awt.Label;
import javax.swing.SwingConstants;

public class FrmLogin extends JFrame {

	private JPanel pPrincipal, pSecundario;
	public JButton btnConfigurarCuenta,btnCrearCuenta,btnIniciarSession,btnOlvidePassword;
	public JLabel lblUser,lblpassword,lblU;
	public JTextField txtUser;
	public JPasswordField pwdPassword;
	private JSeparator sOrizontal,sDiagonal;
	private ImageIcon iiUsu;
	private Icon iUsu;
	public FrmLogin() {
		iniciarComponentes();
	}

	private void iniciarComponentes() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		pPrincipal = new JPanel();
		pPrincipal.setBorder(new EmptyBorder(5, 5, 5, 5));
		pPrincipal.setLayout(new BorderLayout(0, 0));
		setLocationRelativeTo(null);
		setContentPane(pPrincipal);
		
		pSecundario = new JPanel();
		pSecundario.setLayout(null);
		pSecundario.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Inicio de session del sistema", TitledBorder.RIGHT, TitledBorder.TOP, null, new Color(0, 0, 51)));
		pPrincipal.add(pSecundario);
		
		btnConfigurarCuenta = new JButton("Configurar mi cuenta");
		btnConfigurarCuenta.setForeground(new Color(0, 100, 0));
		btnConfigurarCuenta.setMargin(new Insets(1, 1, 1, 1));
		btnConfigurarCuenta.setFont(new Font("Arial Narrow", Font.BOLD, 15));
		btnConfigurarCuenta.setBounds(10, 20, 141, 20);
		pSecundario.add(btnConfigurarCuenta);
		
		btnCrearCuenta = new JButton("crear una cuenta de usuario");
		btnCrearCuenta.setForeground(new Color(255, 153, 0));
		btnCrearCuenta.setFont(new Font("Arial Narrow", Font.BOLD, 18));
		btnCrearCuenta.setBounds(180, 20, 234, 25);
		pSecundario.add(btnCrearCuenta);
		
		sOrizontal = new JSeparator();
		sOrizontal.setBounds(170, 50, 250, 7);
		pSecundario.add(sOrizontal);
		
		lblUser = new JLabel("Usuario");
		lblUser.setFont(new Font("Arial Narrow", Font.BOLD, 15));
		lblUser.setBounds(275, 60, 50, 25);
		pSecundario.add(lblUser);
		
		txtUser = new JTextField("");
		txtUser.setFont(new Font("Arial Narrow", Font.BOLD, 15));
		txtUser.setBounds(180, 80, 234, 25);
		pSecundario.add(txtUser);
		
		lblpassword = new JLabel("Contraseña");
		lblpassword.setFont(new Font("Arial Narrow", Font.BOLD, 15));
		lblpassword.setBounds(268, 110, 75, 25);
		pSecundario.add(lblpassword);
		
		pwdPassword = new JPasswordField();
		pwdPassword.setBounds(180, 130, 234, 25);
		pSecundario.add(pwdPassword);
		
		btnIniciarSession = new JButton("INICIAR SESSION");
		btnIniciarSession.setFont(new Font("Arial Narrow", Font.BOLD, 18));
		btnIniciarSession.setBounds(214, 166, 176, 30);
		pSecundario.add(btnIniciarSession);
			
		btnOlvidePassword = new JButton("¿Olvidaste tu cuenta? Recuperar Cuenta");
		btnOlvidePassword.setForeground(Color.RED);
		btnOlvidePassword.setMargin(new Insets(1, 1, 1, 1));
		btnOlvidePassword.setFont(new Font("Arial Narrow", Font.BOLD, 14));
		btnOlvidePassword.setBounds(189, 225, 225, 17);
		pSecundario.add(btnOlvidePassword);
		
		lblU = new JLabel();
		iiUsu= new ImageIcon("iconos/usuario.png");
		iUsu = new ImageIcon(iiUsu.getImage().getScaledInstance(160,160, Image.SCALE_DEFAULT));
		lblU.setIcon(iUsu);
		lblU.setBounds(10, 42, 155, 168);
		pSecundario.add(lblU);
		
		sDiagonal = new JSeparator();
		sDiagonal.setOrientation(SwingConstants.VERTICAL);
		sDiagonal.setBounds(170, 50, 2, 195);
		pSecundario.add(sDiagonal);
		
	}
}
