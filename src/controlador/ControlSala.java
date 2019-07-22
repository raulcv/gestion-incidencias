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
import bean.SalaBean;
import consultas.ExportarExcel;
import dao.SalaDAO;
import utilidades.RenderizarCeldaImg;
import vista.FrmMensaje;
import vista.FrmSala;

public class ControlSala implements ActionListener, MouseListener, KeyListener{
	private SalaBean salaBean;
	private SalaDAO salaDao;
	private ArrayList<SalaBean> listaSala;
	private FrmSala frmSala;
	private int modo = 0;
	private ExportarExcel exportarExcel;
	private TableRowSorter<DefaultTableModel> trs;
	private FrmMensaje frmMSG;
	private int tipooperacion=0; /*1=normal,2=si hay ya existe una sala*/
	private int codigosala=0;
	private String nombresala=null;
	
	public ControlSala(SalaBean salaB, SalaDAO salaD, FrmSala frmSala) {
		this.salaBean = salaB;
		this.salaDao = salaD;
		this.frmSala = frmSala;
		this.frmSala.btnnuevo.addActionListener(this);
		this.frmSala.btnCancel.addActionListener(this);
		this.frmSala.btnOk.addActionListener(this);
		this.frmSala.btnExportarExcel.addActionListener(this);
		this.frmSala.tlbLista.addMouseListener(this);
		this.frmSala.txtNombreS.addMouseListener(this);
		this.frmSala.txtNombreSB.addKeyListener(this);
		this.frmSala.txtNombreS.addKeyListener(this);
		this.frmSala.txtaDescripcion.addKeyListener(this);
		this.frmSala.btnOk.addKeyListener(this);
		this.frmSala.btnCancel.addKeyListener(this);
		this.frmSala.lblTotalItems.addKeyListener(this);
		listarSalas();
	}
	//EVENTOS DEL LOS COMPONENTES
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==frmSala.btnnuevo){
			listarSalas();
		}else if(e.getSource()==frmSala.btnCancel){
				frmSala.dispose();
		}else if(e.getSource()==frmSala.btnOk){
			if(modo==1){
				if(validaciones()==false){
					regitrarSala();
				}
			}else if(modo==2){
				if(validaciones()==false){				
					frmMSG = new FrmMensaje(frmMSG, true);
					frmMSG.txtaMgs.setText("¿Estas seguro de Modificar el Registro Nº: "+frmSala.spNumReg.getValue()+"?");
					frmMSG.setVisible(true);
					if(FrmMensaje.valor==1){
						modificarSala();
					}
				}
			}else if(modo==3){
				if(validaciones()==false){
					frmMSG = new FrmMensaje(frmMSG, true);
					frmMSG.txtaMgs.setText("¿Estas seguro de Eliminar el Registro Nº: "+frmSala.spNumReg.getValue()+"?");
					frmMSG.setVisible(true);
					if(FrmMensaje.valor==1){
						eliminarSala();
					}
				}
			}else{
				JOptionPane.showMessageDialog(null, "Tipo de Operacion No existe \n Consulte con sistemas Musli-");
			}
		}else if(e.getSource()==frmSala.btnExportarExcel){
			exportarExcel();
		}else{
			JOptionPane.showMessageDialog(null, "Incomveniente Interno.\n Resuelva con Sistemas Musli-");
		}
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		int col = frmSala.tlbLista.columnAtPoint(e.getPoint());
		if(col==4){
			modoActualizar(e);
		}else if(col==5){
			modoEliminar(e);
			if(validaciones()==false){
				frmMSG = new FrmMensaje(frmMSG, true);
				frmMSG.txtaMgs.setText("¿Estas seguro de Eliminar el Registro Nº: "+frmSala.spNumReg.getValue()+"?");
				frmMSG.setVisible(true);
				if(FrmMensaje.valor==1){
					eliminarSala();
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
		if(arg0.getSource()==frmSala.txtaDescripcion){
			if(arg0.getKeyCode()==KeyEvent.VK_TAB){
				frmSala.btnOk.requestFocus();
			}
		}else if(arg0.getSource()==frmSala.btnOk){
			if(arg0.getKeyCode()==KeyEvent.VK_ENTER){
				if(validaciones()==false){
					frmSala.btnOk.doClick();
				}
			}
		}else if(arg0.getSource()==frmSala.btnCancel){
			if(arg0.getKeyCode()==KeyEvent.VK_ENTER){
				frmSala.btnCancel.doClick();
			}
		}
		if(arg0.getKeyCode()==KeyEvent.VK_ESCAPE){
			frmSala.dispose();
		}
	}
	@Override
	public void keyReleased(KeyEvent arg0) {}
	@Override
	public void keyTyped(KeyEvent arg0) {
		if(arg0.getSource()==frmSala.txtNombreSB){
			frmSala.txtNombreSB.addKeyListener(new KeyAdapter() {
				@Override //ESTE EVENTO PUEDE IR DENTRO DE OTRO EVENTO O FUERA
				public void keyReleased(KeyEvent e) {
					String cadena = (frmSala.txtNombreSB.getText());
					frmSala.txtNombreSB.setText(cadena);
					trs.setRowFilter(RowFilter.regexFilter("(?i)"+frmSala.txtNombreSB.getText(), 1));

					frmSala.lblTotalItems.setText("Total Items: " + frmSala.tlbLista.getRowCount());
				}
			});
			trs = new TableRowSorter<DefaultTableModel>(frmSala.dtm);
			frmSala.tlbLista.setRowSorter(trs);			
		}		
	}
	
	//OPERACIONES DE LISTAR, INSERTAR, MODIFICAR Y ELIMINAR
	private void listarSalas(){
		modoRegistro();
		listaSala = new ArrayList<SalaBean>();
		salaDao = new SalaDAO();
		frmSala.dtm = new DefaultTableModel();
		trs = new TableRowSorter<DefaultTableModel>(null);
	/*	frmSala.dtm.addColumn("Cod");*/
		//---INICIO - PARA LA IMAGEN EN LA CELDA
		frmSala.tlbLista.setDefaultRenderer(Object.class, new RenderizarCeldaImg());
		
		ImageIcon iilapiz = new ImageIcon("iconos/lapiz.png");
		Icon ilapiz = new ImageIcon(iilapiz.getImage().getScaledInstance(25, 25, Image.SCALE_DEFAULT));
		JLabel llapiz=new JLabel();
		llapiz.setIcon(ilapiz);
		ImageIcon iix = new ImageIcon("iconos/x.png");
		Icon ix = new ImageIcon(iix.getImage().getScaledInstance(24, 23, Image.SCALE_DEFAULT));
		JLabel lx=new JLabel();
		lx.setIcon(ix);
		///--- FIN 
		String cabecera[] = new String[] {"Código", "Nombre de Sala","Descripción", "Fecha de Registro"," M "," E "};
		listaSala = salaDao.listarTodasSalas();	
		frmSala.dtm = new DefaultTableModel(cabecera, 0);
		for (int i = 0; i < listaSala.size(); i++) {
			Object [] obj = {listaSala.get(i).getCodigoS(),listaSala.get(i).getNombreS(),listaSala.get(i).getDescripcionS(),
					listaSala.get(i).getFechaS(),llapiz,lx};
			frmSala.dtm.addRow(obj);
		}
		trs = new TableRowSorter<DefaultTableModel>(frmSala.dtm);
		frmSala.tlbLista.setRowSorter(trs);//PARA QUE CUANDO HAGA EL FILTRO SE RESTABLEZCA LA LISTA
		frmSala.tlbLista.setModel(frmSala.dtm);
		frmSala.tlbLista.getColumnModel().getColumn(0).setPreferredWidth(51);
		frmSala.tlbLista.getColumnModel().getColumn(1).setPreferredWidth(160);
		frmSala.tlbLista.getColumnModel().getColumn(2).setPreferredWidth(101);
		frmSala.tlbLista.getColumnModel().getColumn(3).setPreferredWidth(125);
		frmSala.tlbLista.getColumnModel().getColumn(4).setPreferredWidth(35);
		frmSala.tlbLista.getColumnModel().getColumn(5).setPreferredWidth(30);
		
		frmSala.lblTotalItems.setText("Total Items: " + frmSala.tlbLista.getRowCount());
	}
	
	private void regitrarSala(){
		salaBean = new SalaBean();
		salaDao = new SalaDAO();
		//SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		salaBean.setCodigoS((int) frmSala.spNumReg.getValue());
		salaBean.setNombreS(frmSala.txtNombreS.getText());
		salaBean.setDescripcionS(frmSala.txtaDescripcion.getText().trim());
		//salaBean.setFechaS(sdf.format(new Date()));
		int reg = salaDao.registrarSala(salaBean);
		if(reg>0){
			if(tipooperacion==1){
				JOptionPane.showMessageDialog(null, "Nº de Registro: " + frmSala.spNumReg.getValue() + ", Registrado Correctamente.!!!");
			}else{
				JOptionPane.showMessageDialog(null, "Sala: "+ nombresala +". Con Nº de Registro: " + codigosala + ", Recuperado.");
			}
			listarSalas();
		}else{
			JOptionPane.showMessageDialog(null, "Nº de Registro: " + frmSala.spNumReg.getValue() + ".\n No Registrado, con sulte con sistemas-Musli");
		}
	}

	private void modificarSala(){
		salaBean = new SalaBean();
		salaDao = new SalaDAO();
		salaBean.setNombreS(frmSala.txtNombreS.getText());
		salaBean.setDescripcionS(frmSala.txtaDescripcion.getText().trim());
		salaBean.setCodigoS((int) frmSala.spNumReg.getValue());
		int mod = salaDao.modificarSala(salaBean);
		if(mod>0){
			JOptionPane.showMessageDialog(null, "Nº de Registro: " + frmSala.spNumReg.getValue() + ", Modificado Correctamente.!!!");
			listarSalas();
		}else{
			JOptionPane.showMessageDialog(null, "Nº de Registro: " + frmSala.spNumReg.getValue() + ", No Modificado.\n Consulte con sistemas-Musli");
		}
	}
	
	private void eliminarSala(){
		salaBean = new SalaBean();
		salaDao = new SalaDAO();
		salaBean.setCodigoS((int) frmSala.spNumReg.getValue());
		int eli = salaDao.eliminarSala(salaBean);
		if(eli>0){
			JOptionPane.showMessageDialog(null, "Nº de Registro: " + frmSala.spNumReg.getValue() + ", Eliminado Correctamente.!!!");
			listarSalas();
		}else{
			JOptionPane.showMessageDialog(null, "Nº de Registro: " + frmSala.spNumReg.getValue() + ", No Eliminado, consulte con sistemas-Musli");
		}
	}
	//MANEJO DE CONTROLES
	private boolean validaciones(){
		boolean valor = false;
		codigosala=0;
		nombresala=null;
		int num = 0;
		String nom =null;
		num = (int) frmSala.spNumReg.getValue();
		nom = frmSala.txtNombreS.getText();
		
		if(modo==1 || modo==2){
			tipooperacion = 1;
			TableModel tm = frmSala.tlbLista.getModel();
			int filas = tm.getRowCount();
			for (int i = 0; i < filas ; i++) {
				codigosala = (int) tm.getValueAt(i, 0);
				nombresala = (String) tm.getValueAt(i, 1);
				if(nombresala.equalsIgnoreCase(nom)){
					if(codigosala!=num){
						JOptionPane.showMessageDialog(null,"La Sala: "+nom+". Ya existe en la BD. Con Nº Registro: "+codigosala);
						valor = true;
					}
				}
			}
		}
		
		if(modo==1||modo==2){
			tipooperacion = 1;
			salaDao = new SalaDAO();
			salaBean = new SalaBean();
			listaSala = new ArrayList<SalaBean>();
			listaSala=salaDao.verificarNombreSala(nom);
			if(listaSala.size()!=0){
				codigosala = listaSala.get(0).getCodigoS();
				nombresala = listaSala.get(0).getNombreS();
				if(codigosala!=num){
					if(modo==1){
						frmMSG = new FrmMensaje(frmMSG, true);
						frmMSG.txtaMgs.setText("Se ha encontrado en la BD el Registro N°: "+codigosala+". Perteneciente a: "+ nombresala);
						frmMSG.txtaMgs2.setText("¿Desea Recuperar la Sala de la BD?");
						frmMSG.setVisible(true);
						if(FrmMensaje.valor==1){
							valor = false;
						}else{
							valor = true;
						}
						tipooperacion = 2;
					}else{
						JOptionPane.showMessageDialog(null,"Se encontró en la BD el Registro N°: "+codigosala+
								  "\nPertenece a: "+ nombresala+"\nREGISTRE como nuevo la Sala con nombre: "+nombresala);
						valor=true;
					}
				}
			}
		}
		
		if(num==0){
			JOptionPane.showMessageDialog(null, "El 'Nº Registro' No puede ser vacío ó ( 0 )");
			valor = true;
		}else if(nom.equalsIgnoreCase("")){
			JOptionPane.showMessageDialog(null, "Debe Ingresar el Nombre de la Sala.");
			valor = true;
		}
		return valor;
	}
	
	private void modoRegistro(){
		modo = 1;
		limpiarControles();
		int max = 0;
	 	salaDao = new SalaDAO();
	 	max = salaDao.obtenerUltimoRegistro();	
		frmSala.spNumReg.setValue(max);
	 	frmSala.spNumReg.setEnabled(false);
	 	SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
	 	frmSala.txtFechaReg.setText(sdf.format(new Date()));
	 	frmSala.btnnuevo.setVisible(false);
	 	frmSala.btnOk.setText("REGISTRAR");
	 	ImageIcon iiguardar= new ImageIcon("iconos/guardar.png");
		Icon iguardar = new ImageIcon(iiguardar.getImage().getScaledInstance(30, frmSala.btnOk.getHeight(), Image.SCALE_DEFAULT));
		frmSala.btnOk.setIcon(iguardar);
		cambiotexto();
	}
	
	private void modoActualizar(MouseEvent e){
		modo = 2;
		limpiarControles();
		int filaSeleccion = frmSala.tlbLista.rowAtPoint(e.getPoint());
		frmSala.spNumReg.setValue(Integer.valueOf(frmSala.tlbLista.getValueAt(filaSeleccion, 0).toString()));
		frmSala.txtNombreS.setText(frmSala.tlbLista.getValueAt(filaSeleccion, 1).toString());
		frmSala.txtaDescripcion.setText(frmSala.tlbLista.getValueAt(filaSeleccion, 2).toString());
		frmSala.txtFechaReg.setText(frmSala.tlbLista.getValueAt(filaSeleccion, 3).toString());
		frmSala.btnnuevo.setVisible(true);
		frmSala.btnOk.setText("MODIFICAR");
		ImageIcon iimodificar= new ImageIcon("iconos/modificar.png");
		Icon imodificar = new ImageIcon(iimodificar.getImage().getScaledInstance(30, frmSala.btnOk.getHeight(), Image.SCALE_DEFAULT));
		frmSala.btnOk.setIcon(imodificar);
		cambiotexto();
	}
	
	private void modoEliminar(MouseEvent e){
		modo = 3;
		limpiarControles();
		int filaSeleccion = frmSala.tlbLista.rowAtPoint(e.getPoint());
		frmSala.spNumReg.setValue(Integer.valueOf(frmSala.tlbLista.getValueAt(filaSeleccion, 0).toString()));
		frmSala.txtNombreS.setText(frmSala.tlbLista.getValueAt(filaSeleccion, 1).toString());
		frmSala.txtaDescripcion.setText(frmSala.tlbLista.getValueAt(filaSeleccion, 2).toString());
		frmSala.txtFechaReg.setText(frmSala.tlbLista.getValueAt(filaSeleccion, 3).toString());
		frmSala.btnnuevo.setVisible(true);
		frmSala.btnOk.setText("ELIMINAR");
		ImageIcon iieliminar= new ImageIcon("iconos/eliminarbd.png");
		Icon ieliminar = new ImageIcon(iieliminar.getImage().getScaledInstance(30, frmSala.btnOk.getHeight(), Image.SCALE_DEFAULT));
		frmSala.btnOk.setIcon(ieliminar);
		cambiotexto();
	}
	
	private void cambiotexto(){
		if(modo==1){
			frmSala.lblRegistroDeSalas.setText("AGREGANDO NUEVA SALA CON REGISTRO Nº: "+frmSala.spNumReg.getValue());
		}else if(modo==2){
			frmSala.lblRegistroDeSalas.setText("MODIFICANDO EL REGISTRO Nº: "+frmSala.spNumReg.getValue()+".  | SALA: " + frmSala.txtNombreS.getText());
		}else if(modo==3){
			frmSala.lblRegistroDeSalas.setText("ELIMINANDO EL REGISTRO Nº: "+frmSala.spNumReg.getValue()+".  | SALA: " + frmSala.txtNombreS.getText());
		}
	}
	
	private void limpiarControles(){
		if(modo==1){
			frmSala.spNumReg.setEnabled(false);	
		}else if(modo==2){
			frmSala.spNumReg.setEnabled(true);
		}else if(modo==3){
			frmSala.spNumReg.setEnabled(true);
		}
		frmSala.txtFechaReg.setText(null);
		frmSala.txtNombreS.setText(null);
		frmSala.txtaDescripcion.setText(null);		
		if(modo==1){
			frmSala.txtNombreSB.setText(null);
		}
	}
	
	private void exportarExcel(){
		exportarExcel = new ExportarExcel();
		TableModel tm1 = frmSala.tlbLista.getModel();
		DefaultTableModel dtm1 = new DefaultTableModel(); 
		String cabecera[] = new String[] {"Código", "Nombre","Descripción", "Fecha Registro"};
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
