package controlador;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import bean.AreaBean;
import bean.PaisBean;
import bean.PersonaBean;
import consultas.ExportarExcel;
import dao.AreaDAO;
import dao.PaisDAO;
import dao.PersonaDAO;
import utilidades.RenderizarCeldaImg;
import vista.FrmArea;
import vista.FrmMensaje;
import vista.FrmPais;
import vista.FrmPersona;


public class ControlPersona implements ActionListener, MouseListener, KeyListener{

	private PersonaBean personaBean=null;
	private PersonaDAO personaDao=null;
	private ArrayList<PersonaBean> listaPersona=null;
	private FrmPersona frmPersona = null;
	
	private FrmArea frmArea=null;
	private AreaBean areaB=null;
	private AreaDAO areaD=null;
	private ArrayList<AreaBean> listaArea=null;
	ControlArea ctrolArea = null;
	
	private FrmPais frmPais;
	private PaisBean paisB=null;
	private PaisDAO paisD=null;
	private ArrayList<PaisBean> listaPais=null;
	ControlPais ctrolPais = null;
	
	private int modo = 0;
	private ExportarExcel exportarExcel=null;
	private TableRowSorter<DefaultTableModel> trsP=null;
	private FrmMensaje frmMSG=null;
	private int tipooperacion=0; /*1=normal,2=si hay ya existe una persona*/
	private int codigopersona=0;
	private String nombrepersona,apellidopersona=null;
	private static int usuario=0;
	
