package vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.LineBorder;

public class FrmDecision extends JDialog implements ActionListener, KeyListener{

	private static final long serialVersionUID = 1L;
	public final JPanel jpMensajePrincipal = new JPanel();
	public JButton btnSala, btnPersona, btnCancelar;
	public int valorResultado = 0;
	public JLabel lblTitMgs;
	public JTextArea txtaMgs;
	private ImageIcon iipregunta;
	private Icon ipregunta;
/*	public static void main(String[] args) {
	try {
		FrmMensaje dialog = new FrmMensaje(null, true);
		dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		dialog.setVisible(true);
	} catch (Exception e) {
		e.printStackTrace();
	}
	}*/
	public FrmDecision(FrmDecision frmMensaje, boolean modal) {
		super(frmMensaje,modal);
		iniciarComponentes();
	}

	private void iniciarComponentes() {
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 370, 125);
		setLocationRelativeTo(null);
		jpMensajePrincipal.setFocusTraversalKeysEnabled(false);
		jpMensajePrincipal.setBorder(new LineBorder(Color.CYAN));
		jpMensajePrincipal.setBackground(new Color(75, 149, 145));
		jpMensajePrincipal.setLayout(null);
		getContentPane().add(jpMensajePrincipal, BorderLayout.CENTER);
		setTitle("Mensaje de Consulta");
		setLocationRelativeTo(null);
		//setResizable(false);
		
		lblTitMgs = new JLabel();
		lblTitMgs.setBounds(-10, -6, 70, 70);
		iipregunta= new ImageIcon("iconos/pregunta.png");
		ipregunta = new ImageIcon(iipregunta.getImage().getScaledInstance(lblTitMgs.getWidth(),lblTitMgs.getHeight(), Image.SCALE_DEFAULT));
		lblTitMgs.setIcon(ipregunta);
		jpMensajePrincipal.add(lblTitMgs);
		
		txtaMgs = new JTextArea();
		txtaMgs.setBounds(80, 18, 283, 30);
		txtaMgs.setFont(new Font("Arial", Font.PLAIN | Font.PLAIN, 15));
		txtaMgs.setForeground(Color.WHITE);
		txtaMgs.setBackground(null);
		txtaMgs.setEditable(false);
		txtaMgs.setBorder(null);
		txtaMgs.setLineWrap(true);
		txtaMgs.setWrapStyleWord(true);
		txtaMgs.setFocusable(false);
		jpMensajePrincipal.add(txtaMgs);
		
		btnSala =  new JButton("SALA");
		btnSala.setBounds(48, 50, 70, 25);
		btnSala.setFont(new Font("Arial", Font.BOLD | Font.CENTER_BASELINE, 12));
		btnSala.addActionListener(this);
		btnSala.addKeyListener(this);
		jpMensajePrincipal.add(btnSala);
		
		btnPersona =  new JButton("PERSONA");
		btnPersona.setBounds(125, 50, 108, 25);
		btnPersona.setFont(new Font("Arial", Font.BOLD | Font.CENTER_BASELINE, 12));
		btnPersona.addActionListener(this);
		btnPersona.addKeyListener(this);
		jpMensajePrincipal.add(btnPersona);
		
		btnCancelar =  new JButton("CANCELAR");
		btnCancelar.setBounds(240, 50, 108, 25);
		btnCancelar.setFont(new Font("Arial", Font.BOLD | Font.CENTER_BASELINE, 12));
		btnCancelar.addActionListener(this);
		btnCancelar.addKeyListener(this);
		jpMensajePrincipal.add(btnCancelar);
		//setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==btnSala){
			valorResultado = 1;
			dispose();
		}else if(e.getSource()==btnPersona){
			valorResultado = 2;
			dispose();
		}else if(e.getSource()==btnCancelar){
			valorResultado = 3;
			dispose();
		}
	}
	@Override
	public void keyPressed(KeyEvent arg0) {
		if(arg0.getSource()==btnSala){
			if(arg0.getKeyCode()==KeyEvent.VK_ENTER){
				valorResultado = 1;
				dispose();
			}else if(arg0.getKeyCode()==KeyEvent.VK_RIGHT){
				btnPersona.requestFocus();
			}
		}else if(arg0.getSource()==btnPersona){
			if(arg0.getKeyCode()==KeyEvent.VK_ENTER){
				valorResultado = 2;
				dispose();
			}else if(arg0.getKeyCode()==KeyEvent.VK_RIGHT){
				btnCancelar.requestFocus();
			}else if(arg0.getKeyCode()==KeyEvent.VK_LEFT){
				btnSala.requestFocus();
			}
		}else if(arg0.getSource()==btnCancelar){
			if(arg0.getKeyCode()==KeyEvent.VK_ENTER){
				valorResultado = 3;
				dispose();
			}else if(arg0.getKeyCode()==KeyEvent.VK_LEFT){
				btnPersona.requestFocus();
			}
		}
		if(arg0.getKeyCode()==KeyEvent.VK_ESCAPE){
			dispose();
		}
	}
	@Override
	public void keyReleased(KeyEvent arg0) {}
	@Override
	public void keyTyped(KeyEvent arg0) {}	
}
