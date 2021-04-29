package jdbc;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import pojos.Clientes;

	
public class ClienteSQL {
	private static Connection c;
	
	public static void addCliente(Clientes cliente) throws SQLException, IOException{
		Connection c =Conexion.connect();
		Statement stmt = c.createStatement();
		String sql = "INSERT INTO Clientes (Nombre,Telefono,Email)" + "VALUES ('" + cliente.getNombre() + "', '" + cliente.getTelefono()	+ "', '" + cliente.getEmail() + "');";
		stmt.executeUpdate(sql);
		stmt.close();
		Conexion.disconnect(c);
			
	}	
	
		
		
		/*public static void buscarDatos() throws SQLException, IOException {
			Connection c =Conexion.openConnection();
			//  SQLSearch

			BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
			System.out.print("Nombre cliente: ");
			String searchName = reader.readLine();
			
			
			String sql = "SELECT * FROM Clientes Where nombre LIKE ? ";
			PreparedStatement prep = c.prepareStatement(sql);
			prep.setString(1, searchName);
			ResultSet rs = prep.executeQuery();
			if(rs != null) {
			while(rs.next()) {
				int id = rs.getInt("Id");
				String name = rs.getString("Nombre");
				int edad = rs.getInt("Edad");
				int altura = rs.getInt("Altura");
				String fecha_entrada = rs.getString("Fecha_entrada");
				String fecha_salida = rs.getString("Fecha_salida");
				boolean numerosa = rs.getBoolean("Familia_numerosa");
				int puesto_id =  rs.getInt("Puesto_id");
				String nombrePuesto = SQLPuestos.getNombrePuesto(puesto_id,c);
				int atraccion_id =  rs.getInt("Cargo_id");
				String nombreAtraccion = SQLAtracciones.getNombreAtraccion(atraccion_id,c);
				
				
				System.out.println(id + ": "+ name + ", " + edad + ", " + altura + ", " + fecha_entrada
						+ ", " + fecha_salida + ", "+ "familia numerosa: "+ numerosa + ", " + nombrePuesto + ", " + nombreAtraccion);
			}
			}else {
				System.out.println("No hubo resultados");
			}
				
			
			Conexion.closeConnection(c);
				
		}
		
		*/
		public static void borrarTabla() throws SQLException {
			
			Connection c = Conexion.connect();
			
			Statement stmt = c.createStatement();
			String sql = "Drop table Clientes" ;
			stmt.executeUpdate(sql);
			stmt.close();
			System.out.println("\n Tabla Clientes borrada");
			Conexion.disconnect(c);
					
		}
		public boolean eliminarCliente(String nombreCliente) throws SQLException, IOException {
			boolean existe = false;
				PreparedStatement prep = c.prepareStatement("DELETE FROM Empleados WHERE Nombre LIKE ?;");
				prep.setString(1,"%" + nombreCliente + "%");
				int res = prep.executeUpdate();
				if(res > 0)
					existe = true;
				prep.close();
				Conexion.disconnect(c);
			return existe;
		}
		
		public static void mostrarClientes(Connection c) throws SQLException, IOException {
					
			System.out.println("Imprimiendo Clientes");
			Statement stmt = c.createStatement();
			String sql = "SELECT * FROM Clientes";
			ResultSet rs = stmt.executeQuery(sql);
			if (rs!= null) {
			while (rs.next()) {
				int id = rs.getInt("Id");
				String nombre = rs.getString("Nombre");
				int telefono = rs.getInt("Telefono");
				String email = rs.getString("Email");
				System.out.println(id + ": "+ nombre + ", " + telefono + ", " + email);
				
			}
			}else System.out.println("No existen datos");
			rs.close();
			stmt.close();
		}
		
		public List<Clientes> searchClientes() {
			List<Clientes> clientes = new ArrayList<Clientes>();
			try {
				Statement stmt = c.createStatement();
				ResultSet rs = stmt.executeQuery("SELECT * FROM Clientes;");
				while(rs.next()){
					int id = rs.getInt("Id");
					String nombre = rs.getString("Nombre");
					int telefono = rs.getInt("Telefono");
					String email = rs.getString("Email");
					Clientes cliente = new Clientes (id, nombre, telefono , email);
					clientes.add(cliente);
					//LOGGER.fine("Cliente: " + cliente);
				}
				rs.close();
				stmt.close();
			} catch (SQLException e) {
				//LOGGER.severe("Error al hacer un SELECT");
				e.printStackTrace();
			}
			return clientes;
		}
	}
		

