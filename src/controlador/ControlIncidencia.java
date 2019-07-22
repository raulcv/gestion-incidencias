package controlador;
import java.awt.Color;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.xml.soap.Text;

import bean.IncidenciaBean;
import bean.MedioBean;
import bean.PersonaBean;
import bean.SalaBean;
import bean.UsuarioBean;
import consultas.ExportarExcel;
import dao.IncidenciaDAO;
import dao.MedioDAO;
import dao.PersonaDAO;
import dao.SalaDAO;
import dao.UsuarioDAO;
import utilidades.FormatoHora;
import utilidades.RenderizarCeldaImg;
import vista.FrmDecision;
import vista.FrmIncidencia;
import vista.FrmMedio;
import vista.FrmMensaje;
import vista.FrmPersona;
import vista.FrmSala;

public class ControlIncidencia implements ActionListener, FocusListener,MouseListener,ChangeListener,KeyListener{

	private FrmIncidencia frm = null;
	private IncidenciaBean incB = null;
	private IncidenciaDAO incD = null;
	private ArrayList<IncidenciaBean> listaInc = null;
	
	
	private FrmSala frmSala = null;
	private SalaBean salaB = null;
	private SalaDAO salaD = null;
	private ArrayList<SalaBean> listaSala=null;
	ControlSala ctrolSala=null;
	
	private FrmMedio frmMedio = null;
	private MedioBean medioB = null;
	private MedioDAO medioD = null;
	private ArrayList<MedioBean> listaMedio=null;
	ControlMedio ctrolMedio=null;
	
	private FrmDecision frmDecision=null;
	private FormatoHora fh=null;
	private ExportarExcel exportarExcel=null;
	private FrmMensaje frmMSG=null;
	
	private FrmPersona frmPersona = null;
	private PersonaBean personaB = null;
	private PersonaDAO personaD = null;
	private ArrayList<PersonaBean> listaPersona = null;
	ControlPersona ctrolPersona = null;
	
	public int codigoUsu;
	public String usuario;
	
	private static int tipoBusqueda=0;
	private int modo = 0;
	private int Numventana = 0;
	
