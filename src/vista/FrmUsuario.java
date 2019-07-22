package vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.SystemColor;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import utilidades.RenderizarCeldaImg;
import javax.swing.JPasswordField;
import javax.swing.JTabbedPane;
import javax.swing.JComboBox;
import javax.swing.JCheckBox;

public class FrmUsuario extends JDialog {
	private static final long serialVersionUID = 1L;
	private final JPanel pPrincipal = new JPanel();
	private JPanel pPieA;
	public JLabel lblBienvenida,lblNumRegU,lblUsuarioU,lblFechaRegA,lblFechaUltimaModificacion,
	lblPassword,lblRepetirPassword,lblNombres,lblApellidos,lblPregunta,lblRespuesta;
	public JSpinner spNumRegU;
	public JTextField txtEstadoU,txtApellidos,txtNombre,txtFechaRegU,txtUsuarioU,txtFechaUltMod;
	public JPasswordField pwdPassword,pwdRepetirPassword,pwdRespuesta;
	public JComboBox<String> cboPregunta;
	public JButton btnOkA, btnCancelA;
	public JCheckBox chckbxEliminar;
	public ImageIcon iinuevo,iiExcel,iisalir;
	public Icon inuevo,iexcel,isalir;
	
	public FrmUsuario(FrmUsuario frmUsuario, boolean modal) {
		super(frmUsuario, modal);
		inicializarValores();
	}
	public void inicializarValores(){
		
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 483, 384);
		pPrincipal.setFocusTraversalKeysEnabled(false);
		pPrincipal.setBorder(new LineBorder(new Color(0, 0, 0)));
		pPrincipal.setBackground(new Color(47, 79, 79));
		pPrincipal.setLayout(null);
		getContentPane().add(pPrincipal, BorderLayout.CENTER);
		setLocationRelativeTo(null);
		
		lblBienvenida = new JLabel("REGISTRAR USUARIO");
		lblBienvenida.setBorder(new LineBorder(new Color(255, 165, 0), 1, true));
		lblBienvenida.setOpaque(true);
		lblBienvenida.setBackground(new Color(205, 92, 92));
		lblBienvenida.setForeground(Color.white);
		lblBienvenida.setHorizontalAlignment(SwingConstants.CENTER);
		lblBienvenida.setFont(new Font("Arial Narrow", Font.BOLD, 15));
		lblBienvenida.setBounds(1, 4, 465, 18);
		pPrincipal.add(lblBienvenida);
		
		lblNumRegU = new JLabel("Nº Registro:");
		lblNumRegU.setForeground(Color.LIGHT_GRAY);
		lblNumRegU.setFont(new Font("Arial Narrow", Font.BOLD | Font.ITALIC, 14));
		lblNumRegU.setBounds(10,23,65,22);
		pPrincipal.add(lblNumRegU);
		
		spNumRegU = new JSpinner();
		spNumRegU.setFocusable(false);
		spNumRegU.setModel(new SpinnerNumberModel(new Integer(0), new Integer(0), null, new Integer(1)));
		spNumRegU.setFont(new Font("Arial Narrow", Font.BOLD | Font.ITALIC, 20));
		spNumRegU.setBounds(10,43,80,22);
		pPrincipal.add(spNumRegU);
		
		txtEstadoU = new JTextField(){
			@Override protected void paintComponent(Graphics g) {
			if (!isOpaque()) {
		          int w = getWidth() - 1;
		          int h = getHeight() - 1;
		          Graphics2D g2 = (Graphics2D) g.create();
		          g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		          g2.setPaint(UIManager.getColor("TextField.foreground"));
		          g2.fillRoundRect(0, 0, w, h, h, h);
		          g2.setPaint(Color.WHITE);//COLOR BORDE
		          g2.drawRoundRect(0, 0, w, h, h, h);
		          g2.dispose();
		        }
		        super.paintComponent(g);
		      }
			@Override public void updateUI() {
				super.updateUI();
		    	setOpaque(false);
		    	setBackground(new Color(0x0, true));
		    	setBorder(BorderFactory.createEmptyBorder(4, 8, 4, 8));
			}
		};
		txtEstadoU.setForeground(Color.WHITE);
		txtEstadoU.setFont(new Font("Arial Narrow", Font.BOLD | Font.ITALIC, 12));
		txtEstadoU.setCaretColor(Color.WHITE);
		txtEstadoU.setBounds(100, 44, 80, 24);
		pPrincipal.add(txtEstadoU);
		
		chckbxEliminar = new JCheckBox("Eliminar");
		chckbxEliminar.setFont(new Font("Arial Narrow", Font.BOLD, 12));
		chckbxEliminar.setBounds(198, 43, 65, 23);
		pPrincipal.add(chckbxEliminar);
		
