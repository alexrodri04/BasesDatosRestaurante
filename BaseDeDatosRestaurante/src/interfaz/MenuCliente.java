package interfaz;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.logging.Logger;

import jdbc.DBManager;
import jdbc.JDBCManager;
import logging.MyLogger;
import pojos.Clientes;
import pojos.Menus;
import pojos.Pedidos;

public class MenuCliente {
	
	final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	private static DBManager dbman = new JDBCManager();
	private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
	private static Clientes usuario = new Clientes();
	
	public static void main(String[] args) {
		try {
			MyLogger.setup();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		dbman.connect();
		int respuesta;
		System.out.println("\nElige una opci�n:");
		System.out.println("1. Registrarse");
		System.out.println("2. Iniciar Sesion");			
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
				usuario = addCliente(usuario);
				break;
			case 2:
				usuario = iniciarSesion(usuario);
				break;
			}
		respuesta = 0;
		do {
			System.out.println("\nElige una opci�n:");
			System.out.println("1. Mostrar informacion del usuario");
			System.out.println("2. Modificar informacion del usuario");
			System.out.println("3. Visualizar Menu");
			System.out.println("4. Hacer pedido");
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
					System.out.println(usuario);
					break;
				case 2:
					usuario = addCliente(usuario);
					break;
				case 3:
					mostrarMenu();
					break;
				case 4:
					break;
			}
 			
		} while (respuesta != 0);
		dbman.disconnect();
		}
	
	private static Clientes addCliente(Clientes usuario) {
		try {
			System.out.println("Nombre del cliente:");
			String nombre = reader.readLine();
			System.out.println("Telefono: ");
			int telefono = Integer.parseInt(reader.readLine());
			System.out.println("Email: ");
			String email = reader.readLine();
			usuario = new Clientes(nombre, telefono , email );
			dbman.addCliente(usuario);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return usuario;
	}
	
	private static Clientes iniciarSesion(Clientes usuario) {
		try {
			System.out.println("Introducir Email: ");
			String email = reader.readLine();
			usuario = dbman.searchClienteByEmail(email);
			System.out.print(usuario);
			
		} catch (NumberFormatException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return usuario;
	}
	
	private static void mostrarMenu() {
		List<Menus> menus = dbman.searchMenu();
		System.out.println("\nMenu: \n");
		for(Menus menu : menus) {
			System.out.println(menu);
		}
	}
	
	private static void a�adirAlPedido() {
		mostrarMenu();
		String respuesta = "s";
		do {
			mostrarMenu();
			System.out.println("Seleccione el nombre de un plato: ");
			try {
				String plato = reader.readLine();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			System.out.println("Desea pedir algo mas?(s/n)");
			try {
				respuesta = reader.readLine();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} while ( respuesta == ("s"));
	}
	
	
	
	
}

