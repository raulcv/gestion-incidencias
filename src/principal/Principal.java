package principal;

import java.io.IOException;
import java.net.ServerSocket;

import javax.swing.JOptionPane;

import bean.AreaBean;
import bean.IncidenciaBean;
import bean.SalaBean;
import bean.UsuarioBean;
import controlador.ControlArea;
import controlador.ControlIncidencia;
import controlador.ControlSala;
import controlador.ControlLogin;
import dao.AreaDAO;
import dao.IncidenciaDAO;
import dao.SalaDAO;
import dao.UsuarioDAO;
import vista.FrmArea;
import vista.FrmIncidencia;
import vista.FrmLogin;
import vista.FrmSala;

public class Principal {

	private static ServerSocket serverSocket;
	
	public static void main(String[] args) {

		FrmLogin frmLogin = new FrmLogin();
		try {
			UsuarioBean usuarioBean =null;
			UsuarioDAO usuarioDAO = null;
			ControlLogin crtl = new ControlLogin(frmLogin, usuarioBean, usuarioDAO);
			serverSocket = new ServerSocket(1995);
			frmLogin.setVisible(true);
		} catch (IOException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "El Aplicativo ya se encuentra Abierto");
			System.exit(-1);
		}
		//PARA INICIAR AREA
	/*	FrmArea area = null;
		 area= new  FrmArea(area, true);
		 AreaBean areaB = new AreaBean();
		 AreaDAO areaD = new AreaDAO();
		 ControlArea crtol = new ControlArea(area, areaB, areaD);
		 area.setVisible(true);*/

	//	FrmIncidencia frmIncidencia = new FrmIncidencia();
	//	IncidenciaBean incBean = new IncidenciaBean();
	//	IncidenciaDAO incDao = new IncidenciaDAO();
		
		//SalaBean salaB = new SalaBean();
		//SalaDAO salaD = new SalaDAO();
		//ControlSala crtlSala = new ControlSala(salaB, salaD, frmIncidencia);
		
	//	ControlIncidencia crtlinc = new ControlIncidencia(frmIncidencia, incDao, incBean);
	//	frmIncidencia.setVisible(true); 	
			
		
		}
	}
