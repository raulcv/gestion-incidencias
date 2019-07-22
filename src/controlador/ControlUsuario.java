package controlador;

import java.awt.Component;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;

import bean.UsuarioBean;
import dao.UsuarioDAO;
import vista.FrmMensaje;
import vista.FrmUsuario;

public class ControlUsuario<tipoIni> implements ActionListener, KeyListener{

	private FrmUsuario frmU = null;
	private UsuarioBean usuB=null;
	private UsuarioDAO usuD=null;
	private ArrayList<UsuarioBean> listaUsu=null;
	
	private FrmMensaje frmMSG = null;
	
	public int tipoIni =  0;
	private int tipoOpera = 0;
	private int limpiarUsu = 0;
	private String username=null;
	public ControlUsuario(FrmUsuario frmUsuario, UsuarioBean usuarioBean, UsuarioDAO usuarioDAO,tipoIni tip) {
		this.tipoIni = (int) tip;
		this.frmU = frmUsuario;
		this.usuB = usuarioBean;
		this.usuD = usuarioDAO;
		//ACTION LISTENER
		this.frmU.btnOkA.addActionListener(this);
		this.frmU.btnCancelA.addActionListener(this);
		this.frmU.chckbxEliminar.addActionListener(this);
		//KEY LISTENER
		this.frmU.btnOkA.addKeyListener(this);
		this.frmU.btnCancelA.addKeyListener(this);
		tipoInicio();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==frmU.chckbxEliminar){
			if(validarEliminar()==false){
				frmMSG = new FrmMensaje(frmMSG, true);
				frmMSG.txtaMgs.setText("¿Estas seguro de Eliminar tu Cuenta?");
				frmMSG.setVisible(true);
				if(FrmMensaje.valor==1){
					eliminarUsuario();
				}else{
					frmU.chckbxEliminar.setSelected(false);
				}
			}else{
				frmU.chckbxEliminar.setSelected(false);
			}
		}else if(e.getSource()==frmU.btnOkA){
			if(tipoOpera==1){
				if(validaciones()==false){
					registrarUsuario();
					frmU.dispose();
				}
			}else if(tipoOpera==2){
				if(validaciones()==false){
					frmMSG = new FrmMensaje(frmMSG, true);
					frmMSG.txtaMgs.setText("¿Estas seguro de Modificar tu Cuenta?");
					frmMSG.setVisible(true);
					if(FrmMensaje.valor==1){
						modificarUsuario();
					}
				}else{
					JOptionPane.showMessageDialog(null, "Operacion de Modificar No completado");
				}
			}else if(tipoOpera==3){
				if(validarModificarPassword()==false){
					actualizarPassword();	
				}
			}
		}else if(e.getSource()==frmU.btnCancelA){
			frmU.dispose();
		}
	}
	
	private void tipoInicio(){
		if(tipoIni==1){
			cargarPreguntas();
			modoRegistrar();
		}else if(tipoIni==2){
			cargarPreguntas();
			modoModificar();
		}else if(tipoIni==3){
			cargarPreguntas();
			modoOlvidePassword();
		}
	}
	
	private void cargarPreguntas(){
		frmU.cboPregunta.removeAllItems();
		//SI MODIFICAS NO CAMBIAR LAS PREGUNTAS, PERO SI PUEDE AÑADIR NUEVA
		frmU.cboPregunta.addItem("¿Cual es el nombre de la escuela en el que estudiaste?");
		frmU.cboPregunta.addItem("¿Como se llama tu primo o prima favorita?");
		frmU.cboPregunta.addItem("¿Como se llama tu abuelo por parte de tu madre?");
		frmU.cboPregunta.addItem("¿Cual es el nombre de tu mascota que tenias de niño?");
		frmU.cboPregunta.addItem("¿cual es el nombre de tu profesor favorito de la escuela?");
		frmU.cboPregunta.addItem("¿cual es el nombre de tu tio favorito?");
		frmU.cboPregunta.addItem("¿Como se llamaba tu mejor amigo o amiga de la infancia?");
	}

	private void registrarUsuario() {
		usuB = new UsuarioBean();
		usuD = new UsuarioDAO();
		
		usuB.setCodigoU((int) frmU.spNumRegU.getValue());
		usuB.setUsuarioU(frmU.txtUsuarioU.getText().trim());
		char[] pwd = frmU.pwdPassword.getPassword();
		String pass = new String(pwd);
		usuB.setPasswordU(pass);
		usuB.setNombreU(frmU.txtNombre.getText().trim());
		usuB.setApellidoU(frmU.txtApellidos.getText().trim());
		usuB.setPregunta((String) frmU.cboPregunta.getSelectedItem());
		char[] res = frmU.pwdRespuesta.getPassword();
		String respuesta = new String(res);
		usuB.setRespuesta(respuesta);
		int reg = usuD.registrarUsuario(usuB);
		if(reg>0){
			JOptionPane.showMessageDialog(null, "Se ha creado el Usuario: " + frmU.txtUsuarioU.getText()+ ", Correctamente.!!!");
			JOptionPane.showMessageDialog(null, "No olvide su Usuario: "+frmU.txtUsuarioU.getText()+"  \nNo olvide su pregunta y respuesta de seguridad \nLe servirá en caso que olvide su contraseña");
		}else{
			JOptionPane.showMessageDialog(null, "Nº de Registro: " + frmU.spNumRegU.getValue() + ".\nNo Grabado, consulte con sistemas-Musli");
		}		
	}
	
	private void modificarUsuario(){
		usuB = new UsuarioBean();
		usuD = new UsuarioDAO();
		
		usuB.setCodigoU((int) frmU.spNumRegU.getValue());
		usuB.setUsuarioU(frmU.txtUsuarioU.getText().trim());
		usuB.setNombreU(frmU.txtNombre.getText().trim());
		usuB.setApellidoU(frmU.txtApellidos.getText().trim());
		usuB.setPregunta((String) frmU.cboPregunta.getSelectedItem());
		char[] res = frmU.pwdRespuesta.getPassword();
		String respuesta = new String(res);
		usuB.setRespuesta(respuesta);
		int reg = usuD.modificarUsuario(usuB);
		if(reg>0){
			JOptionPane.showMessageDialog(null, "Usuario: " + frmU.txtUsuarioU.getText()+ ", Modificado Correctamente.!!!");
			JOptionPane.showMessageDialog(null, "No olvide su Usuario: "+frmU.txtUsuarioU.getText()+"  \nNo olvide su pregunta y respuesta de seguridad \nLe servirá en caso que olvide su contraseña");
			bloquearDesbloquearControl();
		}else{
			JOptionPane.showMessageDialog(null, "Nº de Registro: " + frmU.spNumRegU.getValue() + ".\nNo Grabado, consulte con sistemas-Musli");
		}		
		
	}
	
	private void eliminarUsuario(){
		usuB = new UsuarioBean();
		usuD = new UsuarioDAO();
		usuB.setCodigoU((int)frmU.spNumRegU.getValue());
		int eli = usuD.eliminarUsuario(usuB);
		if(eli>0){
			JOptionPane.showMessageDialog(null, "Tu cuenta perteneciente al Nº de Registro: "+frmU.spNumRegU.getValue() +" \nFue Eliminado Correctamente.!!!");
			frmU.dispose();
		}else{
			JOptionPane.showMessageDialog(null, "Tu cuenta perteneciente al Nº de Registro: "+frmU.spNumRegU.getValue() + ",.\nNo fue Eliminado, consulte con sistemas-Musli-ERROR DE SISTEMA");
		}
	}
	
	private void actualizarPassword(){
		usuB = new UsuarioBean();
		usuD = new UsuarioDAO();
		usuB.setCodigoU((int)frmU.spNumRegU.getValue());
		usuB.setUsuarioU(frmU.txtUsuarioU.getText());
		char[] pwd1 = frmU.pwdPassword.getPassword();
		String pass1 = new String(pwd1);
		usuB.setPasswordU(pass1);
		int act = usuD.modificarPasswordUsuario(usuB);
		if(act>0){
			JOptionPane.showMessageDialog(null, "Contraseña del Usuario: " + frmU.txtUsuarioU.getText() + ", fue restablecido Correctamente.!!!");
			JOptionPane.showMessageDialog(null, "No olvide su Usuario y contraseña. \nInicia Session ahora si desea.");
			frmU.dispose();
		}else{
			JOptionPane.showMessageDialog(null, "Contraseña del Usuario: " + frmU.txtUsuarioU.getText() + ",.\nNo Grabado, consulte con sistemas-Musli-ERROR DE SISTEMA");
		}
	}
	
	private boolean validaciones(){
		boolean valor = false;
		if(tipoIni==1){
			usuB = new UsuarioBean();
			usuD = new UsuarioDAO();
			listaUsu = new ArrayList<UsuarioBean>();
			String usu=null, pas1=null, pas2=null, nom=null, ape=null,pre=null,res1=null;
			char[] pass1=null,pass2=null,res2=null;
			usu = frmU.txtUsuarioU.getText();
			pass1 = frmU.pwdPassword.getPassword();
			pas1 = new String(pass1);
			pass2 = frmU.pwdRepetirPassword.getPassword();
			pas2 = new String(pass2);
			nom = frmU.txtNombre.getText();
			ape = frmU.txtApellidos.getText();
			pre = (String) frmU.cboPregunta.getSelectedItem();
			res2 = frmU.pwdRespuesta.getPassword();
			res1 = new String(res2);
		
			listaUsu=usuD.buscarUsuario(usu);
			if(usu.equals("")){
				JOptionPane.showMessageDialog(null, "Debe ingresar su nombre de Usuario");
				valor = true;
			}else if(listaUsu.size()>0){
				JOptionPane.showMessageDialog(null, "El Usuario: "+usu+" Ya existe, ingrese Otro.");
				valor = true;
			}else if(pas1.equals("")){
				JOptionPane.showMessageDialog(null, "Debe ingresar la contraseña");
				valor = true;
			}else if(pas2.equals("")){
				JOptionPane.showMessageDialog(null, "Debe ingresar la confirmacion de la contraseña");
				valor = true;
			}else if(!pas1.equals(pas2)){
				JOptionPane.showMessageDialog(null, "Las contraseñas no coenciden, Ingrese bien su Contraseña");
				valor = true;
			}else if(nom.equals("")){
				JOptionPane.showMessageDialog(null, "Debe ingresar sus Nombres");
				valor = true;
			}else if(ape.equals("")){
				JOptionPane.showMessageDialog(null, "Debe ingresar sus Apellidos");
				valor = true;
			}else if(pre.equals("")){
				JOptionPane.showMessageDialog(null, "Debe seleccionar una pregunta");
				valor = true;
			}else if(res1.equals("")){
				JOptionPane.showMessageDialog(null, "Debe responder a la pregunta seleccionada");
				valor = true;
			}	
		}else if(tipoIni==2){
			usuB = new UsuarioBean();
			usuD = new UsuarioDAO();
			listaUsu = new ArrayList<UsuarioBean>();
			String usu=null, pas1=null, nom=null, ape=null,pre=null,res1=null;
			char[] pass1=null,res2=null;
			usu = frmU.txtUsuarioU.getText();
			pass1 = frmU.pwdPassword.getPassword();
			pas1 = new String(pass1);
			nom = frmU.txtNombre.getText();
			ape = frmU.txtApellidos.getText();
			pre = (String) frmU.cboPregunta.getSelectedItem();
			res2 = frmU.pwdRespuesta.getPassword();
			res1 = new String(res2);
			if(!usu.equalsIgnoreCase(username)){
				listaUsu=usuD.buscarUsuario(usu);
			}			
			if(usu.equals("")){
				JOptionPane.showMessageDialog(null, "Debe ingresar su nombre de Usuario");
				valor = true;
			}else if(listaUsu.size()>0){
				JOptionPane.showMessageDialog(null, "El Usuario: "+usu+" Ya existe, Intente con otro nombre de Usuario");
				valor = true;
			}else if(nom.equals("")){
				JOptionPane.showMessageDialog(null, "Debe ingresar sus Nombres");
				valor = true;
			}else if(ape.equals("")){
				JOptionPane.showMessageDialog(null, "Debe ingresar sus Apellidos");
				valor = true;
			}else if(pre.equals("")){
				JOptionPane.showMessageDialog(null, "Debe seleccionar una pregunta");
				valor = true;
			}else if(res1.equals("")){
				JOptionPane.showMessageDialog(null, "Debe responder a la pregunta seleccionada");
				valor = true;
			}else{	
				Component cp = null;
				String tit = "Antes de Modificar ingrese su Contraseña";
				String mensaje="Contraseña:";
				String password = null;
				JPasswordField passwordField = new JPasswordField();
				Object[] obj = {mensaje+"\n", passwordField};
				Object stringArray[] = {"OK","Cancel"};
				if (JOptionPane.showOptionDialog(cp, obj,tit,JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,null, stringArray, obj) == JOptionPane.YES_OPTION){
					password = new String(passwordField.getPassword());
					if(pas1.equals(password)){
						valor = false;
					}else{
						JOptionPane.showMessageDialog(null, "Su contraseña no es esa, Intente de nuevo.");
						valor = true;
					}
				}else{
					valor = true;
				}
			}
		}		
		return valor;
	}
	
	private void modoRegistrar(){
		tipoOpera = 1;
		limpiarControles();
		int max = 0;
	 	usuD = new UsuarioDAO();
	 	max = usuD.obtenerUltimoRegistro();	
		frmU.spNumRegU.setValue(max);
		frmU.spNumRegU.setEnabled(false);
		
		frmU.txtEstadoU.setVisible(false);
		frmU.btnOkA.setText("REGISTRAR");
	 	ImageIcon iiguardar= new ImageIcon("iconos/guardar.png");
		Icon iguardar = new ImageIcon(iiguardar.getImage().getScaledInstance(30, frmU.btnOkA.getHeight(), Image.SCALE_DEFAULT));
		frmU.btnOkA.setIcon(iguardar);
	}
	
	private void modoModificar(){
		tipoOpera = 2;
		limpiarControles();
		usuD = new UsuarioDAO();
		listaUsu = new ArrayList<UsuarioBean>();
		listaUsu = usuD.buscarUsuario(frmU.txtUsuarioU.getText());
		if(listaUsu.size()>0){
			frmU.lblBienvenida.setText("Hola "+listaUsu.get(0).getNombreU()+". Usted se ecuentra en la seccion 'configurar cuenta'");
			frmU.txtEstadoU.setText(listaUsu.get(0).getEstado());
			frmU.spNumRegU.setValue(listaUsu.get(0).getCodigoU());
			frmU.txtUsuarioU.setText(listaUsu.get(0).getUsuarioU());
			frmU.txtFechaRegU.setText(listaUsu.get(0).getFechareg());
			frmU.txtFechaUltMod.setText(listaUsu.get(0).getFechaUltMod());
			frmU.pwdPassword.setText(listaUsu.get(0).getPasswordU());
			frmU.pwdRepetirPassword.setText(listaUsu.get(0).getPasswordU());
			frmU.txtNombre.setText(listaUsu.get(0).getNombreU());
			frmU.txtApellidos.setText(listaUsu.get(0).getApellidoU());
			frmU.cboPregunta.setSelectedItem(listaUsu.get(0).getPregunta());
			frmU.pwdRespuesta.setText(listaUsu.get(0).getRespuesta());
			username = listaUsu.get(0).getUsuarioU();
		}
		frmU.spNumRegU.setEnabled(false);
		frmU.txtEstadoU.setEditable(false);
		frmU.pwdPassword.setEnabled(false);
		frmU.pwdRepetirPassword.setEnabled(false);
		frmU.btnOkA.setText("MODIFICAR");
	 	ImageIcon iimod= new ImageIcon("iconos/modificar.png");
		Icon imod = new ImageIcon(iimod.getImage().getScaledInstance(30, frmU.btnOkA.getHeight(), Image.SCALE_DEFAULT));
		frmU.btnOkA.setIcon(imod);
	}
	
	private void modoOlvidePassword(){
		tipoOpera = 3;
		limpiarControles();
		usuD = new UsuarioDAO();
		listaUsu = new ArrayList<UsuarioBean>();
		listaUsu = usuD.buscarUsuario(frmU.txtUsuarioU.getText());
		if(listaUsu.size()>0){
			frmU.lblBienvenida.setText("Hola "+listaUsu.get(0).getNombreU()+". Para restablecer su cuenta, debe crear nueva contraseña.");
			frmU.txtEstadoU.setText(listaUsu.get(0).getEstado());
			frmU.spNumRegU.setValue(listaUsu.get(0).getCodigoU());
			frmU.txtUsuarioU.setText(listaUsu.get(0).getUsuarioU());
			frmU.txtFechaRegU.setText(listaUsu.get(0).getFechareg());
			frmU.txtFechaUltMod.setText(listaUsu.get(0).getFechaUltMod());
			//frmU.pwdContraseña.setText("contraseña");
			//frmU.pwdRepetirConstraseña.setText("contraseña");
			frmU.txtNombre.setText(listaUsu.get(0).getNombreU());
			frmU.txtApellidos.setText(listaUsu.get(0).getApellidoU());
			frmU.cboPregunta.setSelectedItem(listaUsu.get(0).getPregunta());
			frmU.pwdRespuesta.setText(listaUsu.get(0).getRespuesta());
		}
		frmU.spNumRegU.setEnabled(false);
		frmU.chckbxEliminar.setVisible(false);
		frmU.txtEstadoU.setEnabled(false);
		frmU.txtUsuarioU.setEnabled(false);
		frmU.txtNombre.setEnabled(false);
		frmU.txtApellidos.setEnabled(false);
		frmU.cboPregunta.setEnabled(false);
		frmU.pwdRespuesta.setEnabled(false);
		
		frmU.btnOkA.setText("MODIFICAR");
	 	ImageIcon iimod= new ImageIcon("iconos/modificar.png");
		Icon imod = new ImageIcon(iimod.getImage().getScaledInstance(30, frmU.btnOkA.getHeight(), Image.SCALE_DEFAULT));
		frmU.btnOkA.setIcon(imod);
	}
	
	private boolean validarModificarPassword(){
		boolean val = false;
		String pas1=null, pas2=null;
		char[] pass1=null,pass2=null;
		pass1 = frmU.pwdPassword.getPassword();
		pas1 = new String(pass1);
		pass2 = frmU.pwdRepetirPassword.getPassword();
		pas2 = new String(pass2);
		
		if(pas1.equals("")){
			JOptionPane.showMessageDialog(null, "Debe ingresar la contraseña");
			val = true;
		}else if(pas2.equals("")){
			JOptionPane.showMessageDialog(null, "Debe ingresar la confirmacion de la contraseña");
			val = true;
		}else if(!pas1.equals(pas2)){
			JOptionPane.showMessageDialog(null, "Las contraseñas no coenciden, Ingrese bien su Contraseña");
			val = true;
		}
		return val;
	}
	
	private boolean validarEliminar(){
		boolean val3 = false;
		int num = (int) frmU.spNumRegU.getValue();
		if(num<0){
			JOptionPane.showMessageDialog(null, "Tu cuenta no pudo ser eliminado porque el Nº de Registro: "+num+" \nNo pudo ser edintificado. "
					+ "\nConsulte con sistemas para Resolver el inconveniente.");
			val3 = true;
		}else{		
			char[] pwd = frmU.pwdPassword.getPassword();
			String pass = new String(pwd);
			usuB.setPasswordU(pass);
			Component cp = null;
			String tit = "Antes de Eliminar su cuenta ingrese su Contraseña";
			String mensaje="Contraseña:";
			String password = null;
			JPasswordField passwordField = new JPasswordField();
			Object[] obj = {mensaje+"\n", passwordField};
			Object stringArray[] = {"OK","Cancel"};
			if (JOptionPane.showOptionDialog(cp, obj,tit,JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,null, stringArray, obj) == JOptionPane.YES_OPTION){
				password = new String(passwordField.getPassword());
				if(pass.equals(password)){
					val3 = false;
				}else{
					JOptionPane.showMessageDialog(null, "Su contraseña no es esa, Intente de nuevo.");
					val3 = true;
				}
			}else{
				val3 = true;
			}
		}
		return val3;
	}
	
	private void limpiarControles(){
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		if(tipoIni==2 || tipoIni==3){
			limpiarUsu = 1;
		}else{
			limpiarUsu = 0;
		}
		frmU.spNumRegU.setValue(0);
		frmU.chckbxEliminar.setSelected(false);
		frmU.txtEstadoU.setText(null);
		if(limpiarUsu!=1){
			frmU.txtUsuarioU.setText(null);
			frmU.chckbxEliminar.setVisible(false);
		}
		frmU.txtFechaRegU.setText(sdf.format(new Date()));
		frmU.txtFechaUltMod.setText(sdf.format(new Date()));
		frmU.pwdPassword.setText(null);
		frmU.pwdRepetirPassword.setText(null);
		frmU.txtNombre.setText(null);
		frmU.txtApellidos.setText(null);
		if(frmU.cboPregunta.getItemCount()>0){
			frmU.cboPregunta.setSelectedItem(0);
		}
		frmU.pwdRespuesta.setText(null);
	}
	
	private void bloquearDesbloquearControl(){
		frmU.lblBienvenida.setText("Hola "+frmU.txtNombre.getText()+". Usted a actualizado sus Datos");
		frmU.spNumRegU.setEnabled(false);
		frmU.txtEstadoU.setEnabled(false);
		frmU.txtUsuarioU.setEnabled(false);
		frmU.pwdPassword.setEnabled(false);
		frmU.pwdRepetirPassword.setEnabled(false);
		frmU.txtNombre.setEnabled(false);
		frmU.txtApellidos.setEnabled(false);
		frmU.cboPregunta.setEnabled(false);
		frmU.pwdRespuesta.setEnabled(false);
		
		frmU.btnOkA.setEnabled(false);
		
		frmU.btnOkA.setText("MODIFICAR");
	 	ImageIcon iimod= new ImageIcon("iconos/modificar.png");
		Icon imod = new ImageIcon(iimod.getImage().getScaledInstance(30, frmU.btnOkA.getHeight(), Image.SCALE_DEFAULT));
		frmU.btnOkA.setIcon(imod);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getSource()==frmU.btnOkA){
			if(e.getKeyCode()==KeyEvent.VK_ENTER){
				frmU.btnOkA.doClick();
			}
		}else if(e.getSource()==frmU.btnCancelA){
			if(e.getKeyCode()==KeyEvent.VK_ENTER){
				frmU.btnCancelA.doClick();
			}
		}	
		if(e.getKeyCode()==KeyEvent.VK_ESCAPE){
			System.exit(0);
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	
}
