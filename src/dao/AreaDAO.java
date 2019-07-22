package dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import bean.AreaBean;
import conexion.ConexionMySQL;

public class AreaDAO {
	ConexionMySQL conexionMySQL = new ConexionMySQL();
	AreaBean areaBean;
	ArrayList<AreaBean> listaArea;

	public ArrayList<AreaBean> listaA(){
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		listaArea = new ArrayList<>();
		con = conexionMySQL.getConnection();
		String sql = "select idarea as codigo,nomarea as nombre from tb_area where estarea = 1 order by idarea asc";
		try {
			if(con!=null){
				pst = con.prepareStatement(sql);
				rs =pst.executeQuery();				
				while (rs.next()==true) {
					areaBean = new AreaBean();
					areaBean.setCodigoA(rs.getInt("codigo"));
					areaBean.setNombreA(rs.getString("nombre"));
					listaArea.add(areaBean);
				}
			}
		} catch (SQLException e) {
			System.out.println("Error con la consulta BD: " + e.getMessage());
		}finally {
			try {
				con.close();
				pst.close();
				rs.close();
				conexionMySQL.desconectar();
			} catch (SQLException e) {
				System.err.println(e);
			}
		}	
		return listaArea;
	}
	
	//------------------------------PARA VENTANA DE SALAS-------------------
	public ArrayList<AreaBean> listarTodasAreas(){
		Connection con = null;
		/*PreparedStatement pst = null; CUANDO NO ES UN PROCEDURE*/
		CallableStatement cst = null;
		ResultSet rs = null;
		con = conexionMySQL.getConnection();
		listaArea = new ArrayList<AreaBean>();
		String sql = "{call sp_area(?,?,?,?)}";
		try {
			if(con!=null){
			    cst = (CallableStatement) con.prepareCall(sql);
				cst.setInt(1, 0);
				cst.setString(2, null);
				cst.setString(3, null);
				cst.setInt(4, 4);
				rs =cst.executeQuery();
				while (rs.next()==true) {
					areaBean = new AreaBean();
					areaBean.setCodigoA(rs.getInt("codigo"));
					areaBean.setNombreA(rs.getString("nombre"));
					areaBean.setDescripcionA(rs.getString("descripcion"));
					areaBean.setFecharegA(rs.getString("fecharegistro"));
					listaArea.add(areaBean);
				}
				con.close();
				cst.close();
				conexionMySQL.desconectar();
			}
		} catch (SQLException e) {
			System.out.println(" Error con la consulta en la BD: " + e.getMessage());
		}	
		return listaArea;
	}
	
	public int obtenerUltimoRegistro(){
		int siguienteid = 0;
		Connection con = null;
		CallableStatement cst = null;
		ResultSet rs = null;
		con = conexionMySQL.getConnection();
		String sql = "{call sp_area(?,?,?,?)}";
		try {
			if(con!=null){
			    cst = (CallableStatement) con.prepareCall(sql);
				cst.setInt(1, 0);
				cst.setString(2, null);
				cst.setString(3, null);
				cst.setInt(4, 5);
				rs =cst.executeQuery();
				if(rs.next()==true){
					areaBean = new AreaBean();
					areaBean.setCodigoA(rs.getInt("nextid"));
					siguienteid = areaBean.getCodigoA();
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
	
	public ArrayList<AreaBean> verificarNombreArea(String nomarea){
		Connection con = null;
		/*PreparedStatement pst = null; CUANDO NO ES UN PROCEDURE*/
		CallableStatement cst = null;
		ResultSet rs = null;
		con = conexionMySQL.getConnection();
		listaArea = new ArrayList<AreaBean>();
		String sql = "{call sp_area(?,?,?,?)}";
		try {
			if(con!=null){
			    cst = (CallableStatement) con.prepareCall(sql);
				cst.setInt(1, 0);
				cst.setString(2, nomarea);
				cst.setString(3, null);
				cst.setInt(4, 6);
				rs =cst.executeQuery();
				if(rs.next()==true){
					areaBean = new AreaBean();
					areaBean.setCodigoA(rs.getInt("codigo"));
					areaBean.setNombreA(rs.getString("nombre"));
					listaArea.add(areaBean);
				}
				con.close();
				cst.close();
				conexionMySQL.desconectar();
			}
		} catch (SQLException e) {
			System.out.println(" Error con la consulta a la BD: " + e.getMessage());
		}
		return listaArea;		
	}

	public int registrarArea( AreaBean area){
		int reg = 0;
		//PreparedStatement pst = null;
		CallableStatement cst = null;
		Connection con = null;
		con =conexionMySQL.getConnection();
		String sql = "{call sp_area(?,?,?,?)}";
		try{
			cst = (CallableStatement) con.prepareCall(sql);
			cst.setInt(1, area.getCodigoA());
			cst.setString(2, area.getNombreA());
			cst.setString(3, area.getDescripcionA());
			cst.setInt(4, 1);
			reg = cst.executeUpdate();
			if(reg>0){
				System.out.println(" Area Registrado Correctamente: "+reg);
			}
		}catch(SQLException e){
			System.err.println(" Error al insertar Area: "+e);
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

	public int modificarArea( AreaBean area){
		int mod = 0;
		CallableStatement cst = null;
		Connection con = null;
		con =conexionMySQL.getConnection();
		String sql = "{call sp_area(?,?,?,?)}";
		try{
			cst = con.prepareCall(sql);
			cst.setInt(1, area.getCodigoA());
			cst.setString(2, area.getNombreA());
			cst.setString(3, area.getDescripcionA());
			cst.setInt(4, 2);
			mod = cst.executeUpdate();
			if(mod>0){
				System.out.println(" Area Modificado Correctamente: "+mod);
			}
		}catch(SQLException e){
			System.err.println(" Error al Modificar Area: "+e);
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
	
	public int eliminarArea( AreaBean area){
		int eli = 0;
		CallableStatement cst = null;
		Connection con = null;
		con =conexionMySQL.getConnection();
		String sql = "{call sp_area(?,?,?,?)}";
		try{
			cst = con.prepareCall(sql);
			cst.setInt(1, area.getCodigoA());
			cst.setString(2, null);
			cst.setString(3, null);
			cst.setInt(4, 3);
			eli = cst.executeUpdate();
			if(eli>0){
				System.out.println(" Area eliminado Correctamente: "+eli);
			}
		}catch(SQLException e){
			System.err.println(" Error al Eliminar Area: "+e);
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
