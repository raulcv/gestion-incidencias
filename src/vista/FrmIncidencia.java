package vista;


import java.awt.Color;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import com.toedter.calendar.JDateChooser;

import utilidades.RenderizarCeldaImg;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Insets;
import java.awt.RenderingHints;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.MaskFormatter;
import javax.swing.UIManager;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.JTable;
import java.text.ParseException;
import javax.swing.border.LineBorder;
import javax.swing.JToggleButton;
import javax.swing.JFormattedTextField;
import javax.swing.JRadioButton;

public class FrmIncidencia extends JFrame {
	
/*	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FrmIncidencia frame = new FrmIncidencia();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
*/
	public JPanel pEntradaDatos,plistaRegistros,pBusquedaRegistros;
	public JLabel lblNumReg,lblSala, lblFechaIni, lblHoraIni,lblFechaReg,lblFechaUltMod,lblTurno,lblMedio,
	lblFechaFin,lblHoraFin,lblRegistrador,lblEstadoReg,
	lblDescripcionInci, lblComentario, lblSolucion, lblRececionador, lblSolicitador, lblResolvedor,
	lblFecha1, lblFecha2,lblTotalItems;
	public JTextField txtIdUsuario,txtUsuario,txtFechaReg, txtFechaUltMod,txtRegistrador,txtEstadoReg,txtSalaB;
	public JSpinner spNumReg, spTurno, spNumRegB;
	public JDateChooser dtpFechaIni, dtpFechaFin,dtpFecha1B, dtpFecha2B;
	public JComboBox<String> cboSala, cboMedio, cboRecepcionador, cboSolicitador, cboEncargado;
	public JTextArea txtaDescripcionInci, txtaComentario, txtaSolucion;
	public JButton btnNuevo,btnProcesar, btnNuevaSala, btnNuevoMedio, btnNuevoRecepcionador,btnNuevoSolictador,
	btnNuevoEncargado,btnBuscar, btnSalir,btnExportarExcel;
	public JCheckBox chbNumRegB, chbSalaB, chbRangoFechasB;
	public JTable tlbListaInci;
	public JScrollPane JspListaInci;
	private ImageIcon iinuevo;
	private Icon inuevo;
	public DefaultTableModel dtm;
	public JToggleButton tglbtnMostrarocultar;
	public JFormattedTextField txtfHoraIni,txtfHoraFin;
	public JRadioButton rbMostrarEli;
	public MaskFormatter mfhora;
	private ImageIcon iisalir,iifiltro,iiExcel;
	private Icon isalir,ifiltro,iExcel;	
	public FrmIncidencia() {	
		iniciarcomponentes();
	}
	
	private void iniciarcomponentes() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("REGISTRO DE INCIDENCIAS");
		setBounds(100, 100, 1025, 600);
		getContentPane().setBackground(new Color(51, 0, 0));
		getContentPane().setLayout(null);
		setLocationRelativeTo(null);
		
		pEntradaDatos = new JPanel();
		pEntradaDatos.setBackground(new Color(245, 245, 245));		
		pEntradaDatos.setBorder(new TitledBorder(null, "CUADRO DE ENTRADA DE DATOS", TitledBorder.CENTER, TitledBorder.TOP, null, new Color(205, 92, 92)));
		pEntradaDatos.setBounds(3, 2, 1005, 280);
		getContentPane().add(pEntradaDatos);
		pEntradaDatos.setLayout(null);
		
		txtIdUsuario = new JTextField("001");
		txtIdUsuario.setFocusable(false);
		txtIdUsuario.setEditable(false);
		txtIdUsuario.setVisible(false);
		txtIdUsuario.setBounds(800, 10, 45, 18);
		pEntradaDatos.add(txtIdUsuario);
		
		txtUsuario = new JTextField("usuario: raulcv");
		txtUsuario.setFocusable(false);
		txtUsuario.setEditable(false);
		txtUsuario.setForeground(Color.WHITE);
		txtUsuario.setFont(new Font("Arial", Font.BOLD, 14));
		txtUsuario.setBackground(new Color(30, 144, 255));
		txtUsuario.setBounds(844, 10, 141, 20);
		pEntradaDatos.add(txtUsuario);
		
