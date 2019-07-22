package dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import bean.PersonaBean;
import conexion.ConexionMySQL;

public class PersonaDAO {
	ConexionMySQL conexionMySQL = new ConexionMySQL();
	PersonaBean personaBean;
	ArrayList<PersonaBean> listaPersona;
	
	public ArrayList<PersonaBean> listaRecepcionador(){
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		listaPersona = new ArrayList<PersonaBean>();
		con = conexionMySQL.getConnection();
		String sql = "select concat(nomper,' ',a.apeper,' - ' ,b.nomarea) as nombre from tb_persona a inner join tb_area b on a.area=b.idarea where estper = 1 and idper <> 0 order by idper asc";
		try {
			if(con!=null){
				pst = con.prepareStatement(sql);
				rs =pst.executeQuery();
				while (rs.next()==true) {
					personaBean = new PersonaBean();
					personaBean.setNombreP(rs.getString("nombre"));
					listaPersona.add(personaBean);
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
		return listaPersona;
	}
	
	public ArrayList<PersonaBean> listaEncargado(){
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		listaPersona = new ArrayList<PersonaBean>();
		con = conexionMySQL.getConnection();
		String sql = "select ifnull(concat(a.nomper,' ',a.apeper,' - ' ,b.nomarea),'SIN ENCARGADO') as nombre from tb_persona a inner join tb_area b on a.area=b.idarea where estper = 1 order by idper asc";
		try {
			if(con!=null){
				pst = con.prepareStatement(sql);
				rs =pst.executeQuery();
				while (rs.next()==true) {
					personaBean = new PersonaBean();
					personaBean.setNombreP(rs.getString("nombre"));
					listaPersona.add(personaBean);
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
		return listaPersona;
	}
	
	//------------------------------PARA VENTANA DE MEDIO-------------------
	public ArrayList<PersonaBean> listarTodasPersonas(){
		Connection con = null;
		CallableStatement cst = null;
		ResultSet rs = null;
		con = conexionMySQL.getConnection();
		listaPersona = new ArrayList<PersonaBean>();
		String sql = "{call sp_persona(?,?,?,?,?,?,?,?,?,?)}";
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
				cst.setString(8, null);
				cst.setString(9, null);
				cst.setInt(10, 4);
				rs =cst.executeQuery();
				while (rs.next()==true) {
					personaBean = new PersonaBean();
					personaBean.setCodigoP(rs.getInt("codigo"));
					personaBean.setIdAr(rs.getInt("idarea"));
					personaBean.setNombreAr(rs.getString("area"));
					personaBean.setNombreP(rs.getString("nombre"));
					personaBean.setApellidoP(rs.getString("apellido"));
					personaBean.setEmailP(rs.getString("email"));
					personaBean.setNombrePa(rs.getString("pais"));
					personaBean.setTelefonoP(rs.getString("telefono"));
					personaBean.setComentarioP(rs.getString("comentario"));
					personaBean.setFechaRegP(rs.getString("fecharegistro"));
					personaBean.setFechaUltM(rs.getString("fechaultmodifica"));
					personaBean.setNombreUs(rs.getString("usuario"));
					listaPersona.add(personaBean);
				}
				con.close();
				cst.close();
				conexionMySQL.desconectar();
			}
		} catch (SQLException e) {
			System.out.println(" Error con la consulta en la BD: " + e.getMessage());
		}	
		return listaPersona;
	}
	
	public int obtenerUltimoRegistro(){
		int siguienteid = 0;
		Connection con = null;
		CallableStatement cst = null;
		ResultSet rs = null;
		con = conexionMySQL.getConnection();
		String sql = "{call sp_persona(?,?,?,?,?,?,?,?,?,?)}";
		try {
			if(con!=null){
			    cst = (CallableStatement) con.prepareCall(sql);
			    cst = (CallableStatement) con.prepareCall(sql);
				cst.setInt(1, 0);
				cst.setString(2, null);	
				cst.setString(3, null);
				cst.setString(4, null);
				cst.setString(5, null);
				cst.setString(6, null);			
				cst.setString(7, null);
				cst.setString(8, null);
				cst.setString(9, null);
				cst.setInt(10, 5);
				rs =cst.executeQuery();
				if(rs.next()==true){
					personaBean = new PersonaBean();
					personaBean.setCodigoP(rs.getInt("nextid"));
					siguienteid = personaBean.getCodigoP();
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
	
	public ArrayList<PersonaBean> verificarNombreApellidosPersona(String nompersona, String Apellido){
		Connection con = null;
		CallableStatement cst = null;
		ResultSet rs = null;
		con = conexionMySQL.getConnection();
		listaPersona = new ArrayList<PersonaBean>();
		String sql = "{call sp_persona(?,?,?,?,?,?,?,?,?,?)}";
		try {
			if(con!=null){
				cst = (CallableStatement) con.prepareCall(sql);
				cst.setInt(1, 0);
				cst.setString(2, null);	
				cst.setString(3, nompersona);
				cst.setString(4, Apellido);
				cst.setString(5, null);
				cst.setString(6, null);				
				cst.setString(7, null);
				cst.setString(8, null);
				cst.setString(9, null);
				cst.setInt(10, 6);
				rs =cst.executeQuery();
				if(rs.next()==true){
					personaBean = new PersonaBean();
					personaBean.setCodigoP(rs.getInt("codigo"));
					personaBean.setNombreP(rs.getString("nombre"));
					personaBean.setApellidoP(rs.getString("apellido"));
					listaPersona.add(personaBean);
				}
				con.close();
				cst.close();
				conexionMySQL.desconectar();
			}
		} catch (SQLException e) {
			System.out.println(" Error con la consulta a la BD: " + e.getMessage());
		}
		return listaPersona;		
	}

	public int registrarPersona(PersonaBean persona){
		int reg = 0;
		CallableStatement cst = null;
		Connection con = null;
		con =conexionMySQL.getConnection();
		String sql = "{call sp_persona(?,?,?,?,?,?,?,?,?,?)}";
		try{
			cst = (CallableStatement) con.prepareCall(sql);
			cst.setInt(1, persona.getCodigoP());
			cst.setString(2, persona.getNombreAr());
			cst.setString(3, persona.getNombreP());
			cst.setString(4, persona.getApellidoP());
			cst.setString(5, persona.getEmailP());
			cst.setString(6, persona.getNombrePa());			
			cst.setString(7, persona.getTelefonoP());	
			cst.setString(8, persona.getComentarioP());
			cst.setInt(9, persona.getIdUs());
			cst.setInt(10, 1);
			reg = cst.executeUpdate();
			if(reg>0){
				System.out.println(" Persona Registrado Correctamente: "+reg);
			}
		}catch(SQLException e){
			System.err.println(" Error al insertar Persona: "+e);
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

	public int modificarMedio(PersonaBean persona){
		int mod = 0;
		CallableStatement cst = null;
		Connection con = null;
		con =conexionMySQL.getConnection();
		String sql = "{call sp_persona(?,?,?,?,?,?,?,?,?,?)}";
		try{
			cst = (CallableStatement) con.prepareCall(sql);
			cst.setInt(1, persona.getCodigoP());
			cst.setString(2, persona.getNombreAr());
			cst.setString(3, persona.getNombreP());
			cst.setString(4, persona.getApellidoP());
			cst.setString(5, persona.getEmailP());
			cst.setString(6, persona.getNombrePa());			
			cst.setString(7, persona.getTelefonoP());
			cst.setString(8, persona.getComentarioP());
			cst.setString(9, null);
			cst.setInt(10, 2);
			mod = cst.executeUpdate();
			if(mod>0){
				System.out.println(" Persona Modificado Correctamente: "+mod);
			}
		}catch(SQLException e){
			System.err.println(" Error al Modificar Persona: "+e);
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
	
	public int eliminarPersona(PersonaBean persona){
		int eli = 0;
		CallableStatement cst = null;
		Connection con = null;
		con =conexionMySQL.getConnection();
		String sql = "{call sp_persona(?,?,?,?,?,?,?,?,?,?)}";
		try{
			cst = (CallableStatement) con.prepareCall(sql);
			cst.setInt(1, persona.getCodigoP());
			cst.setString(2, null);	
			cst.setString(3, null);
			cst.setString(4, null);
			cst.setString(5, null);
			cst.setString(6, null);			
			cst.setString(7, null);
			cst.setString(8, null);
			cst.setString(9, null);
			cst.setInt(10, 3);
			eli = cst.executeUpdate();
			if(eli>0){
				System.out.println(" Persona Eliminado Correctamente: "+eli);
			}
		}catch(SQLException e){
			System.err.println(" Error al Eliminar Persona: "+e);
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
