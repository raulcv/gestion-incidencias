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

public class FrmPais extends JDialog {

	private final JPanel jpPaisPrincipal = new JPanel();
	private JPanel jpPiePA, jpGrillaPA;
	public JButton btnNuevoPA,btnOkPA, btnCancelPA, btnExportarExcelPA;
	public JLabel lblRegistroDePaises,lblFechaRegPA,lblNumRegPA, lblNombrePA, lblDescripcionPA, lblNombrePAB, lblTotalItemsPA;
	public JSpinner spNumRegPA;
	public JTextField txtFechaRegPA,txtNombrePA, txtNombrePAB;
	public JTextArea txtaDescripcionPA;
	public JTable tlbListaPA;
	public DefaultTableModel dtmM;
	public JScrollPane JscpListaPA;
	public ImageIcon iinuevo,iiExcel,iisalir;
	public Icon inuevo,iexcel,isalir;
	
	public FrmPais(FrmPais frmPais, boolean modal) {
		super(frmPais, modal);
		InicializarValores();
	}
	public void InicializarValores(){
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 560, 411);
		jpPaisPrincipal.setFocusTraversalKeysEnabled(false);
		jpPaisPrincipal.setBorder(new LineBorder(new Color(0, 0, 0)));
		jpPaisPrincipal.setBackground(new Color(47, 79, 79));
		jpPaisPrincipal.setLayout(null);
		getContentPane().add(jpPaisPrincipal, BorderLayout.CENTER);
		setLocationRelativeTo(null);
		
		lblRegistroDePaises = new JLabel("REGISTRO DE PAISES");
		lblRegistroDePaises.setBorder(new LineBorder(new Color(255, 165, 0), 1, true));
		lblRegistroDePaises.setOpaque(true);
		lblRegistroDePaises.setBackground(new Color(205, 92, 92));
		lblRegistroDePaises.setForeground(Color.white);
		lblRegistroDePaises.setHorizontalAlignment(SwingConstants.CENTER);
		lblRegistroDePaises.setFont(new Font("Arial Narrow", Font.BOLD, 15));
		lblRegistroDePaises.setBounds(1, 4, 542, 18);
		jpPaisPrincipal.add(lblRegistroDePaises);