		lblNumReg = new JLabel("Nº Reg:");
		lblNumReg.setBounds(10, 27, 40, 25);
		lblNumReg.setFont(new Font("Arial Narrow", Font.BOLD | Font.ITALIC, 14));
		pEntradaDatos.add(lblNumReg);
		
		spNumReg = new JSpinner();
		spNumReg.setFocusable(false);
		spNumReg.setModel(new SpinnerNumberModel(new Integer(0), new Integer(0), null, new Integer(1)));
		spNumReg.setBounds(50, 27, 90, 25);
		spNumReg.setFont(new Font("Arial Narrow", Font.BOLD, 18));
		pEntradaDatos.add(spNumReg);
		
		btnNuevo = new JButton();
		btnNuevo.setFocusable(false);
		btnNuevo.setMargin(new Insets(1, 2, 2, 2));
		btnNuevo.setHorizontalTextPosition(SwingConstants.LEFT);
		iinuevo= new ImageIcon("iconos/nuevo.png");
		btnNuevo.setBounds(144, 23, 40, 32);
		inuevo = new ImageIcon(iinuevo.getImage().getScaledInstance(35,35, Image.SCALE_DEFAULT));
		btnNuevo.setIcon(inuevo);
		pEntradaDatos.add(btnNuevo);
		
		lblRegistrador = new JLabel("Usuario Registrador:");
		lblRegistrador.setFocusable(false);
		lblRegistrador.setFont(new Font("Arial Narrow", Font.BOLD | Font.ITALIC, 14));
		lblRegistrador.setBounds(669, 85, 110, 15);
		pEntradaDatos.add(lblRegistrador);
		
