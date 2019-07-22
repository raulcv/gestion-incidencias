package controlador;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JOptionPane;
import javax.swing.JPasswordField;

import bean.IncidenciaBean;
import bean.UsuarioBean;
import dao.IncidenciaDAO;
import dao.UsuarioDAO;
import vista.FrmIncidencia;
import vista.FrmLogin;
import vista.FrmUsuario;

public class ControlLogin implements ActionListener,KeyListener{
	
	private FrmLogin frmLogin=null;
	private UsuarioBean usuarioB = null;
	private UsuarioDAO usuarioD = null;
	private ArrayList<UsuarioBean> listaUsuarios=null;
	private FrmUsuario frmUsuario = null;
	private ControlUsuario cntrlUsu = null;
	
	private FrmIncidencia frmI = null;
	private IncidenciaBean incBean = null;
	private IncidenciaDAO incDao = null;
	ControlIncidencia cntrlInc = null;
	
	public int codigoUsu=0;
	public String usuario=null;

	public ControlLogin(FrmLogin frmLogin, UsuarioBean usuarioBean, UsuarioDAO usuarioDAO) {
		this.frmLogin = frmLogin;
		//ACTION LISTENER
		this.frmLogin.btnConfigurarCuenta.addActionListener(this);
		this.frmLogin.btnCrearCuenta.addActionListener(this);
		this.frmLogin.btnIniciarSession.addActionListener(this);
		this.frmLogin.btnOlvidePassword.addActionListener(this);
		
		this.frmLogin.btnConfigurarCuenta.addKeyListener(this);
		this.frmLogin.btnCrearCuenta.addKeyListener(this);
		this.frmLogin.btnIniciarSession.addKeyListener(this);
		this.frmLogin.btnOlvidePassword.addKeyListener(this);

	}


	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==frmLogin.btnCrearCuenta){
			frmUsuario = new FrmUsuario(frmUsuario, true);
			cntrlUsu = new ControlUsuario(frmUsuario, usuarioB, usuarioD,1);
			frmUsuario.setVisible(true);
		}else if(e.getSource()==frmLogin.btnIniciarSession){
			if(logueo()==true){
				frmI = new FrmIncidencia();
				cntrlInc = new ControlIncidencia(frmI, incBean,incDao);
				frmI.txtIdUsuario.setText(Integer.toString(codigoUsu));
				frmI.txtUsuario.setText("usuario: "+usuario);
				cntrlInc.codigoUsu = codigoUsu;
				cntrlInc.usuario = usuario;
				if(listaUsuarios.size()>0){
					SimpleDateFormat sdf = new SimpleDateFormat("'Hoy es ' EEEEEEEEE dd 'de' MMMMM 'del ' yyyy");
					String fecha = sdf.format(new Date());
					frmI.setTitle("REGISTRO DE INCIDENCIAS                              ."
							+ " BIENVENIDO  "+listaUsuarios.get(0).getNombreU()+", "+listaUsuarios.get(0).getApellidoU()
							+ ".  "+fecha);
				}				
				frmI.setVisible(true);
				frmLogin.dispose();
			}
		}else if(e.getSource()==frmLogin.btnConfigurarCuenta){
			if(logueo2()==true){
				frmUsuario = new FrmUsuario(frmUsuario, true);
				frmUsuario.txtUsuarioU.setText(usuario);
				cntrlUsu = new ControlUsuario(frmUsuario, usuarioB, usuarioD,2);
				cntrlUsu.tipoIni = 2;
				frmUsuario.setVisible(true);
			}
		}else if(e.getSource()==frmLogin.btnOlvidePassword){
			if(buscarUsuario()==true){
				frmUsuario = new FrmUsuario(frmUsuario, true);
				frmUsuario.txtUsuarioU.setText(usuario);
				cntrlUsu = new ControlUsuario(frmUsuario, usuarioB, usuarioD,3);
				cntrlUsu.tipoIni = 3;
				frmUsuario.setVisible(true);
				//cargarUsuario();
			}else{
				JOptionPane.showMessageDialog(null, "Secuencia de Recuperacion de contraseña \nNo culminada.");
			}
		}
	}
	
	private Boolean logueo(){
		boolean val = false;
		String user =frmLogin.txtUser.getText();
		char[] pwd = frmLogin.pwdPassword.getPassword();
		String pass = new String(pwd);	
		if(user.equals("")){
			JOptionPane.showMessageDialog(null, "Debe ingresar su Usuario");
			val = false;
		}else if(pass.equals("")){
			JOptionPane.showMessageDialog(null, "Debe ingresar su Contraseña");
			val = false;
		}else{
			listaUsuarios = new ArrayList<UsuarioBean>();
			usuarioD = new UsuarioDAO();
			listaUsuarios=usuarioD.buscarUsuario(user);
			if(listaUsuarios.size()>0){
				usuarioD = new UsuarioDAO();
				listaUsuarios = new ArrayList<UsuarioBean>();
				listaUsuarios = usuarioD.obtenerUsuario(user, pass);
				if(listaUsuarios.size()>0){
					codigoUsu = listaUsuarios.get(0).getCodigoU();
					usuario = listaUsuarios.get(0).getUsuarioU();
					val = true;
				}else{
					JOptionPane.showMessageDialog(null, "La Contraseña es incorrecto. Vuelva a Intentar");
					val = false;
				}
			}else{
				JOptionPane.showMessageDialog(null, "El Usuario: '"+user+"'  No existe, Intente otra vez.");
				val = false;
			}
		}
		return val;
	}
	
	private Boolean buscarUsuario(){
		boolean ExisteUsu = false;		
		String pregunta = null,respuesta=null;
		usuarioD = new UsuarioDAO();
		listaUsuarios = new ArrayList<UsuarioBean>();
		String usu = JOptionPane.showInputDialog(null, "Buscar mi Usuario", "Escriba su Usuario");
		if(!usu.equalsIgnoreCase("")){
			if(!usu.equals("Escriba su Usuario")){
				listaUsuarios=usuarioD.buscarUsuario(usu);
				if(listaUsuarios.size()>0){
					usuario = listaUsuarios.get(0).getUsuarioU();
					pregunta = listaUsuarios.get(0).getPregunta();
					respuesta = listaUsuarios.get(0).getRespuesta();
					Component th = null;
					String tit="Escriba la respuesta a su pregunta de seguridad";
					String mensaje=pregunta;
					String rpta = null;
					JPasswordField passwordField = new JPasswordField();
					Object[] obj = {mensaje+"\n\n", passwordField};
					Object stringArray[] = {"OK","Cancel"};
					if (JOptionPane.showOptionDialog(th, obj,tit,JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,null, stringArray, obj) == JOptionPane.YES_OPTION){
						rpta = new String(passwordField.getPassword());
						if(respuesta.equals(rpta)){
							ExisteUsu = true;
						}else{
							JOptionPane.showMessageDialog(null, "La respuesta a la pregunta es Incorrecta.");
						}
					}else{
						ExisteUsu = false;
					}
				//String res = JOptionPane.showInputDialog(null, pregunta, "Escriba su respuesta");
				}else{
					JOptionPane.showMessageDialog(null, "El Usuario: '"+usu+"'  No existe, Intente otra vez.");
					ExisteUsu = false;
				}
			}
		}
		return ExisteUsu;
	}	
	
	private Boolean logueo2(){
		boolean val2 = false;
		String contraseña = null;
		usuarioB = new UsuarioBean(); //NO SE USA AQUI, PERO SIRVE EN CONTROL USUARIO
		usuarioD = new UsuarioDAO();
		listaUsuarios = new ArrayList<UsuarioBean>();
		String usu = JOptionPane.showInputDialog(null, "Ingrese su Usuario", "Escriba su Usuario");
		if(!usu.equalsIgnoreCase("")){
			if(!usu.equalsIgnoreCase("Escriba su Usuario")){
				listaUsuarios=usuarioD.buscarUsuario(usu);
				if(listaUsuarios.size()>0){
					usuario = listaUsuarios.get(0).getUsuarioU();
					contraseña = listaUsuarios.get(0).getPasswordU();
					Component th = null;
					String tit="Escriba su Contraseña";
					String mensaje=usuario;
					String password = null;
					JPasswordField passwordField = new JPasswordField();
					Object[] obj = {"Usuario: "+mensaje+"\n","Contraseña: ", passwordField};
					Object stringArray[] = {"Aceptar","Cancelar"};
					if (JOptionPane.showOptionDialog(th, obj,tit,JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,null, stringArray, obj) == JOptionPane.YES_OPTION){
						password = new String(passwordField.getPassword());
						if(contraseña.equals(password)){
							val2 = true;
						}else{
							JOptionPane.showMessageDialog(null, "La contraseña es Incorrecta, Vuelva a Intentar.");
							val2 = false;
						}
					}else{
						val2 = false;
					}
				}else{
					JOptionPane.showMessageDialog(null, "El Usuario: '"+usu+"'  No existe, Intente de Nuevo.");
					val2 = false;
				}
			}
		}
		return val2;
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getSource()==frmLogin.btnConfigurarCuenta){
			if(e.getKeyCode()==KeyEvent.VK_ENTER){
				if(logueo2()==true){
					frmUsuario = new FrmUsuario(frmUsuario, true);
					frmUsuario.txtUsuarioU.setText(usuario);
					cntrlUsu = new ControlUsuario(frmUsuario, usuarioB, usuarioD,2);
					cntrlUsu.tipoIni = 2;
					frmUsuario.setVisible(true);
				}
			}
		}else if(e.getSource()==frmLogin.btnCrearCuenta){
			if(e.getKeyCode()==KeyEvent.VK_ENTER){
				frmUsuario = new FrmUsuario(frmUsuario, true);
				cntrlUsu = new ControlUsuario(frmUsuario, usuarioB, usuarioD,1);
				frmUsuario.setVisible(true);
			}
		}else if(e.getSource()==frmLogin.btnIniciarSession){
			if(e.getKeyCode()==KeyEvent.VK_ENTER){
				frmLogin.btnIniciarSession.doClick();
			}
		}else if(e.getSource()==frmLogin.btnOlvidePassword){
			if(e.getKeyCode()==KeyEvent.VK_ENTER){
				if(buscarUsuario()==true){
					frmUsuario = new FrmUsuario(frmUsuario, true);
					frmUsuario.txtUsuarioU.setText(usuario);
					cntrlUsu = new ControlUsuario(frmUsuario, usuarioB, usuarioD,3);
					cntrlUsu.tipoIni = 3;
					frmUsuario.setVisible(true);
					//cargarUsuario();
				}else{
					JOptionPane.showMessageDialog(null, "Secuencia de Recuperacion de contraseña \nNo culminada.");
				}
			}
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
