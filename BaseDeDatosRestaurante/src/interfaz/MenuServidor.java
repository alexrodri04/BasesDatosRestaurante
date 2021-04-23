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
			System.out.println("\nElige una opci�n:");
			System.out.println("1. A�adir Empleado");
			System.out.println("2. Mostrar Empleados");
			System.out.println("3. Mostrar Clientes");
			System.out.println("4. Mostrar Pedidos");
			try {
				respuesta=Integer.parseInt(reader.readLine());
				LOGGER.info("El usuario elige " + respuesta);
			} catch  (NumberFormatException | IOException e) {
				respuesta = -1;
				LOGGER.warning("El usuario no ha introducido un n�mero");
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
				mostrarClientes();
				break;
			case 4:
				mostrarPedidos();
				break;
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
			System.out.println("CargoId: ");
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
			System.out.println("Se ha a�adido el pedido con exito \n");
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
		
}