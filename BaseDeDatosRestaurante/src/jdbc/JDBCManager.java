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
import jdbc.ClienteSQL;
import pojos.Clientes;
import pojos.Empleados;
import pojos.Menus;
import pojos.Pedidos;
import interfaz.MenuServidor;
import jdbc.DBManager;
import jdbc.Conexion;
import jdbc.EmpleadoSQL;
import jdbc.MenuSQL;

public class JDBCManager implements DBManager{
	
	final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	private final String addEmpleado = "INSERT INTO Empleados (Nombre,Salario,Cargo_Id) VALUES (?,?,?);";
	private final String addCliente = "INSERT INTO Clientes (Nombre,Telefono,Email) VALUES (?,?,?);";
	private final String addPedido = "INSERT INTO Pedidos (Cliente_Id, Fecha,Coste, Direccion,Hora) VALUES (?,?,?)";
	private final String addMenu = "INSERT INTO Menus (Plato,Precio) VALUES (?,?)";
	private final String searchEmpleado = "SELECT * FROM Empleados;";
	private final String searchCliente = "SELECT * FROM Clientes;";
	private final String searchPedido = "SELECT * FROM Pedidos;";
	private final String searchMenu = "SELECT * FROM Menus;";
	private final String searchEmpleadoById = "SELECT * FROM Empleados WHERE Id = ?;";
	private final String searchEmpleadoByNombre = "SELECT * FROM Empleados WHERE Nombre = ?;";
	private final String searchClienteByNombre = "SELECT * FROM Clientes WHERE Nombre = ?;";
	private final String searchMenuByNombre = "SELECT * FROM Menus WHERE Plato = ?;";
	private final String eliminarEmpleado = "DELETE FROM Empleados WHERE Nombre LIKE ?;";
	private final String eliminarMenu = "DELETE FROM Menus WHERE Plato LIKE ?;";
	private final String eliminarCliente = "DELETE FROM Clientes WHERE Nombre = ?"
			+ " LIKE ?;";
			
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
	
	public Connection connect() {
		
		 return Conexion.connect();
	}
	
	public void disconnect(Connection c) {
		try {
			Conexion.disconnect(c);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void addCliente(Clientes cliente){
		
		try {
			ClienteSQL.añadirCliente(cliente);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void addEmpleado(Empleados empleado) {
		try {
			EmpleadoSQL.añadirEmpleado(empleado);
		} catch (SQLException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
		
	
	
	public void addPedido(Pedidos pedido) {
		PedidosSQL.añadirPedido(pedido);
	}

	public void addMenu(Menus menu) {
		MenuSQL.añadirMenu(menu);
	}

	
	public List<Empleados> searchEmpleados() {
	
		return EmpleadoSQL.buscarEmpleados();		
	}
	
	
	public Empleados searchEmpleadoById(int id) {
		
		return EmpleadoSQL.buscarEmpleadosId(id);
	}
	
	
	public List<Empleados> searchEmpleadoByNombre(String nombre) {
		
		return EmpleadoSQL.buscarEmpleadosNombre(nombre);
		
	}
	public List<Clientes> searchClientes() {
		
		return ClienteSQL.buscarClientes();
	}

	public List<Clientes> searchClienteByNombre(String cliente) {
	
		return ClienteSQL.buscarClienteNombre(cliente);
	}
	
	public List<Pedidos> searchPedidos() {
		
		return PedidosSQL.buscarPedidos();
	}
		
	public List<Menus> searchMenu() {
		
		return MenuSQL.buscarMenu();
	}
	
	public List<Menus> searchMenuByNombre(String menu) {
		
		return MenuSQL.buscarMenuNombre(menu);
	}
	
	public boolean deleteEmpleado(String nombre) {
		return EmpleadoSQL.eliminarEmpleado(nombre);
		
	}
			
	public boolean deleteEmpleadoId(int id) {
		try {
			return EmpleadoSQL.eliminarEmpleadoId(id);
		} catch (SQLException | IOException e) {
			e.printStackTrace();
		}
		return false;
		
	}
	
	public boolean deleteCliente(String nombre) {
		try {
			return ClienteSQL.eliminarCliente(nombre);
		} catch (SQLException | IOException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean deleteMenu(String nombreMenu) {
		
		return MenuSQL.eliminarMenu(nombreMenu);
	}

	@Override
	public void printEmpleados(Connection c) {
		try {
			EmpleadoSQL.mostarEmpleados(c);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	public void printClientes(Connection c) {
	try {
		ClienteSQL.mostrarClientes(c);
	} catch (SQLException | IOException e) {
	
		e.printStackTrace();
	}
		
	}
	@Override
	public void printPedidos(Connection c) {
		try {
			PedidosSQL.mostarPedidos(c);
		} catch (SQLException e) {
		
			e.printStackTrace();
		}
		
	}

	@Override
	public void updateEmpleado(int id, int salario) {
		try {
			EmpleadoSQL.actualizarEmpleado(id, salario);
		} catch (SQLException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public void updateMenu(int id, int precio) {
		// TODO Auto-generated method stub
		
	}

	/*@Override
	public void updateMenu(int id, int precio) {
		try {
			MenuSQL.actualizarMenu(id, precio);
		} catch (SQLException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	} falta funcion actualizarMenu(id, precio)
	*/

}
