package interfaz;

import pojos.Clientes;
import pojos.Empleados;
import pojos.Menus;
import pojos.Pedidos;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.logging.Logger;

import jdbc.JDBCManager;
import logging.MyLogger;
import jdbc.DBManager;

public class MenuServidor {
	
	final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	private static DBManager dbman = new JDBCManager();
	private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
	private final static String[] EMPLEADOS_NOMBRES = {"Maria","Javier", "David"};
	private final static int[] EMPLEADOS_SUELDO= {1400,1100,1100};
	private final static int[] EMPLEADOS_CARGOID = {1, 2, 3};
	private final static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	private final static DateTimeFormatter formattertime = DateTimeFormatter.ofPattern("hh:mm");
	
	public static void main(String[] args) {
		try {
			MyLogger.setup();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		dbman.connect();
		int respuesta;
		do {
			System.out.println("\nElige una opción:");
			System.out.println("1. Añadir Empleado");
			System.out.println("2. Mostrar Empleados");
			System.out.println("3. Eliminar Empleado");
			System.out.println("4. Añadir Cliente");
			System.out.println("5. Mostrar Clientes");
			System.out.println("6. Eliminar Cliente ");
			System.out.println("7. Hacer Pedido");
			System.out.println("8. Mostrar menu");
			System.out.println("9. Eliminar menu");
			
			
			
			try {
				respuesta=Integer.parseInt(reader.readLine());
				LOGGER.info("El usuario elige " + respuesta);
			} catch  (NumberFormatException | IOException e) {
				respuesta = -1;
				LOGGER.warning("El usuario no ha introducido un número");
				e.printStackTrace();
			}
			
			switch(respuesta) {
		
			case 1:
				addEmpleado();
				break;
			case 2:
				mostrarEmpleados();
				break;
			
			case 3:
				eliminarEmpleado();
			case 4:
				addCliente();
				break;
			case 5:
				mostrarClientes();
				break;
			case 6:
				eliminarCliente();
				break;
			case 7:
				crearPedido();
				mostrarPedidos();
				break;
			case 8:
				mostrarMenu();
				break;
			case 9:
				eliminarMenu();
			}
		} while (respuesta != 0);
		dbman.disconnect();
	}
	

	private static void addEmpleado() {
		try {
			System.out.println("Nombre del empleado:");
			String nombre = reader.readLine();
			System.out.println("Sueldo: ");
			int sueldo = Integer.parseInt(reader.readLine());
			System.out.println("Cargo_Id: ");
			int cargoid = Integer.parseInt(reader.readLine());
			Empleados empleado = new Empleados(nombre, sueldo, cargoid);
			dbman.addEmpleado(empleado);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private static void addCliente() {
		try {
			System.out.println("Nombre del cliente:");
			String nombre = reader.readLine();
			System.out.println("Telefono: ");
			int telefono = Integer.parseInt(reader.readLine());
			System.out.println("Email: ");
			String email = reader.readLine();
			Clientes cliente = new Clientes(nombre, telefono , email );
			dbman.addCliente(cliente);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private static void crearMenu() {
		try {
			System.out.println("Nombre del plato:");
			String plato = reader.readLine();
			System.out.println("Precio: ");
			float coste = Float.parseFloat(reader.readLine());
			Menus menu = new Menus(plato,coste);
			dbman.addMenu(menu);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private static void crearPedido() {
		try {
			System.out.println("Cliente_id: ");
			int cliente_id = Integer.parseInt(reader.readLine());
			System.out.println("Fecha: ");
			LocalDate fecha = LocalDate.parse(reader.readLine(),formatter);
			System.out.println("Coste: ");
			float coste = Float.parseFloat(reader.readLine());
			System.out.println("Direccion: ");
			String direccion = reader.readLine();
			System.out.println("Hora: ");
			LocalTime hora = LocalTime.parse(reader.readLine(),formattertime);
			System.out.println("RepartidorId: ");
			int idRepartidor = Integer.parseInt(reader.readLine());
			Empleados repartidor = dbman.searchEmpleadoById(idRepartidor);
			Pedidos pedido = new Pedidos(cliente_id,Date.valueOf(fecha),coste,direccion,Time.valueOf(hora),repartidor);
			dbman.addPedido(pedido);
			System.out.println("Se ha añadido el pedido con exito \n");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static void generarEmpleados() {
		for(int i = 0; i < EMPLEADOS_NOMBRES.length; i++) {
						
			Empleados empleado = new Empleados(EMPLEADOS_NOMBRES[i],EMPLEADOS_SUELDO[i],EMPLEADOS_CARGOID[i]);
			dbman.addEmpleado(empleado);
		}
		System.out.println("Se han generado " + EMPLEADOS_NOMBRES.length + " empleados.");
	}
	
	private static void mostrarEmpleados() {
		List<Empleados> empleados = dbman.searchEmpleados();
		System.out.println("\nEmpleados: \n");
		for(Empleados empleado : empleados) {
			System.out.println(empleado);
		}
	}
	
	private static void mostrarClientes() {
		List<Clientes> clientes = dbman.searchClientes();
		System.out.println("\nClientes: \n");
		for(Clientes cliente : clientes) {
			System.out.println(cliente);
		}
	}
	
	private static void mostrarPedidos() {
		List<Pedidos> pedidos = dbman.searchPedidos();
		System.out.println("\nPedidos: \n");
		for(Pedidos pedido : pedidos) {
			System.out.println(pedido);
		}
	}
	
	private static void mostrarMenu() {
		List<Menus> menus = dbman.searchMenu();
		System.out.println("\nMenu: \n");
		for(Menus menu : menus) {
			System.out.println(menu);
		}
	}
	
	private static void eliminarEmpleado() {
		mostrarEmpleados();
		System.out.println("Introduzca nombre del empleado:");
		try {
			String nombreEmpleado = reader.readLine();
			List<Empleados> empleados = dbman.searchEmpleadoByNombre(nombreEmpleado);
			if (empleados.size() > 0) {
				System.out.println("Se va a borrar el siguiente empleado: ");
				for(Empleados empleado : empleados) {
					System.out.println(empleado);
				}
				System.out.println("¿Confirmar borrado?(s/n)");
				String respuesta = reader.readLine();
				if(respuesta.equalsIgnoreCase("s")) {
					boolean exiteEmpleado = dbman.eliminarEmpleado(nombreEmpleado);
					if(exiteEmpleado) {
						System.out.println("El empleado se ha borrado con éxito");
					} else {
						System.out.println("Ha habido un error al intentar eliminar el empleado");
					}
				} else {
					System.out.println("Se ha cancelado la operación de borrado");
				}
			} else {
				System.out.println("El empleado no existe");
			}
		} catch (NumberFormatException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private static void eliminarMenu() {
		mostrarMenu();
		System.out.println("Introduzca nombre del plato:");
		try {
			String nombreMenu = reader.readLine();
			List<Menus> menus = dbman.searchMenuByNombre(nombreMenu);
			if (menus.size() > 0) {
				System.out.println("Se va a borrar el siguiente plato: ");
				for(Menus menu : menus) {
					System.out.println(menu);
				}
				System.out.println("¿Confirmar borrado?(s/n)");
				String respuesta = reader.readLine();
				if(respuesta.equalsIgnoreCase("s")) {
					boolean exiteMenu = dbman.eliminarMenu(nombreMenu);
					if(exiteMenu) {
						System.out.println("El plato se ha borrado con éxito");
					} else {
						System.out.println("Ha habido un error al intentar eliminar el plato");
					}
				} else {
					System.out.println("Se ha cancelado la operación de borrado");
				}
			} else {
				System.out.println("El plato no existe");
			}
		} catch (NumberFormatException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private static void eliminarCliente() {
		mostrarMenu();
		System.out.println("Introduzca nombre del cliente:");
		try {
			String nombreCliente = reader.readLine();
			List<Clientes> clientes = dbman.searchClienteByNombre(nombreCliente);
			if (clientes.size() > 0) {
				System.out.println("Se va a borrar El cliente: ");
				for(Clientes cliente : clientes) {
					System.out.println(cliente);
				}
				System.out.println("¿Confirmar borrado?(s/n)");
				String respuesta = reader.readLine();
				if(respuesta.equalsIgnoreCase("s")) {
					boolean existeCliente = dbman.eliminarCliente(nombreCliente);
					if(existeCliente) {
						System.out.println("El Cliente se ha borrado con éxito");
					} else {
						System.out.println("Ha habido un error al intentar eliminar el cliente");
					}
				} else {
					System.out.println("Se ha cancelado la operación de borrado");
				}
			} else {
				System.out.println("El cliente no esta registrado");
			}
		} catch (NumberFormatException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
		
}