		lblUsuarioU = new JLabel("Usuario:");
		lblUsuarioU.setForeground(Color.LIGHT_GRAY);
		lblUsuarioU.setFont(new Font("Arial Narrow", Font.BOLD | Font.ITALIC, 14));
		lblUsuarioU.setBounds(10,66,45,22);
		pPrincipal.add(lblUsuarioU);
		
		txtUsuarioU = new JTextField(){
			@Override protected void paintComponent(Graphics g) {
			if (!isOpaque()) {
		          int w = getWidth() - 1;
		          int h = getHeight() - 1;
		          Graphics2D g2 = (Graphics2D) g.create();
		          g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		          g2.setPaint(UIManager.getColor("TextField.foreground"));
		          g2.fillRoundRect(0, 0, w, h, h, h);
		          g2.setPaint(Color.WHITE);//COLOR BORDE
		          g2.drawRoundRect(0, 0, w, h, h, h);
		          g2.dispose();
		        }
		        super.paintComponent(g);
		      }
			@Override public void updateUI() {
				super.updateUI();
		    	setOpaque(false);
		    	setBackground(new Color(0x0, true));
		    	setBorder(BorderFactory.createEmptyBorder(4, 8, 4, 8));
			}
		};
		txtUsuarioU.setCaretColor(Color.WHITE);
		txtUsuarioU.setForeground(Color.WHITE);
		txtUsuarioU.setFont(new Font("Arial Narrow", Font.BOLD | Font.ITALIC, 13));
		txtUsuarioU.setBounds(10,85,170,25);
		pPrincipal.add(txtUsuarioU);
		
		lblFechaRegA = new JLabel("Fecha de Registro");
		lblFechaRegA.setForeground(Color.LIGHT_GRAY);
		lblFechaRegA.setFont(new Font("Arial Narrow", Font.BOLD | Font.ITALIC, 14));
		lblFechaRegA.setBounds(212,67,106,20);
		pPrincipal.add(lblFechaRegA);
		
		txtFechaRegU = new JTextField(){
			@Override protected void paintComponent(Graphics g) {
				if (!isOpaque()) {
			          int w = getWidth() - 1;
			          int h = getHeight() - 1;
			          Graphics2D g2 = (Graphics2D) g.create();
			          g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			          g2.setPaint(UIManager.getColor("TextField.foreground"));
			          g2.fillRoundRect(0, 0, w, h, h, h);
			          g2.setPaint(Color.cyan);//COLOR BORDE
			          g2.drawRoundRect(0, 0, w, h, h, h);
			          g2.dispose();
			        }
			        super.paintComponent(g);
			      }
				@Override public void updateUI() {
					super.updateUI();
			    	setOpaque(false);
			    	setBackground(new Color(0x0, true));
			    	setBorder(BorderFactory.createEmptyBorder(4, 8, 4, 8));
				}
		};
		txtFechaRegU.setFont(new Font("Arial Narrow", Font.BOLD, 14));
		txtFechaRegU.setFocusable(false);
		txtFechaRegU.setForeground(Color.CYAN);
		txtFechaRegU.setEditable(false);
		txtFechaRegU.setBounds(200,85,125,23);
		pPrincipal.add(txtFechaRegU);
		
		lblFechaUltimaModificacion = new JLabel("Fecha ult. Modificacion");
		lblFechaUltimaModificacion.setForeground(Color.LIGHT_GRAY);
		lblFechaUltimaModificacion.setFont(new Font("Arial Narrow", Font.BOLD | Font.ITALIC, 14));
		lblFechaUltimaModificacion.setBounds(335, 67, 125, 20);
		pPrincipal.add(lblFechaUltimaModificacion);
		
		txtFechaUltMod = new JTextField(){
			@Override protected void paintComponent(Graphics g) {
				if (!isOpaque()) {
			          int w = getWidth() - 1;
			          int h = getHeight() - 1;
			          Graphics2D g2 = (Graphics2D) g.create();
			          g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			          g2.setPaint(UIManager.getColor("TextField.foreground"));
			          g2.fillRoundRect(0, 0, w, h, h, h);
			          g2.setPaint(Color.cyan);//COLOR BORDE
			          g2.drawRoundRect(0, 0, w, h, h, h);
			          g2.dispose();
			        }
			        super.paintComponent(g);
			      }
				@Override public void updateUI() {
					super.updateUI();
			    	setOpaque(false);
			    	setBackground(new Color(0x0, true));
			    	setBorder(BorderFactory.createEmptyBorder(4, 8, 4, 8));
				}
		};
		txtFechaUltMod.setForeground(Color.CYAN);
		txtFechaUltMod.setFont(new Font("Arial Narrow", Font.BOLD, 14));
		txtFechaUltMod.setFocusable(false);
		txtFechaUltMod.setEditable(false);
		txtFechaUltMod.setBounds(335, 85, 125, 23);
		pPrincipal.add(txtFechaUltMod);
		
