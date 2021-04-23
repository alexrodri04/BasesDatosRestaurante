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
	
	public List<Empleados> searchEmpleados();
	
	public Empleados searchEmpleadoById(int id);

	public List<Clientes> searchClientes();

	public List<Pedidos> searchPedidos();

	void addPedido(Pedidos pedido);

}
