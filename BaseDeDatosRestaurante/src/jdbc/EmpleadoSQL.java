package jdbc;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import pojos.Empleados;
	
public class EmpleadoSQL {
		private static Connection c;
		final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
		
		public static void añadirEmpleado(Empleados empleado) throws SQLException, IOException{
			Connection c =Conexion.connect();
			Statement stmt = c.createStatement();
			String sql = "INSERT INTO Empleados (Nombre, Cargo_id, Zona_id, Salario) "
					+ "VALUES ('" + empleado.getNombre() + "', '" + empleado.getCargoId()	+ "', '" + "', '" + empleado.getSalario() + "');";
			stmt.executeUpdate(sql);
			stmt.close();
			Conexion.disconnect(c);
				
		}	
		
		
		public static boolean eliminarEmpleado(String nombreEmpleado) {
			boolean existe = false;
			try {
				PreparedStatement prep = c.prepareStatement("DELETE FROM Empleados WHERE Nombre LIKE ?;");
				prep.setString(1,"%" + nombreEmpleado + "%");
				int res = prep.executeUpdate();
				if(res > 0)
					existe = true;
				prep.close();
				Conexion.disconnect(c);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return existe;
		}
		
		public static boolean eliminarEmpleadoId(int id) throws SQLException, IOException{
			boolean existe = false;
			Connection c =Conexion.connect();
			String sql = "DELETE FROM Empleados WHERE id = ?";
			PreparedStatement prep = c.prepareStatement(sql);
			prep.setInt(1, id);
			int res = prep.executeUpdate();
			if(res > 0)
				System.out.println("\nBorrado completado");
				existe = true;
			prep.close();
			Conexion.disconnect(c);
		
			return existe;		
		}
		
		
		public static void mostarEmpleados(Connection c) throws SQLException {
			Statement stmt = c.createStatement();
			String sql = "SELECT * FROM Empleados";
			ResultSet rs = stmt.executeQuery(sql);
			if(rs != null) {
			while (rs.next()) {
				int id = rs.getInt("Id");
				String nombre = rs.getString("Nombre");
				int salario = rs.getInt("Salario");
				int cargo_id = rs.getInt("Cargo_id");	
				String cargo = CargoSQL.getNombre(cargo_id,c);
				Empleados empleado = new Empleados(id, nombre, salario, cargo_id);
				empleado.setId(id);
				System.out.println(empleado + "nombreCargo: "+ cargo);
			}
			}
			rs.close();
			stmt.close();
		}
		public static List<Empleados> buscarEmpleados() {
		List<Empleados> empleados = new ArrayList<Empleados>();
		try {
			Statement stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM Empleados;");
			while(rs.next()){
				int id = rs.getInt("Id");
				String nombre = rs.getString("Nombre");
				int salario = rs.getInt("Salario");
				int cargoid = rs.getInt("Cargo_Id");
				Empleados empleado = new Empleados (id, nombre, salario , cargoid);
				empleados.add(empleado);
				LOGGER.fine("Empleado: " + empleado);
			}
			rs.close();
			stmt.close();
		} catch (SQLException e) {
			LOGGER.severe("Error al hacer un SELECT");
			e.printStackTrace();
		}
		return empleados;
	}
		public static Empleados buscarEmpleadosId(int id) {
		Empleados empleado = null;
		try {
			PreparedStatement prep = c.prepareStatement("SELECT * FROM Empleados WHERE Id = ?;");
			prep.setString(1,id + "");
			ResultSet rs = prep.executeQuery();
			while(rs.next()) {
				String nombre = rs.getString("Nombre");
				int salario = rs.getInt("Salario");
				int cargoid = rs.getInt("Cargo_Id");
				empleado = new Empleados (id, nombre, salario , cargoid);
				LOGGER.fine("Empleado: " + empleado);
			}
			prep.close();
		} catch (SQLException e) {
			LOGGER.severe("Error al hacer un SELECT");
			e.printStackTrace();
		}
		return empleado;
	}
	
		public static List<Empleados> buscarEmpleadosNombre(String nombre) {
			List<Empleados> empleados = new ArrayList<Empleados>();
			try {
				PreparedStatement prep = c.prepareStatement("SELECT * FROM Empleados WHERE Nombre = ?;");
				prep.setString(1,nombre + "");
				ResultSet rs = prep.executeQuery();
				while(rs.next()) {
					int id = rs.getInt("Id");
					int salario = rs.getInt("Salario");
					int cargoid = rs.getInt("Cargo_Id");
					Empleados empleado = new Empleados (id, nombre, salario , cargoid);
					empleados.add(empleado);
					LOGGER.fine("Empleado: " + empleado);
				}
				prep.close();
			} catch (SQLException e) {
				LOGGER.severe("Error al hacer un SELECT");
				e.printStackTrace();
			}
			return empleados;
		}
		/*public static Cargo getCargos(int cargo_id, Connection c) throws SQLException {
			Statement stmt = c.createStatement();
			String sql = "SELECT * FROM Cargos WHERE id = "+ cargo_id;
			ResultSet rs = stmt.executeQuery(sql);
			rs.next();
			int id = rs.getInt("Id");
			String nombre = rs.getString("Nombre");
			Cargo cargo = new Cargo( nombre);
			cargo.setId(id);
			rs.close();
			stmt.close();
			return cargo;
		}
		/*public static void search(String searchname) throws SQLException {
			Connection c =Conexion.connect();
			String sql = "SELECT * FROM Empleados Where nombre LIKE ? ";
			PreparedStatement prep = c.prepareStatement(sql);
			prep.setString(1, searchname);
			ResultSet rs = prep.executeQuery();
			if(rs != null) {
			while(rs.next()) {
				int id = rs.getInt("Id");
				String name = rs.getString("Nombre");
				int cargo_id = rs.getInt("Cargo_id");
				String nombreCargo = SQLCargos.getNombreCargo(cargo_id, c);
				int zona_id = rs.getInt("Zona_id");
				String nombreZona = SQLZonas.getNombreZona(zona_id, c);
				int sueldo = rs.getInt("Sueldo");
				System.out.println("Id: " + id + "\nNombre: "+ name + "\nCargo: " + nombreCargo +
						"\nZona: " + nombreZona + "\nSueldo: " + sueldo);
			}
			}else {
				System.out.println("No hubo resultados");
			}
			
			// CLOSE Statement
			rs.close();
			prep.close();
			System.out.println("Busqueda Completada");
			Conexion.disconnect(c);
		}
		*/


		public static void actualizarEmpleado(int id, int salario) throws SQLException, IOException{
			Connection c =Conexion.connect();
			String sql = "UPDATE Empleados SET salario =? WHERE id =?";
			PreparedStatement prep = c.prepareStatement(sql);
			prep.setInt(1, salario);
			prep.setInt(2, id);
			prep.executeUpdate();
			System.out.println("\n Update terminado");
			Conexion.disconnect(c);
				
		}


		public static void borrarTabla() throws SQLException, IOException {
			
			Connection c =Conexion.connect();
			
			Statement stmt1 = c.createStatement();
			String sql1 = "Drop table Empleados" ;
			stmt1.executeUpdate(sql1);
			stmt1.close();
			System.out.println("\nTabla Empleados borrada");
			
			Conexion.disconnect(c);
					
		}
	
}