		lblPassword = new JLabel("Contraseña:");
		lblPassword.setForeground(Color.LIGHT_GRAY);
		lblPassword.setFont(new Font("Arial Narrow", Font.BOLD | Font.ITALIC, 14));
		lblPassword.setBounds(10, 110, 65, 22);
		pPrincipal.add(lblPassword);
		
		pwdPassword = new JPasswordField(){
			@Override protected void paintComponent(Graphics g) {
			if (!isOpaque()) {
		          int w = getWidth() - 1;
		          int h = getHeight() - 1;
		          Graphics2D g2 = (Graphics2D) g.create();
		          g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		          g2.setPaint(UIManager.getColor("TextField.foreground"));
		          g2.fillRoundRect(0, 0, w, h, h, h);
		          g2.setPaint(Color.WHITE);//COLOR BORDE
		          g2.drawRoundRect(0, 0, w, h, h, h);
		          g2.dispose();
		        }
		        super.paintComponent(g);
		      }
			@Override public void updateUI() {
				super.updateUI();
		    	setOpaque(false);
		    	setBackground(new Color(0x0, true));
		    	setBorder(BorderFactory.createEmptyBorder(4, 8, 4, 8));
			}
		};
		pwdPassword.setForeground(Color.WHITE);
		pwdPassword.setCaretColor(Color.WHITE);
		pwdPassword.setBounds(10, 130, 170, 25);
		pPrincipal.add(pwdPassword);
		
		lblRepetirPassword = new JLabel("Volver  ingresar su contraseña");
		lblRepetirPassword.setForeground(Color.LIGHT_GRAY);
		lblRepetirPassword.setFont(new Font("Arial Narrow", Font.BOLD | Font.ITALIC, 14));
		lblRepetirPassword.setBounds(200, 110, 170, 22);
		pPrincipal.add(lblRepetirPassword);
		
		pwdRepetirPassword = new JPasswordField(){
			@Override protected void paintComponent(Graphics g) {
			if (!isOpaque()) {
		          int w = getWidth() - 1;
		          int h = getHeight() - 1;
		          Graphics2D g2 = (Graphics2D) g.create();
		          g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		          g2.setPaint(UIManager.getColor("TextField.foreground"));
		          g2.fillRoundRect(0, 0, w, h, h, h);
		          g2.setPaint(Color.WHITE);//COLOR BORDE
		          g2.drawRoundRect(0, 0, w, h, h, h);
		          g2.dispose();
		        }
		        super.paintComponent(g);
		      }
			@Override public void updateUI() {
				super.updateUI();
		    	setOpaque(false);
		    	setBackground(new Color(0x0, true));
		    	setBorder(BorderFactory.createEmptyBorder(4, 8, 4, 8));
			}
		};
		pwdRepetirPassword.setForeground(Color.WHITE);
		pwdRepetirPassword.setCaretColor(Color.WHITE);
		pwdRepetirPassword.setBounds(200, 130, 260, 25);
		pPrincipal.add(pwdRepetirPassword);
		
		lblNombres = new JLabel("Nombres:");
		lblNombres.setForeground(Color.LIGHT_GRAY);
		lblNombres.setFont(new Font("Arial Narrow", Font.BOLD | Font.ITALIC, 14));
		lblNombres.setBounds(10, 155, 65, 22);
		pPrincipal.add(lblNombres);
		
		txtNombre = new JTextField(){
			@Override protected void paintComponent(Graphics g) {
			if (!isOpaque()) {
		          int w = getWidth() - 1;
		          int h = getHeight() - 1;
		          Graphics2D g2 = (Graphics2D) g.create();
		          g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		          g2.setPaint(UIManager.getColor("TextField.foreground"));
		          g2.fillRoundRect(0, 0, w, h, h, h);
		          g2.setPaint(Color.WHITE);//COLOR BORDE
		          g2.drawRoundRect(0, 0, w, h, h, h);
		          g2.dispose();
		        }
		        super.paintComponent(g);
		      }
			@Override public void updateUI() {
				super.updateUI();
		    	setOpaque(false);
		    	setBackground(new Color(0x0, true));
		    	setBorder(BorderFactory.createEmptyBorder(4, 8, 4, 8));
			}
		};
		txtNombre.setForeground(Color.WHITE);
		txtNombre.setFont(new Font("Arial Narrow", Font.BOLD | Font.ITALIC, 13));
		txtNombre.setCaretColor(Color.WHITE);
		txtNombre.setBounds(10, 175, 170, 24);
		pPrincipal.add(txtNombre);
		