		lblFechaRegPA = new JLabel("Fecha:");
		lblFechaRegPA.setForeground(Color.LIGHT_GRAY);
		lblFechaRegPA.setFont(new Font("Arial Narrow", Font.BOLD | Font.ITALIC, 14));
		lblFechaRegPA.setBounds(287,30,38,20);
		jpPaisPrincipal.add(lblFechaRegPA);
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		txtFechaRegPA = new JTextField(sdf.format(new Date())){
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
		txtFechaRegPA.setFont(new Font("Arial Narrow", Font.BOLD, 14));
		txtFechaRegPA.setFocusable(false);
		txtFechaRegPA.setForeground(Color.CYAN);
		txtFechaRegPA.setEditable(false);
		txtFechaRegPA.setBounds(325,30,125,23);
		jpPaisPrincipal.add(txtFechaRegPA);
		
		lblNumRegPA = new JLabel("Nº de Registro:");
		lblNumRegPA.setForeground(Color.LIGHT_GRAY);
		lblNumRegPA.setFont(new Font("Arial Narrow", Font.BOLD | Font.ITALIC, 14));
		lblNumRegPA.setBounds(10,30,90,22);
		jpPaisPrincipal.add(lblNumRegPA);
		
		spNumRegPA = new JSpinner();
		spNumRegPA.setFocusable(false);
		spNumRegPA.setModel(new SpinnerNumberModel(new Integer(0), new Integer(0), null, new Integer(1)));
		spNumRegPA.setFont(new Font("Arial Narrow", Font.BOLD | Font.ITALIC, 20));
		spNumRegPA.setBounds(92,30,80,22);
		jpPaisPrincipal.add(spNumRegPA);
		
		btnNuevoPA = new JButton();
		btnNuevoPA.setMargin(new Insets(1, 2, 2, 2));
		btnNuevoPA.setHorizontalTextPosition(SwingConstants.LEFT);
		iinuevo= new ImageIcon("iconos/nuevo.png");
		btnNuevoPA.setBounds(180, 27, 40, 30);
		inuevo = new ImageIcon(iinuevo.getImage().getScaledInstance(35,btnNuevoPA.getHeight(), Image.SCALE_DEFAULT));
		btnNuevoPA.setIcon(inuevo);
		jpPaisPrincipal.add(btnNuevoPA);
		
		lblNombrePA = new JLabel("Nombre Pais:");
		lblNombrePA.setForeground(Color.LIGHT_GRAY);
		lblNombrePA.setFont(new Font("Arial Narrow", Font.BOLD | Font.ITALIC, 14));
		lblNombrePA.setBounds(10,60,82,22);
		jpPaisPrincipal.add(lblNombrePA);
		
		txtNombrePA = new JTextField("Havana"){
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
		txtNombrePA.setCaretColor(Color.WHITE);
		txtNombrePA.setForeground(Color.WHITE);
		txtNombrePA.setFont(new Font("Arial Narrow", Font.BOLD | Font.ITALIC, 13));
		txtNombrePA.setBounds(92,60,150,24);
		jpPaisPrincipal.add(txtNombrePA);
		
		lblDescripcionPA = new JLabel("Descripción:");
		lblDescripcionPA.setForeground(Color.LIGHT_GRAY);
		lblDescripcionPA.setFont(new Font("Arial Narrow", Font.BOLD | Font.ITALIC, 14));
		lblDescripcionPA.setBounds(255,60,70,22);
		jpPaisPrincipal.add(lblDescripcionPA);
		
		txtaDescripcionPA = new JTextArea("Nueva sala..."){
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
		txtaDescripcionPA.setCaretColor(Color.WHITE);
		txtaDescripcionPA.setForeground(Color.WHITE);
		txtaDescripcionPA.setFont(new Font("Arial Narrow", Font.BOLD | Font.ITALIC, 13));
		txtaDescripcionPA.setBounds(325,60,215,38);
		txtaDescripcionPA.setLineWrap(true);
		jpPaisPrincipal.add(txtaDescripcionPA);
		
		jpGrillaPA = new JPanel();
		jpGrillaPA.setBackground(SystemColor.controlHighlight);
		jpGrillaPA.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Lista de registro de Paises", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 51)));
		jpGrillaPA.setBounds(3, 107, 538, 225);
		jpPaisPrincipal.add(jpGrillaPA);
		jpGrillaPA.setLayout(null);
		
		txtNombrePAB = new JTextField("A"){
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
		};
		txtNombrePAB.setFocusTraversalKeysEnabled(false);
		txtNombrePAB.setCaretColor(Color.ORANGE);
		txtNombrePAB.setForeground(Color.ORANGE);
		txtNombrePAB.setBounds(150, 20, 170, 23);
		jpGrillaPA.add(txtNombrePAB);
		
		lblNombrePAB = new JLabel("Escribe el Nombre del Pais:");
		lblNombrePAB.setForeground(new Color(51, 102, 51));
		lblNombrePAB.setBounds(5, 23, 155, 15);
		jpGrillaPA.add(lblNombrePAB);
		lblNombrePAB.setFont(new Font("Arial Narrow", Font.BOLD | Font.ITALIC, 13));
		
	
		tlbListaPA = new JTable(dtmM){
			@Override
		public boolean isCellEditable(int row, int column) {
			return false;
		}};//LAS CELDAS DEL JTABLE YA NO SON EDITABLES
		tlbListaPA.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
		tlbListaPA.setDefaultRenderer(Object.class, new RenderizarCeldaImg());
		tlbListaPA.getTableHeader().setOpaque(true);
		tlbListaPA.getTableHeader().setPreferredSize(new java.awt.Dimension(0, 30));
		tlbListaPA.getTableHeader().setFont(new Font("Arial Narrow", Font.BOLD, 15));
		tlbListaPA.getTableHeader().setForeground(Color.WHITE);
		tlbListaPA.getTableHeader().setBackground(new Color(3, 188, 86));
		tlbListaPA.setShowVerticalLines(false);
		tlbListaPA.setRowHeight(25);
		tlbListaPA.setFont(new Font("Arial", Font.PLAIN, 13));
		tlbListaPA.setSelectionForeground(new Color(255, 215, 0));
		tlbListaPA.setSelectionBackground(new Color(0, 153, 153));
		tlbListaPA.setForeground(Color.DARK_GRAY);
		tlbListaPA.setBackground(new Color(230, 230, 250));

		JscpListaPA = new JScrollPane(tlbListaPA);
		JscpListaPA.setViewportBorder(new LineBorder(new Color(0, 139, 139), 1, true));
		JscpListaPA.setBounds(5,50,527,145);
		jpGrillaPA.add(JscpListaPA);
		
		lblTotalItemsPA = new JLabel("Total Items:");
		lblTotalItemsPA.setBounds(5, 200, 100, 22);
		lblTotalItemsPA.setForeground(new Color(255, 165, 0));
		lblTotalItemsPA.setBackground(new Color(0, 128, 128));
		lblTotalItemsPA.setOpaque(true);
		lblTotalItemsPA.setFont(new Font("Arial Narrow", Font.BOLD | Font.ITALIC, 13));
		jpGrillaPA.add(lblTotalItemsPA);
		
		btnExportarExcelPA = new JButton();
		btnExportarExcelPA.setMargin(new Insets(1, 2, 2, 2));
		btnExportarExcelPA.setHorizontalTextPosition(SwingConstants.LEFT);
		btnExportarExcelPA.setText("Excel");
		iiExcel = new ImageIcon("iconos/excel.png");
		btnExportarExcelPA.setBounds(447, 195, 85, 28);
		iexcel = new ImageIcon(iiExcel.getImage().getScaledInstance(30, 33, Image.SCALE_DEFAULT));
		btnExportarExcelPA.setIcon(iexcel);
		jpGrillaPA.add(btnExportarExcelPA);
		
		jpPiePA = new JPanel();
		jpPiePA.setBackground(new Color(205, 92, 92));
		jpPiePA.setBounds(1,333,542,38);
		jpPiePA.setLayout(null);
		jpPaisPrincipal.add(jpPiePA);
		
		btnOkPA = new JButton("REGISTRAR");
		btnOkPA.setMargin(new Insets(1, 2, 2, 2));
		btnOkPA.setHorizontalTextPosition(SwingConstants.LEFT);
	 	btnOkPA.setBounds(300,3,120,33);
		jpPiePA.add(btnOkPA);

		btnCancelPA = new JButton("SALIR");
		btnCancelPA.setMargin(new Insets(1, 2, 2, 2));
		btnOkPA.setHorizontalTextPosition(SwingConstants.LEFT);
		iisalir= new ImageIcon("iconos/salir.png");
		btnCancelPA.setBounds(445,3,90,33);
		isalir = new ImageIcon(iisalir.getImage().getScaledInstance(33,btnCancelPA.getHeight(), Image.SCALE_DEFAULT));
		btnCancelPA.setIcon(isalir);
		jpPiePA.add(btnCancelPA);
		
	}
	
}
