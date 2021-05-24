package interfaz;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.eclipse.persistence.jpa.jpql.parser.DateTime;

import jdbc.DBManager;
import jdbc.JDBCManager;
import jpa.ManagerJPA;
import jpa.UsuarioManager;
import logging.MyLogger;
import pojos.Clientes;
import pojos.Menus;
import pojos.Pedidos;

public class MenuCliente {
	
	final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	private static DBManager dbman = new JDBCManager();
	private static ManagerJPA userman = new UsuarioManager();
	private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
	//private static Clientes usuario = new Clientes();
	private final static DateTimeFormatter formattertime = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
	
	public static void main(Clientes usuario) {
		try {
			MyLogger.setup();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		dbman.connect();
		int respuesta = 0;
		do {
			System.out.println("\nElige una opción:");
			System.out.println("1. Mostrar informacion del usuario");
			System.out.println("2. Modificar informacion del usuario");
			System.out.println("3. Visualizar Menu");
			System.out.println("4. Hacer pedido");
			

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
					System.out.println(usuario);
					break;
				case 2:
					actualizarCliente(usuario);
					break;
				case 3:
					mostrarMenu();
					break;
				case 4:
					añadirAlPedido(usuario);
					break;
				case 5: 
					//can
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
	
	private static void actualizarCliente(Clientes usuario){
		try {			
			String nombre = usuario.getNombre();
			System.out.println("Esta actualizando informacion de cliente:");
			System.out.println(nombre);
			System.out.println("Nuevo Telefono: ");
			int telefono = Integer.parseInt(reader.readLine());
			System.out.println("Nuevo Email: ");
			String email = reader.readLine();
			dbman.actualizarCliente(nombre, email, telefono);
			userman.updateUsuario(usuario.getId(), email);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
	
	private static void añadirAlPedido(Clientes usuario) {
		String respuesta = "s";
		ArrayList<Menus> menus = new ArrayList<Menus>();
		
		float coste = 0;
		String direccion;
		LocalDate hora = LocalDate.now(); 
		direccion = " ";
		Pedidos pedido = new Pedidos(usuario.getId(),coste,direccion,Date.valueOf(hora), 3, menus);
		do {
			mostrarMenu();
			System.out.println("Seleccione el nombre de un plato: ");
			try {
				String plato = reader.readLine();
				Menus menu = dbman.searchMenuByNombre(plato);
				menus.add(menu);
				coste = coste + menu.getPrecio();
				dbman.addPedido_Menu(pedido,menu);
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
		} while (respuesta == ("s"));
		System.out.println("Introduzca una dirección de entrega: \n");
		try {
			direccion = reader.readLine();
			pedido.setDireccion(direccion);
			pedido.setCoste(coste);
			pedido.setMenu(menus);
			//System.out.println("Indutroduzca la Hora: ");
			//LocalDate time = LocalDate.now();
			//hora = LocalDate.parse(time, formattertime);
			//pedido = new Pedidos(usuario.getId(),coste,direccion,Date.valueOf(time), 3, menus);
			dbman.addPedido(pedido);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
}


