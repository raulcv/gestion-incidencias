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

public class FrmArea extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final JPanel jpAreaPrincipal = new JPanel();
	private JPanel jpPieA, jpGrillaA;
	public JButton btnNuevoA,btnOkA, btnCancelA, btnExportarExcelA;
	public JLabel lblRegistroDeAreas,lblFechaRegA,lblNumRegA, lblNombreA, lblDescripcionA, lblNombreAB, lblTotalItemsA;
	public JSpinner spNumRegA;
	public JTextField txtFechaRegA,txtNombreA, txtNombreAB;
	public JTextArea txtaDescripcionA;
	public JTable tlbListaA;
	public DefaultTableModel dtmM;
	public JScrollPane JscpListaA;
	public ImageIcon iinuevo,iiExcel,iisalir;
	public Icon inuevo,iexcel,isalir;
	
	public FrmArea(FrmArea frmArea, boolean modal) {
		super(frmArea, modal);
		InicializarValores();
	}
	public void InicializarValores(){
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 560, 410);
		jpAreaPrincipal.setFocusTraversalKeysEnabled(false);
		jpAreaPrincipal.setBorder(new LineBorder(new Color(0, 0, 0)));
		jpAreaPrincipal.setBackground(new Color(47, 79, 79));
		jpAreaPrincipal.setLayout(null);
		getContentPane().add(jpAreaPrincipal, BorderLayout.CENTER);
		setLocationRelativeTo(null);
		
		lblRegistroDeAreas = new JLabel("REGISTRO DE AREAS");
		lblRegistroDeAreas.setBorder(new LineBorder(new Color(255, 165, 0), 1, true));
		lblRegistroDeAreas.setOpaque(true);
		lblRegistroDeAreas.setBackground(new Color(205, 92, 92));
		lblRegistroDeAreas.setForeground(Color.white);
		lblRegistroDeAreas.setHorizontalAlignment(SwingConstants.CENTER);
		lblRegistroDeAreas.setFont(new Font("Arial Narrow", Font.BOLD, 15));
		lblRegistroDeAreas.setBounds(1, 4, 542, 18);
		jpAreaPrincipal.add(lblRegistroDeAreas);

		lblFechaRegA = new JLabel("Fecha:");
		lblFechaRegA.setForeground(Color.LIGHT_GRAY);
		lblFechaRegA.setFont(new Font("Arial Narrow", Font.BOLD | Font.ITALIC, 14));
		lblFechaRegA.setBounds(287,30,38,20);
		jpAreaPrincipal.add(lblFechaRegA);
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		txtFechaRegA = new JTextField(sdf.format(new Date())){
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
		txtFechaRegA.setFont(new Font("Arial Narrow", Font.BOLD, 14));
		txtFechaRegA.setFocusable(false);
		txtFechaRegA.setForeground(Color.CYAN);
		txtFechaRegA.setEditable(false);
		txtFechaRegA.setBounds(325,30,125,23);
		jpAreaPrincipal.add(txtFechaRegA);
		
		lblNumRegA = new JLabel("Nº de Registro:");
		lblNumRegA.setForeground(Color.LIGHT_GRAY);
		lblNumRegA.setFont(new Font("Arial Narrow", Font.BOLD | Font.ITALIC, 14));
		lblNumRegA.setBounds(10,30,90,22);
		jpAreaPrincipal.add(lblNumRegA);
		
		spNumRegA = new JSpinner();
		spNumRegA.setFocusable(false);
		spNumRegA.setModel(new SpinnerNumberModel(new Integer(0), new Integer(0), null, new Integer(1)));
		spNumRegA.setFont(new Font("Arial Narrow", Font.BOLD | Font.ITALIC, 20));
		spNumRegA.setBounds(92,30,80,22);
		jpAreaPrincipal.add(spNumRegA);
		
		btnNuevoA = new JButton();
		btnNuevoA.setMargin(new Insets(1, 2, 2, 2));
		btnNuevoA.setHorizontalTextPosition(SwingConstants.LEFT);
		iinuevo= new ImageIcon("iconos/nuevo.png");
		btnNuevoA.setBounds(180, 27, 40, 30);
		inuevo = new ImageIcon(iinuevo.getImage().getScaledInstance(35,btnNuevoA.getHeight(), Image.SCALE_DEFAULT));
		btnNuevoA.setIcon(inuevo);
		jpAreaPrincipal.add(btnNuevoA);
		
		lblNombreA = new JLabel("Nombre Area:");
		lblNombreA.setForeground(Color.LIGHT_GRAY);
		lblNombreA.setFont(new Font("Arial Narrow", Font.BOLD | Font.ITALIC, 14));
		lblNombreA.setBounds(10,60,82,22);
		jpAreaPrincipal.add(lblNombreA);
		
		txtNombreA = new JTextField("Havana"){
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
		txtNombreA.setCaretColor(Color.WHITE);
		txtNombreA.setForeground(Color.WHITE);
		txtNombreA.setFont(new Font("Arial Narrow", Font.BOLD | Font.ITALIC, 13));
		txtNombreA.setBounds(92,60,150,24);
		jpAreaPrincipal.add(txtNombreA);
		
		lblDescripcionA = new JLabel("Descripción:");
		lblDescripcionA.setForeground(Color.LIGHT_GRAY);
		lblDescripcionA.setFont(new Font("Arial Narrow", Font.BOLD | Font.ITALIC, 14));
		lblDescripcionA.setBounds(255,60,70,22);
		jpAreaPrincipal.add(lblDescripcionA);
		
		txtaDescripcionA = new JTextArea("Nueva sala..."){
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
		txtaDescripcionA.setCaretColor(Color.WHITE);
		txtaDescripcionA.setForeground(Color.WHITE);
		txtaDescripcionA.setFont(new Font("Arial Narrow", Font.BOLD | Font.ITALIC, 13));
		txtaDescripcionA.setBounds(325,60,215,38);
		txtaDescripcionA.setLineWrap(true);
		jpAreaPrincipal.add(txtaDescripcionA);
		
		jpGrillaA = new JPanel();
		jpGrillaA.setBackground(SystemColor.controlHighlight);
		jpGrillaA.setBorder(new TitledBorder(null, "Lista de registro de Medios", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 51)));
		jpGrillaA.setBounds(3, 107, 538, 225);
		jpAreaPrincipal.add(jpGrillaA);
		jpGrillaA.setLayout(null);
		
		txtNombreAB = new JTextField("A"){
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
		txtNombreAB.setFocusTraversalKeysEnabled(false);
		txtNombreAB.setCaretColor(Color.ORANGE);
		txtNombreAB.setForeground(Color.ORANGE);
		txtNombreAB.setBounds(160, 20, 170, 23);
		jpGrillaA.add(txtNombreAB);
		
		lblNombreAB = new JLabel("Escribe el Nombre del Medio:");
		lblNombreAB.setForeground(new Color(51, 102, 51));
		lblNombreAB.setBounds(5, 23, 155, 15);
		jpGrillaA.add(lblNombreAB);
		lblNombreAB.setFont(new Font("Arial Narrow", Font.BOLD | Font.ITALIC, 13));
		
	
		tlbListaA = new JTable(dtmM){
			@Override
		public boolean isCellEditable(int row, int column) {
			return false;
		}};//LAS CELDAS DEL JTABLE YA NO SON EDITABLES
		tlbListaA.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
		tlbListaA.setDefaultRenderer(Object.class, new RenderizarCeldaImg());
		tlbListaA.getTableHeader().setOpaque(true);
		tlbListaA.getTableHeader().setPreferredSize(new java.awt.Dimension(0, 30));
		tlbListaA.getTableHeader().setFont(new Font("Arial Narrow", Font.BOLD, 15));
		tlbListaA.getTableHeader().setForeground(Color.WHITE);
		tlbListaA.getTableHeader().setBackground(new Color(3, 188, 86));
		tlbListaA.setShowVerticalLines(false);
		tlbListaA.setRowHeight(25);
		tlbListaA.setFont(new Font("Arial", Font.PLAIN, 13));
		tlbListaA.setSelectionForeground(new Color(255, 215, 0));
		tlbListaA.setSelectionBackground(new Color(0, 153, 153));
		tlbListaA.setForeground(Color.DARK_GRAY);
		tlbListaA.setBackground(new Color(230, 230, 250));

		JscpListaA = new JScrollPane(tlbListaA);
		JscpListaA.setViewportBorder(new LineBorder(new Color(0, 139, 139), 1, true));
		JscpListaA.setBounds(5,50,527,145);
		jpGrillaA.add(JscpListaA);
		
		lblTotalItemsA = new JLabel("Total Items:");
		lblTotalItemsA.setBounds(5, 200, 100, 22);
		lblTotalItemsA.setForeground(new Color(255, 165, 0));
		lblTotalItemsA.setBackground(new Color(0, 128, 128));
		lblTotalItemsA.setOpaque(true);
		lblTotalItemsA.setFont(new Font("Arial Narrow", Font.BOLD | Font.ITALIC, 13));
		jpGrillaA.add(lblTotalItemsA);
		
		btnExportarExcelA = new JButton();
		btnExportarExcelA.setMargin(new Insets(1, 2, 2, 2));
		btnExportarExcelA.setHorizontalTextPosition(SwingConstants.LEFT);
		btnExportarExcelA.setText("Excel");
		iiExcel = new ImageIcon("iconos/excel.png");
		btnExportarExcelA.setBounds(447, 195, 85, 28);
		iexcel = new ImageIcon(iiExcel.getImage().getScaledInstance(30, 33, Image.SCALE_DEFAULT));
		btnExportarExcelA.setIcon(iexcel);
		jpGrillaA.add(btnExportarExcelA);
		
		jpPieA = new JPanel();
		jpPieA.setBackground(new Color(205, 92, 92));
		jpPieA.setBounds(1,333,542,38);
		jpPieA.setLayout(null);
		jpAreaPrincipal.add(jpPieA);
		
		btnOkA = new JButton("REGISTRAR");
		btnOkA.setMargin(new Insets(1, 2, 2, 2));
		btnOkA.setHorizontalTextPosition(SwingConstants.LEFT);
	 	btnOkA.setBounds(300,3,120,33);
		jpPieA.add(btnOkA);

		btnCancelA = new JButton("SALIR");
		btnCancelA.setMargin(new Insets(1, 2, 2, 2));
		btnOkA.setHorizontalTextPosition(SwingConstants.LEFT);
		iisalir= new ImageIcon("iconos/salir.png");
		btnCancelA.setBounds(445,3,90,33);
		isalir = new ImageIcon(iisalir.getImage().getScaledInstance(33,btnCancelA.getHeight(), Image.SCALE_DEFAULT));
		btnCancelA.setIcon(isalir);
		jpPieA.add(btnCancelA);
		
	}

}
