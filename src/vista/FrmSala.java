package vista;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import javax.swing.SpinnerNumberModel;
import javax.swing.JTable;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.plaf.TableHeaderUI;
import javax.swing.plaf.metal.MetalBorders.TableHeaderBorder;
import javax.swing.table.DefaultTableModel;

import utilidades.RenderizarCeldaImg;

import javax.swing.UIManager;
import javax.swing.SwingConstants;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.SystemColor;

public class FrmSala extends JDialog {

	private final JPanel jpSalaPrincipal = new JPanel();
	private JPanel jpPie, jpGrlla;
	public JButton btnnuevo,btnOk, btnCancel, btnExportarExcel;
	public JLabel lblRegistroDeSalas,lblFechaReg,lblNumReg, lblNombreS, lblDescripcion, lblNombreSB, lblTotalItems;
	public JSpinner spNumReg;
	public JTextField txtFechaReg,txtNombreS, txtNombreSB, txtPrueba;
	public JTextArea txtaDescripcion;
	public JTable tlbLista;
	public DefaultTableModel dtm;
	public JScrollPane JscpLista;
	public ImageIcon iinuevo,iiExcel,iisalir;
	public Icon inuevo,iexcel,isalir;
/*	public static void main(String[] args) {
		try {
			FrmSala dialog = new FrmSala();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}*/
	public FrmSala(FrmSala frmSala, boolean modal) {
		super(frmSala,modal);
		InicializarValores();
	}
	public void InicializarValores(){
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 560, 411);
		jpSalaPrincipal.setFocusTraversalKeysEnabled(false);
		jpSalaPrincipal.setBorder(new LineBorder(new Color(0, 0, 0)));
		jpSalaPrincipal.setBackground(new Color(47, 79, 79));
		jpSalaPrincipal.setLayout(null);
		getContentPane().add(jpSalaPrincipal, BorderLayout.CENTER);
		setLocationRelativeTo(null);
		
		lblRegistroDeSalas = new JLabel("REGISTRO DE SALAS");
		lblRegistroDeSalas.setBorder(new LineBorder(new Color(255, 165, 0), 1, true));
		lblRegistroDeSalas.setOpaque(true);
		lblRegistroDeSalas.setBackground(new Color(205, 92, 92));
		lblRegistroDeSalas.setForeground(Color.white);
		lblRegistroDeSalas.setHorizontalAlignment(SwingConstants.CENTER);
		lblRegistroDeSalas.setFont(new Font("Arial Narrow", Font.BOLD, 15));
		lblRegistroDeSalas.setBounds(1, 4, 542, 18);
		jpSalaPrincipal.add(lblRegistroDeSalas);

