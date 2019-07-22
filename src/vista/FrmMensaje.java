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

public class FrmMensaje extends JDialog implements ActionListener, KeyListener{

	private static final long serialVersionUID = 1L;
	public final JPanel jpMensajePrincipal = new JPanel();
	public JButton btnOk, btnNo, btnCancel;
	public static int valor = 0;
	public JLabel lblTitMgs;
	public JTextArea txtaMgs,txtaMgs2;
	private ImageIcon iipregunta;
	private Icon ipregunta;
	public String mensajerecibida, mensajerecibida2;
/*	public static void main(String[] args) {
	try {
		FrmMensaje dialog = new FrmMensaje(null, true);
		dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		dialog.setVisible(true);
	} catch (Exception e) {
		e.printStackTrace();
	}
	}*/
	public FrmMensaje(FrmMensaje frmMensaje, boolean modal) {
		super(frmMensaje,modal);
		iniciarComponentes();
	}

	private void iniciarComponentes() {
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 350, 140);
		setLocationRelativeTo(null);
		jpMensajePrincipal.setFocusTraversalKeysEnabled(false);
		jpMensajePrincipal.setBorder(new LineBorder(Color.WHITE));
		jpMensajePrincipal.setBackground(new Color(205, 92, 92));
		jpMensajePrincipal.setLayout(null);
		getContentPane().add(jpMensajePrincipal, BorderLayout.CENTER);
		setTitle("Mensaje de Pregunta!");
		setLocationRelativeTo(null);
		//setResizable(false);
		
		lblTitMgs = new JLabel();
		lblTitMgs.setBounds(-10, -6, 70, 70);
		iipregunta= new ImageIcon("iconos/pregunta.png");
		ipregunta = new ImageIcon(iipregunta.getImage().getScaledInstance(lblTitMgs.getWidth(),lblTitMgs.getHeight(), Image.SCALE_DEFAULT));
		lblTitMgs.setIcon(ipregunta);
		jpMensajePrincipal.add(lblTitMgs);
		
		txtaMgs = new JTextArea();
		txtaMgs.setBounds(50, 14, 283, 30);
		txtaMgs.setFont(new Font("Arial", Font.PLAIN | Font.PLAIN, 13));
		txtaMgs.setForeground(Color.WHITE);
		txtaMgs.setBackground(null);
		txtaMgs.setEditable(false);
		txtaMgs.setBorder(null);
		txtaMgs.setLineWrap(true);
		txtaMgs.setWrapStyleWord(true);
		txtaMgs.setFocusable(false);
		jpMensajePrincipal.add(txtaMgs);
		
		txtaMgs2 = new JTextArea();
		txtaMgs2.setBounds(60, 46, 250, 18);
		txtaMgs2.setFont(new Font("Arial", Font.ITALIC | Font.BOLD, 13));
		txtaMgs2.setForeground(Color.white);
		txtaMgs2.setBackground(null);
		txtaMgs2.setEditable(false);
		txtaMgs2.setBorder(null);
		txtaMgs2.setLineWrap(true);
		txtaMgs2.setWrapStyleWord(true);
		txtaMgs2.setFocusable(false);
		jpMensajePrincipal.add(txtaMgs2);
		
		btnOk =  new JButton("SI");
		btnOk.setBounds(80, 70, 80, 25);
		btnOk.setFont(new Font("Arial", Font.BOLD | Font.CENTER_BASELINE, 20));
		btnOk.addActionListener(this);
		btnOk.addKeyListener(this);
		jpMensajePrincipal.add(btnOk);
		
		btnNo =  new JButton("NO");
		btnNo.setBounds(200, 70, 80, 25);
		btnNo.setFont(new Font("Arial", Font.BOLD | Font.CENTER_BASELINE, 20));
		btnNo.addActionListener(this);
		btnNo.addKeyListener(this);
		jpMensajePrincipal.add(btnNo);
		//setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==btnOk){
			valor = 1;
			dispose();
		}else if(e.getSource()==btnNo){
			valor = 2;
			dispose();
		}
	}
	@Override
	public void keyPressed(KeyEvent arg0) {
		if(arg0.getSource()==btnOk){
			if(arg0.getKeyCode()==KeyEvent.VK_ENTER){
				valor = 1;
				dispose();
			}
		}else if(arg0.getSource()==btnNo){
			if(arg0.getKeyCode()==KeyEvent.VK_ENTER){
				valor = 2;
				dispose();
			}
		}
		if(arg0.getKeyCode()==KeyEvent.VK_LEFT){
			btnOk.requestFocus();
		}else if(arg0.getKeyCode()==KeyEvent.VK_RIGHT){
			btnNo.requestFocus();
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
