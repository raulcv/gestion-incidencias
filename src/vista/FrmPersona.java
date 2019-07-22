package vista;

import java.awt.BorderLayout;
import java.awt.Color;
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
import javax.swing.JComboBox;
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
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import utilidades.RenderizarCeldaImg;

public class FrmPersona extends JDialog {

	private final JPanel jpPersonaPrincipal = new JPanel();
	private JPanel jpPieP, jpGrillaP;
	public JButton btnNuevoP,btnNuevoPaisP,btnNuevaAreaP,btnOkP, btnCancelP, btnExportarExcelP;
	public JLabel lblRegistroDePersonas,lblUsuarioP,lblNumRegP,lblRegistradorP,lblAreaP,lblApellidoP,
	lblEmailP,lblTelefonoP,lblPaisP,lblFechaRegP,lblFechaUltModP, lblNombreP, lblComentarioP, lblNombrePB, lblTotalItemsP;
	public JSpinner spNumRegP;
	public JTextField txtIdUsuarioP,txtUsuarioP,txtFechaRegP,txtNombreP,txtApellidoP,txtRegistradorP,
	txtEmailP,txtTelefonoP,txtFechaUltModP, txtNombrePB;
	public JTextArea txtaComentarioP;
	public JTable tlbListaP;
	public DefaultTableModel dtmP;
	public JScrollPane JscpListaP;
	public ImageIcon iinuevo,iinuevaarea,iinuevopais,iiExcel,iisalir;
	public Icon inuevo,inuevaarea,inuevopais,iexcel,isalir;
	public JComboBox<String> cboAreaP,cboPaisP;

	public FrmPersona(FrmPersona frmPersona, boolean modal) {
		super(frmPersona,modal);
		InicializarValores();
	}
	public void InicializarValores(){
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 810, 493);
		jpPersonaPrincipal.setFocusable(false);
		jpPersonaPrincipal.setFocusTraversalKeysEnabled(false);
		jpPersonaPrincipal.setBorder(new LineBorder(new Color(0, 0, 0)));
		jpPersonaPrincipal.setBackground(new Color(47, 79, 79));
		jpPersonaPrincipal.setLayout(null);
		getContentPane().add(jpPersonaPrincipal, BorderLayout.CENTER);
		setLocationRelativeTo(null);
		
		lblRegistroDePersonas = new JLabel("REGISTRO DE PERSONAS");
		lblRegistroDePersonas.setFocusable(false);
		lblRegistroDePersonas.setBorder(new LineBorder(new Color(255, 165, 0), 1, true));
		lblRegistroDePersonas.setOpaque(true);
		lblRegistroDePersonas.setBackground(new Color(205, 92, 92));
		lblRegistroDePersonas.setForeground(Color.white);
		lblRegistroDePersonas.setHorizontalAlignment(SwingConstants.CENTER);
		lblRegistroDePersonas.setFont(new Font("Arial Narrow", Font.BOLD, 15));
		lblRegistroDePersonas.setBounds(75, 4, 584, 18);
		jpPersonaPrincipal.add(lblRegistroDePersonas);

		lblUsuarioP = new JLabel("Usuario:");
		lblUsuarioP.setFocusable(false);
		lblUsuarioP.setForeground(new Color(255, 255, 255));
		lblUsuarioP.setFont(new Font("Arial Narrow", Font.BOLD | Font.ITALIC, 14));
		lblUsuarioP.setBounds(664, 4, 45, 18);
		jpPersonaPrincipal.add(lblUsuarioP);
		
		txtIdUsuarioP = new JTextField("001");
		txtIdUsuarioP.setFocusable(false);
		txtIdUsuarioP.setEditable(false);
		txtIdUsuarioP.setVisible(false);
		txtIdUsuarioP.setBounds(744, 22, 45, 18);
		jpPersonaPrincipal.add(txtIdUsuarioP);
		
		txtUsuarioP = new JTextField("raulcv");
		txtUsuarioP.setFocusable(false);
		txtUsuarioP.setEditable(false);
		txtUsuarioP.setForeground(Color.WHITE);
		txtUsuarioP.setFont(new Font("Arial Narrow", Font.BOLD | Font.ITALIC, 13));
		txtUsuarioP.setBackground(new Color(30, 144, 255));
		txtUsuarioP.setBounds(709, 4, 80, 18);
		jpPersonaPrincipal.add(txtUsuarioP);
		
