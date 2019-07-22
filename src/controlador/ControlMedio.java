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
import bean.MedioBean;
import consultas.ExportarExcel;
import dao.MedioDAO;
import utilidades.RenderizarCeldaImg;
import vista.FrmMedio;
import vista.FrmMensaje;


public class ControlMedio implements ActionListener, MouseListener, KeyListener{

	private MedioBean medioBean;
	private MedioDAO medioDao;
	private ArrayList<MedioBean> listaMedio;
	private FrmMedio frmMedio;
	private int modo = 0;
	private ExportarExcel exportarExcel;
	private TableRowSorter<DefaultTableModel> trs;
	private FrmMensaje frmMSG;
	private int tipooperacion=0; /*1=normal,2=si hay ya existe una sala*/
	private int codigomedio=0;
	private String nombremedio=null;
	
	public ControlMedio(MedioBean medioB, MedioDAO medioD, FrmMedio frmMedio) {
		this.medioBean = medioB;
		this.medioDao = medioD;
		this.frmMedio = frmMedio;
		this.frmMedio.btnNuevoM.addActionListener(this);
		this.frmMedio.btnCancelM.addActionListener(this);
		this.frmMedio.btnOkM.addActionListener(this);
		this.frmMedio.btnExportarExcelM.addActionListener(this);
		this.frmMedio.tlbListaM.addMouseListener(this);
		this.frmMedio.txtNombreM.addMouseListener(this);
		this.frmMedio.txtNombreMB.addKeyListener(this);
		this.frmMedio.txtNombreM.addKeyListener(this);
		this.frmMedio.txtaDescripcionM.addKeyListener(this);
		this.frmMedio.btnOkM.addKeyListener(this);
		this.frmMedio.btnCancelM.addKeyListener(this);
		this.frmMedio.lblTotalItemsM.addKeyListener(this);
		listarMedios();
	}
	//EVENTOS DEL LOS COMPONENTES
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==frmMedio.btnNuevoM){
			listarMedios();
		}else if(e.getSource()==frmMedio.btnCancelM){
			frmMedio.dispose();
		}else if(e.getSource()==frmMedio.btnOkM){
			if(modo==1){
				if(validaciones()==false){
					regitrarMedio();
				}
			}else if(modo==2){
				if(validaciones()==false){
					frmMSG = new FrmMensaje(frmMSG, true);
					frmMSG.txtaMgs.setText("¿Estas seguro de Modificar el Registro Nº: "+frmMedio.spNumRegM.getValue()+"?");
					frmMSG.setVisible(true);
					if(FrmMensaje.valor==1){
						modificarMedio();
					}
				}
			}else if(modo==3){
				if(validaciones()==false){
					frmMSG = new FrmMensaje(frmMSG, true);
					frmMSG.txtaMgs.setText("¿Estas seguro de Eliminar el Registro Nº: "+frmMedio.spNumRegM.getValue()+"?");
					frmMSG.setVisible(true);
					if(FrmMensaje.valor==1){
						eliminarMedio();
					}
				}
			}else{
				JOptionPane.showMessageDialog(null, "Tipo de Operacion No existe \n Consulte con sistemas Musli-");
			}
		}else if(e.getSource()==frmMedio.btnExportarExcelM){
			exportarExcel();
		}else{
			JOptionPane.showMessageDialog(null, "Incomveniente Interno con la BD.\n Resuelva con Sistemas Musli-");
		}
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		int col = frmMedio.tlbListaM.columnAtPoint(e.getPoint());
		if(col==4){
			modoActualizar(e);
		}else if(col==5){
			modoEliminar(e);
			if(validaciones()==false){
				frmMSG = new FrmMensaje(frmMSG, true);
				frmMSG.txtaMgs.setText("¿Estas seguro de Eliminar el Registro Nº: "+frmMedio.spNumRegM.getValue()+"?");
				frmMSG.setVisible(true);
				if(FrmMensaje.valor==1){
					eliminarMedio();
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
		if(arg0.getSource()==frmMedio.txtaDescripcionM){
			if(arg0.getKeyCode()==KeyEvent.VK_TAB){
				frmMedio.btnOkM.requestFocus();
			}
		}else if(arg0.getSource()==frmMedio.btnOkM){
			if(arg0.getKeyCode()==KeyEvent.VK_ENTER){
				if(validaciones()==false){
					frmMedio.btnOkM.doClick();
				}
			}
		}else if(arg0.getSource()==frmMedio.btnCancelM){
			if(arg0.getKeyCode()==KeyEvent.VK_ENTER){
				frmMedio.btnCancelM.doClick();
			}
		}
		if(arg0.getKeyCode()==KeyEvent.VK_ESCAPE){
			frmMedio.dispose();
		}
	}
	@Override
	public void keyReleased(KeyEvent arg0) {}
	@Override
	public void keyTyped(KeyEvent arg0) {
		if(arg0.getSource()==frmMedio.txtNombreMB){
			frmMedio.txtNombreMB.addKeyListener(new KeyAdapter() {
				@Override //ESTE EVENTO PUEDE IR DENTRO DE OTRO EVENTO O FUERA
				public void keyReleased(KeyEvent e) {
					String cadena = (frmMedio.txtNombreMB.getText());
					frmMedio.txtNombreMB.setText(cadena);
					trs.setRowFilter(RowFilter.regexFilter("(?i)"+frmMedio.txtNombreMB.getText(), 1));

					frmMedio.lblTotalItemsM.setText("Total Items: " + frmMedio.tlbListaM.getRowCount());
				}
			});
			trs = new TableRowSorter<DefaultTableModel>(frmMedio.dtmM);
			frmMedio.tlbListaM.setRowSorter(trs);			
		}		
	}
	
	//OPERACIONES DE LISTAR, INSERTAR, MODIFICAR Y ELIMINAR
	private void listarMedios(){
		modoRegistro();
		listaMedio = new ArrayList<MedioBean>();
		medioDao = new MedioDAO();
		frmMedio.dtmM = new DefaultTableModel();
		trs = new TableRowSorter<DefaultTableModel>(null);
		//---INICIO - PARA LA IMAGEN EN LA CELDA
		frmMedio.tlbListaM.setDefaultRenderer(Object.class, new RenderizarCeldaImg());
		
		ImageIcon iilapiz = new ImageIcon("iconos/lapiz.png");
		Icon ilapiz = new ImageIcon(iilapiz.getImage().getScaledInstance(25, 25, Image.SCALE_DEFAULT));
		JLabel llapiz=new JLabel();
		llapiz.setIcon(ilapiz);
		ImageIcon iix = new ImageIcon("iconos/x.png");
		Icon ix = new ImageIcon(iix.getImage().getScaledInstance(24, 23, Image.SCALE_DEFAULT));
		JLabel lx=new JLabel();
		lx.setIcon(ix);
		///--- FIN 
		String cabecera[] = new String[] {"Código", "Nombre de Medio","Descripción", "Fecha de Registro"," M "," E "};
		listaMedio = medioDao.listarTodosMedios();	
		frmMedio.dtmM = new DefaultTableModel(cabecera, 0);
		for (int i = 0; i < listaMedio.size(); i++) {
			Object [] obj = {listaMedio.get(i).getCodigoM(),listaMedio.get(i).getNombreM(),listaMedio.get(i).getDescripcionM(),
					listaMedio.get(i).getFechaM(),llapiz,lx};
			frmMedio.dtmM.addRow(obj);
		}
		trs = new TableRowSorter<DefaultTableModel>(frmMedio.dtmM);
		frmMedio.tlbListaM.setRowSorter(trs);//PARA QUE CUANDO HAGA EL FILTRO SE RESTABLEZCA LA LISTA
		frmMedio.tlbListaM.setModel(frmMedio.dtmM);
		frmMedio.tlbListaM.getColumnModel().getColumn(0).setPreferredWidth(51);
		frmMedio.tlbListaM.getColumnModel().getColumn(1).setPreferredWidth(160);
		frmMedio.tlbListaM.getColumnModel().getColumn(2).setPreferredWidth(101);
		frmMedio.tlbListaM.getColumnModel().getColumn(3).setPreferredWidth(125);
		frmMedio.tlbListaM.getColumnModel().getColumn(4).setPreferredWidth(35);
		frmMedio.tlbListaM.getColumnModel().getColumn(5).setPreferredWidth(30);
		
		frmMedio.lblTotalItemsM.setText("Total Items: " + frmMedio.tlbListaM.getRowCount());
	}
	
	private void regitrarMedio(){
		medioBean = new MedioBean();
		medioDao = new MedioDAO();
		medioBean.setCodigoM((int) frmMedio.spNumRegM.getValue());
		medioBean.setNombreM(frmMedio.txtNombreM.getText());
		medioBean.setDescripcionM(frmMedio.txtaDescripcionM.getText().trim());
		int reg = medioDao.registrarMedio(medioBean);
		if(reg>0){
			if(tipooperacion==1){
				JOptionPane.showMessageDialog(null, "Nº de Registro: " + frmMedio.spNumRegM.getValue() + ", Registrado Correctamente.!!!");
			}else{
				JOptionPane.showMessageDialog(null, "Medio: "+ nombremedio +". Con Nº de Registro: " + codigomedio + ", Recuperado.");
			}
			listarMedios();
		}else{
			JOptionPane.showMessageDialog(null, "Nº de Registro: " + frmMedio.spNumRegM.getValue() + ".\n No Registrado, consulte con sistemas-Musli");
		}
	}

	private void modificarMedio(){
		medioBean = new MedioBean();
		medioDao = new MedioDAO();
		medioBean.setNombreM(frmMedio.txtNombreM.getText());
		medioBean.setDescripcionM(frmMedio.txtaDescripcionM.getText().trim());
		medioBean.setCodigoM((int) frmMedio.spNumRegM.getValue());
		int mod = medioDao.modificarMedio(medioBean);
		if(mod>0){
			JOptionPane.showMessageDialog(null, "Nº de Registro: " + frmMedio.spNumRegM.getValue() + ", Modificado Correctamente.!!!");
			listarMedios();
		}else{
			JOptionPane.showMessageDialog(null, "Nº de Registro: " + frmMedio.spNumRegM.getValue() + ", No Modificado.\n Consulte con sistemas-Musli");
		}
	}
	
	private void eliminarMedio(){
		medioBean = new MedioBean();
		medioDao = new MedioDAO();
		medioBean.setCodigoM((int) frmMedio.spNumRegM.getValue());
		int eli = medioDao.eliminarMedio(medioBean);
		if(eli>0){
			JOptionPane.showMessageDialog(null, "Nº de Registro: " + frmMedio.spNumRegM.getValue() + ", Eliminado Correctamente.!!!");
			listarMedios();
		}else{
			JOptionPane.showMessageDialog(null, "Nº de Registro: " + frmMedio.spNumRegM.getValue() + ", No Eliminado, consulte con sistemas-Musli");
		}
	}
	//MANEJO DE CONTROLES
	private boolean validaciones(){
		boolean valor = false;
		codigomedio=0;
		nombremedio=null;
		int num = 0;
		String nom =null;
		num = (int) frmMedio.spNumRegM.getValue();
		nom = frmMedio.txtNombreM.getText();
		
		if(modo==1 || modo==2){
			tipooperacion = 1;
			TableModel tm = frmMedio.tlbListaM.getModel();
			int filas = tm.getRowCount();
			for (int i = 0; i < filas ; i++) {
				codigomedio = (int) tm.getValueAt(i, 0);
				nombremedio = (String) tm.getValueAt(i, 1);
				if(nombremedio.equalsIgnoreCase(nom)){
					if(codigomedio!=num){
						JOptionPane.showMessageDialog(null,"El Medio: "+nom+". Ya existe en la BD. Con Nº Registro: "+codigomedio);
						valor = true;
					}
				}
			}
		}
		
		if(modo==1||modo==2){
			tipooperacion = 1;
			medioBean = new MedioBean();
			medioDao = new MedioDAO();
			listaMedio = new ArrayList<MedioBean>();
			listaMedio=medioDao.verificarNombreMedio(nom);
			if(listaMedio.size()!=0){
				codigomedio = listaMedio.get(0).getCodigoM();
				nombremedio = listaMedio.get(0).getNombreM();
				if(codigomedio!=num){
					if(modo==1){
						frmMSG = new FrmMensaje(frmMSG, true);
						frmMSG.txtaMgs.setText("Se ha encontrado en la BD el Registro N°: "+codigomedio+". Perteneciente a: "+ nombremedio);
						frmMSG.txtaMgs2.setText("¿Desea Recuperar el Medio de la BD?");
						if(FrmMensaje.valor==1){
							valor = false;
						}else{
							valor = true;
						}
						tipooperacion = 2;
					}else{
						JOptionPane.showMessageDialog(null,"Se encontró en la BD el Registro N°: "+codigomedio+
								  "\nPertenece a: "+ nombremedio+"\nREGISTRE como nuevo la sala con nombre: "+nombremedio);
						valor=true;
					}
				}
			}
		}
		
		if(num==0){
			JOptionPane.showMessageDialog(null, "El 'Nº Registro' No puede ser vacío ó ( 0 )");
			valor = true;
		}else if(nom==null || nom==""){
			JOptionPane.showMessageDialog(null, "Debe Ingresar el Nombre del Medio.");
			valor = true;
		}
		return valor;
	}
	
	private void modoRegistro(){
		modo = 1;
		limpiarControles();
		int max = 0;
	 	medioDao = new MedioDAO();
	 	max = medioDao.obtenerUltimoRegistro();	
		frmMedio.spNumRegM.setValue(max);
		frmMedio.spNumRegM.setEnabled(false);
	 	SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
	 	frmMedio.txtFechaRegM.setText(sdf.format(new Date()));
	 	frmMedio.btnNuevoM.setVisible(false);
	 	frmMedio.btnOkM.setText("REGISTRAR");
	 	ImageIcon iiguardar= new ImageIcon("iconos/guardar.png");
		Icon iguardar = new ImageIcon(iiguardar.getImage().getScaledInstance(30, frmMedio.btnOkM.getHeight(), Image.SCALE_DEFAULT));
		frmMedio.btnOkM.setIcon(iguardar);
		cambiotexto();
	}
	
	private void modoActualizar(MouseEvent e){
		modo = 2;
		limpiarControles();
		int filaSeleccion = frmMedio.tlbListaM.rowAtPoint(e.getPoint());
		frmMedio.spNumRegM.setValue(Integer.valueOf(frmMedio.tlbListaM.getValueAt(filaSeleccion, 0).toString()));
		frmMedio.txtNombreM.setText(frmMedio.tlbListaM.getValueAt(filaSeleccion, 1).toString());
		frmMedio.txtaDescripcionM.setText(frmMedio.tlbListaM.getValueAt(filaSeleccion, 2).toString());
		frmMedio.txtFechaRegM.setText(frmMedio.tlbListaM.getValueAt(filaSeleccion, 3).toString());
		frmMedio.btnNuevoM.setVisible(true);
		frmMedio.btnOkM.setText("MODIFICAR");
		ImageIcon iimodificar= new ImageIcon("iconos/modificar.png");
		Icon imodificar = new ImageIcon(iimodificar.getImage().getScaledInstance(30, frmMedio.btnOkM.getHeight(), Image.SCALE_DEFAULT));
		frmMedio.btnOkM.setIcon(imodificar);
		cambiotexto();
	}
	
	private void modoEliminar(MouseEvent e){
		modo = 3;
		limpiarControles();
		int filaSeleccion = frmMedio.tlbListaM.rowAtPoint(e.getPoint());
		frmMedio.spNumRegM.setValue(Integer.valueOf(frmMedio.tlbListaM.getValueAt(filaSeleccion, 0).toString()));
		frmMedio.txtNombreM.setText(frmMedio.tlbListaM.getValueAt(filaSeleccion, 1).toString());
		frmMedio.txtaDescripcionM.setText(frmMedio.tlbListaM.getValueAt(filaSeleccion, 2).toString());
		frmMedio.txtFechaRegM.setText(frmMedio.tlbListaM.getValueAt(filaSeleccion, 3).toString());
		frmMedio.btnNuevoM.setVisible(true);
		frmMedio.btnOkM.setText("ELIMINAR");
		ImageIcon iieliminar= new ImageIcon("iconos/eliminarbd.png");
		Icon ieliminar = new ImageIcon(iieliminar.getImage().getScaledInstance(30, frmMedio.btnOkM.getHeight(), Image.SCALE_DEFAULT));
		frmMedio.btnOkM.setIcon(ieliminar);
		cambiotexto();
	}
	
	private void cambiotexto(){
		if(modo==1){
			frmMedio.lblRegistroDeMedios.setText("AGREGANDO NUEVO MEDIO CON REGISTRO Nº: "+frmMedio.spNumRegM.getValue());
		}else if(modo==2){
			frmMedio.lblRegistroDeMedios.setText("MODIFICANDO EL REGISTRO Nº: "+frmMedio.spNumRegM.getValue()+".  | MEDIO: " + frmMedio.txtNombreM.getText());
		}else if(modo==3){
			frmMedio.lblRegistroDeMedios.setText("ELIMINANDO EL REGISTRO Nº: "+frmMedio.spNumRegM.getValue()+".  | MEDIO: " + frmMedio.txtNombreM.getText());
		}
	}
	
	private void limpiarControles(){
		if(modo==1){
			frmMedio.spNumRegM.setEnabled(false);	
		}else if(modo==2){
			frmMedio.spNumRegM.setEnabled(true);
		}else if(modo==3){
			frmMedio.spNumRegM.setEnabled(true);
		}
		frmMedio.txtFechaRegM.setText(null);
		frmMedio.txtNombreM.setText(null);
		frmMedio.txtaDescripcionM.setText(null);		
		if(modo==1){
			frmMedio.txtNombreMB.setText(null);
		}
	}
	
	private void exportarExcel(){
		exportarExcel = new ExportarExcel();
		TableModel tm1 = frmMedio.tlbListaM.getModel();
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
