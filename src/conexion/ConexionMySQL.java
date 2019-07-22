package conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.swing.JOptionPane;

public class ConexionMySQL {
/*
	public Connection getConexion(){
		try {
			Class.forName("com.mysql.jdbc.Driver");
		 return	DriverManager.getConnection("jdbc:mysql://localhost:3306/incidenciadb", "root", "mysql");
		} catch (ClassNotFoundException ex) {
			Logger.getLogger(ConexionMySQL.class.getName()).log(Level.SEVERE, null, ex);
		} catch (SQLException ex) {
			Logger.getLogger(ConexionMySQL.class.getName()).log(Level.SEVERE, null, ex);
		}
		return null;
	}
*/

	private String user = "root";
	private String pwd = "mysql";
	private String db = "incidenciadb";
	private String url = "jdbc:mysql://localhost:3306/"+db+"?useTimezone=true&serverTimezone=UTC";
	Connection con = null;
	public ConexionMySQL() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection(url,user,pwd);
			if(con!=null){
				System.out.print("Conexion con exito: " + con);	
			}
		} catch (ClassNotFoundException e) {
			System.out.println("Ocurrio un ClassNotFoundException: " + e.getMessage());
		}catch (SQLException e) {
			System.out.print("Ocurrio un SQLException: " + e.getMessage()+ " AQUI :" + con);
			JOptionPane.showMessageDialog(null, "Error con la Conexion a La BD. \n"+e.getMessage());
			System.exit(1);
		}
	}
	public Connection getConnection(){
		return con;
	}

	public void desconectar(){
		try {
			con.close();
			con = null;
		} catch (Exception e) {
			System.out.println("DESCONECTAR :"+e.getMessage());
		}
	}
}
