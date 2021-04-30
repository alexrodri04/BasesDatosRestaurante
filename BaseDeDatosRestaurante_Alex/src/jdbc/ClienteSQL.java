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

import pojos.Clientes;

	
public class ClienteSQL {
	private static Connection c;
	final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	public static void añadirCliente(Clientes cliente) throws SQLException, IOException{
		Connection c =Conexion.connect();
		Statement stmt = c.createStatement();
		String sql = "INSERT INTO Clientes (Nombre,Telefono,Email)" + "VALUES ('" + cliente.getNombre() + "', '" + cliente.getTelefono()	+ "', '" + cliente.getEmail() + "');";
		stmt.executeUpdate(sql);
		stmt.close();
		Conexion.disconnect(c);
			
	}	
	
		public static void borrarTabla() throws SQLException {
			
			Connection c = Conexion.connect();
			
			Statement stmt = c.createStatement();
			String sql = "Drop table Clientes" ;
			stmt.executeUpdate(sql);
			stmt.close();
			System.out.println("\n Tabla Clientes borrada");
			Conexion.disconnect(c);
					
		}
		//HABRIA QUE CAMBIARLO A ELIMINAR POR EMAIL
		public static boolean eliminarCliente(String nombreCliente) throws SQLException, IOException {
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
		// ambos hacen lo mismo, uno devuelve una lista, otro los imprime por pantalla
		
		public static List<Clientes> buscarClientes() {
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
					LOGGER.fine("Cliente: " + cliente);
				}
				rs.close();
				stmt.close();
			} catch (SQLException e) {
				LOGGER.severe("Error al hacer un SELECT");
				e.printStackTrace();
			}
			return clientes;
		}
		
		public static List<Clientes> buscarClienteNombre(String cliente) {
			List<Clientes> clientes = new ArrayList<Clientes>();
			try {
				PreparedStatement prep = c.prepareStatement("SELECT * FROM Clientes WHERE Nombre = ?;");
				prep.setString(1,cliente + "");
				ResultSet rs = prep.executeQuery();
				while(rs.next()) {
					int id = rs.getInt("Id");
					String nombre = rs.getString("Nombre");
					int telefono=rs.getInt("Telefono"); 
					String email = rs.getString("Email");
					Clientes cliente1 = new Clientes (id,nombre,telefono,email);
					clientes.add(cliente1);
					LOGGER.fine("Cliente: " + cliente1);
				}
				prep.close();
			} catch (SQLException e) {
				LOGGER.severe("Error al hacer un SELECT");
				e.printStackTrace();
			}
			return clientes;
		}
	}
		