	public ControlIncidencia(FrmIncidencia frm,IncidenciaBean incBean,IncidenciaDAO incDao) {
		this.frm = frm;
		this.incB = incBean;
		this.incD = incDao;		
		this.frm.btnNuevo.addActionListener(this);
		this.frm.btnProcesar.addActionListener(this);
		this.frm.btnNuevaSala.addActionListener(this);
		this.frm.btnNuevoMedio.addActionListener(this);
		this.frm.btnNuevoSolictador.addActionListener(this);
		this.frm.btnNuevoRecepcionador.addActionListener(this);
		this.frm.btnNuevoEncargado.addActionListener(this);
		this.frm.tglbtnMostrarocultar.addActionListener(this);
		this.frm.btnBuscar.addActionListener(this);
		this.frm.btnSalir.addActionListener(this);
		this.frm.btnExportarExcel.addActionListener(this);
		//FOCUS LISTENER
		this.frm.txtfHoraIni.addFocusListener(this);
		this.frm.txtfHoraFin.addFocusListener(this);		
		//CHANGED LISTENER
		this.frm.chbNumRegB.addChangeListener(this);
		this.frm.chbSalaB.addChangeListener(this);
		this.frm.chbRangoFechasB.addChangeListener(this);
		
		//MOUSE LISTENER
		this.frm.tlbListaInci.addMouseListener(this);
		this.frm.rbMostrarEli.addMouseListener(this);
		//KEY LISTENER
		this.frm.txtaDescripcionInci.addKeyListener(this);
		this.frm.txtaComentario.addKeyListener(this);
		this.frm.txtaSolucion.addKeyListener(this);
		this.frm.btnProcesar.addKeyListener(this);
		this.frm.btnSalir.addKeyListener(this);
		
		
		inicarComponentes();
		cargarSalas();
		cargarMedios();
		cargarRecepcionadores();
		cargarSolicitadores();
		cargarEncargados();
		listarIncidencias();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==frm.btnNuevo){
			modoRegistrar();
		}else if(e.getSource()==frm.btnProcesar){
			if(validaciones()==false){
				if(modo==1){
					registrarIncidencia();
				}else if(modo==2){
					frmMSG = new FrmMensaje(frmMSG, true);
					frmMSG.txtaMgs.setText("¿Estas seguro de Modificar el Registro Nº: "+frm.spNumReg.getValue()+"?");
					frmMSG.setVisible(true);
					if(FrmMensaje.valor==1){
						modificarIncidencia();
					}
				}else if(modo==3){
					frmMSG = new FrmMensaje(frmMSG, true);
					frmMSG.txtaMgs.setText("¿Estas seguro de Eliminar el Registro Nº: "+frm.spNumReg.getValue()+"?");
					frmMSG.setVisible(true);
					if(FrmMensaje.valor==1){
						eliminarIncidencia();
					}					
				}
			}
		}else if(e.getSource()==frm.btnNuevaSala){
			frmSala = new FrmSala(frmSala, true);
			ctrolSala = new ControlSala(salaB, salaD, frmSala);
			frmSala.setVisible(true);
			Numventana = 1;
			refrescar();
		}else if(e.getSource()==frm.btnNuevoMedio){
			frmMedio = new FrmMedio(frmMedio, true);
			ctrolMedio = new ControlMedio(medioB, medioD, frmMedio);
			frmMedio.setVisible(true);
			Numventana = 2;
			refrescar();
		}else if(e.getSource()==frm.btnNuevoRecepcionador){
			frmPersona = new FrmPersona(frmPersona, true);
			ctrolPersona = new ControlPersona(personaB, personaD, frmPersona);
			frmPersona.txtIdUsuarioP.setText(Integer.toString(codigoUsu));
			frmPersona.txtUsuarioP.setText(usuario);
			frmPersona.setVisible(true);
			Numventana = 3;
			refrescar();
		}else if(e.getSource()==frm.btnNuevoSolictador){
			frmDecision = new FrmDecision(frmDecision, true);
			frmDecision.txtaMgs.setText("¿Que ventana desea Abrir?");
			frmDecision.setVisible(true);
			if(frmDecision.valorResultado==1){
				frmSala = new FrmSala(frmSala, true);
				ctrolSala = new ControlSala(salaB, salaD, frmSala);
				frmSala.setVisible(true);
				Numventana = 1;
				refrescar();
			}else if(frmDecision.valorResultado==2){
				frmPersona = new FrmPersona(frmPersona, true);
				ctrolPersona = new ControlPersona(personaB, personaD, frmPersona);
				frmPersona.setVisible(true);
				Numventana = 3;
				refrescar();
			}else if(frmDecision.valorResultado==3){	
				frmDecision.setVisible(false);
			}
		}else if(e.getSource()==frm.btnNuevoEncargado){
			frmPersona = new FrmPersona(frmPersona, true);
			ctrolPersona = new ControlPersona(personaB, personaD, frmPersona);
			frmPersona.setVisible(true);
			Numventana = 3;
			refrescar();
		}else if(e.getSource()==frm.tglbtnMostrarocultar){
			mostrarOcultarBuscar();
		}else if(e.getSource()==frm.btnBuscar){
			tipoBusqueda = 2;
			listarIncidencias();
		}else if(e.getSource()==frm.btnSalir){
			System.exit(0);
		}else if(e.getSource()==frm.btnExportarExcel){
			exportarExcel();
		}else{
			JOptionPane.showMessageDialog(null, "Accion desconocido");
		}
	}
	//EVENTOS
	@Override
	public void focusGained(FocusEvent e) {	}
	@Override
	public void focusLost(FocusEvent e) {
		fh = new FormatoHora();
		SimpleDateFormat sdfh = new SimpleDateFormat("HH:mm:ss");
		String hora = null;
		boolean val = false;
		if(e.getSource()==frm.txtfHoraIni){
			hora = frm.txtfHoraIni.getText();
			val = fh.validarhora(hora);
			if(val==false){
				JOptionPane.showMessageDialog(null, "La Hora de Inicio: "+hora+"  es Incorrecto\nEl formato de la hora correcta es: ##:##:##   Ejemplo: (00:00:00)");
				frm.txtfHoraIni.setText(sdfh.format(new Date()));
			}
		}else{
			hora = frm.txtfHoraFin.getText();
			val = fh.validarhora(hora);
			if(val==false){
				JOptionPane.showMessageDialog(null, "La Hora de Fin: "+hora+"  es Incorrecto\nEl formato de la hora correcta es: ##:##:##   Ejemplo: (00:00:00)");
				frm.txtfHoraFin.setText(sdfh.format(new Date()));
			}
		}
		
	}
	
	//CHANGED LISTENER
	@Override
	public void stateChanged(ChangeEvent e) {
		if(e.getSource()==frm.chbNumRegB){
			if(frm.chbNumRegB.isSelected()==true){
				frm.spNumRegB.setEnabled(true);
			}else{
				frm.spNumRegB.setEnabled(false);
			}
		}else if(e.getSource()==frm.chbSalaB){
			if(frm.chbSalaB.isSelected()==true){
				frm.txtSalaB.setEnabled(true);
			}else{
				frm.txtSalaB.setEnabled(false);
			}
		}else if(e.getSource()==frm.chbRangoFechasB){
			if(frm.chbRangoFechasB.isSelected()==true){
				frm.dtpFecha1B.setEnabled(true);
				frm.dtpFecha2B.setEnabled(true);
			}else{
				frm.dtpFecha1B.setEnabled(false);
				frm.dtpFecha2B.setEnabled(false);
			}
		}
	}
	//MOUSE EVENT
	@Override
	public void mouseClicked(MouseEvent e) {
		if(e.getSource()==frm.tlbListaInci){
			int col = frm.tlbListaInci.columnAtPoint(e.getPoint());
			if(col==18){
				modoModificar(e);
			}else if(col==19){
				modoEliminar(e);
				if(validaciones()==false){
					frmMSG = new FrmMensaje(frmMSG, true);
					frmMSG.txtaMgs.setText("¿Estas seguro de Eliminar el Registro Nº: "+frm.spNumReg.getValue()+"?");
					frmMSG.setVisible(true);
					if(FrmMensaje.valor==1){
						eliminarIncidencia();
					}
				}
			}	
		}else if(e.getSource()==frm.rbMostrarEli){
			if(frm.rbMostrarEli.isSelected()==true){
				frm.rbMostrarEli.setForeground(Color.RED);
				frm.rbMostrarEli.setText("Ocultar registros Eliminados");
			}else{
				frm.rbMostrarEli.setForeground(new Color(2, 100, 5));
				frm.rbMostrarEli.setText("Mostrar registros Eliminados");
			}
			tipoBusqueda = 2;
			listarIncidencias();
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {	}

	@Override
	public void mouseExited(MouseEvent e) {	}
	
	@Override
	public void mousePressed(MouseEvent e) {	}

	@Override
	public void mouseReleased(MouseEvent e) {	}
	
	//KEY EVENT LISTENER
	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getSource()==frm.txtaDescripcionInci){
			if(e.getKeyCode()==KeyEvent.VK_TAB){
				frm.txtaComentario.requestFocus();
			}
		}else if(e.getSource()==frm.txtaComentario){
			if(e.getKeyCode()==KeyEvent.VK_TAB){
				frm.txtaSolucion.requestFocus();
			}
		}else if(e.getSource()==frm.txtaSolucion){
			if(e.getKeyCode()==KeyEvent.VK_TAB){
				frm.cboRecepcionador.requestFocus();
			}
		}else if(e.getSource()==frm.btnProcesar){
			if(e.getKeyCode()==KeyEvent.VK_ENTER){
				if(validaciones()==false){
					if(modo==1){
						registrarIncidencia();
					}else if(modo==2){
						frmMSG = new FrmMensaje(frmMSG, true);
						frmMSG.txtaMgs.setText("¿Estas seguro de Modificar el Registro Nº: "+frm.spNumReg.getValue()+"?");
						frmMSG.setVisible(true);
						if(FrmMensaje.valor==1){
							modificarIncidencia();
						}
					}else if(modo==3){
						frmMSG = new FrmMensaje(frmMSG, true);
						frmMSG.txtaMgs.setText("¿Estas seguro de Eliminar el Registro Nº: "+frm.spNumReg.getValue()+"?");
						frmMSG.setVisible(true);
						if(FrmMensaje.valor==1){
							eliminarIncidencia();
						}					
					}
				}
			}
		}else if(e.getSource()==frm.btnSalir){
			if(e.getKeyCode()==KeyEvent.VK_ENTER){
				System.exit(0);
			}
		}
		if(e.getKeyCode()==KeyEvent.VK_ESCAPE){
			System.exit(0);
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {}

	@Override
	public void keyTyped(KeyEvent e) {}
	
	//METODOS
	private void cargarSalas(){
		salaD = new SalaDAO();
		listaSala = new ArrayList<SalaBean>();
		listaSala=salaD.listaS();
		frm.cboSala.removeAllItems();
		if(listaSala.size()!=0){
			for(int i = 0; i<listaSala.size();i++){
				frm.cboSala.addItem(listaSala.get(i).getNombreS());
			}
		}
	}
	
	private void cargarMedios(){
		medioD = new MedioDAO();
		listaMedio = new ArrayList<MedioBean>();
		listaMedio=medioD.listaM();
		frm.cboMedio.removeAllItems();
		if(listaMedio.size()!=0){
			for(int i = 0; i<listaMedio.size();i++){
				frm.cboMedio.addItem(listaMedio.get(i).getNombreM());
			}
		}
	}
	
	private void cargarRecepcionadores(){
		personaD = new PersonaDAO();
		listaPersona = new ArrayList<PersonaBean>();
		listaPersona=personaD.listaRecepcionador();
		frm.cboRecepcionador.removeAllItems();
		if(listaPersona.size()!=0){
			for(int i = 0; i<listaPersona.size();i++){
				frm.cboRecepcionador.addItem(listaPersona.get(i).getNombreP());
			}
		}
	}

	private void cargarSolicitadores(){
		incD = new IncidenciaDAO();
		listaInc = new ArrayList<IncidenciaBean>();
		listaInc=incD.listaSol();
		frm.cboSolicitador.removeAllItems();
		if(listaInc.size()!=0){
			for(int i = 0; i<listaInc.size();i++){
				frm.cboSolicitador.addItem(listaInc.get(i).getSolicitador());
			}
		}
	}

	private void cargarEncargados(){
		personaD = new PersonaDAO();
		listaPersona = new ArrayList<PersonaBean>();
		listaPersona=personaD.listaEncargado();
		frm.cboEncargado.removeAllItems();
		if(listaPersona.size()!=0){
			for(int i = 0; i<listaPersona.size();i++){
				frm.cboEncargado.addItem(listaPersona.get(i).getNombreP());
			}
		}
	}	
	
	private void listarIncidencias() {
		if(tipoBusqueda!=2){
			modoRegistrar();
		}	
		incB = new IncidenciaBean();
		incD = new IncidenciaDAO();
		listaInc = new ArrayList<IncidenciaBean>();
		frm.dtm = new DefaultTableModel();
		
		//---INICIO - PARA LA IMAGEN EN LA CELDA
		frm.tlbListaInci.setDefaultRenderer(Object.class, new RenderizarCeldaImg());

		ImageIcon iilapiz = new ImageIcon("iconos/lapiz.png");
		Icon ilapiz = new ImageIcon(iilapiz.getImage().getScaledInstance(25, 25, Image.SCALE_DEFAULT));
		JLabel llapiz=new JLabel();
		llapiz.setIcon(ilapiz);
		ImageIcon iix = new ImageIcon("iconos/x.png");
		Icon ix = new ImageIcon(iix.getImage().getScaledInstance(24, 23, Image.SCALE_DEFAULT));
		JLabel lx=new JLabel();
		lx.setIcon(ix);
		///--- FIN 
		String cabecera[] = new String[] {"Nº", "Sala","Fecha Ini", "Hora Ini","Fecha Fin","Hora Fin",
				"Turno","Medio","Incidencia","Comentario","Solución","Recepcionista","Solicitador","Asignado A",
				"Estado","Fecha Registro","Fecha Ult MOD","Registrador","M","E"};
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		int numreg = frm.chbNumRegB.isSelected()==true? (int) frm.spNumRegB.getValue():0;
		String nomsala = frm.chbSalaB.isSelected()==true? frm.txtSalaB.getText():"";
		String fechaini = frm.chbRangoFechasB.isSelected()==true? sdf.format(frm.dtpFecha1B.getDate()):"";
		String fechafin = frm.chbRangoFechasB.isSelected()==true? sdf.format(frm.dtpFecha2B.getDate()):"";
		int est = frm.rbMostrarEli.isSelected()==true?0:1;
		listaInc = incD.listaIncidencia(numreg,nomsala,fechaini,fechafin, est);
		frm.dtm = new DefaultTableModel(cabecera, 0);
		
		for (int i = 0; i < listaInc.size(); i++) {			
			Object [] obj = {listaInc.get(i).getCodigoI(),listaInc.get(i).getSala(),listaInc.get(i).getFechaiI(),listaInc.get(i).getHoraiI(),
					listaInc.get(i).getFechafI(),listaInc.get(i).getHorafI(),listaInc.get(i).getTurnoI(),listaInc.get(i).getMedio(),
					listaInc.get(i).getIncidencia(),listaInc.get(i).getComentario(),listaInc.get(i).getSolucion(),listaInc.get(i).getRecepcionista(),
					listaInc.get(i).getSolicitador(),listaInc.get(i).getEncargado(),listaInc.get(i).getEstadoI(),listaInc.get(i).getFechaRegI(),
					listaInc.get(i).getFechaUltModI(),listaInc.get(i).getNomUsuReg(),llapiz,lx};
			frm.dtm.addRow(obj);
		}
		//JOptionPane.showMessageDialog(null, frm.dtm.getRowCount());
		frm.tlbListaInci.setModel(frm.dtm);
		frm.tlbListaInci.getColumnModel().getColumn(0).setPreferredWidth(28);	//NUMEGO REGISTRO
		frm.tlbListaInci.getColumnModel().getColumn(1).setPreferredWidth(77);	//SALA
		frm.tlbListaInci.getColumnModel().getColumn(2).setPreferredWidth(68);	//FECHA INICIO
		frm.tlbListaInci.getColumnModel().getColumn(3).setPreferredWidth(60);	//HORA INICIO
		frm.tlbListaInci.getColumnModel().getColumn(4).setPreferredWidth(68);	//FECHA FIN
		frm.tlbListaInci.getColumnModel().getColumn(5).setPreferredWidth(58);	//HORA FIN
		
		frm.tlbListaInci.getColumnModel().getColumn(6).setPreferredWidth(0);	//TURNO
		frm.tlbListaInci.getColumnModel().getColumn(6).setMinWidth(0);
		frm.tlbListaInci.getColumnModel().getColumn(6).setMaxWidth(0);
		
		frm.tlbListaInci.getColumnModel().getColumn(7).setPreferredWidth(67);	//MEDIO
		frm.tlbListaInci.getColumnModel().getColumn(8).setPreferredWidth(70);	//INCIDENCIA
		frm.tlbListaInci.getColumnModel().getColumn(9).setPreferredWidth(75);	//COMENTARIO
		frm.tlbListaInci.getColumnModel().getColumn(10).setPreferredWidth(60);	//SOLUCION
		frm.tlbListaInci.getColumnModel().getColumn(11).setPreferredWidth(100);	//RECEPCIONISTA
		frm.tlbListaInci.getColumnModel().getColumn(12).setPreferredWidth(110);	//SOLICITADOR
		frm.tlbListaInci.getColumnModel().getColumn(13).setPreferredWidth(100);	//ASIGANADO A / ENCARGADO
		
		frm.tlbListaInci.getColumnModel().getColumn(14).setPreferredWidth(0);	//ESTADO
		frm.tlbListaInci.getColumnModel().getColumn(14).setMinWidth(0);
		frm.tlbListaInci.getColumnModel().getColumn(14).setMaxWidth(0);
		
		frm.tlbListaInci.getColumnModel().getColumn(15).setPreferredWidth(0);	//FECHA REGISTRO
		frm.tlbListaInci.getColumnModel().getColumn(15).setMinWidth(0);
		frm.tlbListaInci.getColumnModel().getColumn(15).setMaxWidth(0);
		
		frm.tlbListaInci.getColumnModel().getColumn(16).setPreferredWidth(0);	//FECHA ULTIMA MODIFICACION
		frm.tlbListaInci.getColumnModel().getColumn(16).setMinWidth(0);
		frm.tlbListaInci.getColumnModel().getColumn(16).setMaxWidth(0);
		
		frm.tlbListaInci.getColumnModel().getColumn(17).setPreferredWidth(0);	//USUARIO REGISTRADOR
		frm.tlbListaInci.getColumnModel().getColumn(17).setMinWidth(0);
		frm.tlbListaInci.getColumnModel().getColumn(17).setMaxWidth(0);
		
		frm.tlbListaInci.getColumnModel().getColumn(18).setPreferredWidth(25);	//MODIFICAR
		frm.tlbListaInci.getColumnModel().getColumn(19).setPreferredWidth(25);	//ELIMINAR
		if(tipoBusqueda==2){
			if(Numventana==0){
				if(frm.tlbListaInci.getRowCount()<=0){
					JOptionPane.showMessageDialog(null, "No se Encontraron Registros para la Búsqueda.");
				}
			}
		}
		tipoBusqueda = 0;
		frm.lblTotalItems.setText("Total Items:  "+frm.tlbListaInci.getRowCount());
	}

	private void registrarIncidencia(){
		incB = new IncidenciaBean();
		incD = new IncidenciaDAO();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		incB.setCodigoI((int) frm.spNumReg.getValue());
		incB.setSala((String) frm.cboSala.getSelectedItem());
		incB.setFechaiI(sdf.format(frm.dtpFechaIni.getDate()));
		incB.setHoraiI(frm.txtfHoraIni.getText());
		incB.setFechafI(sdf.format(frm.dtpFechaFin.getDate()));
		incB.setHorafI(frm.txtfHoraFin.getText());
		incB.setTurnoI((int) frm.spTurno.getValue());
		incB.setMedio((String) frm.cboMedio.getSelectedItem());
		incB.setIncidencia(frm.txtaDescripcionInci.getText().trim());
		incB.setComentario(frm.txtaComentario.getText().trim());
		incB.setSolucion(frm.txtaSolucion.getText().trim());
		incB.setRecepcionista((String)frm.cboRecepcionador.getSelectedItem());
		incB.setSolicitador((String)frm.cboSolicitador.getSelectedItem());
		incB.setEncargado((String)frm.cboEncargado.getSelectedItem());
		incB.setUsuRegI(Integer.parseInt(frm.txtIdUsuario.getText()));
		int registro= incD.registrarInc(incB);	
		if(registro>0){
			JOptionPane.showMessageDialog(null, "Nº de Registro: " + frm.spNumReg.getValue() + ", Registrado Correctamente.!!!");
		}else{
			JOptionPane.showMessageDialog(null, "No Se Grabó - Consulta con sistemas Musl.");
		}
		listarIncidencias();
	}
	
	private void modificarIncidencia(){
		incB = new IncidenciaBean();
		incD = new IncidenciaDAO();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		incB.setCodigoI((int) frm.spNumReg.getValue());
		incB.setSala((String) frm.cboSala.getSelectedItem());
		incB.setFechaiI(sdf.format(frm.dtpFechaIni.getDate()));
		incB.setHoraiI(frm.txtfHoraIni.getText());
		incB.setFechafI(sdf.format(frm.dtpFechaFin.getDate()));
		incB.setHorafI(frm.txtfHoraFin.getText());
		incB.setTurnoI((int) frm.spTurno.getValue());
		incB.setMedio((String) frm.cboMedio.getSelectedItem());
		incB.setIncidencia(frm.txtaDescripcionInci.getText().trim());
		incB.setComentario(frm.txtaComentario.getText().trim());
		incB.setSolucion(frm.txtaSolucion.getText().trim());
		incB.setRecepcionista((String)frm.cboRecepcionador.getSelectedItem());
		incB.setSolicitador((String)frm.cboSolicitador.getSelectedItem());
		incB.setEncargado((String)frm.cboEncargado.getSelectedItem());
		int mod= incD.modificarInc(incB);	
		if(mod>0){
			JOptionPane.showMessageDialog(null, "Nº de Registro: " + frm.spNumReg.getValue() + ", Modificado Correctamente.!!!");
		}else{
			JOptionPane.showMessageDialog(null, "No Se Grabó - Consulta con sistemas Musl.");
		}
		listarIncidencias();
	}
	
	private void eliminarIncidencia(){
		incB = new IncidenciaBean();
		incD = new IncidenciaDAO();
		incB.setCodigoI((int) frm.spNumReg.getValue());
		int eli= incD.eliminarInc(incB);	
		if(eli>0){
			JOptionPane.showMessageDialog(null, "Nº de Registro: " + frm.spNumReg.getValue() + ", Eliminado Correctamente.!!!");
		}else{
			JOptionPane.showMessageDialog(null, "No Se Eliminó - Consulta con sistemas Musl.");
		}
		listarIncidencias();
	}

	private void recuperarIncidencia(){
		incB = new IncidenciaBean();
		incD = new IncidenciaDAO();
		incB.setCodigoI((int) frm.spNumReg.getValue());
		int eli= incD.recuperarInc(incB);	
		if(eli>0){
			JOptionPane.showMessageDialog(null, "Nº de Registro: " + frm.spNumReg.getValue() + ", Recuperado Correctamente.!!!");
		}else{
			JOptionPane.showMessageDialog(null, "No Se Recuperò - Consulta con sistemas Musl.");
		}
		listarIncidencias();
	}	
	private boolean validaciones(){
		boolean valor = false;
		if(modo==2 || modo == 3){
			if(frm.rbMostrarEli.isSelected()==true){
				frmMSG = new FrmMensaje(frmMSG, true);
				frmMSG.txtaMgs.setText("El registro N°: "+frm.spNumReg.getValue()+" Se encuentra Eliminado.");
				frmMSG.txtaMgs2.setText("¿Desea Recuperar el Registro?");
				frmMSG.setVisible(true);
				if(FrmMensaje.valor==1){
					recuperarIncidencia();
					modo = 4;
					return valor = true;
				}else{
					return valor = true;
				}
			}
		}
		if(modo==1 || modo ==2 ){
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			SimpleDateFormat sdf2 = new SimpleDateFormat("HH:mm:ss");
			int numreg=0,turno=0,cbosa=0,cbom=0,cbor=0,cboso=0,cboe = 0;
			String fecinis=null,horinis=null,fecfins=null,horfins=null,inc=null,sol=null;
			Date fecini = null,fecfin=null,horini=null,horfin=null;
			numreg = (int) frm.spNumReg.getValue();
			try {
				fecinis = sdf.format(frm.dtpFechaIni.getDate());
				fecini = sdf.parse(fecinis);
				fecfins = sdf.format(frm.dtpFechaFin.getDate());
				fecfin = sdf.parse(fecfins);
			
				horinis = frm.txtfHoraIni.getText();
				horini = sdf2.parse(horinis);
				horfins = frm.txtfHoraFin.getText();
				horfin = sdf2.parse(horfins);
			} catch (ParseException e) {
				System.out.println(" Hubo un error al parsear fechas: "+e.getMessage());
			}
			cbosa = frm.cboSala.getItemCount();
			turno = (int) frm.spTurno.getValue();
			cbom = frm.cboMedio.getItemCount();
			inc = frm.txtaDescripcionInci.getText();
			sol = frm.txtaSolucion.getText();

			cbor = frm.cboRecepcionador.getItemCount();
			cboso = frm.cboSolicitador.getItemCount();
			cboe = frm.cboEncargado.getItemCount();

			if(numreg==0){
				JOptionPane.showMessageDialog(null, "El numero de registro no puede ser vacío ò cero.");
				valor = true;
			}else if(cbosa<=0){
				JOptionPane.showMessageDialog(null, "Primero debe añadir la sala donde ocurrió la Incidencia.");
				valor = true;
			}else if(fecini.after(fecfin)){
				JOptionPane.showMessageDialog(null, "La fecha de inicio no puede ser mayor a la fecha de Fin.");
				valor = true;
			}else if(fecini.equals(fecfin)&&horini.after(horfin)){
				JOptionPane.showMessageDialog(null, "La Hora de inicio no puede ser mayor a la Hora de Fin.");
				valor = true;
			}else if(turno==0){
				JOptionPane.showMessageDialog(null, "El Turno debe ser diferente a Cero | del 1 a 9.");
				valor = true;
			}else if(cbom<=0){
				JOptionPane.showMessageDialog(null, "Primero dede añadir el Medio por el que se recibió la Incidenica que esta registrando.");
				valor = true;
			}else if(inc.equalsIgnoreCase("")){
				JOptionPane.showMessageDialog(null, "Debe ingresar la descripcion de la Incidencia");
				frm.txtaDescripcionInci.requestFocus();
				valor = true;
			}else if(sol.equalsIgnoreCase("")){
				JOptionPane.showMessageDialog(null, "Debe ingresar la descripcion de la Solución");
				frm.txtaSolucion.requestFocus();
				valor = true;
			}else if(cbor<=0){
				JOptionPane.showMessageDialog(null, "Primero debe añadir una Persona que recepciona La Llamada para asignar a la Incidenica.");
				valor = true;
			}else if(cboso<=0){
				JOptionPane.showMessageDialog(null, "Primero debe añadir una Persona o Sala que Solicita resolver la Incidenica.");
				valor = true;
			}else if(cboe<=0){
				JOptionPane.showMessageDialog(null, "Primero debe añadir una Persona al que se le encarga  resolver la Incidenica.");
				valor = true;
			}
		}else{
			int numreg2 = 0;
			numreg2 = (int) frm.spNumReg.getValue();
			if(numreg2==0){
				JOptionPane.showMessageDialog(null, "El numero de registro no puede ser vacío ò cero.");
				valor = true;
			}
		}		
		return valor;
	}
	
	private void modoRegistrar(){
		modo = 1;
		limpiarControles();
		int ultNumReg = 0;
		incD = new IncidenciaDAO();
		ultNumReg = incD.obtenerUltimoRegistro();	
		frm.spNumReg.setValue(ultNumReg);
		
		frm.btnProcesar.setText("REGISTRAR INCIDENCIA");
	 	ImageIcon iiguardar= new ImageIcon("iconos/guardar.png");
		Icon iguardar = new ImageIcon(iiguardar.getImage().getScaledInstance(40, 38, Image.SCALE_DEFAULT));
		frm.btnProcesar.setIcon(iguardar);
	}

	private void modoModificar(MouseEvent e){
		modo = 2;
		limpiarControles();
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

		int filaSeleccion = frm.tlbListaInci.rowAtPoint(e.getPoint());
		frm.spNumReg.setValue(Integer.valueOf(frm.tlbListaInci.getValueAt(filaSeleccion, 0).toString()));
		String sala = frm.tlbListaInci.getValueAt(filaSeleccion, 1).toString();
		frm.cboSala.setSelectedItem(sala);
		try {
			String fecini = frm.tlbListaInci.getValueAt(filaSeleccion, 2).toString();
			frm.dtpFechaIni.setDate(sdf.parse(fecini));
			String fecfin = frm.tlbListaInci.getValueAt(filaSeleccion, 4).toString();
			frm.dtpFechaFin.setDate(sdf.parse(fecfin));
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		frm.txtfHoraIni.setText(frm.tlbListaInci.getValueAt(filaSeleccion, 3).toString());
		frm.txtfHoraFin.setText(frm.tlbListaInci.getValueAt(filaSeleccion, 5).toString());
		frm.spTurno.setValue(Integer.valueOf(frm.tlbListaInci.getValueAt(filaSeleccion, 6).toString()));
		String medio = frm.tlbListaInci.getValueAt(filaSeleccion, 7).toString();
		frm.cboMedio.setSelectedItem(medio);
		frm.txtaDescripcionInci.setText(frm.tlbListaInci.getValueAt(filaSeleccion, 8).toString());
		frm.txtaComentario.setText(frm.tlbListaInci.getValueAt(filaSeleccion, 9).toString());
		frm.txtaSolucion.setText(frm.tlbListaInci.getValueAt(filaSeleccion, 10).toString());
		String rec = frm.tlbListaInci.getValueAt(filaSeleccion, 11).toString();
		frm.cboRecepcionador.setSelectedItem(rec);
		String sol = frm.tlbListaInci.getValueAt(filaSeleccion, 12).toString();
		frm.cboSolicitador.setSelectedItem(sol);
		String enc = frm.tlbListaInci.getValueAt(filaSeleccion, 13).toString();
		frm.cboEncargado.setSelectedItem(enc);
		
		frm.txtEstadoReg.setText(frm.tlbListaInci.getValueAt(filaSeleccion, 14).toString());
		frm.txtFechaReg.setText(frm.tlbListaInci.getValueAt(filaSeleccion, 15).toString());
		frm.txtFechaUltMod.setText(frm.tlbListaInci.getValueAt(filaSeleccion, 16).toString());
		frm.txtRegistrador.setText(frm.tlbListaInci.getValueAt(filaSeleccion, 17).toString());
		
		frm.btnProcesar.setText("MODIFICAR INCIDENCIA");
		ImageIcon iimodificar= new ImageIcon("iconos/modificar.png");
		Icon imodificar = new ImageIcon(iimodificar.getImage().getScaledInstance(40, 38, Image.SCALE_DEFAULT));
		frm.btnProcesar.setIcon(imodificar);
	}
	
	private void modoEliminar(MouseEvent e){
		modo = 3;
		limpiarControles();
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		int filaSeleccion = frm.tlbListaInci.rowAtPoint(e.getPoint());
		frm.spNumReg.setValue(Integer.valueOf(frm.tlbListaInci.getValueAt(filaSeleccion, 0).toString()));
		String sala = frm.tlbListaInci.getValueAt(filaSeleccion, 1).toString();
		frm.cboSala.setSelectedItem(sala);
		try {
			String fecini = frm.tlbListaInci.getValueAt(filaSeleccion, 2).toString();
			frm.dtpFechaIni.setDate(sdf.parse(fecini));
			String fecfin = frm.tlbListaInci.getValueAt(filaSeleccion, 4).toString();
			frm.dtpFechaFin.setDate(sdf.parse(fecfin));
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		frm.txtfHoraIni.setText(frm.tlbListaInci.getValueAt(filaSeleccion, 3).toString());
		frm.txtfHoraFin.setText(frm.tlbListaInci.getValueAt(filaSeleccion, 5).toString());
		frm.spTurno.setValue(Integer.valueOf(frm.tlbListaInci.getValueAt(filaSeleccion, 6).toString()));
		String medio = frm.tlbListaInci.getValueAt(filaSeleccion, 7).toString();
		frm.cboMedio.setSelectedItem(medio);
		frm.txtaDescripcionInci.setText(frm.tlbListaInci.getValueAt(filaSeleccion, 8).toString());
		frm.txtaComentario.setText(frm.tlbListaInci.getValueAt(filaSeleccion, 9).toString());
		frm.txtaSolucion.setText(frm.tlbListaInci.getValueAt(filaSeleccion, 10).toString());
		String rec = frm.tlbListaInci.getValueAt(filaSeleccion, 11).toString();
		frm.cboRecepcionador.setSelectedItem(rec);
		String sol = frm.tlbListaInci.getValueAt(filaSeleccion, 12).toString();
		frm.cboSolicitador.setSelectedItem(sol);
		String enc = frm.tlbListaInci.getValueAt(filaSeleccion, 13).toString();
		frm.cboEncargado.setSelectedItem(enc);
		
		frm.txtEstadoReg.setText(frm.tlbListaInci.getValueAt(filaSeleccion, 14).toString());
		frm.txtFechaReg.setText(frm.tlbListaInci.getValueAt(filaSeleccion, 15).toString());
		frm.txtFechaUltMod.setText(frm.tlbListaInci.getValueAt(filaSeleccion, 16).toString());
		frm.txtRegistrador.setText(frm.tlbListaInci.getValueAt(filaSeleccion, 17).toString());
		
		
		frm.btnProcesar.setText("ELIMINAR INCIDENCIA ");
		ImageIcon iieliminar= new ImageIcon("iconos/eliminarbd.png");
		Icon ieliminar = new ImageIcon(iieliminar.getImage().getScaledInstance(40, 33, Image.SCALE_DEFAULT));
		frm.btnProcesar.setIcon(ieliminar);
	}
	
	private void limpiarControles(){
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		SimpleDateFormat sdf2 = new SimpleDateFormat("HH:mm:ss");
		frm.spNumReg.setValue(0);
		if(frm.cboSala.getItemCount()>0){
			frm.cboSala.setSelectedIndex(0);
		}
		frm.dtpFechaIni.setDate(new Date());
		frm.txtfHoraIni.setText(sdf2.format(new Date()));
		frm.dtpFechaFin.setDate(new Date());
		frm.txtfHoraFin.setText(sdf2.format(new Date()));
		frm.spTurno.setValue(1);
		if(frm.cboMedio.getItemCount()>0){
			frm.cboMedio.setSelectedIndex(0);
		}
		frm.txtaDescripcionInci.setText(null);
		frm.txtaComentario.setText(null);
		frm.txtaSolucion.setText(null);
		if(frm.cboRecepcionador.getItemCount()>0){
			frm.cboRecepcionador.setSelectedIndex(0);
		}
		if(frm.cboSolicitador.getItemCount()>0){
			frm.cboSolicitador.setSelectedIndex(0);
		}
		if(frm.cboEncargado.getItemCount()>0){
			frm.cboEncargado.setSelectedIndex(0);
		}
		frm.txtFechaReg.setText(sdf.format(new Date()));
		frm.txtFechaUltMod.setText(sdf.format(new Date()));
		frm.txtRegistrador.setText(null);
		frm.txtEstadoReg.setText(null);
		if(modo==1){
			frm.btnNuevo.setVisible(false);
			frm.lblRegistrador.setVisible(false);
			frm.txtRegistrador.setVisible(false);
			frm.lblEstadoReg.setVisible(false);
			frm.txtEstadoReg.setVisible(false);
		}else{
			frm.btnNuevo.setVisible(true);
			frm.lblRegistrador.setVisible(true);
			frm.txtRegistrador.setVisible(true);
			frm.lblEstadoReg.setVisible(true);
			frm.txtEstadoReg.setVisible(true);
		}
	}
	
	private void inicarComponentes(){
		frm.txtIdUsuario.setText(null);
		frm.txtUsuario.setText(null);
		frm.spNumReg.setEnabled(false);
		frm.chbNumRegB.setSelected(false);
		frm.spNumRegB.setEnabled(false);
		frm.spNumRegB.setValue(1);
		frm.chbSalaB.setSelected(false);
		frm.txtSalaB.setEnabled(false);
		frm.chbRangoFechasB.setSelected(true);
		frm.dtpFecha1B.setDate(new Date());
		frm.dtpFecha2B.setDate(new Date());
		//codigoUsu = Integer.valueOf(frm.txtIdUsuario.getText());
		usuario = frm.txtUsuario.getText();
		frm.tglbtnMostrarocultar.setText("OCULTAR OPCIONES DE BUSQUEDA");
		mostrarOcultarBuscar();
	}
	
	private void mostrarOcultarBuscar(){
		if(frm.tglbtnMostrarocultar.getText().equalsIgnoreCase("MOSTRAR OPCIONES DE BUSQUEDA")){
			frm.pBusquedaRegistros.setVisible(true);
			frm.JspListaInci.setBounds(0, 75, 1005, 170);
			frm.tglbtnMostrarocultar.setForeground(new Color(169, 29, 40));
			frm.tglbtnMostrarocultar.setText("OCULTAR OPCIONES DE BUSQUEDA");
			ImageIcon iiguardar= new ImageIcon("iconos/ojocerrado.png");
			Icon iguardar = new ImageIcon(iiguardar.getImage().getScaledInstance(40, 38, Image.SCALE_DEFAULT));
			frm.tglbtnMostrarocultar.setIcon(iguardar);
		}else{
			frm.pBusquedaRegistros.setVisible(false);
			frm.JspListaInci.setBounds(0, 2, 1005, 243);
			frm.tglbtnMostrarocultar.setForeground(new Color(18, 132, 95));
			frm.tglbtnMostrarocultar.setText("MOSTRAR OPCIONES DE BUSQUEDA");
			ImageIcon iiguardar= new ImageIcon("iconos/ojoabierto.png");
			Icon iguardar = new ImageIcon(iiguardar.getImage().getScaledInstance(40, 38, Image.SCALE_DEFAULT));
			frm.tglbtnMostrarocultar.setIcon(iguardar);
		}
	}
	private void refrescar(){
		if(Numventana!=0){
			tipoBusqueda = 2;
			if(Numventana==1){
				cargarSalas();
				cargarSolicitadores();
			}else if(Numventana==2){
				cargarMedios();
			}else if(Numventana==3){
				cargarRecepcionadores();
				cargarSolicitadores();
				cargarEncargados();
			}
			listarIncidencias();
			Numventana = 0;
		}
	}
	private void exportarExcel(){
		exportarExcel = new ExportarExcel();
		TableModel tm1 = frm.tlbListaInci.getModel();
		DefaultTableModel dtm1 = new DefaultTableModel(); 
		String cabecera[] = new String[] {"Sala","Fecha ini", "Hora ini","Fecha fin","Hora fin",
				"Turno","Medio","Incidencia","Comentario","Solución","Recepcionado","Solicitado","Caso asignado A"};
		dtm1 = new DefaultTableModel(cabecera, 0);
		JTable tabla1 = new JTable(null);
		int filas = tm1.getRowCount();
		for (int i = 0; i < filas ; i++) {
			Object obj1[] = {tm1.getValueAt(i, 1),tm1.getValueAt(i, 2),tm1.getValueAt(i, 3),
					tm1.getValueAt(i, 4),tm1.getValueAt(i, 5),tm1.getValueAt(i, 6),tm1.getValueAt(i, 7),tm1.getValueAt(i, 8),
					tm1.getValueAt(i, 9),tm1.getValueAt(i, 10),tm1.getValueAt(i, 11),tm1.getValueAt(i, 12),tm1.getValueAt(i, 13)};
			dtm1.addRow(obj1);
		}
		tabla1.setModel(dtm1);
		try {
			exportarExcel.exportarExcel(tabla1);
		} catch (IOException e) {
			System.out.println(" Problemas con Exportar Excel: " + e.getMessage());
		}
	}

}
