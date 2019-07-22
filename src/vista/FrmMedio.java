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

public class FrmMedio extends JDialog {

/*	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FrmMedio frame = new FrmMedio();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}*/

	/**
	 * Create the frame.
	 */
	private final JPanel jpMedioPrincipal = new JPanel();
	private JPanel jpPieM, jpGrillaM;
	public JButton btnNuevoM,btnOkM, btnCancelM, btnExportarExcelM;
	public JLabel lblRegistroDeMedios,lblFechaRegM,lblNumRegM, lblNombreM, lblDescripcionM, lblNombreMB, lblTotalItemsM;
	public JSpinner spNumRegM;
	public JTextField txtFechaRegM,txtNombreM, txtNombreMB;
	public JTextArea txtaDescripcionM;
	public JTable tlbListaM;
	public DefaultTableModel dtmM;
	public JScrollPane JscpListaM;
	public ImageIcon iinuevo,iiExcel,iisalir;
	public Icon inuevo,iexcel,isalir;
	
	public FrmMedio(FrmMedio frmMedio, boolean modal) {
		super(frmMedio,modal);
		InicializarValores();
	}
	public void InicializarValores(){
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 560, 411);
		jpMedioPrincipal.setFocusTraversalKeysEnabled(false);
		jpMedioPrincipal.setBorder(new LineBorder(new Color(0, 0, 0)));
		jpMedioPrincipal.setBackground(new Color(46, 93, 148));
		jpMedioPrincipal.setLayout(null);
		getContentPane().add(jpMedioPrincipal, BorderLayout.CENTER);
		setLocationRelativeTo(null);
		
		lblRegistroDeMedios = new JLabel("REGISTRO DE MEDIOS");
		lblRegistroDeMedios.setBorder(new LineBorder(new Color(255, 165, 0), 1, true));
		lblRegistroDeMedios.setOpaque(true);
		lblRegistroDeMedios.setBackground(new Color(205, 92, 92));
		lblRegistroDeMedios.setForeground(Color.white);
		lblRegistroDeMedios.setHorizontalAlignment(SwingConstants.CENTER);
		lblRegistroDeMedios.setFont(new Font("Arial Narrow", Font.BOLD, 15));
		lblRegistroDeMedios.setBounds(1, 4, 542, 18);
		jpMedioPrincipal.add(lblRegistroDeMedios);

