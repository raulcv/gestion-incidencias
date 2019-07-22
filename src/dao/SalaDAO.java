package dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import bean.SalaBean;
import conexion.ConexionMySQL;

public class SalaDAO {
	ConexionMySQL conexionMySQL = new ConexionMySQL();
	SalaBean salaBean;
	ArrayList<SalaBean> listaSala;
	
	public ArrayList<SalaBean> listaS(){
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		listaSala = new ArrayList<SalaBean>();
		con = conexionMySQL.getConnection();
		String sql = "select nomsala as nombre from tb_sala where estsala = 1 and idsala <> 0 order by idsala asc";
		try {
			if(con!=null){
				pst = con.prepareStatement(sql);
				rs =pst.executeQuery();
				
				while (rs.next()==true) {
					salaBean = new SalaBean();
					salaBean.setNombreS(rs.getString("nombre"));
					listaSala.add(salaBean);
				}
			}
		} catch (SQLException e) {
			System.out.println("Error con la consulta BD: " + e.getMessage());
		}finally {
			try {
				con.close();
				conexionMySQL.desconectar();
			} catch (SQLException e) {
				System.err.println(e);
			}
		}	
		return listaSala;
	}
	
	//------------------------------PARA VENTANA DE SALAS-------------------
	public ArrayList<SalaBean> listarTodasSalas(){
		Connection con = null;
		/*PreparedStatement pst = null; CUANDO NO ES UN PROCEDURE*/
		CallableStatement cst = null;
		ResultSet rs = null;
		con = conexionMySQL.getConnection();
		salaBean = new SalaBean();
		listaSala = new ArrayList<SalaBean>();
		String sql = "{call sp_Sala(?,?,?,?)}";
		try {
			if(con!=null){
			    cst = (CallableStatement) con.prepareCall(sql);
				cst.setInt(1, 0);
				cst.setString(2, null);
				cst.setString(3, null);
				cst.setInt(4, 4);
				rs =cst.executeQuery();
				while (rs.next()==true) {
					salaBean = new SalaBean();
					salaBean.setCodigoS(rs.getInt("codigo"));
					salaBean.setNombreS(rs.getString("nombre"));
					salaBean.setDescripcionS(rs.getString("descripcion"));
					salaBean.setFechaS(rs.getString("fecharegistro"));
					listaSala.add(salaBean);
				}
				con.close();
				cst.close();
				conexionMySQL.desconectar();
			}
		} catch (SQLException e) {
			System.out.println(" Error con la consulta en la BD: " + e.getMessage());
		}	
		return listaSala;
	}
	
	public int obtenerUltimoRegistro(){
		int siguienteid = 0;
		Connection con = null;
		/*PreparedStatement pst = null; CUANDO NO ES UN PROCEDURE*/
		CallableStatement cst = null;
		ResultSet rs = null;
		con = conexionMySQL.getConnection();
		salaBean = new SalaBean();
		String sql = "{call sp_Sala(?,?,?,?)}";
		try {
			if(con!=null){
			    cst = (CallableStatement) con.prepareCall(sql);
				cst.setInt(1, 0);
				cst.setString(2, null);
				cst.setString(3, null);
				cst.setInt(4, 5);
				rs =cst.executeQuery();
				if(rs.next()==true){
					salaBean = new SalaBean();
					salaBean.setCodigoS(rs.getInt("nextid"));
					siguienteid = salaBean.getCodigoS();
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
	
	public ArrayList<SalaBean> verificarNombreSala(String nomsala){
		Connection con = null;
		/*PreparedStatement pst = null; CUANDO NO ES UN PROCEDURE*/
		CallableStatement cst = null;
		ResultSet rs = null;
		con = conexionMySQL.getConnection();
		salaBean = new SalaBean();
		listaSala = new ArrayList<SalaBean>();
		String sql = "{call sp_Sala(?,?,?,?)}";
		try {
			if(con!=null){
			    cst = (CallableStatement) con.prepareCall(sql);
				cst.setInt(1, 0);
				cst.setString(2, nomsala);
				cst.setString(3, null);
				cst.setInt(4, 6);
				rs =cst.executeQuery();
				if(rs.next()==true){
					salaBean = new SalaBean();
					salaBean.setCodigoS(rs.getInt("codigo"));
					salaBean.setNombreS(rs.getString("nombre"));
					listaSala.add(salaBean);
				}
				con.close();
				cst.close();
				conexionMySQL.desconectar();
			}
		} catch (SQLException e) {
			System.out.println(" Error con la consulta a la BD: " + e.getMessage());
		}
		return listaSala;		
	}

	public int registrarSala( SalaBean sala){
		int reg = 0;
		//PreparedStatement pst = null;
		CallableStatement cst;
		Connection con = null;
		con =conexionMySQL.getConnection();
		String sql = "{call sp_Sala(?,?,?,?)}";
		try{
			cst = (CallableStatement) con.prepareCall(sql);
			cst.setInt(1, sala.getCodigoS());
			cst.setString(2, sala.getNombreS());
			cst.setString(3, sala.getDescripcionS());
			cst.setInt(4, 1);
			reg = cst.executeUpdate();
			if(reg>0){
				System.out.println(" Registrado Correctamente: "+reg);
			}
		}catch(SQLException e){
			System.err.println(" Error al insertar: "+e);
			return reg;
		}finally {
			try {
				con.close();
			} catch (SQLException e) {
				System.err.println(e);
			}
		}
		return reg;
	}

	public int modificarSala( SalaBean sala){
		int mod = 0;
		//PreparedStatement pst = null;
		CallableStatement cst;
		Connection con = null;
		con =conexionMySQL.getConnection();
		String sql = "{call sp_Sala(?,?,?,?)}";
		try{
			cst = con.prepareCall(sql);
			cst.setInt(1, sala.getCodigoS());
			cst.setString(2, sala.getNombreS());
			cst.setString(3, sala.getDescripcionS());
			cst.setInt(4, 2);
			mod = cst.executeUpdate();
			if(mod>0){
				System.out.println(" Modificado Correctamente: "+mod);
			}
		}catch(SQLException e){
			System.err.println(" Error al Modificar: "+e);
			return mod;
		}finally {
			try {
				con.close();
			} catch (SQLException e) {
				System.err.println(e);
			}
		}
		return mod;
	}
	
	public int eliminarSala( SalaBean sala){
		int eli = 0;
		CallableStatement cst;
		Connection con = null;
		con =conexionMySQL.getConnection();
		String sql = "{call sp_Sala(?,?,?,?)}";
		try{
			cst = con.prepareCall(sql);
			cst.setInt(1, sala.getCodigoS());
			cst.setString(2, null);
			cst.setString(3, null);
			cst.setInt(4, 3);
			eli = cst.executeUpdate();
			if(eli>0){
				System.out.println(" Eliminado Correctamente: "+eli);
			}
		}catch(SQLException e){
			System.err.println(" Error al Eliminar: "+e);
			return eli;
		}finally {
			try {
				con.close();
			} catch (SQLException e) {
				System.err.println(e);
			}
		}
		return eli;
	}
	
}