		txtRegistrador = new JTextField("Juan Arco de la Vega"){
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
		txtRegistrador.setFocusable(false);
		txtRegistrador.setEditable(false);
		txtRegistrador.setForeground(Color.ORANGE);
		txtRegistrador.setFont(new Font("Arial Narrow", Font.BOLD | Font.ITALIC, 13));
		txtRegistrador.setCaretColor(Color.WHITE);
		txtRegistrador.setBounds(669, 98, 206, 24);
		pEntradaDatos.add(txtRegistrador);
		
		
		lblEstadoReg = new JLabel("Estado Registro:");
		lblEstadoReg.setFocusable(false);
		lblEstadoReg.setFont(new Font("Arial Narrow", Font.BOLD | Font.ITALIC, 14));
		lblEstadoReg.setBounds(890, 85, 92, 15);
		pEntradaDatos.add(lblEstadoReg);
		
		txtEstadoReg = new JTextField("Modificado"){
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
		txtEstadoReg.setHorizontalAlignment(SwingConstants.CENTER);
		txtEstadoReg.setFocusable(false);
		txtEstadoReg.setEditable(false);
		txtEstadoReg.setForeground(Color.ORANGE);
		txtEstadoReg.setFont(new Font("Arial Narrow", Font.BOLD | Font.ITALIC, 13));
		txtEstadoReg.setCaretColor(Color.WHITE);
		txtEstadoReg.setBounds(885, 98, 100, 22);
		pEntradaDatos.add(txtEstadoReg);
		
		lblSala = new JLabel("N Sala:");
		lblSala.setBounds(10, 60, 40, 25);
		lblSala.setFont(new Font("Arial Narrow", Font.BOLD | Font.ITALIC, 14));
		pEntradaDatos.add(lblSala);
		
		cboSala = new JComboBox<String>();
		cboSala.setBounds(50, 60, 220, 25);
		cboSala.setFont(new Font("Arial Narrow", Font.BOLD, 15));
		cboSala.setModel(new DefaultComboBoxModel<>(new String[]{"Optimus","Palmeras","Palmeros"}));
		pEntradaDatos.add(cboSala);
		
		btnNuevaSala = new JButton();
		btnNuevaSala.setFocusable(false);
		btnNuevaSala.setBounds(275, 58, 40, 30);
		iinuevo= new ImageIcon("iconos/añadir.png");
		inuevo = new ImageIcon(iinuevo.getImage().getScaledInstance(35,btnNuevaSala.getHeight(), Image.SCALE_DEFAULT));
		btnNuevaSala.setIcon(inuevo);
		pEntradaDatos.add(btnNuevaSala);
		
		//------------------FechaInicio---------------------
		lblFechaIni = new JLabel("Fecha de Ini:");
		lblFechaIni.setBounds(330, 60, 71, 25);
		lblFechaIni.setFont(new Font("Arial Narrow", Font.BOLD | Font.ITALIC, 14));
		pEntradaDatos.add(lblFechaIni);
		
		dtpFechaIni = new JDateChooser(new Date());
		dtpFechaIni.setDateFormatString("dd/MM/yyyy");
		dtpFechaIni.setBounds(400, 60, 110, 25);
		dtpFechaIni.setFont(new Font("Arial Narrow", Font.BOLD, 17));
		pEntradaDatos.add(dtpFechaIni);
		
		lblHoraIni = new JLabel("Hora de Ini:");
		lblHoraIni.setBounds(520, 60, 62, 25);
		lblHoraIni.setFont(new Font("Arial Narrow", Font.BOLD | Font.ITALIC, 14));
		pEntradaDatos.add(lblHoraIni);

		try {
			mfhora = new MaskFormatter("##:##:##");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		txtfHoraIni = new JFormattedTextField(mfhora);
		txtfHoraIni.setFont(new Font("Arial Narrow", Font.BOLD, 17));
		txtfHoraIni.setBounds(583, 60, 70, 25);
		pEntradaDatos.add(txtfHoraIni);
		
		//FECHA REGISTRO
		lblFechaReg = new JLabel("Fecha de Registro:");
		lblFechaReg.setFocusable(false);
		lblFechaReg.setFont(new Font("Arial Narrow", Font.BOLD | Font.ITALIC, 14));
		lblFechaReg.setBounds(669,46,125,15);
		pEntradaDatos.add(lblFechaReg);
		
		txtFechaReg = new JTextField(){
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
		txtFechaReg.setFont(new Font("Arial Narrow", Font.BOLD, 15));
		txtFechaReg.setFocusable(false);
		txtFechaReg.setForeground(Color.ORANGE);
		txtFechaReg.setEditable(false);
		txtFechaReg.setBounds(669,61,140,23);
		pEntradaDatos.add(txtFechaReg);
		
		lblFechaUltMod = new JLabel("Última Modificación:");
		lblFechaUltMod.setFocusable(false);
		lblFechaUltMod.setFont(new Font("Arial Narrow", Font.BOLD | Font.ITALIC, 14));
		lblFechaUltMod.setBounds(844,46,125,15);
		pEntradaDatos.add(lblFechaUltMod);
		
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
		txtFechaUltMod.setFont(new Font("Arial Narrow", Font.BOLD, 15));
		txtFechaUltMod.setFocusable(false);
		txtFechaUltMod.setForeground(Color.ORANGE);
		txtFechaUltMod.setEditable(false);
		txtFechaUltMod.setBounds(844,61,140,23);
		pEntradaDatos.add(txtFechaUltMod);
		
		//------------------FechaFin---------------------
		lblFechaFin = new JLabel("Fecha de fin:");
		lblFechaFin.setBounds(330, 95, 71, 25);
		lblFechaFin.setFont(new Font("Arial Narrow", Font.BOLD | Font.ITALIC, 14));
		pEntradaDatos.add(lblFechaFin);
		
		dtpFechaFin = new JDateChooser(new Date());
		dtpFechaFin.setDateFormatString("dd/MM/yyyy");
		dtpFechaFin.setBounds(400, 95, 110, 25);
		dtpFechaFin.setFont(new Font("Arial Narrow", Font.BOLD, 17));
		pEntradaDatos.add(dtpFechaFin);
		
		lblHoraFin = new JLabel("Hora de fin:");
		lblHoraFin.setBounds(520, 95, 62, 25);
		lblHoraFin.setFont(new Font("Arial Narrow", Font.BOLD | Font.ITALIC, 14));
		pEntradaDatos.add(lblHoraFin);

		try {
			mfhora = new MaskFormatter("##:##:##");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		txtfHoraFin = new JFormattedTextField(mfhora);
		txtfHoraFin.setFont(new Font("Arial Narrow", Font.BOLD, 17));
		txtfHoraFin.setBounds(583, 95, 70, 25);
		pEntradaDatos.add(txtfHoraFin);
		
		//--------------------TurnoyMedio--------------------
		lblTurno = new JLabel("Turno:");
		lblTurno.setBounds(10, 95, 40, 25);
		lblTurno.setFont(new Font("Arial Narrow", Font.BOLD | Font.ITALIC, 14));
		pEntradaDatos.add(lblTurno);
		
		spTurno = new JSpinner();
		spTurno.setModel(new SpinnerNumberModel(1, 1, 3, 1));
		spTurno.setFont(new Font("Arial Narrow", Font.BOLD, 15));
		spTurno.setBounds(50, 95, 50, 25);
		pEntradaDatos.add(spTurno);
		
		lblMedio = new JLabel("Medio:");
		lblMedio.setBounds(105, 96, 38, 25);
		lblMedio.setFont(new Font("Arial Narrow", Font.BOLD | Font.ITALIC, 14));
		pEntradaDatos.add(lblMedio);
		
		cboMedio = new JComboBox<String>();
		cboMedio.setBounds(144, 95, 126, 25);
		cboMedio.setFont(new Font("Arial Narrow", Font.BOLD, 15));
		pEntradaDatos.add(cboMedio);
		
		btnNuevoMedio = new JButton();
		btnNuevoMedio.setFocusable(false);
		btnNuevoMedio.setBounds(275, 93, 40, 30);
		btnNuevoMedio.setIcon(inuevo);
		pEntradaDatos.add(btnNuevoMedio);
		
		//--------------------IncidenciaComentarioSolucion--------------------
		lblDescripcionInci = new JLabel("Incidencia:");
		lblDescripcionInci.setBounds(10, 125, 82, 15);
		lblDescripcionInci.setFont(new Font("Arial Narrow", Font.BOLD | Font.ITALIC, 14));
		pEntradaDatos.add(lblDescripcionInci);
		
		txtaDescripcionInci = new JTextArea("Hubo un problema"){
			@Override protected void paintComponent(Graphics g) {
				if (!isOpaque()) {
			          int w = getWidth() - 1;
			          int h = getHeight() - 1;
			          Graphics2D g2 = (Graphics2D) g.create();
			          g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			          g2.setPaint(UIManager.getColor("TextField.foreground"));
			          g2.fillRoundRect(0, 0, w, h, h, h);
			          g2.setPaint(Color.CYAN);//COLOR BORDE
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
		txtaDescripcionInci.setCaretColor(Color.WHITE);
		txtaDescripcionInci.setForeground(Color.WHITE);
		txtaDescripcionInci.setLineWrap(true);
		txtaDescripcionInci.setWrapStyleWord(true);
		txtaDescripcionInci.setBounds(7, 140, 310, 50);
		txtaDescripcionInci.setFont(new Font("Arial", Font.ITALIC, 12));
		pEntradaDatos.add(txtaDescripcionInci);
		
		lblComentario = new JLabel("Comentario:");
		lblComentario.setBounds(330, 125, 71, 15);
		lblComentario.setFont(new Font("Arial Narrow", Font.BOLD | Font.ITALIC, 14));
		pEntradaDatos.add(lblComentario);
		
		txtaComentario = new JTextArea("Nada se"){
			@Override protected void paintComponent(Graphics g) {
				if (!isOpaque()) {
			          int w = getWidth() - 1;
			          int h = getHeight() - 1;
			          Graphics2D g2 = (Graphics2D) g.create();
			          g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			          g2.setPaint(UIManager.getColor("TextField.foreground"));
			          g2.fillRoundRect(0, 0, w, h, h, h);
			          g2.setPaint(Color.CYAN);//COLOR BORDE
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
		txtaComentario.setCaretColor(Color.WHITE);
		txtaComentario.setForeground(Color.WHITE);
		txtaComentario.setLineWrap(true);
		txtaComentario.setWrapStyleWord(true);
		txtaComentario.setBounds(327, 140, 330, 50);
		txtaComentario.setFont(new Font("Arial", Font.ITALIC, 12));
		pEntradaDatos.add(txtaComentario);
		
		lblSolucion = new JLabel("Soluciòn:");
		lblSolucion.setBounds(670, 125, 76, 15);
		lblSolucion.setFont(new Font("Arial Narrow", Font.BOLD | Font.ITALIC, 14));
		pEntradaDatos.add(lblSolucion);
		
		txtaSolucion = new JTextArea("Se"){
			@Override protected void paintComponent(Graphics g) {
				if (!isOpaque()) {
			          int w = getWidth() - 1;
			          int h = getHeight() - 1;
			          Graphics2D g2 = (Graphics2D) g.create();
			          g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			          g2.setPaint(UIManager.getColor("TextField.foreground"));
			          g2.fillRoundRect(0, 0, w, h, h, h);
			          g2.setPaint(Color.CYAN);//COLOR BORDE
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
		txtaSolucion.setCaretColor(Color.WHITE);
		txtaSolucion.setForeground(Color.WHITE);
		txtaSolucion.setLineWrap(true);
		txtaSolucion.setWrapStyleWord(true);
		txtaSolucion.setLineWrap(true);
		txtaSolucion.setBounds(667, 140, 323, 50);
		txtaSolucion.setFont(new Font("Arial", Font.ITALIC, 12));
		pEntradaDatos.add(txtaSolucion);
		
		//--------------------CombosPersonas--------------------	
		lblRececionador = new JLabel("Recepcionado Por:");
		lblRececionador.setBounds(10, 190, 250, 15);
		lblRececionador.setFont(new Font("Arial Narrow", Font.BOLD | Font.ITALIC, 14));
		pEntradaDatos.add(lblRececionador);
		
		cboRecepcionador = new JComboBox<String>();
		cboRecepcionador.setBounds(10, 205, 260, 25);
		cboRecepcionador.setFont(new Font("Arial Narrow", Font.BOLD, 15));
		pEntradaDatos.add(cboRecepcionador);

		btnNuevoRecepcionador = new JButton();
		btnNuevoRecepcionador.setFocusable(false);
		btnNuevoRecepcionador.setBounds(275, 202, 40, 30);
		btnNuevoRecepcionador.setIcon(inuevo);
		pEntradaDatos.add(btnNuevoRecepcionador);
		
		//------------------------------------------------
		lblSolicitador = new JLabel("Solicitado Por:");
		lblSolicitador.setBounds(330, 190, 258, 15);
		lblSolicitador.setFont(new Font("Arial Narrow", Font.BOLD | Font.ITALIC, 14));
		pEntradaDatos.add(lblSolicitador);
		
		cboSolicitador = new JComboBox<String>();
		cboSolicitador.setBounds(330, 205, 280, 25);
		cboSolicitador.setFont(new Font("Arial Narrow", Font.BOLD, 15));
		pEntradaDatos.add(cboSolicitador);

		btnNuevoSolictador = new JButton();
		btnNuevoSolictador.setFocusable(false);
		btnNuevoSolictador.setBounds(614, 202, 40, 30);
		btnNuevoSolictador.setIcon(inuevo);
		pEntradaDatos.add(btnNuevoSolictador);
		
		//--------------------------------------------------
		lblResolvedor = new JLabel("Caso Asignado A:");
		lblResolvedor.setBounds(670, 190, 244, 15);
		lblResolvedor.setFont(new Font("Arial Narrow", Font.BOLD | Font.ITALIC, 14));
		pEntradaDatos.add(lblResolvedor);
		
		cboEncargado = new JComboBox<String>();
		cboEncargado.setBounds(670, 205, 275, 25);
		cboEncargado.setFont(new Font("Arial Narrow", Font.BOLD, 15));
		pEntradaDatos.add(cboEncargado);
		
		btnNuevoEncargado = new JButton();
		btnNuevoEncargado.setFocusable(false);
		btnNuevoEncargado.setBounds(950, 202, 40, 30);
		btnNuevoEncargado.setIcon(inuevo);
		pEntradaDatos.add(btnNuevoEncargado);

		//--------------------BotonProcesar/SALIR--------------------	
		btnProcesar = new JButton("PROCESAR");
		btnProcesar.setFont(new Font("Arial Narrow", Font.BOLD, 15));
		btnProcesar.setMargin(new Insets(1, 1, 0, 1));
		btnProcesar.setBounds(10, 239, 210, 35);
		btnProcesar.setHorizontalTextPosition(SwingConstants.LEFT);
		pEntradaDatos.add(btnProcesar);
		
		tglbtnMostrarocultar = new JToggleButton("MostrarOcultar");
		tglbtnMostrarocultar.setFont(new Font("Arial Narrow", Font.BOLD | Font.ITALIC, 14));
		tglbtnMostrarocultar.setMargin(new Insets(1, 1, 1, 1));
		tglbtnMostrarocultar.setBounds(383, 239, 270, 35);
		tglbtnMostrarocultar.setHorizontalTextPosition(SwingConstants.LEFT);
		pEntradaDatos.add(tglbtnMostrarocultar);
		
		btnSalir = new JButton("SALIR DEL SISTEMA");
		btnSalir.setFont(new Font("Arial Narrow", Font.BOLD, 15));
		btnSalir.setMargin(new Insets(1, 1, 0, 1));
		btnSalir.setBounds(820, 239, 170, 35);
		btnSalir.setHorizontalTextPosition(SwingConstants.LEFT);
		iisalir= new ImageIcon("iconos/salir.png");
		isalir = new ImageIcon(iisalir.getImage().getScaledInstance(33,btnSalir.getHeight(), Image.SCALE_DEFAULT));
		btnSalir.setIcon(isalir);
		pEntradaDatos.add(btnSalir);
		
		//--------------------Panel--------------------	
		plistaRegistros = new JPanel();
		plistaRegistros.setBounds(2, 285, 1005, 272);
		plistaRegistros.setBackground(Color.WHITE);
		getContentPane().add(plistaRegistros);
		plistaRegistros.setLayout(null);
		
		pBusquedaRegistros = new JPanel();
		pBusquedaRegistros.setBackground(new Color(51, 102, 102));
		pBusquedaRegistros.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "REALIZAR UNA B\u00DASQUEDA", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(255, 255, 255)));
		pBusquedaRegistros.setBounds(2, 2, 1003, 70);
		plistaRegistros.add(pBusquedaRegistros);
		pBusquedaRegistros.setLayout(null);
		
		chbNumRegB = new JCheckBox("Por Nº Registro:");
		chbNumRegB.setFont(new Font("Arial Narrow", Font.BOLD | Font.ITALIC, 13));
		chbNumRegB.setBounds(8, 18, 110, 15);
		pBusquedaRegistros.add(chbNumRegB);
		
		spNumRegB = new JSpinner();
		spNumRegB.setModel(new SpinnerNumberModel(new Integer(1), new Integer(1), null, new Integer(1)));
		spNumRegB.setFont(new Font("Tahoma", Font.BOLD, 14));
		spNumRegB.setBounds(8, 34, 110, 25);
		pBusquedaRegistros.add(spNumRegB);
		
		chbSalaB = new JCheckBox("Por Nombre de Sala:");
		chbSalaB.setFont(new Font("Arial Narrow", Font.BOLD | Font.ITALIC, 13));
		chbSalaB.setBounds(150, 18, 150, 15);
		pBusquedaRegistros.add(chbSalaB);
		
		txtSalaB = new JTextField("HaVaNa");
		txtSalaB.setFont(new Font("Arial", Font.BOLD, 14));
		txtSalaB.setBounds(150, 34, 150, 25);
		pBusquedaRegistros.add(txtSalaB);
		
		chbRangoFechasB = new JCheckBox("Por Rango de Fechas (Fecha de Inicio):");
		chbRangoFechasB.setFont(new Font("Arial Narrow", Font.BOLD | Font.ITALIC, 13));
		chbRangoFechasB.setBounds(330, 18, 265, 15);
		pBusquedaRegistros.add(chbRangoFechasB);
		
		lblFecha1 = new JLabel("Inicio:");
		lblFecha1.setForeground(new Color(255, 255, 255));
		lblFecha1.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 10));
		lblFecha1.setBounds(328, 35, 33, 22);
		pBusquedaRegistros.add(lblFecha1);
		
		dtpFecha1B = new JDateChooser(new Date());
		dtpFecha1B.setDateFormatString("dd/MM/yyyy");
		dtpFecha1B.setBounds(360, 34, 105, 25);
		dtpFecha1B.setFont(new Font("Arial", Font.BOLD, 14));
		pBusquedaRegistros.add(dtpFecha1B);

		lblFecha2 = new JLabel("Fin:");
		lblFecha2.setForeground(new Color(255, 255, 255));
		lblFecha2.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 11));
		lblFecha2.setBounds(470, 37, 20, 22);
		pBusquedaRegistros.add(lblFecha2);
		
		dtpFecha2B = new JDateChooser(new Date());
		dtpFecha2B.setDateFormatString("dd/MM/yyyy");
		dtpFecha2B.setBounds(490, 34, 105, 25);
		dtpFecha2B.setFont(new Font("Arial", Font.BOLD, 14));
		pBusquedaRegistros.add(dtpFecha2B);
		
		rbMostrarEli = new JRadioButton("<html>Mostrar registros Eliminados.</html>");
		rbMostrarEli.setBorderPainted(true);
		rbMostrarEli.setBounds(610, 18, 195, 25);
		rbMostrarEli.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 12));
		rbMostrarEli.setForeground(new Color(2, 100, 5));
		pBusquedaRegistros.add(rbMostrarEli);
		
		btnBuscar = new JButton("BUSCAR");
		btnBuscar.setFont(new Font("Tahoma", Font.BOLD, 16));
		btnBuscar.setBounds(870, 30, 120, 30);
		btnBuscar.setMargin(new Insets(1, 1, 0, 1));
		btnBuscar.setHorizontalTextPosition(SwingConstants.LEFT);
		iifiltro= new ImageIcon("iconos/filtro.png");
		ifiltro = new ImageIcon(iifiltro.getImage().getScaledInstance(33,btnBuscar.getHeight(), Image.SCALE_DEFAULT));
		btnBuscar.setIcon(ifiltro);
		pBusquedaRegistros.add(btnBuscar);
		
		
		tlbListaInci = new JTable(dtm){
			@Override
		public boolean isCellEditable(int row, int column) {
			return false;
		}};//LAS CELDAS DEL JTABLE YA NO SON EDITABLES
		tlbListaInci.setShowVerticalLines(false);
		tlbListaInci.setAutoResizeMode(JTable.AUTO_RESIZE_NEXT_COLUMN);
		tlbListaInci.setDefaultRenderer(Object.class, new RenderizarCeldaImg());
		tlbListaInci.getTableHeader().setOpaque(true);
		tlbListaInci.getTableHeader().setPreferredSize(new java.awt.Dimension(0, 30));
		tlbListaInci.getTableHeader().setFont(new Font("Arial Narrow", Font.BOLD, 15));
		tlbListaInci.getTableHeader().setForeground(Color.WHITE);
		tlbListaInci.getTableHeader().setBackground(new Color(0, 150, 200));
		tlbListaInci.setRowHeight(26);
		tlbListaInci.setFont(new Font("Arial", Font.PLAIN, 13));
		tlbListaInci.setSelectionForeground(new Color(255, 215, 0));
		tlbListaInci.setSelectionBackground(new Color(205, 92, 92));
		tlbListaInci.setForeground(Color.DARK_GRAY);
		tlbListaInci.setBackground(new Color(230, 230, 250));
		
		JspListaInci = new JScrollPane(tlbListaInci);
		JspListaInci.setBounds(0, 75, 1005, 170);
		JspListaInci.setViewportBorder(new LineBorder(new Color(0, 139, 139), 1, true));
		plistaRegistros.add(JspListaInci);
		
		lblTotalItems = new JLabel("Total Items:");
		lblTotalItems.setBounds(1, 248, 115, 22);
		lblTotalItems.setForeground(Color.ORANGE);
		lblTotalItems.setBackground(new Color(204, 51, 51));
		lblTotalItems.setOpaque(true);
		lblTotalItems.setFont(new Font("Arial Narrow", Font.BOLD | Font.ITALIC, 13));
		plistaRegistros.add(lblTotalItems);
	
		btnExportarExcel = new JButton();
		btnExportarExcel.setBackground(new Color(153, 204, 153));
		btnExportarExcel.setMargin(new Insets(1, 2, 2, 2));
		btnExportarExcel.setHorizontalTextPosition(SwingConstants.LEFT);
		btnExportarExcel.setText("Excel");
		iiExcel = new ImageIcon("iconos/excel.png");
		btnExportarExcel.setBounds(918, 244, 85, 27);
		iExcel = new ImageIcon(iiExcel.getImage().getScaledInstance(30, 33, Image.SCALE_DEFAULT));
		btnExportarExcel.setIcon(iExcel);
		plistaRegistros.add(btnExportarExcel);
	}
}