		lblFechaReg = new JLabel("Fecha:");
		lblFechaReg.setForeground(Color.LIGHT_GRAY);
		lblFechaReg.setFont(new Font("Arial Narrow", Font.BOLD | Font.ITALIC, 14));
		lblFechaReg.setBounds(275,30,40,20);
		jpSalaPrincipal.add(lblFechaReg);
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		txtFechaReg = new JTextField(sdf.format(new Date())){
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
		txtFechaReg.setFont(new Font("Arial Narrow", Font.BOLD, 14));
		txtFechaReg.setFocusable(false);
		txtFechaReg.setForeground(Color.CYAN);
		txtFechaReg.setEditable(false);
		txtFechaReg.setBounds(313,30,125,23);
		jpSalaPrincipal.add(txtFechaReg);
		
		lblNumReg = new JLabel("Nº Registro:");
		lblNumReg.setForeground(Color.LIGHT_GRAY);
		lblNumReg.setFont(new Font("Arial Narrow", Font.BOLD | Font.ITALIC, 14));
		lblNumReg.setBounds(10,30,72,22);
		jpSalaPrincipal.add(lblNumReg);
		
		spNumReg = new JSpinner();
		spNumReg.setFocusable(false);
		spNumReg.setModel(new SpinnerNumberModel(new Integer(0), new Integer(0), null, new Integer(1)));
		spNumReg.setFont(new Font("Arial Narrow", Font.BOLD | Font.ITALIC, 20));
		spNumReg.setBounds(82,30,80,22);
		jpSalaPrincipal.add(spNumReg);
		
		btnnuevo = new JButton();
		btnnuevo.setMargin(new Insets(1, 2, 2, 2));
		btnnuevo.setHorizontalTextPosition(SwingConstants.LEFT);
		iinuevo= new ImageIcon("iconos/nuevo.png");
		btnnuevo.setBounds(165, 27, 40, 30);
		inuevo = new ImageIcon(iinuevo.getImage().getScaledInstance(35,btnnuevo.getHeight(), Image.SCALE_DEFAULT));
		btnnuevo.setIcon(inuevo);
		jpSalaPrincipal.add(btnnuevo);
		
		lblNombreS = new JLabel("Nombre Sala:");
		lblNombreS.setForeground(Color.LIGHT_GRAY);
		lblNombreS.setFont(new Font("Arial Narrow", Font.BOLD | Font.ITALIC, 14));
		lblNombreS.setBounds(10,60,72,22);
		jpSalaPrincipal.add(lblNombreS);
		
		txtNombreS = new JTextField("Havana"){
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
		txtNombreS.setCaretColor(Color.WHITE);
		//txtNombreS.setOpaque(true);
		//txtNombreS.setBackground(Color.RED);
		txtNombreS.setForeground(Color.WHITE);
		txtNombreS.setFont(new Font("Arial Narrow", Font.BOLD | Font.ITALIC, 13));
		txtNombreS.setBounds(82,60,155,24);
		jpSalaPrincipal.add(txtNombreS);
		
		lblDescripcion = new JLabel("Descripción:");
		lblDescripcion.setForeground(Color.LIGHT_GRAY);
		lblDescripcion.setFont(new Font("Arial Narrow", Font.BOLD | Font.ITALIC, 14));
		lblDescripcion.setBounds(245,60,72,22);
		jpSalaPrincipal.add(lblDescripcion);
		
		txtaDescripcion = new JTextArea("Nueva sala..."){
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
		txtaDescripcion.setCaretColor(Color.WHITE);
		txtaDescripcion.setForeground(Color.WHITE);
		//txtaDescripcion.setBackground(Color.GRAY);
		txtaDescripcion.setFont(new Font("Arial Narrow", Font.BOLD | Font.ITALIC, 13));
		txtaDescripcion.setBounds(313,60,221,38);
		txtaDescripcion.setLineWrap(true);
		jpSalaPrincipal.add(txtaDescripcion);
		
		jpGrlla = new JPanel();
		jpGrlla.setBackground(SystemColor.controlHighlight);
		jpGrlla.setBorder(new TitledBorder(null, "Lista de registro de Salas", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 51)));
		jpGrlla.setBounds(3, 107, 538, 225);
		jpSalaPrincipal.add(jpGrlla);
		jpGrlla.setLayout(null);
		
		txtNombreSB = new JTextField("A"){
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
		txtNombreSB.setFocusTraversalKeysEnabled(false);
		txtNombreSB.setCaretColor(Color.ORANGE);
		txtNombreSB.setForeground(Color.ORANGE);
		txtNombreSB.setBounds(160, 20, 170, 23);
		jpGrlla.add(txtNombreSB);
		
		lblNombreSB = new JLabel("Escribe el Nombre de la Sala:");
		lblNombreSB.setForeground(new Color(0, 128, 128));
		lblNombreSB.setBounds(5, 23, 155, 15);
		jpGrlla.add(lblNombreSB);
		lblNombreSB.setFont(new Font("Arial Narrow", Font.BOLD | Font.ITALIC, 13));
		
	
		tlbLista = new JTable(dtm){
			@Override
		public boolean isCellEditable(int row, int column) {
			return false;
		}};//LAS CELDAS DEL JTABLE YA NO SON EDITABLES
		tlbLista.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
		tlbLista.setDefaultRenderer(Object.class, new RenderizarCeldaImg());
		tlbLista.getTableHeader().setOpaque(true);
		tlbLista.getTableHeader().setPreferredSize(new java.awt.Dimension(0, 30));
		tlbLista.getTableHeader().setFont(new Font("Arial Narrow", Font.BOLD, 15));
		tlbLista.getTableHeader().setForeground(Color.WHITE);
		tlbLista.getTableHeader().setBackground(new Color(23, 136, 203));
		tlbLista.setShowVerticalLines(false);
		tlbLista.setRowHeight(25);
		tlbLista.setFont(new Font("Arial", Font.PLAIN, 13));
		tlbLista.setSelectionForeground(new Color(255, 215, 0));
		tlbLista.setSelectionBackground(new Color(47, 79, 79));
		tlbLista.setForeground(Color.DARK_GRAY);
		tlbLista.setBackground(new Color(230, 230, 250));

		JscpLista = new JScrollPane(tlbLista);
		JscpLista.setViewportBorder(new LineBorder(new Color(0, 139, 139), 1, true));
		JscpLista.setBounds(5,50,527,145);
		//JscpLista.setViewportView(tlbLista);
		jpGrlla.add(JscpLista);
		
		lblTotalItems = new JLabel("Total Items:");
		lblTotalItems.setBounds(5, 200, 100, 22);
		lblTotalItems.setForeground(Color.ORANGE);
		lblTotalItems.setBackground(new Color(47, 79, 79));
		lblTotalItems.setOpaque(true);
		lblTotalItems.setFont(new Font("Arial Narrow", Font.BOLD | Font.ITALIC, 13));
		jpGrlla.add(lblTotalItems);
		
		btnExportarExcel = new JButton();
		btnExportarExcel.setBackground(new Color(153, 204, 153));
		btnExportarExcel.setMargin(new Insets(1, 2, 2, 2));
		btnExportarExcel.setHorizontalTextPosition(SwingConstants.LEFT);
		btnExportarExcel.setText("Excel");
		iiExcel = new ImageIcon("iconos/excel.png");
		btnExportarExcel.setBounds(447, 195, 85, 28);
		iexcel = new ImageIcon(iiExcel.getImage().getScaledInstance(30, 33, Image.SCALE_DEFAULT));
		btnExportarExcel.setIcon(iexcel);
		jpGrlla.add(btnExportarExcel);
		
		jpPie = new JPanel();
		jpPie.setBackground(new Color(205, 92, 92));
		jpPie.setBounds(1,333,542,38);
		jpPie.setLayout(null);
		jpSalaPrincipal.add(jpPie);
		//jpPie.setLayout(new FlowLayout(FlowLayout.RIGHT));
		//getContentPane().add(jpPie, BorderLayout.SOUTH);

		btnOk = new JButton("REGISTRAR");
		btnOk.setMargin(new Insets(1, 2, 2, 2));
		btnOk.setHorizontalTextPosition(SwingConstants.LEFT);
	 	btnOk.setBounds(300,3,120,33);
		jpPie.add(btnOk);

		btnCancel = new JButton("SALIR");
		btnCancel.setMargin(new Insets(1, 2, 2, 2));
		btnOk.setHorizontalTextPosition(SwingConstants.LEFT);
		iisalir= new ImageIcon("iconos/salir.png");
		btnCancel.setBounds(445,3,90,33);
		isalir = new ImageIcon(iisalir.getImage().getScaledInstance(33,btnCancel.getHeight(), Image.SCALE_DEFAULT));
		btnCancel.setIcon(isalir);
		jpPie.add(btnCancel);
	}
	
}