		lblFechaRegM = new JLabel("Fecha:");
		lblFechaRegM.setForeground(Color.LIGHT_GRAY);
		lblFechaRegM.setFont(new Font("Arial Narrow", Font.BOLD | Font.ITALIC, 14));
		lblFechaRegM.setBounds(287,30,38,20);
		jpMedioPrincipal.add(lblFechaRegM);
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		txtFechaRegM = new JTextField(sdf.format(new Date())){
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
		txtFechaRegM.setFont(new Font("Arial Narrow", Font.BOLD, 14));
		txtFechaRegM.setFocusable(false);
		txtFechaRegM.setForeground(Color.CYAN);
		txtFechaRegM.setEditable(false);
		txtFechaRegM.setBounds(325,30,125,23);
		jpMedioPrincipal.add(txtFechaRegM);
		
		lblNumRegM = new JLabel("Nº de Registro:");
		lblNumRegM.setForeground(Color.LIGHT_GRAY);
		lblNumRegM.setFont(new Font("Arial Narrow", Font.BOLD | Font.ITALIC, 14));
		lblNumRegM.setBounds(10,30,90,22);
		jpMedioPrincipal.add(lblNumRegM);
		
		spNumRegM = new JSpinner();
		spNumRegM.setFocusable(false);
		spNumRegM.setModel(new SpinnerNumberModel(new Integer(0), new Integer(0), null, new Integer(1)));
		spNumRegM.setFont(new Font("Arial Narrow", Font.BOLD | Font.ITALIC, 20));
		spNumRegM.setBounds(92,30,80,22);
		jpMedioPrincipal.add(spNumRegM);
		
		btnNuevoM = new JButton();
		btnNuevoM.setMargin(new Insets(1, 2, 2, 2));
		btnNuevoM.setHorizontalTextPosition(SwingConstants.LEFT);
		iinuevo= new ImageIcon("iconos/nuevo.png");
		btnNuevoM.setBounds(180, 27, 40, 30);
		inuevo = new ImageIcon(iinuevo.getImage().getScaledInstance(35,btnNuevoM.getHeight(), Image.SCALE_DEFAULT));
		btnNuevoM.setIcon(inuevo);
		jpMedioPrincipal.add(btnNuevoM);
		
		lblNombreM = new JLabel("Nombre Medio:");
		lblNombreM.setForeground(Color.LIGHT_GRAY);
		lblNombreM.setFont(new Font("Arial Narrow", Font.BOLD | Font.ITALIC, 14));
		lblNombreM.setBounds(10,60,82,22);
		jpMedioPrincipal.add(lblNombreM);
		
		txtNombreM = new JTextField("Havana"){
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
		txtNombreM.setCaretColor(Color.WHITE);
		txtNombreM.setForeground(Color.WHITE);
		txtNombreM.setFont(new Font("Arial Narrow", Font.BOLD | Font.ITALIC, 13));
		txtNombreM.setBounds(92,60,150,24);
		jpMedioPrincipal.add(txtNombreM);
		
		lblDescripcionM = new JLabel("Descripción:");
		lblDescripcionM.setForeground(Color.LIGHT_GRAY);
		lblDescripcionM.setFont(new Font("Arial Narrow", Font.BOLD | Font.ITALIC, 14));
		lblDescripcionM.setBounds(255,60,70,22);
		jpMedioPrincipal.add(lblDescripcionM);
		
		txtaDescripcionM = new JTextArea("Nueva sala..."){
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
		txtaDescripcionM.setCaretColor(Color.WHITE);
		txtaDescripcionM.setForeground(Color.WHITE);
		txtaDescripcionM.setFont(new Font("Arial Narrow", Font.BOLD | Font.ITALIC, 13));
		txtaDescripcionM.setBounds(325,60,215,38);
		txtaDescripcionM.setLineWrap(true);
		jpMedioPrincipal.add(txtaDescripcionM);
		
		jpGrillaM = new JPanel();
		jpGrillaM.setBackground(SystemColor.controlHighlight);
		jpGrillaM.setBorder(new TitledBorder(null, "Lista de registro de Medios", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 51)));
		jpGrillaM.setBounds(3, 107, 538, 225);
		jpMedioPrincipal.add(jpGrillaM);
		jpGrillaM.setLayout(null);
		
		txtNombreMB = new JTextField("A"){
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
		txtNombreMB.setFocusTraversalKeysEnabled(false);
		txtNombreMB.setCaretColor(Color.ORANGE);
		txtNombreMB.setForeground(Color.ORANGE);
		txtNombreMB.setBounds(160, 20, 170, 23);
		jpGrillaM.add(txtNombreMB);
		
		lblNombreMB = new JLabel("Escribe el Nombre del Medio:");
		lblNombreMB.setForeground(new Color(0, 128, 128));
		lblNombreMB.setBounds(5, 23, 155, 15);
		jpGrillaM.add(lblNombreMB);
		lblNombreMB.setFont(new Font("Arial Narrow", Font.BOLD | Font.ITALIC, 13));
		
	
		tlbListaM = new JTable(dtmM){
			@Override
		public boolean isCellEditable(int row, int column) {
			return false;
		}};//LAS CELDAS DEL JTABLE YA NO SON EDITABLES
		tlbListaM.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
		tlbListaM.setDefaultRenderer(Object.class, new RenderizarCeldaImg());
		tlbListaM.getTableHeader().setOpaque(true);
		tlbListaM.getTableHeader().setPreferredSize(new java.awt.Dimension(0, 30));
		tlbListaM.getTableHeader().setFont(new Font("Arial Narrow", Font.BOLD, 15));
		tlbListaM.getTableHeader().setForeground(Color.WHITE);
		tlbListaM.getTableHeader().setBackground(new Color(201, 24, 24));
		tlbListaM.setShowVerticalLines(false);
		tlbListaM.setRowHeight(25);
		tlbListaM.setFont(new Font("Arial", Font.PLAIN, 13));
		tlbListaM.setSelectionForeground(new Color(255, 215, 0));
		tlbListaM.setSelectionBackground(new Color(46, 93, 148));
		tlbListaM.setForeground(Color.DARK_GRAY);
		tlbListaM.setBackground(new Color(230, 230, 250));

		JscpListaM = new JScrollPane(tlbListaM);
		JscpListaM.setViewportBorder(new LineBorder(new Color(0, 139, 139), 1, true));
		JscpListaM.setBounds(5,50,527,145);
		jpGrillaM.add(JscpListaM);
		
		lblTotalItemsM = new JLabel("Total Items:");
		lblTotalItemsM.setBounds(5, 200, 100, 22);
		lblTotalItemsM.setForeground(Color.ORANGE);
		lblTotalItemsM.setBackground(new Color(46, 93, 148));
		lblTotalItemsM.setOpaque(true);
		lblTotalItemsM.setFont(new Font("Arial Narrow", Font.BOLD | Font.ITALIC, 13));
		jpGrillaM.add(lblTotalItemsM);
		
		btnExportarExcelM = new JButton();
		btnExportarExcelM.setBackground(new Color(153, 204, 153));
		btnExportarExcelM.setMargin(new Insets(1, 2, 2, 2));
		btnExportarExcelM.setHorizontalTextPosition(SwingConstants.LEFT);
		btnExportarExcelM.setText("Excel");
		iiExcel = new ImageIcon("iconos/excel.png");
		btnExportarExcelM.setBounds(447, 195, 85, 28);
		iexcel = new ImageIcon(iiExcel.getImage().getScaledInstance(30, 33, Image.SCALE_DEFAULT));
		btnExportarExcelM.setIcon(iexcel);
		jpGrillaM.add(btnExportarExcelM);
		
		jpPieM = new JPanel();
		jpPieM.setBackground(new Color(205, 92, 92));
		jpPieM.setBounds(1,333,542,38);
		jpPieM.setLayout(null);
		jpMedioPrincipal.add(jpPieM);
		
		btnOkM = new JButton("REGISTRAR");
		btnOkM.setMargin(new Insets(1, 2, 2, 2));
		btnOkM.setHorizontalTextPosition(SwingConstants.LEFT);
	 	btnOkM.setBounds(300,3,120,33);
		jpPieM.add(btnOkM);

		btnCancelM = new JButton("SALIR");
		btnCancelM.setMargin(new Insets(1, 2, 2, 2));
		btnOkM.setHorizontalTextPosition(SwingConstants.LEFT);
		iisalir= new ImageIcon("iconos/salir.png");
		btnCancelM.setBounds(445,3,90,33);
		isalir = new ImageIcon(iisalir.getImage().getScaledInstance(33,btnCancelM.getHeight(), Image.SCALE_DEFAULT));
		btnCancelM.setIcon(isalir);
		jpPieM.add(btnCancelM);
	}


}
