package jdbc;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.logging.Level;

import pojos.Clientes;
import pojos.Empleados;
import pojos.Menus;
import pojos.Pedidos;
import interfaz.MenuServidor;
import jdbc.DBManager;


public class JDBCManager implements DBManager{
	
	final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	private final String addEmpleado = "INSERT INTO Empleados (Nombre,Salario,Cargo_Id) VALUES (?,?,?);";
	private final String addCliente = "INSERT INTO Clientes (Nombre,Telefono,Email) VALUES (?,?,?);";
	private final String addPedido = "INSERT INTO Pedidos (Cliente_Id, Fecha,Coste, Direccion,Hora) VALUES (?,?,?)";
	private final String addMenu = "INSERT INTO Menus (Plato,Precio) VALUES (?,?)";
	private final String searchEmpleado = "SELECT * FROM Empleados;";
	private final String searchCliente = "SELECT * FROM Clientes;";
	private final String searchClienteByEmail = "SELECT * FROM Clientes WHERE Email = ?;";
	private final String searchPedido = "SELECT * FROM Pedidos;";
	private final String searchMenu = "SELECT * FROM Menus;";
	private final String searchEmpleadoById = "SELECT * FROM Empleados WHERE Id = ?;";
	private final String searchEmpleadoByNombre = "SELECT * FROM Empleados WHERE Nombre = ?;";
	private final String searchClienteByNombre = "SELECT * FROM Clientes WHERE Nombre = ?;";
	private final String searchMenuByNombre = "SELECT * FROM Menus WHERE Plato = ?;";
	private final String eliminarEmpleado = "DELETE FROM Empleados WHERE Nombre LIKE ?;";
	private final String eliminarMenu = "DELETE FROM Menus WHERE Plato LIKE ?;";
	private final String eliminarCliente = "DELETE FROM Clientes WHERE Nombre"+ " LIKE ?;";
	private Connection c;
	
	
	public void initializeDB() {
		try {
			Statement stmt = c.createStatement();
			stmt.executeUpdate("CREATE TABLE IF NOT EXISTS "
					+ "Empleados (Id INTEGER PRIMARY KEY, Nombre STRING, Salario INTEGER, Cargo_Id INTEGER)");
			stmt.executeUpdate("CREATE TABLE IF NOT EXISTS "
					+ "Menus (Id INTEGER PRIMARY KEY, Plato STRING, Precio FLOAT)");
			stmt.executeUpdate("CREATE TABLE IF NOT EXISTS "
					+ "Clientes (Id INTEGER PRIMARY KEY, Nombre STRING, Telefono INTEGER, Email STRING)");
			stmt.executeUpdate("CREATE TABLE IF NOT EXISTS "
					+ "Pedidos (Id INTEGER PRIMARY KEY, Cliente_Id INTEGER REFERENCES Clientes(Id), Fecha DATE, Coste INTEGER, Direccion STRING, Hora TIME, Repartidor_Id INTEGER REFERENCES Empleados(Id))");
			stmt.close();
		} catch (SQLException e) {
			LOGGER.severe("Error al crear las tablas");
			e.printStackTrace();
		}
	}
	