		lblFechaRegP = new JLabel("Fecha de Registro:");
		lblFechaRegP.setFocusable(false);
		lblFechaRegP.setForeground(Color.LIGHT_GRAY);
		lblFechaRegP.setFont(new Font("Arial Narrow", Font.BOLD | Font.ITALIC, 14));
		lblFechaRegP.setBounds(534,45,125,20);
		jpPersonaPrincipal.add(lblFechaRegP);
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		txtFechaRegP = new JTextField(sdf.format(new Date())){
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
		txtFechaRegP.setFont(new Font("Arial Narrow", Font.BOLD, 14));
		txtFechaRegP.setFocusable(false);
		txtFechaRegP.setForeground(Color.CYAN);
		txtFechaRegP.setEditable(false);
		txtFechaRegP.setBounds(534,63,125,23);
		jpPersonaPrincipal.add(txtFechaRegP);
		
		lblFechaUltModP = new JLabel("Última Modificación:");
		lblFechaUltModP.setFocusable(false);
		lblFechaUltModP.setForeground(Color.LIGHT_GRAY);
		lblFechaUltModP.setFont(new Font("Arial Narrow", Font.BOLD | Font.ITALIC, 14));
		lblFechaUltModP.setBounds(664,45,125,20);
		jpPersonaPrincipal.add(lblFechaUltModP);
		
		txtFechaUltModP = new JTextField(sdf.format(new Date())){
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
		txtFechaUltModP.setFont(new Font("Arial Narrow", Font.BOLD, 14));
		txtFechaUltModP.setFocusable(false);
		txtFechaUltModP.setForeground(Color.CYAN);
		txtFechaUltModP.setEditable(false);
		txtFechaUltModP.setBounds(664,63,125,23);
		jpPersonaPrincipal.add(txtFechaUltModP);
		
		lblNumRegP = new JLabel("Nº Registro:");
		lblNumRegP.setFocusable(false);
		lblNumRegP.setForeground(Color.LIGHT_GRAY);
		lblNumRegP.setFont(new Font("Arial Narrow", Font.BOLD | Font.ITALIC, 14));
		lblNumRegP.setBounds(10,30,65,22);
		jpPersonaPrincipal.add(lblNumRegP);
		
		spNumRegP = new JSpinner();
		spNumRegP.setFocusable(false);
		spNumRegP.setModel(new SpinnerNumberModel(new Integer(0), new Integer(0), null, new Integer(1)));
		spNumRegP.setFont(new Font("Arial Narrow", Font.BOLD | Font.ITALIC, 20));
		spNumRegP.setBounds(75,30,80,24);
		jpPersonaPrincipal.add(spNumRegP);
		
		btnNuevoP = new JButton();
		btnNuevoP.setFocusable(false);
		btnNuevoP.setMargin(new Insets(1, 2, 2, 2));
		btnNuevoP.setHorizontalTextPosition(SwingConstants.LEFT);
		iinuevo= new ImageIcon("iconos/nuevo.png");
		btnNuevoP.setBounds(165, 27, 40, 30);
		inuevo = new ImageIcon(iinuevo.getImage().getScaledInstance(35,btnNuevoP.getHeight(), Image.SCALE_DEFAULT));
		btnNuevoP.setIcon(inuevo);
		jpPersonaPrincipal.add(btnNuevoP);
		
		lblRegistradorP = new JLabel("Registrador:");
		lblRegistradorP.setFocusable(false);
		lblRegistradorP.setForeground(Color.LIGHT_GRAY);
		lblRegistradorP.setFont(new Font("Arial Narrow", Font.BOLD | Font.ITALIC, 14));
		lblRegistradorP.setBounds(253, 33, 68, 22);
		jpPersonaPrincipal.add(lblRegistradorP);
		
		txtRegistradorP = new JTextField("Juan Arco de la Vega");
		txtRegistradorP.setFocusable(false);
		txtRegistradorP.setEditable(false);
		txtRegistradorP.setBackground(new Color(47, 79, 79));
		txtRegistradorP.setForeground(Color.WHITE);
		txtRegistradorP.setFont(new Font("Arial Narrow", Font.BOLD | Font.ITALIC, 13));
		txtRegistradorP.setCaretColor(Color.WHITE);
		txtRegistradorP.setBounds(320, 33, 200, 22);
		jpPersonaPrincipal.add(txtRegistradorP);
		
		lblAreaP = new JLabel("Area Trabajo:");
		lblAreaP.setFocusable(false);
		lblAreaP.setForeground(Color.LIGHT_GRAY);
		lblAreaP.setFont(new Font("Arial Narrow", Font.BOLD | Font.ITALIC, 14));
		lblAreaP.setBounds(4,95,71,22);
		jpPersonaPrincipal.add(lblAreaP);
		
		cboAreaP = new JComboBox<>();/*{
			@Override protected void paintComponent(Graphics g) {
			if (!isOpaque()) {
		          int w = getWidth() - 1;
		          int h = getHeight() - 1;
		          Graphics2D g2 = (Graphics2D) g.create();
		          g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		          g2.setPaint(UIManager.getColor("TextField.foreground"));
		          g2.fillRoundRect(0, 0, w, h, h, h);
		          g2.setPaint(Color.ORANGE);//COLOR BORDE
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
		};*/				
		cboAreaP.setFont(new Font("Arial Narrow", Font.BOLD | Font.ITALIC, 14));
		cboAreaP.setBounds(75,95,144,24);
		jpPersonaPrincipal.add(cboAreaP);		
		
		btnNuevaAreaP = new JButton();
		btnNuevaAreaP.setFocusable(false);
		btnNuevaAreaP.setMargin(new Insets(1, 2, 2, 2));
		btnNuevaAreaP.setHorizontalTextPosition(SwingConstants.LEFT);
		btnNuevaAreaP.setBounds(222, 92, 33, 30);
		iinuevaarea= new ImageIcon("iconos/añadir.png");
		inuevaarea = new ImageIcon(iinuevaarea.getImage().getScaledInstance(33,33, Image.SCALE_DEFAULT));
		btnNuevaAreaP.setIcon(inuevaarea);
		jpPersonaPrincipal.add(btnNuevaAreaP);
		
		lblNombreP = new JLabel("Nombre:");
		lblNombreP.setFocusable(false);
		lblNombreP.setForeground(Color.LIGHT_GRAY);
		lblNombreP.setFont(new Font("Arial Narrow", Font.BOLD | Font.ITALIC, 14));
		lblNombreP.setBounds(27,63,48,22);
		jpPersonaPrincipal.add(lblNombreP);
		
		txtNombreP = new JTextField("Havana"){
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
		txtNombreP.setCaretColor(Color.WHITE);
		txtNombreP.setForeground(Color.WHITE);
		txtNombreP.setFont(new Font("Arial Narrow", Font.BOLD | Font.ITALIC, 13));
		txtNombreP.setBounds(75,63,180,24);
		jpPersonaPrincipal.add(txtNombreP);
		
		lblApellidoP = new JLabel("Apellido:");
		lblApellidoP.setFocusable(false);
		lblApellidoP.setForeground(Color.LIGHT_GRAY);
		lblApellidoP.setFont(new Font("Arial Narrow", Font.BOLD | Font.ITALIC, 14));
		lblApellidoP.setBounds(269,64,50,22);
		jpPersonaPrincipal.add(lblApellidoP);
		
		txtApellidoP = new JTextField("Benites"){
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
		txtApellidoP.setCaretColor(Color.WHITE);
		txtApellidoP.setForeground(Color.WHITE);
		txtApellidoP.setFont(new Font("Arial Narrow", Font.BOLD | Font.ITALIC, 13));
		txtApellidoP.setBounds(320,63,200,24);
		jpPersonaPrincipal.add(txtApellidoP);
		
		lblEmailP = new JLabel("E-Mail:");
		lblEmailP.setFocusable(false);
		lblEmailP.setForeground(Color.LIGHT_GRAY);
		lblEmailP.setFont(new Font("Arial Narrow", Font.BOLD | Font.ITALIC, 14));
		lblEmailP.setBounds(33,128,40,22);
		jpPersonaPrincipal.add(lblEmailP);
		
		txtEmailP = new JTextField("raulcv@gmail.com"){
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
		txtEmailP.setCaretColor(Color.WHITE);
		txtEmailP.setForeground(Color.WHITE);
		txtEmailP.setFont(new Font("Arial Narrow", Font.BOLD | Font.ITALIC, 13));
		txtEmailP.setBounds(75,128,180,24);
		jpPersonaPrincipal.add(txtEmailP);
		
		lblPaisP = new JLabel("Pais:");
		lblPaisP.setFocusable(false);
		lblPaisP.setForeground(Color.LIGHT_GRAY);
		lblPaisP.setFont(new Font("Arial Narrow", Font.BOLD | Font.ITALIC, 14));
		lblPaisP.setBounds(292,95,27,22);
		jpPersonaPrincipal.add(lblPaisP);
		
		cboPaisP = new JComboBox<>();
		cboPaisP.setFont(new Font("Arial Narrow", Font.BOLD | Font.ITALIC, 14));
		cboPaisP.setBounds(320,95,164,24);
		jpPersonaPrincipal.add(cboPaisP);
		
		btnNuevoPaisP = new JButton();
		btnNuevoPaisP.setFocusable(false);
		btnNuevoPaisP.setMargin(new Insets(1, 2, 2, 2));
		btnNuevoPaisP.setHorizontalTextPosition(SwingConstants.LEFT);
		btnNuevoPaisP.setBounds(487, 92, 33, 30);
		iinuevopais= new ImageIcon("iconos/añadir.png");
		inuevopais = new ImageIcon(iinuevopais.getImage().getScaledInstance(33,33, Image.SCALE_DEFAULT));
		btnNuevoPaisP.setIcon(inuevopais);
		jpPersonaPrincipal.add(btnNuevoPaisP);
		
		lblTelefonoP = new JLabel("Teléfono:");
		lblTelefonoP.setFocusable(false);
		lblTelefonoP.setForeground(Color.LIGHT_GRAY);
		lblTelefonoP.setFont(new Font("Arial Narrow", Font.BOLD | Font.ITALIC, 14));
		lblTelefonoP.setBounds(269,128,52,22);
		jpPersonaPrincipal.add(lblTelefonoP);
		
		txtTelefonoP = new JTextField("+51 935154523"){
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
		txtTelefonoP.setCaretColor(Color.WHITE);
		txtTelefonoP.setForeground(Color.WHITE);
		txtTelefonoP.setFont(new Font("Arial Narrow", Font.BOLD | Font.ITALIC, 13));
		txtTelefonoP.setBounds(320,128,200,24);
		jpPersonaPrincipal.add(txtTelefonoP);
		
		lblComentarioP = new JLabel("Comentario:");
		lblComentarioP.setFocusable(false);
		lblComentarioP.setForeground(Color.LIGHT_GRAY);
		lblComentarioP.setFont(new Font("Arial Narrow", Font.BOLD | Font.ITALIC, 14));
		lblComentarioP.setBounds(534,98,66,22);
		jpPersonaPrincipal.add(lblComentarioP);
		
		txtaComentarioP = new JTextArea("Nueva sala..."){
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
		txtaComentarioP.setWrapStyleWord(true);
		txtaComentarioP.setCaretColor(Color.WHITE);
		txtaComentarioP.setForeground(Color.WHITE);
		//txtaDescripcion.setBackground(Color.GRAY);
		txtaComentarioP.setFont(new Font("Arial Narrow", Font.BOLD | Font.ITALIC, 13));
		txtaComentarioP.setBounds(534,119,255,37);
		txtaComentarioP.setLineWrap(true);
		jpPersonaPrincipal.add(txtaComentarioP);
		
		jpGrillaP = new JPanel();
		jpGrillaP.setBackground(SystemColor.controlHighlight);
		jpGrillaP.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Lista de registro de Personas", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 51)));
		jpGrillaP.setBounds(3, 167, 788, 248);
		jpPersonaPrincipal.add(jpGrillaP);
		jpGrillaP.setLayout(null);
		
		txtNombrePB = new JTextField("A"){
			@Override protected void paintComponent(Graphics g){
				if (!isOpaque()) {
			          int w = getWidth() - 1;
			          int h = getHeight() - 1;
			          Graphics2D g2 = (Graphics2D) g.create();
			          g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			          g2.setPaint(UIManager.getColor("TextField.foreground"));
			          g2.fillRoundRect(0, 0, w, h, h, h);
			          g2.setPaint(Color.ORANGE);//COLOR BORDE
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
		txtNombrePB.setFocusTraversalKeysEnabled(false);
		txtNombrePB.setCaretColor(Color.ORANGE);
		txtNombrePB.setForeground(Color.ORANGE);
		txtNombrePB.setBounds(180, 20, 200, 23);
		jpGrillaP.add(txtNombrePB);
		
		lblNombrePB = new JLabel("Escribe el Nombre de la Persona:");
		lblNombrePB.setForeground(new Color(0, 128, 128));
		lblNombrePB.setBounds(5, 23, 175, 15);
		jpGrillaP.add(lblNombrePB);
		lblNombrePB.setFont(new Font("Arial Narrow", Font.BOLD | Font.ITALIC, 13));
		
	
		tlbListaP = new JTable(dtmP){
			@Override
		public boolean isCellEditable(int row, int column) {
			return false;
		}};//LAS CELDAS DEL JTABLE YA NO SON EDITABLES
		tlbListaP.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		tlbListaP.setDefaultRenderer(Object.class, new RenderizarCeldaImg());
		tlbListaP.getTableHeader().setOpaque(true);
		//tlbListaP.getTableHeader().setPreferredSize(new java.awt.Dimension(0, 28));
		tlbListaP.getTableHeader().setFont(new Font("Arial Narrow", Font.BOLD, 17));
		tlbListaP.getTableHeader().setForeground(Color.WHITE);
		tlbListaP.getTableHeader().setBackground(new Color(23, 136, 203));
		tlbListaP.setShowVerticalLines(false);
		tlbListaP.setRowHeight(24);
		tlbListaP.setFont(new Font("Arial", Font.PLAIN, 12));
		tlbListaP.setSelectionForeground(new Color(255, 215, 0));
		tlbListaP.setSelectionBackground(new Color(47, 79, 79));
		tlbListaP.setForeground(Color.DARK_GRAY);
		tlbListaP.setBackground(new Color(230, 230, 250));

		JscpListaP = new JScrollPane(tlbListaP);
		JscpListaP.setViewportBorder(new LineBorder(new Color(0, 139, 139), 1, true));
		JscpListaP.setBounds(2,50,784,166);
		//JscpListaP.setViewportView(tlbListaP);
		jpGrillaP.add(JscpListaP);
		
		lblTotalItemsP = new JLabel("Total Items:");
		lblTotalItemsP.setBounds(5, 222, 115, 22);
		lblTotalItemsP.setForeground(Color.ORANGE);
		lblTotalItemsP.setBackground(new Color(47, 79, 79));
		lblTotalItemsP.setOpaque(true);
		lblTotalItemsP.setFont(new Font("Arial Narrow", Font.BOLD | Font.ITALIC, 13));
		jpGrillaP.add(lblTotalItemsP);
		
		btnExportarExcelP = new JButton();
		btnExportarExcelP.setBackground(new Color(153, 204, 153));
		btnExportarExcelP.setMargin(new Insets(1, 2, 2, 2));
		btnExportarExcelP.setHorizontalTextPosition(SwingConstants.LEFT);
		btnExportarExcelP.setText("Excel");
		iiExcel = new ImageIcon("iconos/excel.png");
		btnExportarExcelP.setBounds(700, 218, 85, 27);
		iexcel = new ImageIcon(iiExcel.getImage().getScaledInstance(30, 33, Image.SCALE_DEFAULT));
		btnExportarExcelP.setIcon(iexcel);
		jpGrillaP.add(btnExportarExcelP);
		
		jpPieP = new JPanel();
		jpPieP.setBackground(new Color(205, 92, 92));
		jpPieP.setBounds(0,416,794,38);
		jpPieP.setLayout(null);
		jpPersonaPrincipal.add(jpPieP);
		//jpPie.setLayout(new FlowLayout(FlowLayout.RIGHT));
		//getContentPane().add(jpPie, BorderLayout.SOUTH);

		btnOkP = new JButton("REGISTRAR");
		btnOkP.setMargin(new Insets(1, 2, 2, 2));
		btnOkP.setHorizontalTextPosition(SwingConstants.LEFT);
	 	btnOkP.setBounds(550,2,120,33);
		jpPieP.add(btnOkP);

		btnCancelP = new JButton("SALIR");
		btnCancelP.setMargin(new Insets(1, 2, 2, 2));
		btnOkP.setHorizontalTextPosition(SwingConstants.LEFT);
		iisalir= new ImageIcon("iconos/salir.png");
		btnCancelP.setBounds(700,2,90,33);
		isalir = new ImageIcon(iisalir.getImage().getScaledInstance(33,btnCancelP.getHeight(), Image.SCALE_DEFAULT));
		btnCancelP.setIcon(isalir);
		jpPieP.add(btnCancelP);
		
	}
}
