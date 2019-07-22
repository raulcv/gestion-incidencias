package dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import bean.PaisBean;
import conexion.ConexionMySQL;

public class PaisDAO {
	ConexionMySQL conexionMySQL = new ConexionMySQL();
	PaisBean paisBean;
	ArrayList<PaisBean> listaPais;
	
	public ArrayList<PaisBean> listaP(){
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		listaPais = new ArrayList<PaisBean>();
		con = conexionMySQL.getConnection();
		String sql = "select idpais as codigo,nompais as nombre from tb_pais where estpais = 1 order by idpais asc";
		try {
			if(con!=null){
				pst = con.prepareStatement(sql);
				rs =pst.executeQuery();				
				while (rs.next()==true) {
					paisBean = new PaisBean();
					paisBean.setCodigoPA(rs.getInt("codigo"));
					paisBean.setNombrePA(rs.getString("nombre"));
					listaPais.add(paisBean);
				}
			}
		} catch (SQLException e) {
			System.out.println("Error con la consulta BD: " + e.getMessage());
		}finally {
			try {
				con.close();
				pst.close();
				conexionMySQL.desconectar();
			} catch (SQLException e) {
				System.err.println(e);
			}
		}	
		return listaPais;
	}
	
	//------------------------------PARA VENTANA DE SALAS-------------------
	public ArrayList<PaisBean> listarTodosPaises(){
		Connection con = null;
		CallableStatement cst = null;
		ResultSet rs = null;
		con = conexionMySQL.getConnection();
		paisBean = new PaisBean();
		listaPais = new ArrayList<PaisBean>();
		String sql = "{call sp_pais(?,?,?,?)}";
		try {
			if(con!=null){
			    cst = (CallableStatement) con.prepareCall(sql);
				cst.setInt(1, 0);
				cst.setString(2, null);
				cst.setString(3, null);
				cst.setInt(4, 4);
				rs =cst.executeQuery();
				while (rs.next()==true) {
					paisBean = new PaisBean();
					paisBean.setCodigoPA(rs.getInt("codigo"));
					paisBean.setNombrePA(rs.getString("nombre"));
					paisBean.setDescripcionPA(rs.getString("descripcion"));
					paisBean.setFechaPA(rs.getString("fecharegistro"));
					listaPais.add(paisBean);
				}
				con.close();
				cst.close();
				conexionMySQL.desconectar();
			}
		} catch (SQLException e) {
			System.out.println(" Error con la consulta en la BD: " + e.getMessage());
		}	
		return listaPais;
	}
	
	public int obtenerUltimoRegistro(){
		int siguienteid = 0;
		Connection con = null;
		/*PreparedStatement pst = null; CUANDO NO ES UN PROCEDURE*/
		CallableStatement cst = null;
		ResultSet rs = null;
		con = conexionMySQL.getConnection();
		paisBean = new PaisBean();
		String sql = "{call sp_pais(?,?,?,?)}";
		try {
			if(con!=null){
			    cst = (CallableStatement) con.prepareCall(sql);
				cst.setInt(1, 0);
				cst.setString(2, null);
				cst.setString(3, null);
				cst.setInt(4, 5);
				rs =cst.executeQuery();
				if(rs.next()==true){
					paisBean = new PaisBean();
					paisBean.setCodigoPA(rs.getInt("nextid"));
					siguienteid = paisBean.getCodigoPA();
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
	
	public ArrayList<PaisBean> verificarNombrePais(String nompais){
		Connection con = null;
		/*PreparedStatement pst = null; CUANDO NO ES UN PROCEDURE*/
		CallableStatement cst = null;
		ResultSet rs = null;
		con = conexionMySQL.getConnection();
		paisBean = new PaisBean();
		listaPais = new ArrayList<PaisBean>();
		String sql = "{call sp_pais(?,?,?,?)}";
		try {
			if(con!=null){
			    cst = (CallableStatement) con.prepareCall(sql);
				cst.setInt(1, 0);
				cst.setString(2, nompais);
				cst.setString(3, null);
				cst.setInt(4, 6);
				rs =cst.executeQuery();
				if(rs.next()==true){
					paisBean = new PaisBean();
					paisBean.setCodigoPA(rs.getInt("codigo"));
					paisBean.setNombrePA(rs.getString("nombre"));
					listaPais.add(paisBean);
				}
				con.close();
				cst.close();
				conexionMySQL.desconectar();
			}
		} catch (SQLException e) {
			System.out.println(" Error con la consulta a la BD: " + e.getMessage());
		}
		return listaPais;		
	}

	public int registrarPais( PaisBean pais){
		int reg = 0;
		CallableStatement cst = null;
		Connection con = null;
		con =conexionMySQL.getConnection();
		String sql = "{call sp_pais(?,?,?,?)}";
		try{
			cst = (CallableStatement) con.prepareCall(sql);
			cst.setInt(1, pais.getCodigoPA());
			cst.setString(2, pais.getNombrePA());
			cst.setString(3, pais.getDescripcionPA());
			cst.setInt(4, 1);
			reg = cst.executeUpdate();
			if(reg>0){
				System.out.println(" Pais Registrado Correctamente: "+reg);
			}
		}catch(SQLException e){
			System.err.println(" Error al insertar Pais: "+e);
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

	public int modificarPais( PaisBean pais){
		int mod = 0;
		//PreparedStatement pst = null;
		CallableStatement cst = null;
		Connection con = null;
		con =conexionMySQL.getConnection();
		String sql = "{call sp_pais(?,?,?,?)}";
		try{
			cst = con.prepareCall(sql);
			cst.setInt(1, pais.getCodigoPA());
			cst.setString(2, pais.getNombrePA());
			cst.setString(3, pais.getDescripcionPA());
			cst.setInt(4, 2);
			mod = cst.executeUpdate();
			if(mod>0){
				System.out.println(" Pais Modificado Correctamente: "+mod);
			}
		}catch(SQLException e){
			System.err.println(" Error al Modificar Pais: "+e);
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
	
	public int eliminarPais( PaisBean pais){
		int eli = 0;
		CallableStatement cst = null;
		Connection con = null;
		con =conexionMySQL.getConnection();
		String sql = "{call sp_pais(?,?,?,?)}";
		try{
			cst = con.prepareCall(sql);
			cst.setInt(1, pais.getCodigoPA());
			cst.setString(2, null);
			cst.setString(3, null);
			cst.setInt(4, 3);
			eli = cst.executeUpdate();
			if(eli>0){
				System.out.println(" Pais Eliminado Correctamente: "+eli);
			}
		}catch(SQLException e){
			System.err.println(" Error al Eliminar Pais: "+e);
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