		lblApellidos = new JLabel("Apellidos");
		lblApellidos.setForeground(Color.LIGHT_GRAY);
		lblApellidos.setFont(new Font("Arial Narrow", Font.BOLD | Font.ITALIC, 14));
		lblApellidos.setBounds(200, 155, 51, 22);
		pPrincipal.add(lblApellidos);
		
		txtApellidos = new JTextField(){
			@Override protected void paintComponent(Graphics g) {
			if (!isOpaque()) {
		          int w = getWidth() - 1;
		          int h = getHeight() - 1;
		          Graphics2D g2 = (Graphics2D) g.create();
		          g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		          g2.setPaint(UIManager.getColor("TextField.foreground"));
		          g2.fillRoundRect(0, 0, w, h, h, h);
		          g2.setPaint(Color.WHITE);//COLOR BORDE
		          g2.drawRoundRect(0, 0, w, h, h, h);
		          g2.dispose();
		        }
		        super.paintComponent(g);
		      }
			@Override public void updateUI() {
				super.updateUI();
		    	setOpaque(false);
		    	setBackground(new Color(0x0, true));
		    	setBorder(BorderFactory.createEmptyBorder(4, 8, 4, 8));
			}
		};
		txtApellidos.setForeground(Color.WHITE);
		txtApellidos.setFont(new Font("Arial Narrow", Font.BOLD | Font.ITALIC, 13));
		txtApellidos.setCaretColor(Color.WHITE);
		txtApellidos.setBounds(200, 175, 260, 24);
		pPrincipal.add(txtApellidos);
		
		lblPregunta = new JLabel("Pregunta de Seguridad");
		lblPregunta.setForeground(Color.LIGHT_GRAY);
		lblPregunta.setFont(new Font("Arial Narrow", Font.BOLD | Font.ITALIC, 14));
		lblPregunta.setBounds(170,203,125,22);
		pPrincipal.add(lblPregunta);
		
		cboPregunta = new JComboBox<String>();
		cboPregunta.setForeground(Color.RED);
		cboPregunta.setBounds(10, 223, 450, 25);
		pPrincipal.add(cboPregunta);
		
		lblRespuesta = new JLabel("Respuesta a la pregunta de Seguridad");
		lblRespuesta.setForeground(Color.LIGHT_GRAY);
		lblRespuesta.setFont(new Font("Arial Narrow", Font.BOLD | Font.ITALIC, 14));
		lblRespuesta.setBounds(138, 250, 203, 22);
		pPrincipal.add(lblRespuesta);
		
		pwdRespuesta = new JPasswordField(){
			@Override protected void paintComponent(Graphics g) {
			if (!isOpaque()) {
		          int w = getWidth() - 1;
		          int h = getHeight() - 1;
		          Graphics2D g2 = (Graphics2D) g.create();
		          g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		          g2.setPaint(UIManager.getColor("TextField.foreground"));
		          g2.fillRoundRect(0, 0, w, h, h, h);
		          g2.setPaint(Color.WHITE);//COLOR BORDE
		          g2.drawRoundRect(0, 0, w, h, h, h);
		          g2.dispose();
		        }
		        super.paintComponent(g);
		      }
			@Override public void updateUI() {
				super.updateUI();
		    	setOpaque(false);
		    	setBackground(new Color(0x0, true));
		    	setBorder(BorderFactory.createEmptyBorder(4, 8, 4, 8));
			}
		};
		pwdRespuesta.setForeground(Color.WHITE);
		pwdRespuesta.setFont(new Font("Arial Narrow", Font.BOLD | Font.ITALIC, 13));
		pwdRespuesta.setCaretColor(Color.WHITE);
		pwdRespuesta.setBounds(10, 270, 450, 24);
		pPrincipal.add(pwdRespuesta);
		
		pPieA = new JPanel();
		pPieA.setBackground(new Color(205, 92, 92));
		pPieA.setBounds(1,305,465,38);
		pPieA.setLayout(null);
		pPrincipal.add(pPieA);
		
		btnOkA = new JButton("REGISTRAR");
		btnOkA.setMargin(new Insets(1, 2, 2, 2));
		btnOkA.setHorizontalTextPosition(SwingConstants.LEFT);
		btnOkA.setBounds(235,3,120,33);
		pPieA.add(btnOkA);
		
		btnCancelA = new JButton("SALIR");
		btnCancelA.setMargin(new Insets(1, 2, 2, 2));
		btnOkA.setHorizontalTextPosition(SwingConstants.LEFT);
		btnCancelA.setBounds(370,3,90,33);
		iisalir= new ImageIcon("iconos/salir.png");
		isalir = new ImageIcon(iisalir.getImage().getScaledInstance(33,btnCancelA.getHeight(), Image.SCALE_DEFAULT));
		btnCancelA.setIcon(isalir);
		pPieA.add(btnCancelA);
		
	}
}
