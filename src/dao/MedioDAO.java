package dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import bean.MedioBean;
import conexion.ConexionMySQL;

public class MedioDAO {

	ConexionMySQL conexionMySQL = new ConexionMySQL();
	MedioBean medioBean;
	ArrayList<MedioBean> listaMedio;
	
	public ArrayList<MedioBean> listaM(){
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		listaMedio = new ArrayList<MedioBean>();
		con = conexionMySQL.getConnection();
		String sql = "select idmedio as codigo,nommedio as nombre from tb_medio where estmedio = 1 order by idmedio asc";
		try {
			if(con!=null){
				pst = con.prepareStatement(sql);
				rs =pst.executeQuery();
				while (rs.next()==true) {
					medioBean = new MedioBean();
					medioBean.setCodigoM(rs.getInt("codigo"));
					medioBean.setNombreM(rs.getString("nombre"));
					listaMedio.add(medioBean);
				}
			}
		} catch (SQLException e) {
			System.out.println(" Error con la consulta BD: " + e.getMessage());
		}finally {
			try {
				con.close();
				pst.close();
				conexionMySQL.desconectar();
			} catch (SQLException e) {
				System.err.println(e);
			}
		}	
		return listaMedio;
	}
	
	//------------------------------PARA VENTANA DE MEDIO-------------------
	public ArrayList<MedioBean> listarTodosMedios(){
		Connection con = null;
		CallableStatement cst = null;
		ResultSet rs = null;
		con = conexionMySQL.getConnection();
		listaMedio = new ArrayList<MedioBean>();
		String sql = "{call sp_medio(?,?,?,?)}";
		try {
			if(con!=null){
			    cst = (CallableStatement) con.prepareCall(sql);
				cst.setInt(1, 0);
				cst.setString(2, null);
				cst.setString(3, null);
				cst.setInt(4, 4);
				rs =cst.executeQuery();
				while (rs.next()==true) {
					medioBean = new MedioBean();
					medioBean.setCodigoM(rs.getInt("codigo"));
					medioBean.setNombreM(rs.getString("nombre"));
					medioBean.setDescripcionM(rs.getString("descripcion"));
					medioBean.setFechaM(rs.getString("fecharegistro"));
					listaMedio.add(medioBean);
				}
				con.close();
				cst.close();
				conexionMySQL.desconectar();
			}
		} catch (SQLException e) {
			System.out.println(" Error con la consulta en la BD: " + e.getMessage());
		}	
		return listaMedio;
	}
	
	public int obtenerUltimoRegistro(){
		int siguienteid = 0;
		Connection con = null;
		CallableStatement cst = null;
		ResultSet rs = null;
		con = conexionMySQL.getConnection();
		String sql = "{call sp_medio(?,?,?,?)}";
		try {
			if(con!=null){
			    cst = (CallableStatement) con.prepareCall(sql);
				cst.setInt(1, 0);
				cst.setString(2, null);
				cst.setString(3, null);
				cst.setInt(4, 5);
				rs =cst.executeQuery();
				if(rs.next()==true){
					medioBean = new MedioBean();
					medioBean.setCodigoM(rs.getInt("nextid"));
					siguienteid = medioBean.getCodigoM();
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
	
	public ArrayList<MedioBean> verificarNombreMedio(String nommedio){
		Connection con = null;
		CallableStatement cst = null;
		ResultSet rs = null;
		con = conexionMySQL.getConnection();
		listaMedio = new ArrayList<MedioBean>();
		String sql = "{call sp_medio(?,?,?,?)}";
		try {
			if(con!=null){
			    cst = (CallableStatement) con.prepareCall(sql);
				cst.setInt(1, 0);
				cst.setString(2, nommedio);
				cst.setString(3, null);
				cst.setInt(4, 6);
				rs =cst.executeQuery();
				if(rs.next()==true){
					medioBean = new MedioBean();
					medioBean.setCodigoM(rs.getInt("codigo"));
					medioBean.setNombreM(rs.getString("nombre"));
					listaMedio.add(medioBean);
				}
				con.close();
				cst.close();
				conexionMySQL.desconectar();
			}
		} catch (SQLException e) {
			System.out.println(" Error con la consulta a la BD: " + e.getMessage());
		}
		return listaMedio;		
	}

	public int registrarMedio(MedioBean medio){
		int reg = 0;
		CallableStatement cst = null;
		Connection con = null;
		con =conexionMySQL.getConnection();
		String sql = "{call sp_medio(?,?,?,?)}";
		try{
			cst = (CallableStatement) con.prepareCall(sql);
			cst.setInt(1, medio.getCodigoM());
			cst.setString(2, medio.getNombreM());
			cst.setString(3, medio.getDescripcionM());
			cst.setInt(4, 1);
			reg = cst.executeUpdate();
			if(reg>0){
				System.out.println(" Medio Registrado Correctamente: "+reg);
			}
		}catch(SQLException e){
			System.err.println(" Error al insertar Medio: "+e);
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

	public int modificarMedio(MedioBean medio){
		int mod = 0;
		CallableStatement cst = null;
		Connection con = null;
		con =conexionMySQL.getConnection();
		String sql = "{call sp_medio(?,?,?,?)}";
		try{
			cst = con.prepareCall(sql);
			cst.setInt(1, medio.getCodigoM());
			cst.setString(2,medio.getNombreM());
			cst.setString(3, medio.getDescripcionM());
			cst.setInt(4, 2);
			mod = cst.executeUpdate();
			if(mod>0){
				System.out.println(" Medio Modificado Correctamente: "+mod);
			}
		}catch(SQLException e){
			System.err.println(" Error al Modificar Medio: "+e);
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
	
	public int eliminarMedio(MedioBean medio){
		int eli = 0;
		CallableStatement cst = null;
		Connection con = null;
		con =conexionMySQL.getConnection();
		String sql = "{call sp_medio(?,?,?,?)}";
		try{
			cst = con.prepareCall(sql);
			cst.setInt(1, medio.getCodigoM());
			cst.setString(2, null);
			cst.setString(3, null);
			cst.setInt(4, 3);
			eli = cst.executeUpdate();
			if(eli>0){
				System.out.println(" Medio Eliminado Correctamente: "+eli);
			}
		}catch(SQLException e){
			System.err.println(" Error al Eliminar Medio: "+e);
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
