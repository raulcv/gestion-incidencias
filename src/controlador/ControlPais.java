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

import bean.PaisBean;
import consultas.ExportarExcel;
import dao.PaisDAO;
import utilidades.RenderizarCeldaImg;
import vista.FrmPais;
import vista.FrmMensaje;


public class ControlPais implements ActionListener, MouseListener, KeyListener{

	private PaisBean paisBean=null;
	private PaisDAO paisDao=null;
	private ArrayList<PaisBean> listaPais=null;
	private FrmPais FrmPais=new FrmPais(null, false);
	private int modo = 0;
	private ExportarExcel exportarExcel;
	private TableRowSorter<DefaultTableModel> trs;
	private FrmMensaje frmMSG;
	private int tipooperacion=0; /*1=normal,2=si hay ya existe una sala*/
	private int codigopais=0;
	private String nombrepais=null;
	
	public ControlPais(FrmPais FrmPais,PaisBean areaB, PaisDAO areaD) {
		this.FrmPais = FrmPais;
		this.paisBean = areaB;
		this.paisDao = areaD;
		this.FrmPais.btnNuevoPA.addActionListener(this);
		this.FrmPais.btnCancelPA.addActionListener(this);
		this.FrmPais.btnOkPA.addActionListener(this);
		this.FrmPais.btnExportarExcelPA.addActionListener(this);
		this.FrmPais.tlbListaPA.addMouseListener(this);
		this.FrmPais.txtNombrePA.addMouseListener(this);
		this.FrmPais.txtNombrePAB.addKeyListener(this);
		this.FrmPais.txtNombrePA.addKeyListener(this);
		this.FrmPais.txtaDescripcionPA.addKeyListener(this);
		this.FrmPais.btnOkPA.addKeyListener(this);
		this.FrmPais.btnCancelPA.addKeyListener(this);
		this.FrmPais.lblTotalItemsPA.addKeyListener(this);
		listarPaises();
	}
	//EVENTOS DEL LOS COMPONENTES
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==FrmPais.btnNuevoPA){
			listarPaises();
		}else if(e.getSource()==FrmPais.btnCancelPA){
			FrmPais.dispose();
		}else if(e.getSource()==FrmPais.btnOkPA){
			if(modo==1){
				if(validaciones()==false){
					registrarPais();
				}
			}else if(modo==2){
				if(validaciones()==false){
					frmMSG = new FrmMensaje(frmMSG, true);
					frmMSG.txtaMgs.setText("¿Estas seguro de Modificar el Registro Nº: "+FrmPais.spNumRegPA.getValue()+"?");
					frmMSG.setVisible(true);
					if(FrmMensaje.valor==1){
						modificarPais();
					}
				}
			}else if(modo==3){
				if(validaciones()==false){
					frmMSG = new FrmMensaje(frmMSG, true);
					frmMSG.txtaMgs.setText("¿Estas seguro de Eliminar el Registro Nº: "+FrmPais.spNumRegPA.getValue()+"?");
					frmMSG.setVisible(true);
					if(FrmMensaje.valor==1){
						eliminarPais();
					}
				}
			}else{
				JOptionPane.showMessageDialog(null, "Tipo de Operacion No existe \n Consulte con sistemas Musli-");
			}
		}else if(e.getSource()==FrmPais.btnExportarExcelPA){
			exportarExcel();
		}else{
			JOptionPane.showMessageDialog(null, "Incomveniente Interno con la BD.\n Resuelva con Sistemas Musli-");
		}
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		int col = FrmPais.tlbListaPA.columnAtPoint(e.getPoint());
		if(col==4){
			modoActualizar(e);
		}else if(col==5){
			modoEliminar(e);
			if(validaciones()==false){
				frmMSG = new FrmMensaje(frmMSG, true);
				frmMSG.txtaMgs.setText("¿Estas seguro de Eliminar el Registro Nº: "+FrmPais.spNumRegPA.getValue()+"?");
				frmMSG.setVisible(true);
				if(FrmMensaje.valor==1){
					eliminarPais();
				}
			}
				//SI VALIDACION ES TRUE, EL SISTEMA NO HACE NADA
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}
	@Override
	public void mouseExited(MouseEvent e) {
	}
	@Override
	public void mousePressed(MouseEvent e) {
	}
	@Override
	public void mouseReleased(MouseEvent e) {
	}
	//----EVENTOS DE KEYPRESSED
	@Override
	public void keyPressed(KeyEvent arg0) {
		trs = new TableRowSorter<DefaultTableModel>();
		if(arg0.getSource()==FrmPais.txtaDescripcionPA){
			if(arg0.getKeyCode()==KeyEvent.VK_TAB){
				FrmPais.btnOkPA.requestFocus();
			}
		}else if(arg0.getSource()==FrmPais.btnOkPA){
			if(arg0.getKeyCode()==KeyEvent.VK_ENTER){
				if(validaciones()==false){
					FrmPais.btnOkPA.doClick();
				}
			}
		}else if(arg0.getSource()==FrmPais.btnCancelPA){
			if(arg0.getKeyCode()==KeyEvent.VK_ENTER){
				FrmPais.btnCancelPA.doClick();
			}
		}
		if(arg0.getKeyCode()==KeyEvent.VK_ESCAPE){
			FrmPais.dispose();
		}
	}
	@Override
	public void keyReleased(KeyEvent arg0) {}
	@Override
	public void keyTyped(KeyEvent arg0) {
		if(arg0.getSource()==FrmPais.txtNombrePAB){
			FrmPais.txtNombrePAB.addKeyListener(new KeyAdapter() {
				@Override //ESTE EVENTO PUEDE IR DENTRO DE OTRO EVENTO O FUERA
				public void keyReleased(KeyEvent e) {
					String cadena = (FrmPais.txtNombrePAB.getText());
					FrmPais.txtNombrePAB.setText(cadena);
					trs.setRowFilter(RowFilter.regexFilter("(?i)"+FrmPais.txtNombrePAB.getText(), 1));

					FrmPais.lblTotalItemsPA.setText("Total Items: " + FrmPais.tlbListaPA.getRowCount());
				}
			});
			trs = new TableRowSorter<DefaultTableModel>(FrmPais.dtmM);
			FrmPais.tlbListaPA.setRowSorter(trs);			
		}		
	}
	
	//OPERACIONES DE LISTAR, INSERTAR, MODIFICAR Y ELIMINAR
	private void listarPaises(){
		modoRegistro();
		listaPais = new ArrayList<PaisBean>();
		paisDao = new PaisDAO();
		FrmPais.dtmM = new DefaultTableModel();
		trs = new TableRowSorter<DefaultTableModel>(null);
		//---INICIO - PARA LA IMAGEN EN LA CELDA
		FrmPais.tlbListaPA.setDefaultRenderer(Object.class, new RenderizarCeldaImg());
		
		ImageIcon iilapiz = new ImageIcon("iconos/lapiz.png");
		Icon ilapiz = new ImageIcon(iilapiz.getImage().getScaledInstance(25, 25, Image.SCALE_DEFAULT));
		JLabel llapiz=new JLabel();
		llapiz.setIcon(ilapiz);
		ImageIcon iix = new ImageIcon("iconos/x.png");
		Icon ix = new ImageIcon(iix.getImage().getScaledInstance(24, 23, Image.SCALE_DEFAULT));
		JLabel lx=new JLabel();
		lx.setIcon(ix);
		///--- FIN 
		String cabecera[] = new String[] {"Código", "Nombre de Pais","Descripción", "Fecha de Registro"," M "," E "};
		listaPais = paisDao.listarTodosPaises();	
		FrmPais.dtmM = new DefaultTableModel(cabecera, 0);
		for (int i = 0; i < listaPais.size(); i++) {
			Object [] obj = {listaPais.get(i).getCodigoPA(),listaPais.get(i).getNombrePA(),listaPais.get(i).getDescripcionPA(),
					listaPais.get(i).getFechaPA(),llapiz,lx};
			FrmPais.dtmM.addRow(obj);
		}
		trs = new TableRowSorter<DefaultTableModel>(FrmPais.dtmM);
		FrmPais.tlbListaPA.setRowSorter(trs);//PARA QUE CUANDO HAGA EL FILTRO SE RESTABLEZCA LA LISTA
		FrmPais.tlbListaPA.setModel(FrmPais.dtmM);
		FrmPais.tlbListaPA.getColumnModel().getColumn(0).setPreferredWidth(51);
		FrmPais.tlbListaPA.getColumnModel().getColumn(1).setPreferredWidth(160);
		FrmPais.tlbListaPA.getColumnModel().getColumn(2).setPreferredWidth(101);
		FrmPais.tlbListaPA.getColumnModel().getColumn(3).setPreferredWidth(125);
		FrmPais.tlbListaPA.getColumnModel().getColumn(4).setPreferredWidth(35);
		FrmPais.tlbListaPA.getColumnModel().getColumn(5).setPreferredWidth(30);
		
		FrmPais.lblTotalItemsPA.setText("Total Items: " + FrmPais.tlbListaPA.getRowCount());
	}
	
	private void registrarPais(){
		paisBean = new PaisBean();
		paisDao = new PaisDAO();
		paisBean.setCodigoPA((int) FrmPais.spNumRegPA.getValue());
		paisBean.setNombrePA(FrmPais.txtNombrePA.getText());
		paisBean.setDescripcionPA(FrmPais.txtaDescripcionPA.getText().trim());
		int reg = paisDao.registrarPais(paisBean);
		if(reg>0){
			if(tipooperacion==1){
				JOptionPane.showMessageDialog(null, "Nº de Registro: " + FrmPais.spNumRegPA.getValue() + ", Registrado Correctamente.!!!");
			}else{
				JOptionPane.showMessageDialog(null, "Pais: "+ nombrepais +". Con Nº de Registro: " + codigopais + ", Recuperado.");
			}
			listarPaises();
		}else{
			JOptionPane.showMessageDialog(null, "Nº de Registro: " + FrmPais.spNumRegPA.getValue() + ".\n No Registrado, consulte con sistemas-Musli");
		}
	}

	private void modificarPais(){
		paisBean = new PaisBean();
		paisDao = new PaisDAO();
		paisBean.setNombrePA(FrmPais.txtNombrePA.getText());
		paisBean.setDescripcionPA(FrmPais.txtaDescripcionPA.getText().trim());
		paisBean.setCodigoPA((int) FrmPais.spNumRegPA.getValue());
		int mod = paisDao.modificarPais(paisBean);
		if(mod>0){
			JOptionPane.showMessageDialog(null, "Nº de Registro: " + FrmPais.spNumRegPA.getValue() + ", Modificado Correctamente.!!!");
			listarPaises();
		}else{
			JOptionPane.showMessageDialog(null, "Nº de Registro: " + FrmPais.spNumRegPA.getValue() + ", No Modificado.\n Consulte con sistemas-Musli");
		}
	}
	
	private void eliminarPais(){
		paisBean = new PaisBean();
		paisDao = new PaisDAO();
		paisBean.setCodigoPA((int) FrmPais.spNumRegPA.getValue());
		int eli = paisDao.eliminarPais(paisBean);
		if(eli>0){
			JOptionPane.showMessageDialog(null, "Nº de Registro: " + FrmPais.spNumRegPA.getValue() + ", Eliminado Correctamente.!!!");
			listarPaises();
		}else{
			JOptionPane.showMessageDialog(null, "Nº de Registro: " + FrmPais.spNumRegPA.getValue() + ", No Eliminado, consulte con sistemas-Musli");
		}
	}
	//MANEJO DE CONTROLES
	private boolean validaciones(){
		boolean valor = false;
		tipooperacion = 1;
		codigopais=0;
		nombrepais=null;
		int num = 0;
		String nom =null;
		num = (int) FrmPais.spNumRegPA.getValue();
		nom = FrmPais.txtNombrePA.getText();
		if(num==0){
			JOptionPane.showMessageDialog(null, "El 'Nº Registro' No puede ser vacío ó ( 0 )");
			valor = true;
		}else if(nom.equalsIgnoreCase("")){
			JOptionPane.showMessageDialog(null, "Debe Ingresar el Nombre del Pais.");
			valor = true;
		}else{ 
			if(modo==1 || modo==2){
				tipooperacion = 1;
				TableModel tm = FrmPais.tlbListaPA.getModel();
				int filas = tm.getRowCount();
				for (int i = 0; i < filas ; i++) {
					codigopais = (int) tm.getValueAt(i, 0);
					nombrepais = (String) tm.getValueAt(i, 1);
					if(nombrepais.equalsIgnoreCase(nom)){
						if(codigopais!=num){
							JOptionPane.showMessageDialog(null,"El Pais: "+nom+". Ya existe en la BD. Con Nº Registro: "+codigopais);
							return valor = true;
						}
					}
				}
			paisBean = new PaisBean();
			paisDao = new PaisDAO();
			listaPais = new ArrayList<PaisBean>();
			listaPais=paisDao.verificarNombrePais(nom);
			if(listaPais.size()!=0){
				codigopais = listaPais.get(0).getCodigoPA();
				nombrepais = listaPais.get(0).getNombrePA();
				if(codigopais!=num){
					if(modo==1){
						tipooperacion = 2;
						frmMSG = new FrmMensaje(frmMSG, true);
						frmMSG.txtaMgs.setText("Se ha encontrado en la BD el Registro N°: "+codigopais+". Perteneciente a: "+ nombrepais);
						frmMSG.txtaMgs2.setText("¿Desea Recuperar el Pais de la BD?");
						frmMSG.setVisible(true);
						if(FrmMensaje.valor==1){
							valor = false;
						}else{
							valor = true;
						}
					}else{
						JOptionPane.showMessageDialog(null,"Se encontró en la BD el Registro N°: "+codigopais+
								  "\nPertenece a: "+ nombrepais+"\nREGISTRE como nuevo el Pais con nombre: "+nombrepais);
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
	 	paisDao = new PaisDAO();
	 	max = paisDao.obtenerUltimoRegistro();	
		FrmPais.spNumRegPA.setValue(max);
		FrmPais.spNumRegPA.setEnabled(false);
	 	SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
	 	FrmPais.txtFechaRegPA.setText(sdf.format(new Date()));
	 	FrmPais.btnNuevoPA.setVisible(false);
	 	FrmPais.btnOkPA.setText("REGISTRAR");
	 	ImageIcon iiguardar= new ImageIcon("iconos/guardar.png");
		Icon iguardar = new ImageIcon(iiguardar.getImage().getScaledInstance(30, FrmPais.btnOkPA.getHeight(), Image.SCALE_DEFAULT));
		FrmPais.btnOkPA.setIcon(iguardar);
		cambiotexto();
	}
	
	private void modoActualizar(MouseEvent e){
		modo = 2;
		limpiarControles();
		int filaSeleccion = FrmPais.tlbListaPA.rowAtPoint(e.getPoint());
		FrmPais.spNumRegPA.setValue(Integer.valueOf(FrmPais.tlbListaPA.getValueAt(filaSeleccion, 0).toString()));
		FrmPais.txtNombrePA.setText(FrmPais.tlbListaPA.getValueAt(filaSeleccion, 1).toString());
		FrmPais.txtaDescripcionPA.setText(FrmPais.tlbListaPA.getValueAt(filaSeleccion, 2).toString());
		FrmPais.txtFechaRegPA.setText(FrmPais.tlbListaPA.getValueAt(filaSeleccion, 3).toString());
		FrmPais.btnNuevoPA.setVisible(true);
		FrmPais.btnOkPA.setText("MODIFICAR");
		ImageIcon iimodificar= new ImageIcon("iconos/modificar.png");
		Icon imodificar = new ImageIcon(iimodificar.getImage().getScaledInstance(30, FrmPais.btnOkPA.getHeight(), Image.SCALE_DEFAULT));
		FrmPais.btnOkPA.setIcon(imodificar);
		cambiotexto();
	}
	
	private void modoEliminar(MouseEvent e){
		modo = 3;
		limpiarControles();
		int filaSeleccion = FrmPais.tlbListaPA.rowAtPoint(e.getPoint());
		FrmPais.spNumRegPA.setValue(Integer.valueOf(FrmPais.tlbListaPA.getValueAt(filaSeleccion, 0).toString()));
		FrmPais.txtNombrePA.setText(FrmPais.tlbListaPA.getValueAt(filaSeleccion, 1).toString());
		FrmPais.txtaDescripcionPA.setText(FrmPais.tlbListaPA.getValueAt(filaSeleccion, 2).toString());
		FrmPais.txtFechaRegPA.setText(FrmPais.tlbListaPA.getValueAt(filaSeleccion, 3).toString());
		FrmPais.btnNuevoPA.setVisible(true);
		FrmPais.btnOkPA.setText("ELIMINAR");
		ImageIcon iieliminar= new ImageIcon("iconos/eliminarbd.png");
		Icon ieliminar = new ImageIcon(iieliminar.getImage().getScaledInstance(30, FrmPais.btnOkPA.getHeight(), Image.SCALE_DEFAULT));
		FrmPais.btnOkPA.setIcon(ieliminar);
		cambiotexto();
	}
	
	private void cambiotexto(){
		if(modo==1){
			FrmPais.lblRegistroDePaises.setText("AGREGANDO NUEVO PAIS CON REGISTRO Nº: "+FrmPais.spNumRegPA.getValue());
		}else if(modo==2){
			FrmPais.lblRegistroDePaises.setText("MODIFICANDO EL REGISTRO Nº: "+FrmPais.spNumRegPA.getValue()+".  | PAIS: " + FrmPais.txtNombrePA.getText());
		}else if(modo==3){
			FrmPais.lblRegistroDePaises.setText("ELIMINANDO EL REGISTRO Nº: "+FrmPais.spNumRegPA.getValue()+".  | PAIS: " + FrmPais.txtNombrePA.getText());
		}
	}
	
	private void limpiarControles(){
		if(modo==1){
			FrmPais.spNumRegPA.setEnabled(false);	
		}else if(modo==2){
			FrmPais.spNumRegPA.setEnabled(true);
		}else if(modo==3){
			FrmPais.spNumRegPA.setEnabled(true);
		}
		FrmPais.txtFechaRegPA.setText(null);
		FrmPais.txtNombrePA.setText(null);
		FrmPais.txtaDescripcionPA.setText(null);		
		if(modo==1){
			FrmPais.txtNombrePAB.setText(null);
		}
	}
	
	private void exportarExcel(){
		exportarExcel = new ExportarExcel();
		TableModel tm1 = FrmPais.tlbListaPA.getModel();
		DefaultTableModel dtm1 = new DefaultTableModel(); 
		String cabecera[] = new String[] {"Código", "Nombre","Comentario", "Fecha Registro"};
		dtm1 = new DefaultTableModel(cabecera, 0);
		JTable tabla1 = new JTable(null);
		int filas = tm1.getRowCount();
		for (int i = 0; i < filas ; i++) {
			Object obj1[] = {tm1.getValueAt(i, 0),tm1.getValueAt(i, 1),tm1.getValueAt(i, 2),tm1.getValueAt(i, 3)};
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
