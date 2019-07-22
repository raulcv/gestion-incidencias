package dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import bean.IncidenciaBean;
import conexion.ConexionMySQL;

public class IncidenciaDAO{	
	ConexionMySQL conexionMySQL = new ConexionMySQL();
	IncidenciaBean incBean;
	ArrayList<IncidenciaBean> listaInc;
	
	public ArrayList<IncidenciaBean> listaSol(){
		Connection con = null;
		CallableStatement cst = null;
		ResultSet rs = null;
		listaInc = new ArrayList<IncidenciaBean>();
		
		con = conexionMySQL.getConnection();
		String sql = "{call sp_sol()}";
		try {
			if(con!=null){
				cst = con.prepareCall(sql);
				rs=cst.executeQuery();
				while (rs.next()==true) {
					incBean = new IncidenciaBean();
					incBean.setSolicitador(rs.getString("nombre"));
					listaInc.add(incBean);
				}
			}
		} catch (SQLException e) {
			System.out.println("Error con la consulta BD: " + e.getMessage());
		}finally {
			try {
				con.close();
				cst.close();
				conexionMySQL.desconectar();
			} catch (SQLException e) {
				System.err.println(e);
			}
		}
		
		return listaInc;
	}

	public ArrayList<IncidenciaBean> listaIncidencia(int numreg,String nomsala, String fecini, String fecfin, int estado){
		Connection con = null;
		CallableStatement cst = null;
		ResultSet rs = null;
		listaInc = new ArrayList<IncidenciaBean>();
		con = conexionMySQL.getConnection();
		String sql = "{call sp_consultainci(?,?,?,?,?)}";
		try {
			if(con!=null){
				cst = con.prepareCall(sql);
				cst.setInt(1, numreg);
				cst.setString(2, nomsala);
				cst.setString(3, fecini);
				cst.setString(4, fecfin);
				cst.setInt(5, estado);
				rs=cst.executeQuery();
				while (rs.next()==true) {
					incBean = new IncidenciaBean();
					incBean.setCodigoI(rs.getInt("codigo"));
					incBean.setSala(rs.getString("sala"));
					incBean.setFechaiI(rs.getString("fechai"));
					incBean.setHoraiI(rs.getString("horai"));
					incBean.setFechafI(rs.getString("fechaf"));
					incBean.setHorafI(rs.getString("horaf"));
					incBean.setTurnoI(rs.getInt("turno"));
					incBean.setMedio(rs.getString("medio"));
					incBean.setIncidencia(rs.getString("incidencia"));
					incBean.setComentario(rs.getString("comentario"));
					incBean.setSolucion(rs.getString("solucion"));
					incBean.setRecepcionista(rs.getString("recepcionista"));
					incBean.setSolicitador(rs.getString("solicitador"));
					incBean.setEncargado(rs.getString("encargado"));
					incBean.setEstadoI(rs.getString("estado"));
					incBean.setFechaRegI(rs.getString("fechareg"));
					incBean.setFechaUltModI(rs.getString("fechaultmod"));
					incBean.setNomUsuReg(rs.getString("usuarioregistrador"));
					listaInc.add(incBean);
				}
			}
		} catch (SQLException e) {
			System.out.println(" Error con la consulta BD: " + e.getMessage());
		}finally {
			try {
				con.close();
				cst.close();
				conexionMySQL.desconectar();
			} catch (SQLException e) {
				System.err.println(e);
			}
		}		
		return listaInc;
	}

	public int obtenerUltimoRegistro(){
		int siguienteid = 0;
		Connection con = null;
		CallableStatement cst = null;
		ResultSet rs = null;
		con = conexionMySQL.getConnection();
		String sql = "{call sp_incidencia(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
		try {
			if(con!=null){
			    cst = (CallableStatement) con.prepareCall(sql);
				cst.setInt(1, 0);
				cst.setString(2, null);
				cst.setString(3, null);
				cst.setString(4, null);
				cst.setString(5, null);
				cst.setString(6, null);
				cst.setInt(7, 0);
				cst.setString(8, null);
				cst.setString(9, null);
				cst.setString(10, null);
				cst.setString(11, null);
				cst.setString(12, null);
				cst.setString(13, null);
				cst.setString(14, null);
				cst.setInt(15, 0);
				cst.setInt(16, 5);
				rs =cst.executeQuery();
				if(rs.next()==true){
					incBean = new IncidenciaBean();
					incBean.setCodigoI(rs.getInt("nextid"));
					siguienteid = incBean.getCodigoI();
				}
				con.close();
				cst.close();
				conexionMySQL.desconectar();
			}
		} catch (SQLException e) {
			System.out.println(" Error con la consulta a la BD: " + e.getMessage());
		}	
		return siguienteid;
	}
	
