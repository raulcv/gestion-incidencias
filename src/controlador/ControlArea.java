package controlador;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
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
import consultas.ExportarExcel;
import dao.AreaDAO;
import utilidades.RenderizarCeldaImg;
import vista.FrmArea;
import vista.FrmMensaje;


public class ControlArea implements ActionListener, MouseListener, KeyListener{

	private AreaBean areaBean=null;
	private AreaDAO areaDao=null;
	private ArrayList<AreaBean> listaArea=null;
	private FrmArea frmArea=new FrmArea(null, false);
	private int modo = 0;
	private ExportarExcel exportarExcel;
	private TableRowSorter<DefaultTableModel> trs;
	private FrmMensaje frmMSG;
	private int tipooperacion=0; /*1=normal,2=si hay ya existe una sala*/
	private int codigoarea=0;
	private String nombrearea=null;
	
	public ControlArea(FrmArea frmArea,AreaBean areaB, AreaDAO areaD) {
		this.frmArea = frmArea;
		this.areaBean = areaB;
		this.areaDao = areaD;
		this.frmArea.btnNuevoA.addActionListener(this);
		this.frmArea.btnCancelA.addActionListener(this);
		this.frmArea.btnOkA.addActionListener(this);
		this.frmArea.btnExportarExcelA.addActionListener(this);
		this.frmArea.tlbListaA.addMouseListener(this);
		this.frmArea.txtNombreA.addMouseListener(this);
		this.frmArea.txtNombreAB.addKeyListener(this);
		this.frmArea.txtNombreA.addKeyListener(this);
		this.frmArea.txtaDescripcionA.addKeyListener(this);
		this.frmArea.btnOkA.addKeyListener(this);
		this.frmArea.btnCancelA.addKeyListener(this);
		this.frmArea.lblTotalItemsA.addKeyListener(this);
		listarMedios();
	}
	//EVENTOS DEL LOS COMPONENTES
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==frmArea.btnNuevoA){
			listarMedios();
		}else if(e.getSource()==frmArea.btnCancelA){
			frmArea.dispose();
		}else if(e.getSource()==frmArea.btnOkA){
			if(modo==1){
				if(validaciones()==false){
					regitrarArea();
				}
			}else if(modo==2){
				if(validaciones()==false){
					frmMSG = new FrmMensaje(frmMSG, true);
					frmMSG.txtaMgs.setText("¿Estas seguro de Modificar el Registro Nº: "+frmArea.spNumRegA.getValue()+"?");
					frmMSG.setVisible(true);
					if(FrmMensaje.valor==1){
						modificarArea();
					}
				}
			}else if(modo==3){
				if(validaciones()==false){
					frmMSG = new FrmMensaje(frmMSG, true);
					frmMSG.txtaMgs.setText("¿Estas seguro de Eliminar el Registro Nº: "+frmArea.spNumRegA.getValue()+"?");
					frmMSG.setVisible(true);
					if(FrmMensaje.valor==1){
						eliminarArea();
					}
				}
			}else{
				JOptionPane.showMessageDialog(null, "Tipo de Operacion No existe \n Consulte con sistemas Musli-");
			}
		}else if(e.getSource()==frmArea.btnExportarExcelA){
			exportarExcel();
		}else{
			JOptionPane.showMessageDialog(null, "Incomveniente Interno con la BD.\n Resuelva con Sistemas Musli-");
		}
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		int col = frmArea.tlbListaA.columnAtPoint(e.getPoint());
		if(col==4){
			modoActualizar(e);
		}else if(col==5){
			modoEliminar(e);
			if(validaciones()==false){
				frmMSG = new FrmMensaje(frmMSG, true);
				frmMSG.txtaMgs.setText("¿Estas seguro de Eliminar el Registro Nº: "+frmArea.spNumRegA.getValue()+"?");
				frmMSG.setVisible(true);
				if(FrmMensaje.valor==1){
					eliminarArea();
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
		if(arg0.getSource()==frmArea.txtaDescripcionA){
			if(arg0.getKeyCode()==KeyEvent.VK_TAB){
				frmArea.btnOkA.requestFocus();
			}
		}else if(arg0.getSource()==frmArea.btnOkA){
			if(arg0.getKeyCode()==KeyEvent.VK_ENTER){
				if(validaciones()==false){
					frmArea.btnOkA.doClick();
				}
			}
		}else if(arg0.getSource()==frmArea.btnCancelA){
			if(arg0.getKeyCode()==KeyEvent.VK_ENTER){
				frmArea.btnCancelA.doClick();
			}
		}
		if(arg0.getKeyCode()==KeyEvent.VK_ESCAPE){
			frmArea.dispose();
		}
	}
	@Override
	public void keyReleased(KeyEvent arg0) {}
	@Override
	public void keyTyped(KeyEvent arg0) {
		if(arg0.getSource()==frmArea.txtNombreAB){
			frmArea.txtNombreAB.addKeyListener(new KeyAdapter() {
				@Override //ESTE EVENTO PUEDE IR DENTRO DE OTRO EVENTO O FUERA
				public void keyReleased(KeyEvent e) {
					String cadena = (frmArea.txtNombreAB.getText());
					frmArea.txtNombreAB.setText(cadena);
					trs.setRowFilter(RowFilter.regexFilter("(?i)"+frmArea.txtNombreAB.getText(), 1));

					frmArea.lblTotalItemsA.setText("Total Items: " + frmArea.tlbListaA.getRowCount());
				}
			});
			trs = new TableRowSorter<DefaultTableModel>(frmArea.dtmM);
			frmArea.tlbListaA.setRowSorter(trs);			
		}		
	}
	
	//OPERACIONES DE LISTAR, INSERTAR, MODIFICAR Y ELIMINAR
	private void listarMedios(){
		modoRegistro();
		listaArea = new ArrayList<AreaBean>();
		areaDao = new AreaDAO();
		frmArea.dtmM = new DefaultTableModel();
		trs = new TableRowSorter<DefaultTableModel>(null);
		//---INICIO - PARA LA IMAGEN EN LA CELDA
		frmArea.tlbListaA.setDefaultRenderer(Object.class, new RenderizarCeldaImg());
		
		ImageIcon iilapiz = new ImageIcon("iconos/lapiz.png");
		Icon ilapiz = new ImageIcon(iilapiz.getImage().getScaledInstance(25, 25, Image.SCALE_DEFAULT));
		JLabel llapiz=new JLabel();
		llapiz.setIcon(ilapiz);
		ImageIcon iix = new ImageIcon("iconos/x.png");
		Icon ix = new ImageIcon(iix.getImage().getScaledInstance(24, 23, Image.SCALE_DEFAULT));
		JLabel lx=new JLabel();
		lx.setIcon(ix);
		///--- FIN 
		String cabecera[] = new String[] {"Código", "Nombre de Area","Descripción", "Fecha de Registro"," M "," E "};
		listaArea = areaDao.listarTodasAreas();	
		frmArea.dtmM = new DefaultTableModel(cabecera, 0);
		for (int i = 0; i < listaArea.size(); i++) {
			Object [] obj = {listaArea.get(i).getCodigoA(),listaArea.get(i).getNombreA(),listaArea.get(i).getDescripcionA(),
					listaArea.get(i).getFecharegA(),llapiz,lx};
			frmArea.dtmM.addRow(obj);
		}
		trs = new TableRowSorter<DefaultTableModel>(frmArea.dtmM);
		frmArea.tlbListaA.setRowSorter(trs);//PARA QUE CUANDO HAGA EL FILTRO SE RESTABLEZCA LA LISTA
		frmArea.tlbListaA.setModel(frmArea.dtmM);
		frmArea.tlbListaA.getColumnModel().getColumn(0).setPreferredWidth(51);
		frmArea.tlbListaA.getColumnModel().getColumn(1).setPreferredWidth(160);
		frmArea.tlbListaA.getColumnModel().getColumn(2).setPreferredWidth(101);
		frmArea.tlbListaA.getColumnModel().getColumn(3).setPreferredWidth(125);
		frmArea.tlbListaA.getColumnModel().getColumn(4).setPreferredWidth(35);
		frmArea.tlbListaA.getColumnModel().getColumn(5).setPreferredWidth(30);
		
		frmArea.lblTotalItemsA.setText("Total Items: " + frmArea.tlbListaA.getRowCount());
	}
	
	private void regitrarArea(){
		areaBean = new AreaBean();
		areaDao = new AreaDAO();
		areaBean.setCodigoA((int) frmArea.spNumRegA.getValue());
		areaBean.setNombreA(frmArea.txtNombreA.getText().trim());
		areaBean.setDescripcionA(frmArea.txtaDescripcionA.getText().trim());
		int reg = areaDao.registrarArea(areaBean);
		if(reg>0){
			if(tipooperacion==1){
				JOptionPane.showMessageDialog(null, "Nº de Registro: " + frmArea.spNumRegA.getValue() + ", Registrado Correctamente.!!!");
			}else{
				JOptionPane.showMessageDialog(null, "Area: "+ nombrearea +". Con Nº de Registro: " + codigoarea + ", Recuperado.");
			}
			listarMedios();
		}else{
			JOptionPane.showMessageDialog(null, "Nº de Registro: " + frmArea.spNumRegA.getValue() + ".\n No Registrado, consulte con sistemas-Musli");
		}
	}

	private void modificarArea(){
		areaBean = new AreaBean();
		areaDao = new AreaDAO();
		areaBean.setNombreA(frmArea.txtNombreA.getText());
		areaBean.setDescripcionA(frmArea.txtaDescripcionA.getText().trim());
		areaBean.setCodigoA((int) frmArea.spNumRegA.getValue());
		int mod = areaDao.modificarArea(areaBean);
		if(mod>0){
			JOptionPane.showMessageDialog(null, "Nº de Registro: " + frmArea.spNumRegA.getValue() + ", Modificado Correctamente.!!!");
			listarMedios();
		}else{
			JOptionPane.showMessageDialog(null, "Nº de Registro: " + frmArea.spNumRegA.getValue() + ", No Modificado.\n Consulte con sistemas-Musli");
		}
	}
	
	private void eliminarArea(){
		areaBean = new AreaBean();
		areaDao = new AreaDAO();
		areaBean.setCodigoA((int) frmArea.spNumRegA.getValue());
		int eli = areaDao.eliminarArea(areaBean);
		if(eli>0){
			JOptionPane.showMessageDialog(null, "Nº de Registro: " + frmArea.spNumRegA.getValue() + ", Eliminado Correctamente.!!!");
			listarMedios();
		}else{
			JOptionPane.showMessageDialog(null, "Nº de Registro: " + frmArea.spNumRegA.getValue() + ", No Eliminado, consulte con sistemas-Musli");
		}
	}
	//MANEJO DE CONTROLES
	private boolean validaciones(){
		boolean valor = false;
		tipooperacion = 1;
		codigoarea=0;
		nombrearea=null;
		int num = 0;
		String nom =null;
		num = (int) frmArea.spNumRegA.getValue();
		nom = frmArea.txtNombreA.getText();
		if(num==0){
			JOptionPane.showMessageDialog(null, "El 'Nº Registro' No puede ser vacío ó ( 0 )");
			valor = true;
		}else if(nom.equalsIgnoreCase("")){
			JOptionPane.showMessageDialog(null, "Debe Ingresar el Nombre del Area.");
			valor = true;
		}else{ 
			if(modo==1 || modo==2){
				tipooperacion = 1;
				TableModel tm = frmArea.tlbListaA.getModel();
				int filas = tm.getRowCount();
				for (int i = 0; i < filas ; i++) {
					codigoarea = (int) tm.getValueAt(i, 0);
					nombrearea = (String) tm.getValueAt(i, 1);
					if(nombrearea.equalsIgnoreCase(nom)){
						if(codigoarea!=num){
							JOptionPane.showMessageDialog(null,"La Area: "+nom+". Ya existe en la BD. Con Nº Registro: "+codigoarea);
							return valor = true;
						}
					}
				}
			areaBean = new AreaBean();
			areaDao = new AreaDAO();
			listaArea = new ArrayList<AreaBean>();
			listaArea=areaDao.verificarNombreArea(nom);
			if(listaArea.size()!=0){
				codigoarea = listaArea.get(0).getCodigoA();
				nombrearea = listaArea.get(0).getNombreA();
				if(codigoarea!=num){
					if(modo==1){
						tipooperacion = 2;
						frmMSG = new FrmMensaje(frmMSG, true);
						frmMSG.txtaMgs.setText("Se ha encontrado en la BD el Registro N°: "+codigoarea+". Perteneciente a: "+ nombrearea);
						frmMSG.txtaMgs2.setText("¿Desea Recuperar la Area de la BD?");
						frmMSG.setVisible(true);
						if(FrmMensaje.valor==1){
							valor = false;
						}else{
							valor = true;
						}
					}else{
						JOptionPane.showMessageDialog(null,"Se encontró en la BD el Registro N°: "+codigoarea+
								  "\nPertenece a: "+ nombrearea+"\nREGISTRE como nuevo la Area con nombre: "+nombrearea);
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
	 	areaDao = new AreaDAO();
	 	max = areaDao.obtenerUltimoRegistro();	
		frmArea.spNumRegA.setValue(max);
		frmArea.spNumRegA.setEnabled(false);
	 	SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
	 	frmArea.txtFechaRegA.setText(sdf.format(new Date()));
	 	frmArea.btnNuevoA.setVisible(false);
	 	frmArea.btnOkA.setText("REGISTRAR");
	 	ImageIcon iiguardar= new ImageIcon("iconos/guardar.png");
		Icon iguardar = new ImageIcon(iiguardar.getImage().getScaledInstance(30, frmArea.btnOkA.getHeight(), Image.SCALE_DEFAULT));
		frmArea.btnOkA.setIcon(iguardar);
		cambiotexto();
	}
	
	private void modoActualizar(MouseEvent e){
		modo = 2;
		limpiarControles();
		int filaSeleccion = frmArea.tlbListaA.rowAtPoint(e.getPoint());
		frmArea.spNumRegA.setValue(Integer.valueOf(frmArea.tlbListaA.getValueAt(filaSeleccion, 0).toString()));
		frmArea.txtNombreA.setText(frmArea.tlbListaA.getValueAt(filaSeleccion, 1).toString());
		frmArea.txtaDescripcionA.setText(frmArea.tlbListaA.getValueAt(filaSeleccion, 2).toString());
		frmArea.txtFechaRegA.setText(frmArea.tlbListaA.getValueAt(filaSeleccion, 3).toString());
		frmArea.btnNuevoA.setVisible(true);
		frmArea.btnOkA.setText("MODIFICAR");
		ImageIcon iimodificar= new ImageIcon("iconos/modificar.png");
		Icon imodificar = new ImageIcon(iimodificar.getImage().getScaledInstance(30, frmArea.btnOkA.getHeight(), Image.SCALE_DEFAULT));
		frmArea.btnOkA.setIcon(imodificar);
		cambiotexto();
	}
	
	private void modoEliminar(MouseEvent e){
		modo = 3;
		limpiarControles();
		int filaSeleccion = frmArea.tlbListaA.rowAtPoint(e.getPoint());
		frmArea.spNumRegA.setValue(Integer.valueOf(frmArea.tlbListaA.getValueAt(filaSeleccion, 0).toString()));
		frmArea.txtNombreA.setText(frmArea.tlbListaA.getValueAt(filaSeleccion, 1).toString());
		frmArea.txtaDescripcionA.setText(frmArea.tlbListaA.getValueAt(filaSeleccion, 2).toString());
		frmArea.txtFechaRegA.setText(frmArea.tlbListaA.getValueAt(filaSeleccion, 3).toString());
		frmArea.btnNuevoA.setVisible(true);
		frmArea.btnOkA.setText("ELIMINAR");
		ImageIcon iieliminar= new ImageIcon("iconos/eliminarbd.png");
		Icon ieliminar = new ImageIcon(iieliminar.getImage().getScaledInstance(30, frmArea.btnOkA.getHeight(), Image.SCALE_DEFAULT));
		frmArea.btnOkA.setIcon(ieliminar);
		cambiotexto();
	}
	
	private void cambiotexto(){
		if(modo==1){
			frmArea.lblRegistroDeAreas.setText("AGREGANDO NUEVA AREA CON REGISTRO Nº: "+frmArea.spNumRegA.getValue());
		}else if(modo==2){
			frmArea.lblRegistroDeAreas.setText("MODIFICANDO EL REGISTRO Nº: "+frmArea.spNumRegA.getValue()+".  | MEDIO: " + frmArea.txtNombreA.getText());
		}else if(modo==3){
			frmArea.lblRegistroDeAreas.setText("ELIMINANDO EL REGISTRO Nº: "+frmArea.spNumRegA.getValue()+".  | MEDIO: " + frmArea.txtNombreA.getText());
		}
	}
	
	private void limpiarControles(){
		if(modo==1){
			frmArea.spNumRegA.setEnabled(false);	
		}else if(modo==2){
			frmArea.spNumRegA.setEnabled(true);
		}else if(modo==3){
			frmArea.spNumRegA.setEnabled(true);
		}
		frmArea.txtFechaRegA.setText(null);
		frmArea.txtNombreA.setText(null);
		frmArea.txtaDescripcionA.setText(null);		
		if(modo==1){
			frmArea.txtNombreAB.setText(null);
		}
	}
	
	private void exportarExcel(){
		exportarExcel = new ExportarExcel();
		TableModel tm1 = frmArea.tlbListaA.getModel();
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
