package jdbc;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.logging.Logger;

import pojos.Clientes;
import pojos.Empleados;
import pojos.Menus;
import pojos.Pedidos;

public class JDBCManager implements DBManager{
	
	final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);			
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
		try {
			MenuSQL.actualizarMenu(id, precio);
		} catch (SQLException | IOException e) {
			e.printStackTrace();
		}		
	}
}
