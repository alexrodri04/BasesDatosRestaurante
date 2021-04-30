package jdbc;

import java.sql.Connection;
import java.util.List;

import pojos.Pedidos;
import pojos.Clientes;
import pojos.Empleados;
import pojos.Menus;

public interface DBManager {
	
	public Connection connect();
	
	public void disconnect(Connection c);
	
	public void addEmpleado(Empleados empleado);
	
	public void addCliente(Clientes cliente);
	
	public void addMenu(Menus menu);	
	
	void addPedido(Pedidos pedido);
	
	public List<Empleados> searchEmpleados();
	
	public Empleados searchEmpleadoById(int id);

	public List<Clientes> searchClientes();

	public List<Pedidos> searchPedidos();

	public List<Menus> searchMenu();	
	
	public List<Empleados> searchEmpleadoByNombre(String nombreEmpleado);

	public List<Menus> searchMenuByNombre(String nombreMenu);
	
	public List<Clientes> searchClienteByNombre(String nombreCliente);
	
	public boolean deleteCliente(String nombreCliente);

	public boolean deleteEmpleado(String nombreEmpleado);
	
	public boolean deleteEmpleadoId(int id);

	public boolean deleteMenu(String nombreMenu);
	
	public void printEmpleados(Connection c);
	
	public void printClientes(Connection c);
	
	public void printPedidos(Connection c);
	
	public void updateEmpleado(int id, int salario);
	
	public void updateMenu(int id, int precio);
	
	
}