	public void connect() {
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:restaurante.db");
			Statement stmt = c.createStatement();
			stmt.execute("PRAGMA foreign_keys=ON");
			stmt.close();
			initializeDB();
			LOGGER.info("Se ha establecido la conexión con la BD");
		} catch (ClassNotFoundException e) {
			LOGGER.severe("No se ha encontrado la clase org.sqlite.JDBC");
			e.printStackTrace();
		} catch (SQLException e) {
			LOGGER.severe("Error al crear la conexión con la DB");
			e.printStackTrace();
		}		
	}
	
	public void disconnect() {
		try {
			c.close();
		} catch (SQLException e) {
			LOGGER.severe("Error al cerrar la conexión con la BD");
			e.printStackTrace();
		}
	}

	@Override
	public void addEmpleado(Empleados empleado) {
		// TODO Auto-generated method stub
		try {
			PreparedStatement prep = c.prepareStatement(addEmpleado);
			prep.setString(1, empleado.getNombre());
			prep.setInt(2, empleado.getSalario());
			prep.setInt(3, empleado.getCargoId());
			prep.executeUpdate();
			prep.close();
		} catch (SQLException e) {
			LOGGER.severe("Error al insertar empleado: " + empleado);
			e.printStackTrace();
		}
		
	}
	
	public void addCliente(Clientes cliente) {
		// TODO Auto-generated method stub
		try {
			PreparedStatement prep = c.prepareStatement(addCliente);
			prep.setString(1, cliente.getNombre());
			prep.setInt(2, cliente.getTelefono());
			prep.setString(3, cliente.getEmail());
			prep.executeUpdate();
			prep.close();
		} catch (SQLException e) {
			LOGGER.severe("Error al insertar cliente: " + cliente);
			e.printStackTrace();
		}
		
	}
	
	@Override
	public void addPedido(Pedidos pedido) {
		try {
			PreparedStatement prep = c.prepareStatement(addPedido);
			prep.setInt(1, pedido.getClienteId());
			prep.setDate(2, pedido.getFecha());
			prep.setFloat(3, pedido.getCoste());
			prep.setString(4, pedido.getDireccion());
			prep.setTime(5,pedido.getHora());
			prep.executeUpdate();
			prep.close();
		} catch (SQLException e) {
			LOGGER.severe("Error al insertar el pedido: " + pedido);
			e.printStackTrace();
		}
	}

	public void addMenu(Menus menu) {
		try {
			PreparedStatement prep = c.prepareStatement(addMenu);
			prep.setString(1, menu.getPlato());
			prep.setFloat(2, menu.getPrecio());
			prep.executeUpdate();
			prep.close();
		} catch (SQLException e) {
			LOGGER.severe("Error al insertar el menu: " + menu);
			e.printStackTrace();
		}
	}

	
	@Override
	public List<Empleados> searchEmpleados() {
		List<Empleados> empleados = new ArrayList<Empleados>();
		try {
			Statement stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery(searchEmpleado);
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
	
	public Empleados searchEmpleadoById(int id) {
		Empleados empleado = null;
		try {
			PreparedStatement prep = c.prepareStatement(searchEmpleadoById);
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
	
	public List<Empleados> searchEmpleadoByNombre(String nombre) {
		List<Empleados> empleados = new ArrayList<Empleados>();
		try {
			PreparedStatement prep = c.prepareStatement(searchEmpleadoByNombre);
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
	
	
	public List<Clientes> searchClientes() {
		List<Clientes> clientes = new ArrayList<Clientes>();
		try {
			Statement stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery(searchCliente);
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
	
	public Clientes searchClienteByEmail(String email) {
		Clientes cliente = new Clientes();
		try {
			PreparedStatement prep = c.prepareStatement(searchClienteByEmail);
			prep.setString(1,email + "");
			ResultSet rs = prep.executeQuery();
			while(rs.next()) {
				int id = rs.getInt("Id");
				String nombre = rs.getString("Nombre");
				int telefono = rs.getInt("Telefono");
				cliente = new Clientes (id,nombre,telefono,email);
			prep.close();
			}
		} catch (SQLException e) {
			LOGGER.severe("Error al hacer un SELECT");
			e.printStackTrace();
		}
		return cliente;
	}
		
	
	public List<Pedidos> searchPedidos() {
		List<Pedidos> pedidos = new ArrayList<Pedidos>();
		try {
			Statement stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery(searchPedido);
			while(rs.next()){
				int id = rs.getInt("Id");
				int cliente_id = rs.getInt("Cliente_Id");
				Date fecha = rs.getDate("Fecha");
				float coste = rs.getFloat("Coste");
				String direccion = rs.getString("Direccion");
				Time hora = rs.getTime("Hora");
				int idEmpleado = rs.getInt("Repartidor_Id");
				Empleados repartidor = searchEmpleadoById(idEmpleado);
				Pedidos pedido = new Pedidos(id,cliente_id,fecha,coste,direccion,hora,repartidor);
				pedidos.add(pedido);
				LOGGER.fine("Pedido: " + pedido);
			}
			rs.close();
			stmt.close();
		} catch (SQLException e) {
			LOGGER.severe("Error al hacer un SELECT");
			e.printStackTrace();
		}
		return pedidos;
		
	}
		
	public List<Menus> searchMenu() {
		List<Menus> menus = new ArrayList<Menus>();
		try {
			Statement stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery(searchMenu);
			while(rs.next()){
				int id = rs.getInt("Id");
				String plato = rs.getString("Plato");
				float precio = rs.getFloat("Precio");
				Menus menu = new Menus(id,plato,precio);
				menus.add(menu);
				LOGGER.fine("Menu: " + menu);
			}
			rs.close();
			stmt.close();
		} catch (SQLException e) {
			LOGGER.severe("Error al hacer un SELECT");
			e.printStackTrace();
		}
		return menus;
	}
	
	public List<Clientes> searchCliente() {
		List<Clientes> clientes = new ArrayList<Clientes>();
		try {
			Statement stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery(searchCliente);
			while(rs.next()){
				int id = rs.getInt("Id");
				String nombre = rs.getString("Nombre");
				int telefono=rs.getInt("Telefono"); 
				String email = rs.getString("Email");
				Clientes cliente = new Clientes(id,nombre,telefono,email);
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
	
	public List<Menus> searchMenuByNombre(String menu) {
		List<Menus> menus = new ArrayList<Menus>();
		try {
			PreparedStatement prep = c.prepareStatement(searchMenuByNombre);
			prep.setString(1,menu + "");
			ResultSet rs = prep.executeQuery();
			while(rs.next()) {
				int id = rs.getInt("Id");
				String plato = rs.getString("Plato");
				int precio = rs.getInt("Precio");
				Menus menu1 = new Menus (id,plato,precio);
				menus.add(menu1);
				LOGGER.fine("Menu: " + menu1);
			}
			prep.close();
		} catch (SQLException e) {
			LOGGER.severe("Error al hacer un SELECT");
			e.printStackTrace();
		}
		return menus;
	}
	
	public List<Clientes> searchClienteByNombre(String cliente) {
		List<Clientes> clientes = new ArrayList<Clientes>();
		try {
			PreparedStatement prep = c.prepareStatement(searchClienteByNombre);
			prep.setString(1,cliente + "");
			ResultSet rs = prep.executeQuery();
			while(rs.next()) {
				int id = rs.getInt("Id");
				String nombre = rs.getString("Nombre");
				int telefono=rs.getInt("Telefono"); 
				String email = rs.getString("Email");
				Clientes cliente1 = new Clientes (id,telefono,email);
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
	
	public boolean eliminarEmpleado(String nombreEmpleado) {
		boolean existe = false;
		try {
			PreparedStatement prep = c.prepareStatement(eliminarEmpleado);
			prep.setString(1,"%" + nombreEmpleado + "%");
			int res = prep.executeUpdate();
			if(res > 0)
				existe = true;
			prep.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return existe;
	}
	
	public boolean eliminarMenu(String nombreMenu) {
		boolean existe = false;
		try {
			PreparedStatement prep = c.prepareStatement(eliminarMenu);
			prep.setString(1,"%" + nombreMenu + "%");
			int res = prep.executeUpdate();
			if(res > 0)
				existe = true;
			prep.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return existe;
	}
	
	public boolean eliminarCliente(String nombreCliente) {
		boolean existe = false;
		try {
			PreparedStatement prep = c.prepareStatement(eliminarCliente);
			prep.setString(1,"%" + nombreCliente + "%");
			int res = prep.executeUpdate();
			if(res > 0)
				existe = true;
			prep.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return existe;
	}
	

}
