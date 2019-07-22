package dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import bean.UsuarioBean;
import conexion.ConexionMySQL;

public class UsuarioDAO {

	ConexionMySQL conexionMySQL = new ConexionMySQL();
	UsuarioBean usuarioBean;
	ArrayList<UsuarioBean> listaUsuario;
	
	public ArrayList<UsuarioBean> obtenerUsuario(String user, String pwd){
		Connection con = null;
		/*PreparedStatement pst = null; CUANDO NO ES UN PROCEDURE*/
		CallableStatement cst = null;
		ResultSet rs = null;
		con = conexionMySQL.getConnection();
		listaUsuario = new ArrayList<UsuarioBean>();
		String sql = "{call sp_usuario(?,?,?,?,?,?,?,?)}";
		try {
			if(con!=null){
			    cst = (CallableStatement) con.prepareCall(sql);
				cst.setInt(1, 0);
				cst.setString(2, user);
				cst.setString(3, pwd);
				cst.setString(4, null);
				cst.setString(5, null);
				cst.setString(6, null);
				cst.setString(7, null);
				cst.setInt(8, 5);
				rs =cst.executeQuery();
				while (rs.next()==true) {
					usuarioBean = new UsuarioBean();
					usuarioBean.setCodigoU(rs.getInt("codigo"));
					usuarioBean.setUsuarioU(rs.getString("usuario"));
					usuarioBean.setPasswordU(rs.getString("password"));
					usuarioBean.setNombreU(rs.getString("nombre"));
					usuarioBean.setApellidoU(rs.getString("apellido"));
					usuarioBean.setPregunta(rs.getString("pregunta"));
					usuarioBean.setRespuesta(rs.getString("respuesta"));
					usuarioBean.setFechareg(rs.getString("fecharegistro"));
					usuarioBean.setFechaUltMod(rs.getString("fecultmod"));
					usuarioBean.setEstado(rs.getString("estado"));
					listaUsuario.add(usuarioBean);
				}
				con.close();
				cst.close();
				conexionMySQL.desconectar();
			}
		} catch (SQLException e) {
			System.out.println(" Error con la consulta en la BD: " + e.getMessage());
		}	
		return listaUsuario;
	}
	
	public ArrayList<UsuarioBean> buscarUsuario(String usu){
		Connection con = null;
		CallableStatement cst = null;
		ResultSet rs = null;
		listaUsuario = new ArrayList<UsuarioBean>();
		con = conexionMySQL.getConnection();
		String sql = "{call sp_usuario(?,?,?,?,?,?,?,?)}";
		try {
			if(con!=null){
				cst = (CallableStatement) con.prepareCall(sql);
				cst.setInt(1, 0);
				cst.setString(2, usu);
				cst.setString(3, null);
				cst.setString(4, null);
				cst.setString(5, null);
				cst.setString(6, null);
				cst.setString(7, null);
				cst.setInt(8, 7);
				rs =cst.executeQuery();
				if(rs.next()==true){
					usuarioBean = new UsuarioBean();
					usuarioBean.setCodigoU(rs.getInt("codigo"));
					usuarioBean.setUsuarioU(rs.getString("usuario"));
					usuarioBean.setPasswordU(rs.getString("password"));
					usuarioBean.setNombreU(rs.getString("nombre"));
					usuarioBean.setApellidoU(rs.getString("apellido"));
					usuarioBean.setPregunta(rs.getString("pregunta"));
					usuarioBean.setRespuesta(rs.getString("respuesta"));
					usuarioBean.setFechareg(rs.getString("fecharegistro"));
					usuarioBean.setFechaUltMod(rs.getString("fecultmod"));
					usuarioBean.setEstado(rs.getString("estado"));
					listaUsuario.add(usuarioBean);
				}
				con.close();
				cst.close();
				conexionMySQL.desconectar();
			}
		} catch (SQLException e) {
			System.out.println("Error con la consulta a la BD: " + e.getMessage());
		}	
		return listaUsuario;
	}

	public int obtenerUltimoRegistro(){
		int siguienteid = 0;
		Connection con = null;
		CallableStatement cst = null;
		ResultSet rs = null;
		con = conexionMySQL.getConnection();
		String sql = "{call sp_usuario(?,?,?,?,?,?,?,?)}";
		try {
			if(con!=null){
				cst = (CallableStatement) con.prepareCall(sql);
				cst.setInt(1, 0);
				cst.setString(2, null);
				cst.setString(3, null);
				cst.setString(4, null);
				cst.setString(5, null);
				cst.setString(6, null);
				cst.setString(7, null);
				cst.setInt(8, 6);
				rs =cst.executeQuery();
				if(rs.next()==true){
					usuarioBean = new UsuarioBean();
					usuarioBean.setCodigoU(rs.getInt("nextid"));
					siguienteid = usuarioBean.getCodigoU();
				}
				con.close();
				cst.close();
				conexionMySQL.desconectar();
			}
		} catch (SQLException e) {
			System.out.println("Error con la consulta a la BD: " + e.getMessage());
		}	
		return siguienteid;
	}

