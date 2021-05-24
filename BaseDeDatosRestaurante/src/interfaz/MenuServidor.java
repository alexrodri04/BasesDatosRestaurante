package interfaz;

import pojos.Cargos;
import pojos.Clientes;
import pojos.Empleados;
import pojos.Jefes;
import pojos.Menus;
import pojos.Pedidos;
import xml.MenuXML;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Logger;

import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;

import org.xml.sax.SAXException;

import jdbc.JDBCManager;
import logging.MyLogger;
import jdbc.DBManager;

public class MenuServidor {
	
	final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	private static DBManager dbman = new JDBCManager();
	private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
	private final static String[] JEFES_NOMBRES = {"Daniel","Alejandro", "Ignacio"};
	private final static String[] EMPLEADOS_NOMBRES = {"Paco","Jaime", "Jose"};
	private final static String[] CARGOS_NOMBRES = {"Informatico","Cocinero","Repartidor"};
	private final static String[]  MENUS_PLATOS = {"Patatas","Pasta","Pizza","Solomillo"};
	private final static int[] MENUS_PRECIOS = {6,10,10,15};
	private final static int[] CARGOS_JEFE_ID = {1, 2, 3};
	private final static int[] EMPLEADOS_SALARIOS = {1000, 1000, 1000};
	private final static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	private final static DateTimeFormatter formattertime = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
	static Scanner sc = new Scanner(System.in);
	
