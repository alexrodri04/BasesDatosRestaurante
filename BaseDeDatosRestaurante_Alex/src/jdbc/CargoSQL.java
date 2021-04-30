package jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CargoSQL {
	
	public static int getId(String nombre, Connection c) throws SQLException {
		int id =0;
		String sql = "SELECT id FROM Cargos Where Nombre LIKE ? ";
		PreparedStatement prep = c.prepareStatement(sql);
		prep.setString(1, nombre);
		ResultSet rs = prep.executeQuery();
		if(rs != null) {
		while(rs.next()) {

			id =  rs.getInt("Id");
		}
		
		}else {
			System.out.println("No hubo resultados");
		}
		
		// CLOSE Statement
		rs.close();
		prep.close();
		return id;
		
	}
	
	public static String getNombre(int id, Connection c) throws SQLException {
		String nombre = null;
		String sql = "SELECT Nombre FROM Cargos Where id LIKE ? ";
		PreparedStatement prep = c.prepareStatement(sql);
		prep.setInt(1, id);
		ResultSet rs = prep.executeQuery();
		if(rs != null) {
		while(rs.next()) {

			 nombre = rs.getString("Nombre");
		}
		
		}else {
			System.out.println("No hubo resultados");
		}
		
		// CLOSE Statement
		rs.close();
		prep.close();
		
		return nombre;
		
	}
	
	public static void borrarTabla() throws SQLException {
		
		Connection c =Conexion.connect();
		
		Statement stmt1 = c.createStatement();
		String sql1 = "Drop table Cargos" ;
		stmt1.executeUpdate(sql1);
		stmt1.close();
		System.out.println("\nTabla Cargo borrada");
		Conexion.disconnect(c);
	}
				

	public static void printCargos(Connection c) throws SQLException {
		Statement stmt = c.createStatement();
		String sql = "SELECT * FROM Cargos";
		ResultSet rs = stmt.executeQuery(sql);
		while (rs.next()) {
			int id = rs.getInt("Id");
			String nombre = rs.getString("Nombre");
			System.out.println("id: " + id + " Nombre: "+ nombre);
		}
		rs.close();
		stmt.close();
	}

}

