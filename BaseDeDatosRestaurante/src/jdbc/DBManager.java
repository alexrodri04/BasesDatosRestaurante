package jdbc;

import java.util.List;

import pojos.Pedidos;
import pojos.Clientes;
import pojos.Empleados;
import pojos.Menus;

public interface DBManager {
	
	public void connect();
	
	public void disconnect();
	
	public void addEmpleado(Empleados empleado);
	
	public void addCliente(Clientes cliente);
	
	public void addMenu(Menus menu);
	
	public List<Empleados> searchEmpleados();
	
	public Empleados searchEmpleadoById(int id);

	public List<Clientes> searchClientes();

	public List<Pedidos> searchPedidos();

	void addPedido(Pedidos pedido);

	public List<Menus> searchMenu();

	public boolean eliminarEmpleado(String nombreEmpleado);

	public List<Empleados> searchEmpleadoByNombre(String nombreEmpleado);

	public List<Menus> searchMenuByNombre(String nombreMenu);

	public boolean eliminarMenu(String nombreMenu);

	public List<Clientes> searchClienteByNombre(String nombreCliente);

	public boolean eliminarCliente(String nombreCliente);

	public Clientes searchClienteByEmail(String email);

}