	public static void main(String[] args) throws XPathExpressionException, IOException, JAXBException, SAXException, ParserConfigurationException {
		try {
			MyLogger.setup();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		dbman.connect();
		if (dbman.searchJefes().isEmpty()) {
			generartablas();
		}
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
			System.out.println("10. Añadir Jefe");
			System.out.println("11. Mostrar Jefes");
			System.out.println("12. Añadir Cargo");
			System.out.println("13. Mostrar Cargos");
<<<<<<< HEAD
			System.out.println("14. Inicializar Valores");
			System.out.println("15. Menu XML");
=======
			System.out.println("14. Actualizar Empleado");
			System.out.println("15. Añadir Menu");
			//System.out.println("14. Inicializar Valores");
>>>>>>> 16699ffd668eb2c050dfb3f685c68eccb387ae3d
			
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
				//mostrarEmpleados(); la otra mola mas
				mostrarEmpleados2();
				break;
			case 3:
				eliminarEmpleado();
				break;
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
				break;
			case 10:
				addJefe();
				break;
			case 11:
				mostrarJefes();
				break;
			case 12:
				addCargo();
				break;
			case 13:
				mostrarCargos();
				break;
			case 14:
				updateEmpleado();
			case 15:
				addMenu();
			/*case 14: meter esto al inicializar base de datos
				generarJefes();
				generarCargos();
				generarMenus();
				generarEmpleados();
<<<<<<< HEAD
				break;
			case 15:
				funcionXML();
=======
				break;*/
>>>>>>> 16699ffd668eb2c050dfb3f685c68eccb387ae3d
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
	private static void updateEmpleado() {
		try {
			System.out.println("Actualizando informacion de Empleado ");
			System.out.println("Nombre del empleado:");
			String nombre = reader.readLine();
			if (dbman.searchEmpleadoByNombre(nombre).isEmpty()) {
				System.out.println("Nombre del empleado no encontrado");
				}
			else {
			System.out.println("Nuevo Sueldo: ");
			int sueldo = Integer.parseInt(reader.readLine());
			Empleados empleado = (Empleados) dbman.searchEmpleadoByNombre(nombre);
			int id = empleado.getId();
			dbman.actualizarEmpleado(id, sueldo);
			System.out.println("Sueldo actualizado con exito: ");
		} }catch (IOException e) {
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
	
	private static void addCargo() {
		try {
			System.out.println("Nombre del cargo:");
			String nombre = reader.readLine();
			System.out.println("Jefe_id: ");
			int jefe_id = Integer.parseInt(reader.readLine());
			Cargos cargo = new Cargos(nombre, jefe_id);
			dbman.addCargo(cargo);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private static void addJefe() {
		try {
			System.out.println("Nombre: ");
			String nombre = reader.readLine();
			Jefes jefe = new Jefes(nombre);
			dbman.addJefe(jefe);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private static void addMenu() {
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
			System.out.println("Coste: ");
			float coste = Float.parseFloat(reader.readLine());
			System.out.println("Direccion: ");
			String direccion = reader.readLine();
			System.out.println("Hora: ");
			LocalDate hora = LocalDate.parse(reader.readLine(),formattertime);
			System.out.println("RepartidorId: ");
			int id_repartidor = Integer.parseInt(reader.readLine());
			Pedidos pedido = new Pedidos(cliente_id,coste,direccion,Date.valueOf(hora),id_repartidor);
			dbman.addPedido(pedido);
			System.out.println("Se ha añadido el pedido con exito \n");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static void generarJefes() {
		for(int i = 0; i < JEFES_NOMBRES.length; i++) {
			
			Jefes jefe = new Jefes(JEFES_NOMBRES[i]);
			dbman.addJefe(jefe);
		}
		System.out.println("Se han generado " + JEFES_NOMBRES.length + " jefes.");
	}
	
	private static void generarEmpleados() {
		for(int i = 0; i < EMPLEADOS_NOMBRES.length; i++) {
			
			Empleados empleado = new Empleados(EMPLEADOS_NOMBRES[i],EMPLEADOS_SALARIOS[i],CARGOS_JEFE_ID[i]);
			dbman.addEmpleado(empleado);
		
		}
		System.out.println("Se han generado " + EMPLEADOS_NOMBRES.length + " empleados");
	}
	
	private static void generarCargos() {
		for(int i = 0; i < CARGOS_NOMBRES.length; i++) {
			Cargos cargo = new Cargos(CARGOS_NOMBRES[i],CARGOS_JEFE_ID[i]);
			dbman.addCargo(cargo);
		}
		System.out.println("Se han generado " + CARGOS_NOMBRES.length + " cargos.");
	}
	
	private static void generarMenus() {
		for(int i = 0; i < MENUS_PLATOS.length; i++) {
			Menus menu = new Menus(MENUS_PLATOS[i],MENUS_PRECIOS[i]);
			dbman.addMenu(menu);
		}
		System.out.println("Se han generado " + MENUS_PLATOS.length + " platos.");
	}
	private static void generartablas() {
		generarJefes();
		generarCargos();
		generarMenus();
		generarEmpleados();
	}
	
	private static void mostrarEmpleados() {
		List<Empleados> empleados = dbman.searchEmpleados();
		System.out.println("\nEmpleados: \n");
		for(Empleados empleado : empleados) {
			System.out.println(empleado);
		}
	}
	private static void mostrarEmpleados2() {
		dbman.mostarEmpleados();
		
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
	
	private static void mostrarJefes() {
		List<Jefes> jefes = dbman.searchJefes();
		System.out.println("\nJefes: \n");
		for(Jefes jefe : jefes) {
			System.out.println(jefe);
		}
	}
	
	private static void mostrarCargos() {
		List<Cargos> cargos = dbman.searchCargos();
		System.out.println("\nCargos: \n");
		for(Cargos cargo : cargos) {
			System.out.println(cargo);
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
			Menus menu = dbman.searchMenuByNombre(nombreMenu);
			if (menu !=  null) {
				System.out.println("Se va a borrar el siguiente plato: ");
				System.out.println(menu);
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
	
	private static void funcionXML() throws IOException, JAXBException, XPathExpressionException, SAXException, ParserConfigurationException {
		
		boolean salir = false;

		while (!salir) {
			System.out.println("Seleccione la función deseada\n1) Realizar operacion de Marshalling \n2) Realizar operacion de Unmarshalling \n"
					+ "3) Generar HTML desde XML\n4) Ejecutar sentencias XPath sobre un archivo XML\n"
					+ "5) Salir");
			int opcion3 = sc.nextInt();
			try {
				switch (opcion3) {
				case 1:
					MenuXML.menuXML(opcion3);
					break;
				case 2:
					MenuXML.menuXML(opcion3);
					break;
				case 3:
					MenuXML.menuXML(opcion3);
					break;
				case 4:
					MenuXML.menuXML(opcion3);
					break;
				case 5:
					salir = true;
					break;
				default:
					LOGGER.info("Solo números entre 1 y 5");

				}

			}catch (InputMismatchException e) {
				System.out.println("Debes insertar un número");
				sc.next(); 

			}
		}


	}
	
	
	
		
}