	public int registrarInc(IncidenciaBean inc){
		int reg = 0;
		CallableStatement cst = null;
		Connection con = null;
		con =conexionMySQL.getConnection();
		String sql = "{call sp_incidencia(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
		try{
			cst = (CallableStatement) con.prepareCall(sql);
			cst.setInt(1, inc.getCodigoI());
			cst.setString(2, inc.getSala());
			cst.setString(3, inc.getFechaiI());
			cst.setString(4, inc.getHoraiI());
			cst.setString(5, inc.getFechafI());
			cst.setString(6, inc.getHorafI());
			cst.setInt(7, inc.getTurnoI());
			cst.setString(8, inc.getMedio());
			cst.setString(9, inc.getIncidencia());
			cst.setString(10, inc.getComentario());
			cst.setString(11, inc.getSolucion());
			cst.setString(12, inc.getRecepcionista());
			cst.setString(13, inc.getSolicitador());
			cst.setString(14, inc.getEncargado());
			cst.setInt(15, inc.getUsuRegI());
			cst.setInt(16, 1);
			reg = cst.executeUpdate();
			if(reg>0){
				System.out.println(" Incidencia Registrado Correctamente: "+reg);
			}
		}catch(SQLException e){
			System.err.println(" Error al insertar Incidencia: "+e);
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

	public int modificarInc(IncidenciaBean inc){
		int reg = 0;
		CallableStatement cst = null;
		Connection con = null;
		con =conexionMySQL.getConnection();
		String sql = "{call sp_incidencia(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
		try{
			cst = (CallableStatement) con.prepareCall(sql);
			cst.setInt(1, inc.getCodigoI());
			cst.setString(2, inc.getSala());
			cst.setString(3, inc.getFechaiI());
			cst.setString(4, inc.getHoraiI());
			cst.setString(5, inc.getFechafI());
			cst.setString(6, inc.getHorafI());
			cst.setInt(7, inc.getTurnoI());
			cst.setString(8, inc.getMedio());
			cst.setString(9, inc.getIncidencia());
			cst.setString(10, inc.getComentario());
			cst.setString(11, inc.getSolucion());
			cst.setString(12, inc.getRecepcionista());
			cst.setString(13, inc.getSolicitador());
			cst.setString(14, inc.getEncargado());
			cst.setInt(15, 0);
			cst.setInt(16, 2);
			reg = cst.executeUpdate();
			if(reg>0){
				System.out.println(" Incidencia Modificado Correctamente: "+reg);
			}
		}catch(SQLException e){
			System.err.println(" Error al Modificar Incidencia: "+e);
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

	public int eliminarInc(IncidenciaBean inc){
		int reg = 0;
		CallableStatement cst = null;
		Connection con = null;
		con =conexionMySQL.getConnection();
		String sql = "{call sp_incidencia(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
		try{
			cst = (CallableStatement) con.prepareCall(sql);
			cst.setInt(1, inc.getCodigoI());
			cst.setString(2, null);
			cst.setString(3, null);
			cst.setString(4, null);
			cst.setString(5, null);
			cst.setString(6, null);
			cst.setInt(7, 0);
			cst.setString(8, null);
			cst.setString(9, null);
			cst.setString(10, null);
			cst.setString(11, null);
			cst.setString(12, null);
			cst.setString(13, null);
			cst.setString(14, null);
			cst.setInt(15, 0);
			cst.setInt(16, 3);
			reg = cst.executeUpdate();
			if(reg>0){
				System.out.println(" Incidencia Eliminado Correctamente: "+reg);
			}
		}catch(SQLException e){
			System.err.println(" Error al Eliminar Incidencia: "+e);
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
	
	public int recuperarInc(IncidenciaBean inc){
		int reg = 0;
		CallableStatement cst = null;
		Connection con = null;
		con =conexionMySQL.getConnection();
		String sql = "{call sp_incidencia(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
		try{
			cst = (CallableStatement) con.prepareCall(sql);
			cst.setInt(1, inc.getCodigoI());
			cst.setString(2, null);
			cst.setString(3, null);
			cst.setString(4, null);
			cst.setString(5, null);
			cst.setString(6, null);
			cst.setInt(7, 0);
			cst.setString(8, null);
			cst.setString(9, null);
			cst.setString(10, null);
			cst.setString(11, null);
			cst.setString(12, null);
			cst.setString(13, null);
			cst.setString(14, null);
			cst.setInt(15, 0);
			cst.setInt(16, 4);
			reg = cst.executeUpdate();
			if(reg>0){
				System.out.println(" Incidencia Eliminado Correctamente: "+reg);
			}
		}catch(SQLException e){
			System.err.println(" Error al Eliminar Incidencia: "+e);
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

}