	public int registrarUsuario( UsuarioBean usu){
		int reg = 0;
		//PreparedStatement pst = null;
		CallableStatement cst = null;
		Connection con = null;
		con =conexionMySQL.getConnection();
		String sql = "{call sp_usuario(?,?,?,?,?,?,?,?)}";
		try{
			cst = (CallableStatement) con.prepareCall(sql);
			cst.setInt(1, usu.getCodigoU());
			cst.setString(2, usu.getUsuarioU());
			cst.setString(3, usu.getPasswordU());
			cst.setString(4, usu.getNombreU());
			cst.setString(5, usu.getApellidoU());
			cst.setString(6, usu.getPregunta());
			cst.setString(7, usu.getRespuesta());
			cst.setInt(8, 1);
			reg = cst.executeUpdate();
			if(reg>0){
				System.out.println(" Usuario Registrado Correctamente: "+reg);
			}
		}catch(SQLException e){
			System.err.println(" Error al insertar Usuario: "+e);
			return reg;
		}finally {
			try {
				con.close();
				cst.close();
			} catch (SQLException e) {
				System.err.println(e);
			}
		}
		return reg;
	}

	public int modificarUsuario( UsuarioBean usu){
		int mod = 0;
		CallableStatement cst = null;
		Connection con = null;
		con =conexionMySQL.getConnection();
		String sql = "{call sp_usuario(?,?,?,?,?,?,?,?)}";
		try{
			cst = (CallableStatement) con.prepareCall(sql);
			cst.setInt(1, usu.getCodigoU());
			cst.setString(2, usu.getUsuarioU());
			cst.setString(3, null);
			cst.setString(4, usu.getNombreU());
			cst.setString(5, usu.getApellidoU());
			cst.setString(6, usu.getPregunta());
			cst.setString(7, usu.getRespuesta());
			cst.setInt(8, 2);
			mod = cst.executeUpdate();
			if(mod>0){
				System.out.println(" Usuario Modificado Correctamente: "+mod);
			}
		}catch(SQLException e){
			System.err.println(" Error al Modificar Usuario: "+e);
			return mod;
		}finally {
			try {
				con.close();
				cst.close();
			} catch (SQLException e) {
				System.err.println(e);
			}
		}
		return mod;
	}
	
	public int modificarPasswordUsuario( UsuarioBean usu){
		int mod = 0;
		CallableStatement cst = null;
		Connection con = null;
		con =conexionMySQL.getConnection();
		String sql = "{call sp_usuario(?,?,?,?,?,?,?,?)}";
		try{
			cst = (CallableStatement) con.prepareCall(sql);
			cst.setInt(1, usu.getCodigoU());
			cst.setString(2, usu.getUsuarioU());
			cst.setString(3, usu.getPasswordU());
			cst.setString(4, null);
			cst.setString(5, null);
			cst.setString(6, null);
			cst.setString(7, null);
			cst.setInt(8, 3);
			mod = cst.executeUpdate();
			if(mod>0){
				System.out.println(" Contraseña de Usuario Modificado Correctamente: "+mod);
			}
		}catch(SQLException e){
			System.err.println(" Error al Modificar Contraseña de Usuario: "+e);
			return mod;
		}finally {
			try {
				con.close();
				cst.close();
			} catch (SQLException e) {
				System.err.println(e);
			}
		}
		return mod;
	}
	
	public int eliminarUsuario( UsuarioBean usu){
		int eli = 0;
		CallableStatement cst = null;
		Connection con = null;
		con =conexionMySQL.getConnection();
		String sql = "{call sp_usuario(?,?,?,?,?,?,?,?)}";
		try{
			cst = (CallableStatement) con.prepareCall(sql);
			cst.setInt(1, usu.getCodigoU());
			cst.setString(2, null);
			cst.setString(3, null);
			cst.setString(4, null);
			cst.setString(5, null);
			cst.setString(6, null);
			cst.setString(7, null);
			cst.setInt(8, 4);
			eli = cst.executeUpdate();
			if(eli>0){
				System.out.println(" Usuario eliminado Correctamente: "+eli);
			}
		}catch(SQLException e){
			System.err.println(" Error al Eliminar Usuario: "+e);
			return eli;
		}finally {
			try {
				con.close();
				cst.close();
			} catch (SQLException e) {
				System.err.println(e);
			}
		}
		return eli;
	}
	
}
