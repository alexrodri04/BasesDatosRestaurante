package jdbc;


import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import pojos.Cargos;
import pojos.Clientes;
import pojos.Empleados;
import pojos.Jefes;
import pojos.Menus;
import pojos.Pedidos;



public class JDBCManager implements DBManager{
	
	final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	private final String addEmpleado = "INSERT INTO Empleados (Nombre,Salario,Cargo_Id) VALUES (?,?,?);";
	private final String addCliente = "INSERT INTO Clientes (Nombre,Telefono,Email) VALUES (?,?,?);";
	private final String addPedido = "INSERT INTO Pedidos (Cliente_Id,Coste, Direccion,Hora,Repartidor_Id) VALUES (?,?,?,?,?)";
	private final String addPedido_Menu = "INSERT INTO Pedidos_Menus (Id_Pedido,Id_Menu) VALUES (?,?)";
	private final String addMenu = "INSERT INTO Menus (Plato,Precio) VALUES (?,?)";
	private final String addJefe = "INSERT INTO Jefes (Nombre) VALUES (?)";
	private final String addCargo = "INSERT INTO Cargos (Nombre, Jefe_Id) VALUES (?,?)";
	private final String searchEmpleado = "SELECT * FROM Empleados;";
	private final String searchCliente = "SELECT * FROM Clientes;";
	private final String searchClienteByEmail = "SELECT * FROM Clientes WHERE Email = ?;";
	private final String searchPedido = "SELECT * FROM Pedidos;";
	private final String searchMenu = "SELECT * FROM Menus;";
	private final String searchJefe = "SELECT * FROM Jefes;";
	private final String searchCargo = "SELECT * FROM Cargos;";
	private final String searchEmpleadoById = "SELECT * FROM Empleados WHERE Id = ?;";
	private final String searchEmpleadoByNombre = "SELECT * FROM Empleados WHERE Nombre = ?;";
	private final String searchClienteByNombre = "SELECT * FROM Clientes WHERE Nombre = ?;";
	private final String searchMenuByNombre = "SELECT * FROM Menus WHERE Plato = ?;";
	private final String eliminarEmpleado = "DELETE FROM Empleados WHERE Nombre LIKE ?;";
	private final String eliminarMenu = "DELETE FROM Menus WHERE Plato LIKE ?;";
	private final String eliminarCliente = "DELETE FROM Clientes WHERE Nombre"+ " LIKE ?;";
	private final String updateEmpleado = "UPDATE Empleados SET salario =? WHERE id =?";
	private Connection c;
	
	
	public void initializeDB() {
		try {
			Statement stmt = c.createStatement();
			stmt.executeUpdate("CREATE TABLE IF NOT EXISTS "
					+ "Empleados (Id INTEGER PRIMARY KEY, Nombre STRING, Salario INTEGER, Cargo_Id INTEGER REFERENCES Cargos(Id))");
			stmt.executeUpdate("CREATE TABLE IF NOT EXISTS "
					+ "Menus (Id INTEGER PRIMARY KEY, Plato STRING, Precio FLOAT)");
			stmt.executeUpdate("CREATE TABLE IF NOT EXISTS "
					+ "Clientes (Id INTEGER PRIMARY KEY, Nombre STRING, Telefono INTEGER, Email STRING)");
			stmt.executeUpdate("CREATE TABLE IF NOT EXISTS "
					+ "Pedidos (Id INTEGER PRIMARY KEY, Cliente_Id INTEGER REFERENCES Clientes(Id), Coste INTEGER, Direccion STRING, Hora DATE, Repartidor_Id INTEGER REFERENCES Empleados(Id))");
			stmt.executeUpdate("CREATE TABLE IF NOT EXISTS "
					+ "Pedidos_Menus (Id_Pedido INTEGER REFERENCES Pedidos(Id), Id_Menu INTEGER REFERENCES Menus(Id))");
			stmt.executeUpdate("CREATE TABLE IF NOT EXISTS "
					+ "Jefes (Id INTEGER PRIMARY KEY, Nombre STRING)");
			stmt.executeUpdate("CREATE TABLE IF NOT EXISTS "
					+ "Cargos (Id INTEGER PRIMARY KEY, Nombre STRING, Jefe_Id REFERENCES Jefes(id))");
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
		// TODO Auto-generated method stub
		try {
			PreparedStatement prep = c.prepareStatement(addPedido);
			prep.setInt(1, pedido.getClienteId());
			prep.setFloat(2, pedido.getCoste());
			prep.setString(3, pedido.getDireccion());
			prep.setDate(4,pedido.getHora());
			prep.setInt(5, pedido.getRepartidor());
			prep.executeUpdate();
			prep.close();
		} catch (SQLException e) {
			LOGGER.severe("Error al insertar el pedido: " + pedido);
			e.printStackTrace();
		}
	}
	
	public void addPedido_Menu(Pedidos pedido,Menus menu) {
		// TODO Auto-generated method stub
		try {
			PreparedStatement prep = c.prepareStatement(addPedido_Menu);
			prep.setInt(1, pedido.getId());
			prep.setInt(2, menu.getId());
			prep.executeUpdate();
			prep.close();
		} catch (SQLException e) {
			LOGGER.severe("Error al insertar el Pedido_Menu: " + pedido.getId() + " "+ menu.getId());
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
	
	public void addJefe(Jefes jefe) {
		try {
			PreparedStatement prep = c.prepareStatement(addJefe);
			prep.setString(1, jefe.getNombre());
			prep.executeUpdate();
			prep.close();
		} catch (SQLException e) {
			LOGGER.severe("Error al insertar el jefe: " + jefe);
			e.printStackTrace();
		}
	}
	
	public void addCargo(Cargos cargo) {
		try {
			PreparedStatement prep = c.prepareStatement(addCargo);
			prep.setString(1, cargo.getNombre());
			prep.setInt(2, cargo.getJefe_Id());
			prep.executeUpdate();
			prep.close();
		} catch (SQLException e) {
			LOGGER.severe("Error al insertar el cargo: " + cargo);
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
				float coste = rs.getFloat("Coste");
				String direccion = rs.getString("Direccion");
				Date hora = rs.getDate("Hora");
				int id_repartidor = rs.getInt("Repartidor_Id");
				Pedidos pedido = new Pedidos(id,cliente_id,coste,direccion,hora,id_repartidor);
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
	
	public List<Jefes> searchJefes() {
		List<Jefes> jefes = new ArrayList<Jefes>();
		try {
			Statement stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery(searchJefe);
			while(rs.next()){
				int id = rs.getInt("Id");
				String nombre = rs.getString("Nombre");
				Jefes jefe = new Jefes(id,nombre);
				jefes.add(jefe);
				LOGGER.fine("Jefe: " + jefe);
			}
			rs.close();
			stmt.close();
		} catch (SQLException e) {
			LOGGER.severe("Error al hacer un SELECT");
			e.printStackTrace();
		}
		return jefes;	
	}
	
	public List<Cargos> searchCargos() {
		List<Cargos> cargos = new ArrayList<Cargos>();
		try {
			Statement stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery(searchCargo);
			while(rs.next()){
				int id = rs.getInt("Id");
				String nombre = rs.getString("Nombre");
				int jefe_id = rs.getInt("Jefe_Id");
				Cargos cargo = new Cargos(id,nombre,jefe_id);
				cargos.add(cargo);
				LOGGER.fine("Cargo: " + cargo);
			}
			rs.close();
			stmt.close();
		} catch (SQLException e) {
			LOGGER.severe("Error al hacer un SELECT");
			e.printStackTrace();
		}
		return cargos;	
	}
	
	public Menus searchMenuByNombre(String plato) {
		Menus menu = new Menus();
		try {
			PreparedStatement prep = c.prepareStatement(searchMenuByNombre);
			prep.setString(1,menu + "");
			ResultSet rs = prep.executeQuery();
			while(rs.next()) {
				int id = rs.getInt("Id");
				int precio = rs.getInt("Precio");
				menu = new Menus (id,plato,precio);
			}
			prep.close();
		} catch (SQLException e) {
			LOGGER.severe("Error al hacer un SELECT");
			e.printStackTrace();
		}
		return menu;
	}
	
	public List<Clientes> searchClienteByNombre(String nombre) {
		List<Clientes> clientes = new ArrayList<Clientes>();
		try {
			PreparedStatement prep = c.prepareStatement(searchClienteByNombre);
			prep.setString(1,nombre + "");
			ResultSet rs = prep.executeQuery();
			while(rs.next()) {
				int id = rs.getInt("Id");
				int telefono=rs.getInt("Telefono"); 
				String email = rs.getString("Email");
				Clientes cliente = new Clientes (id,nombre,telefono,email);
				clientes.add(cliente);
				LOGGER.fine("Cliente: " + cliente);
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
	

	public void actualizarEmpleado(int id, int salario){
		PreparedStatement prep;
		try {
			prep = c.prepareStatement(updateEmpleado);
			prep.setInt(1, salario);
			prep.setInt(2, id);
			prep.executeUpdate();
			System.out.println("\n Update terminado");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	public void actualizarMenu(int id, int precio){
		String sql = "UPDATE Menu SET precio =? WHERE id =?";
		PreparedStatement prep;
		try {
			prep = c.prepareStatement(sql);
			prep.setInt(1, precio);
			prep.setInt(2, id);
			prep.executeUpdate();
			System.out.println("\n Update terminado");	
	} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void actualizarCliente(String nombre, String email, int telefono) {
		String sql1 = "UPDATE Cliente SET Email =?, Telefono=? WHERE Nombre =?";
		PreparedStatement prep;
		try {
			prep = c.prepareStatement(sql1);
			prep.setString(1, email);
			prep.setInt(2, telefono);
			prep.setString(3, nombre);
			prep.executeUpdate();
			System.out.println("\n Update terminado");	
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
}

	public String getNombre(int id) throws SQLException {
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
		rs.close();
		prep.close();
		return nombre;
		
	}
	public void mostarEmpleados() {
		Statement stmt;
		try {
			stmt = c.createStatement();
		
			String sql = "SELECT * FROM Empleados";
			ResultSet rs = stmt.executeQuery(sql);
			if(rs != null) {
				while (rs.next()) {
					int id = rs.getInt("Id");
					String nombre = rs.getString("Nombre");
					int salario = rs.getInt("Salario");
					int cargo_id = rs.getInt("Cargo_id");	
					String cargo = getNombre(cargo_id);
					Empleados empleado = new Empleados(id, nombre, salario, cargo_id);
					empleado.setId(id);
					System.out.println(empleado + "  Cargo: "+ cargo);
		}
		}
		rs.close();
		stmt.close();
	} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
