package jdbc;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import pojos.Empleados;
	
public class EmpleadoSQL {
		private static Connection c;
		
		public static void obtenerInfo() throws SQLException{
			Connection c = Conexion.connect();
			printEmpleados(c);
			Conexion.disconnect(c);
			
		}
		
		public static void addEmpleado(Empleados empleado) throws SQLException, IOException{
			Connection c =Conexion.connect();
			Statement stmt = c.createStatement();
			String sql = "INSERT INTO Empleados (Nombre, Cargo_id, Zona_id, Sueldo) "
					+ "VALUES ('" + empleado.getNombre() + "', '" + empleado.getCargoId()	+ "', '" + "', '" + empleado.getSalario() + "');";
			stmt.executeUpdate(sql);
			stmt.close();
			Conexion.disconnect(c);
				
		}	
		
		
		/*public static void buscarDatos() throws SQLException, IOException{
			Connection c =Conexion.openConnection();
			//  SQLSearch
			
			BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
			System.out.print("Nombre empleado: ");
			String searchName = reader.readLine();
			
			search(searchName);
			
				Conexion.closeConnection(c);
				
		}*/
		
		public static void updateEmpleado(int id, int salario) throws SQLException, IOException{
			Connection c =Conexion.connect();
			String sql = "UPDATE Empleados SET salario =? WHERE id =?";
			PreparedStatement prep = c.prepareStatement(sql);
			prep.setInt(1, salario);
			prep.setInt(2, id);
			prep.executeUpdate();
			System.out.println("\n Update terminado");
			Conexion.disconnect(c);
				
		}
		
		
		public boolean eliminarEmpleado(String nombreEmpleado) {
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
				// TODO Auto-generated catch block
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
		
		
		public static void printEmpleados(Connection c) throws SQLException {
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
		public static void borrarTabla() throws SQLException, IOException {
			
			Connection c =Conexion.connect();
			
			Statement stmt1 = c.createStatement();
			String sql1 = "Drop table Empleados" ;
			stmt1.executeUpdate(sql1);
			stmt1.close();
			System.out.println("\nTabla Empleados borrada");
			
			Conexion.disconnect(c);
					
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
	
}