	public ControlPersona(PersonaBean personaB, PersonaDAO personaD, FrmPersona frmPersona) {
		this.personaBean = personaB;
		this.personaDao = personaD;
		this.frmPersona = frmPersona;
		this.frmPersona.btnNuevoP.addActionListener(this);
		this.frmPersona.btnCancelP.addActionListener(this);
		this.frmPersona.btnOkP.addActionListener(this);
		this.frmPersona.btnExportarExcelP.addActionListener(this);
		this.frmPersona.btnNuevaAreaP.addActionListener(this);
		this.frmPersona.btnNuevoPaisP.addActionListener(this);
		this.frmPersona.tlbListaP.addMouseListener(this);
		this.frmPersona.txtNombreP.addMouseListener(this);
		this.frmPersona.txtNombrePB.addKeyListener(this);
		this.frmPersona.txtNombreP.addKeyListener(this);
		this.frmPersona.txtaComentarioP.addKeyListener(this);
		this.frmPersona.btnOkP.addKeyListener(this);
		this.frmPersona.btnCancelP.addKeyListener(this);
		this.frmPersona.lblTotalItemsP.addKeyListener(this);
		this.frmPersona.cboPaisP.addKeyListener(this);	
		
		cargarAreas();
		cargarPaises();
		listarPersonas();
	}
	//EVENTOS DEL LOS COMPONENTES
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==frmPersona.btnNuevoP){
			listarPersonas();
		}else if(e.getSource()==frmPersona.btnNuevaAreaP){
				frmArea = new FrmArea(frmArea, true);
				ctrolArea = new ControlArea(frmArea, areaB, areaD);
				frmArea.setVisible(true);
				cargarAreas();
		}else if(e.getSource()==frmPersona.btnNuevoPaisP){
			frmPais = new FrmPais(frmPais, true);
			ctrolPais = new ControlPais(frmPais, paisB, paisD);
			frmPais.setVisible(true);
			cargarPaises();
		}else if(e.getSource()==frmPersona.btnCancelP){
			frmPersona.dispose();
		}else if(e.getSource()==frmPersona.btnOkP){
			if(modo==1){
				if(validaciones()==false){
					regitrarPersona();
				}
			}else if(modo==2){
				if(validaciones()==false){				
					frmMSG = new FrmMensaje(frmMSG, true);
					frmMSG.txtaMgs.setText("¿Estas seguro de Modificar el Registro Nº: "+frmPersona.spNumRegP.getValue()+"?");
					frmMSG.setVisible(true);
					if(FrmMensaje.valor==1){
						modificarPersona();
					}
				}
			}else if(modo==3){
				if(validaciones()==false){
					frmMSG = new FrmMensaje(frmMSG, true);
					frmMSG.txtaMgs.setText("¿Estas seguro de Eliminar el Registro Nº: "+frmPersona.spNumRegP.getValue()+"?");
					frmMSG.setVisible(true);
					if(FrmMensaje.valor==1){
						eliminarPersona();
					}
				}
			}else{
				JOptionPane.showMessageDialog(null, "Tipo de Operacion No existe \n Consulte con sistemas Musli-");
			}
		}else if(e.getSource()==frmPersona.btnExportarExcelP){
			exportarExcel();
		}else{
			JOptionPane.showMessageDialog(null, "Incomveniente Interno.\n Resuelva con Sistemas Musli-");
		}
		
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		int col = frmPersona.tlbListaP.columnAtPoint(e.getPoint());
		if(col==11){
			modoActualizar(e);
		}else if(col==12){
			modoEliminar(e);
			if(validaciones()==false){
				frmMSG = new FrmMensaje(frmMSG, true);
				frmMSG.txtaMgs.setText("¿Estas seguro de Eliminar el Registro Nº: "+frmPersona.spNumRegP.getValue()+"?");
				frmMSG.setVisible(true);
				if(FrmMensaje.valor==1){
					eliminarPersona();
				}
			}
				//SI VALIDACION ES TRUE, EL SISTEMA NO HACE NADA
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {}
	@Override
	public void mouseExited(MouseEvent e) {}
	@Override
	public void mousePressed(MouseEvent e) {}
	@Override
	public void mouseReleased(MouseEvent e) {}
	//----EVENTOS DE KEYPRESSED
	@Override
	public void keyPressed(KeyEvent arg0) {
		trsP = new TableRowSorter<DefaultTableModel>();
		if(arg0.getSource()==frmPersona.txtaComentarioP){
			if(arg0.getKeyCode()==KeyEvent.VK_TAB){
				frmPersona.btnOkP.requestFocus();
			}
		}else if(arg0.getSource()==frmPersona.btnOkP){
			if(arg0.getKeyCode()==KeyEvent.VK_ENTER){
				if(validaciones()==false){
					frmPersona.btnOkP.doClick();
				}
			}
		}else if(arg0.getSource()==frmPersona.btnCancelP){
			if(arg0.getKeyCode()==KeyEvent.VK_ENTER){
				frmPersona.btnCancelP.doClick();
			}
		}
		if(arg0.getKeyCode()==KeyEvent.VK_ESCAPE){
			frmPersona.dispose();
		}
	}
	@Override
	public void keyReleased(KeyEvent arg0) {}
	@Override
	public void keyTyped(KeyEvent arg0) {
		if(arg0.getSource()==frmPersona.txtNombrePB){
			frmPersona.txtNombrePB.addKeyListener(new KeyAdapter() {
				@Override //ESTE EVENTO PUEDE IR DENTRO DE OTRO EVENTO O FUERA
				public void keyReleased(KeyEvent e) {
					String cadena = (frmPersona.txtNombrePB.getText());
					frmPersona.txtNombrePB.setText(cadena);
					trsP.setRowFilter(RowFilter.regexFilter("(?i)"+frmPersona.txtNombrePB.getText(), 1));

					frmPersona.lblTotalItemsP.setText("Total Items: " + frmPersona.tlbListaP.getRowCount());
				}
			});
			trsP = new TableRowSorter<DefaultTableModel>(frmPersona.dtmP);
			frmPersona.tlbListaP.setRowSorter(trsP);			
		}	
	}
	
	//----EVENTOS ITEM CHAnGED
	
	//OPERACIONES DE LISTAR, INSERTAR, MODIFICAR Y ELIMINAR
	private void cargarAreas(){
		areaB = new AreaBean();
		areaD = new AreaDAO();
		listaArea = new ArrayList<>();
		listaArea=areaD.listaA();
		frmPersona.cboAreaP.removeAllItems();
		if(listaArea.size()!=0){
			for(int i = 0; i<listaArea.size();i++){
				frmPersona.cboAreaP.addItem(listaArea.get(i).getNombreA());
			}
		}
	}
	private void cargarPaises(){
		paisD = new PaisDAO();
		listaPais = new ArrayList<>();
		listaPais=paisD.listaP();
		frmPersona.cboPaisP.removeAllItems();
		if(listaPais.size()!=0){
			for(int i = 0; i<listaPais.size();i++){
				frmPersona.cboPaisP.addItem(listaPais.get(i).getNombrePA());
			}
		}
	}
	
	private void listarPersonas(){
		modoRegistro();
		listaPersona = new ArrayList<PersonaBean>();
		personaDao = new PersonaDAO();
		frmPersona.dtmP = new DefaultTableModel();
		trsP = new TableRowSorter<DefaultTableModel>(null);
	/*	frmPersona.dtm.addColumn("Cod");*/
		//---INICIO - PARA LA IMAGEN EN LA CELDA
		frmPersona.tlbListaP.setDefaultRenderer(Object.class, new RenderizarCeldaImg());

		ImageIcon iilapiz = new ImageIcon("iconos/lapiz.png");
		Icon ilapiz = new ImageIcon(iilapiz.getImage().getScaledInstance(25, 25, Image.SCALE_DEFAULT));
		JLabel llapiz=new JLabel();
		llapiz.setIcon(ilapiz);
		ImageIcon iix = new ImageIcon("iconos/x.png");
		Icon ix = new ImageIcon(iix.getImage().getScaledInstance(24, 23, Image.SCALE_DEFAULT));
		JLabel lx=new JLabel();
		lx.setIcon(ix);
		///--- FIN 
		String cabecera[] = new String[] {" N°", "Nombres","Apellidos","Area","Pais","Email",
				"Teléfono","Comentario","Fecha Registro","Ultima MOD.","Registrador"," M "," E "};
		listaPersona = personaDao.listarTodasPersonas();	
		frmPersona.dtmP = new DefaultTableModel(cabecera, 0);
		for (int i = 0; i < listaPersona.size(); i++) {
			Object [] obj = {listaPersona.get(i).getCodigoP(),listaPersona.get(i).getNombreP(),listaPersona.get(i).getApellidoP(),
					listaPersona.get(i).getNombreAr(),listaPersona.get(i).getNombrePa(),listaPersona.get(i).getEmailP(),
					listaPersona.get(i).getTelefonoP(),listaPersona.get(i).getComentarioP(),listaPersona.get(i).getFechaRegP(),
					listaPersona.get(i).getFechaUltM(),listaPersona.get(i).getNombreUs(),llapiz,lx};
			frmPersona.dtmP.addRow(obj);
		}
		trsP = new TableRowSorter<DefaultTableModel>(frmPersona.dtmP);
		frmPersona.tlbListaP.setRowSorter(trsP);//PARA QUE CUANDO HAGA EL FILTRO SE RESTABLEZCA LA LISTA
		frmPersona.tlbListaP.setModel(frmPersona.dtmP);
		frmPersona.tlbListaP.getColumnModel().getColumn(0).setPreferredWidth(25);//CODIGO
		frmPersona.tlbListaP.getColumnModel().getColumn(1).setPreferredWidth(70);//NOMBRE
		frmPersona.tlbListaP.getColumnModel().getColumn(2).setPreferredWidth(115);//APELLIDOS
		frmPersona.tlbListaP.getColumnModel().getColumn(3).setPreferredWidth(70);//AREA
		frmPersona.tlbListaP.getColumnModel().getColumn(4).setPreferredWidth(60);//PAIS
		frmPersona.tlbListaP.getColumnModel().getColumn(5).setPreferredWidth(80);//EMAIL
		frmPersona.tlbListaP.getColumnModel().getColumn(6).setPreferredWidth(95);//TELEFONO
		frmPersona.tlbListaP.getColumnModel().getColumn(7).setPreferredWidth(100);//COMENTARIO
		//OCULTANDO COLUMNAS
		frmPersona.tlbListaP.getColumnModel().getColumn(8).setMaxWidth(0);//FECHA REGISTRO
		frmPersona.tlbListaP.getColumnModel().getColumn(8).setMinWidth(0);
		frmPersona.tlbListaP.getColumnModel().getColumn(8).setPreferredWidth(0);
		frmPersona.tlbListaP.getColumnModel().getColumn(9).setMaxWidth(0);//FECHA ULTIMA MODIFICACION
		frmPersona.tlbListaP.getColumnModel().getColumn(9).setMinWidth(0);
		frmPersona.tlbListaP.getColumnModel().getColumn(9).setPreferredWidth(0);		
		frmPersona.tlbListaP.getColumnModel().getColumn(10).setPreferredWidth(95);//USUARIO
		frmPersona.tlbListaP.getColumnModel().getColumn(11).setPreferredWidth(28);//LAPIZ
		frmPersona.tlbListaP.getColumnModel().getColumn(12).setPreferredWidth(28);//X

		frmPersona.lblTotalItemsP.setText("Total Items: " + frmPersona.tlbListaP.getRowCount());
	}
	
	private void regitrarPersona(){
		personaBean = new PersonaBean();
		personaDao = new PersonaDAO();
		personaBean.setCodigoP((int) frmPersona.spNumRegP.getValue());
		personaBean.setNombreAr((String) frmPersona.cboAreaP.getSelectedItem());
		personaBean.setNombreP(frmPersona.txtNombreP.getText().trim());
		personaBean.setApellidoP(frmPersona.txtApellidoP.getText().trim());
		personaBean.setEmailP(frmPersona.txtEmailP.getText().trim());
		personaBean.setNombrePa((String) frmPersona.cboPaisP.getSelectedItem());
		personaBean.setTelefonoP(frmPersona.txtTelefonoP.getText().trim());
		personaBean.setComentarioP(frmPersona.txtaComentarioP.getText().trim());
		personaBean.setIdUs(Integer.valueOf(frmPersona.txtIdUsuarioP.getText()));
		int reg = personaDao.registrarPersona(personaBean);
		if(reg>0){
			if(tipooperacion==1){
				JOptionPane.showMessageDialog(null, "Nº de Registro: " + frmPersona.spNumRegP.getValue() + ", Registrado Correctamente.!!!");
			}else{
				JOptionPane.showMessageDialog(null, "Persona: "+ nombrepersona +", "+apellidopersona+". Con Nº de Registro: " + codigopersona + ", Recuperado.");
			}
			listarPersonas();
		}else{
			JOptionPane.showMessageDialog(null, "Nº de Registro: " + frmPersona.spNumRegP.getValue() + ".\nNo Registrado, consulte con sistemas-Musli");
		}
	}

	private void modificarPersona(){
		personaBean = new PersonaBean();
		personaDao = new PersonaDAO();
		personaBean.setCodigoP((int) frmPersona.spNumRegP.getValue());
		personaBean.setNombreAr((String) frmPersona.cboAreaP.getSelectedItem());
		personaBean.setNombreP(frmPersona.txtNombreP.getText().trim());
		personaBean.setApellidoP(frmPersona.txtApellidoP.getText().trim());
		personaBean.setEmailP(frmPersona.txtEmailP.getText().trim());
		personaBean.setNombrePa((String) frmPersona.cboPaisP.getSelectedItem());
		personaBean.setTelefonoP(frmPersona.txtTelefonoP.getText().trim());
		personaBean.setComentarioP(frmPersona.txtaComentarioP.getText());
		int mod = personaDao.modificarMedio(personaBean);
		if(mod>0){
			JOptionPane.showMessageDialog(null, "Nº de Registro: " + frmPersona.spNumRegP.getValue() + ", Modificado Correctamente.!!!");
			listarPersonas();
		}else{
			JOptionPane.showMessageDialog(null, "Nº de Registro: " + frmPersona.spNumRegP.getValue() + ", No Modificado.\n Consulte con sistemas-Musli");
		}
	}
	
	private void eliminarPersona(){
		personaBean = new PersonaBean();
		personaDao = new PersonaDAO();
		personaBean.setCodigoP((int) frmPersona.spNumRegP.getValue());
		int eli = personaDao.eliminarPersona(personaBean);
		if(eli>0){
			JOptionPane.showMessageDialog(null, "Nº de Registro: " + frmPersona.spNumRegP.getValue() + ", Eliminado Correctamente.!!!");
			listarPersonas();
		}else{
			JOptionPane.showMessageDialog(null, "Nº de Registro: " + frmPersona.spNumRegP.getValue() + ", No Eliminado, consulte con sistemas-Musli");
		}
	}
	
	//MANEJO DE CONTROLES
	private boolean validaciones(){
		boolean valor = false;
		tipooperacion = 1;
		codigopersona=0;
		nombrepersona=null;
		apellidopersona=null;
		int num,cboA,cboP = 0;
		String nom, ape =null;
		num = (int) frmPersona.spNumRegP.getValue();
		nom = frmPersona.txtNombreP.getText();
		ape = frmPersona.txtApellidoP.getText();
		cboA = frmPersona.cboAreaP.getItemCount();
		cboP = frmPersona.cboPaisP.getItemCount();
		
		if(num==0){
			JOptionPane.showMessageDialog(null, "El 'Nº Registro' No puede ser vacío ó ( 0 )");
			valor = true;
		}else if(nom.equalsIgnoreCase("")){
			JOptionPane.showMessageDialog(null, "Debe Ingresar el Nombre de la Persona.");
			valor = true;
		}else if(ape.equalsIgnoreCase("")){
			JOptionPane.showMessageDialog(null, "Debe Ingresar el o los Apellidos de la Persona.");
			valor = true;
		}else if(cboA<=0){
			JOptionPane.showMessageDialog(null, "Primero debe Añadir un Area de Trabajo para atribuirle a la Persona.");
			valor = true;
		}else if(cboP<=0){
			JOptionPane.showMessageDialog(null, "Primero debe Añadir un Pais para atribuirle a la Persona.");
			valor = true;
		}else{
			if(modo==1 || modo==2){
				tipooperacion = 1;
				TableModel tm = frmPersona.tlbListaP.getModel();
				int filas = tm.getRowCount();
				for (int i = 0; i < filas ; i++) {
					codigopersona = (int) tm.getValueAt(i, 0);
					nombrepersona = (String) tm.getValueAt(i, 1);
					apellidopersona=(String) tm.getValueAt(i, 2);
					if(nombrepersona.equalsIgnoreCase(nom)&&apellidopersona.equalsIgnoreCase(ape)){
						if(codigopersona!=num){
							JOptionPane.showMessageDialog(null,"La persona: "+nom+", "+ape+". Ya existe en la BD con Nº Registro: "+codigopersona+
														   "\nNo puede ingresar [Nombres... Apellidos...] existentes en la BD.");
							return valor = true;
					}
				}
			}
				
			personaBean = new PersonaBean();
			personaDao = new PersonaDAO();
			listaPersona = new ArrayList<PersonaBean>();
			listaPersona=personaDao.verificarNombreApellidosPersona(nom,ape);
			if(listaPersona.size()!=0){
				codigopersona = listaPersona.get(0).getCodigoP();
				nombrepersona = listaPersona.get(0).getNombreP();
				apellidopersona=listaPersona.get(0).getApellidoP();
				if(codigopersona!=num){
					if(modo==1){
						frmMSG = new FrmMensaje(frmMSG, true);
						frmMSG.txtaMgs.setText("Se ha encontrado en la BD el Registro N°: "+codigopersona+". Perteneciente a: "
						+ nombrepersona+", "+apellidopersona);
						frmMSG.txtaMgs2.setText("¿Desea Recuperar la Persona de la BD?");
						frmMSG.setVisible(true);
						if(FrmMensaje.valor==1){
							valor = false;
						}else{
							valor = true;
						}
						tipooperacion = 2;
					}else{
						JOptionPane.showMessageDialog(null,"Se encontró en la BD el Registro N°: "+codigopersona+
								  "\nPertenece a: "+ nombrepersona+", "+apellidopersona+"\nREGISTRE como nuevo la persona con:"+ 
								  "\nNombre: "+nombrepersona+".\nApellidos: "+apellidopersona+".");
						valor=true;
					}
				}
			}
			}
		}
		return valor;
	}
	
	private void modoRegistro(){
		modo = 1;
		limpiarControles();
		int max = 0;
		personaDao = new PersonaDAO();
	 	max = personaDao.obtenerUltimoRegistro();	
		frmPersona.spNumRegP.setValue(max);
	 	frmPersona.spNumRegP.setEnabled(false);
	 	SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
	 	frmPersona.txtFechaRegP.setText(sdf.format(new Date()));
	 	frmPersona.txtFechaUltModP.setText(sdf.format(new Date()));
	 	frmPersona.btnNuevoP.setVisible(false);
	 	frmPersona.lblRegistradorP.setVisible(false);
	 	frmPersona.txtRegistradorP.setVisible(false);
	 	if(frmPersona.cboAreaP.getItemCount()>0){
	 		frmPersona.cboAreaP.setSelectedIndex(0);
	 	}
	 	if(frmPersona.cboPaisP.getItemCount()>0){
	 		frmPersona.cboAreaP.setSelectedIndex(0);
	 	}
	 	frmPersona.btnOkP.setText("REGISTRAR");
	 	ImageIcon iiguardar= new ImageIcon("iconos/guardar.png");
		Icon iguardar = new ImageIcon(iiguardar.getImage().getScaledInstance(30, frmPersona.btnOkP.getHeight(), Image.SCALE_DEFAULT));
		frmPersona.btnOkP.setIcon(iguardar);
		cambiotexto();
	}
	
	private void modoActualizar(MouseEvent e){
		modo = 2;
		limpiarControles();
	//	SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
		int filaSeleccion = frmPersona.tlbListaP.rowAtPoint(e.getPoint());
		frmPersona.spNumRegP.setValue(Integer.valueOf(frmPersona.tlbListaP.getValueAt(filaSeleccion, 0).toString()));
		frmPersona.txtNombreP.setText(frmPersona.tlbListaP.getValueAt(filaSeleccion, 1).toString());
		frmPersona.txtApellidoP.setText(frmPersona.tlbListaP.getValueAt(filaSeleccion, 2).toString());
		String area = frmPersona.tlbListaP.getValueAt(filaSeleccion, 3).toString();
		frmPersona.cboAreaP.setSelectedItem(area);
		String pais = frmPersona.tlbListaP.getValueAt(filaSeleccion, 4).toString();
		frmPersona.cboPaisP.setSelectedItem(pais);
	//	frmPersona.cboAreaP.getModel().setSelectedItem(frmPersona.tlbListaP.getModel().getValueAt(filaSeleccion, 4));
	//	frmPersona.cboPaisP.getModel().setSelectedItem(String.valueOf(frmPersona.tlbListaP.getModel().getValueAt(filaSeleccion, 5)));
		frmPersona.txtEmailP.setText(frmPersona.tlbListaP.getValueAt(filaSeleccion, 5).toString());
		frmPersona.txtTelefonoP.setText(frmPersona.tlbListaP.getValueAt(filaSeleccion, 6).toString());
		frmPersona.txtaComentarioP.setText(frmPersona.tlbListaP.getValueAt(filaSeleccion, 7).toString());
		frmPersona.txtFechaRegP.setText(frmPersona.tlbListaP.getValueAt(filaSeleccion, 8).toString());
		frmPersona.txtFechaUltModP.setText(frmPersona.tlbListaP.getValueAt(filaSeleccion, 9).toString());
		frmPersona.txtRegistradorP.setText(frmPersona.tlbListaP.getValueAt(filaSeleccion, 10).toString());
	 	frmPersona.lblRegistradorP.setVisible(true);
	 	frmPersona.txtRegistradorP.setVisible(true);
		frmPersona.btnNuevoP.setVisible(true);
		frmPersona.btnOkP.setText("MODIFICAR");
		ImageIcon iimodificar= new ImageIcon("iconos/modificar.png");
		Icon imodificar = new ImageIcon(iimodificar.getImage().getScaledInstance(30, frmPersona.btnOkP.getHeight(), Image.SCALE_DEFAULT));
		frmPersona.btnOkP.setIcon(imodificar);
		cambiotexto();
		
	}
	
	private void modoEliminar(MouseEvent e){
		modo = 3;
		limpiarControles();
		int filaSeleccion = frmPersona.tlbListaP.rowAtPoint(e.getPoint());
		frmPersona.spNumRegP.setValue(Integer.valueOf(frmPersona.tlbListaP.getValueAt(filaSeleccion, 0).toString()));
		frmPersona.txtNombreP.setText(frmPersona.tlbListaP.getValueAt(filaSeleccion, 1).toString());
		frmPersona.txtApellidoP.setText(frmPersona.tlbListaP.getValueAt(filaSeleccion, 2).toString());
		String area = frmPersona.tlbListaP.getValueAt(filaSeleccion, 3).toString();
		frmPersona.cboAreaP.setSelectedItem(area);
		String pais = frmPersona.tlbListaP.getValueAt(filaSeleccion, 4).toString();
		frmPersona.cboPaisP.setSelectedItem(pais);
	//	frmPersona.cboAreaP.getModel().setSelectedItem(frmPersona.tlbListaP.getModel().getValueAt(filaSeleccion, 4));
	//	frmPersona.cboPaisP.getModel().setSelectedItem(String.valueOf(frmPersona.tlbListaP.getModel().getValueAt(filaSeleccion, 5)));
		frmPersona.txtEmailP.setText(frmPersona.tlbListaP.getValueAt(filaSeleccion, 5).toString());
		frmPersona.txtTelefonoP.setText(frmPersona.tlbListaP.getValueAt(filaSeleccion, 6).toString());
		frmPersona.txtaComentarioP.setText(frmPersona.tlbListaP.getValueAt(filaSeleccion, 7).toString());
		frmPersona.txtFechaRegP.setText(frmPersona.tlbListaP.getValueAt(filaSeleccion, 8).toString());
		frmPersona.txtFechaUltModP.setText(frmPersona.tlbListaP.getValueAt(filaSeleccion, 9).toString());
		frmPersona.txtRegistradorP.setText(frmPersona.tlbListaP.getValueAt(filaSeleccion, 10).toString());
	 	frmPersona.lblRegistradorP.setVisible(true);
	 	frmPersona.txtRegistradorP.setVisible(true);
		frmPersona.btnNuevoP.setVisible(true);
		frmPersona.btnOkP.setText("ELIMINAR");
		ImageIcon iieliminar= new ImageIcon("iconos/eliminarbd.png");
		Icon ieliminar = new ImageIcon(iieliminar.getImage().getScaledInstance(30, frmPersona.btnOkP.getHeight(), Image.SCALE_DEFAULT));
		frmPersona.btnOkP.setIcon(ieliminar);
		cambiotexto();
	}
	
	private void cambiotexto(){
		if(modo==1){
			frmPersona.lblRegistroDePersonas.setText("AGREGANDO NUEVA PERSONA CON REGISTRO Nº: "+frmPersona.spNumRegP.getValue());
		}else if(modo==2){
			frmPersona.lblRegistroDePersonas.setText("MODIFICANDO EL REGISTRO Nº: "+frmPersona.spNumRegP.getValue()+".  | PERSONA: " + 
			frmPersona.txtNombreP.getText()+", "+frmPersona.txtApellidoP.getText());
		}else if(modo==3){
			frmPersona.lblRegistroDePersonas.setText("ELIMINANDO EL REGISTRO Nº: "+frmPersona.spNumRegP.getValue()+".  | PERSONA: " + 
			frmPersona.txtNombreP.getText()+", "+frmPersona.txtApellidoP.getText());
		}
	}
	
	private void limpiarControles(){
		if(modo==1){
			frmPersona.spNumRegP.setEnabled(false);	
		}else if(modo==2){
			frmPersona.spNumRegP.setEnabled(true);
		}else if(modo==3){
			frmPersona.spNumRegP.setEnabled(true);
		}
		frmPersona.txtRegistradorP.setText(null);
	 	frmPersona.txtFechaRegP.setText(null);
	 	frmPersona.txtFechaUltModP.setText(null);
	 	frmPersona.txtNombreP.setText(null);
	 	frmPersona.txtApellidoP.setText(null);
	 	frmPersona.txtEmailP.setText(null);
	 	frmPersona.txtTelefonoP.setText(null);
	 	frmPersona.txtaComentarioP.setText(null);	
		if(modo==1){
			frmPersona.txtNombrePB.setText(null);
		}
	}
	
	private void exportarExcel(){
		exportarExcel = new ExportarExcel();
		TableModel tm1 = frmPersona.tlbListaP.getModel();
		DefaultTableModel dtm1 = new DefaultTableModel(); 
		String cabecera[] = new String[] {"N°", "Nombres","Apellidos","Area Trabajo","Pais","Email",
				"Teléfono","Comentario","Fecha de Registro","Ultima Fecha Modificado","Usuario Registrador"};
		dtm1 = new DefaultTableModel(cabecera, 0);
		JTable tabla1 = new JTable(null);
		int filas = tm1.getRowCount();
		for (int i = 0; i < filas ; i++) {
			Object obj1[] = {tm1.getValueAt(i, 0),tm1.getValueAt(i, 1),tm1.getValueAt(i, 2),tm1.getValueAt(i, 3),
					tm1.getValueAt(i, 4),tm1.getValueAt(i, 5),tm1.getValueAt(i, 6),tm1.getValueAt(i, 7),tm1.getValueAt(i, 8),
					tm1.getValueAt(i, 9),tm1.getValueAt(i, 10)};
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
