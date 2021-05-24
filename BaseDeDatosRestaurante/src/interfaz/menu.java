package interfaz;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.logging.Logger;

import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;
import org.xml.sax.SAXException;
import xml.MenuXML;
import jdbc.DBManager;
import jdbc.JDBCManager;
import jpa.UsuarioManager;
import jpa.ManagerJPA;
import logging.MyLogger;
import pojos.Clientes;
import pojos.Rol;
import pojos.Usuario;

public class menu {

		final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
		private static DBManager dbman = new JDBCManager();
		private static ManagerJPA userman = new UsuarioManager();
		private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		private static Clientes cliente;

		
		public static void main(String[] args) {
			try {
				MyLogger.setup();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			dbman.connect();
			userman.connect();
			int respuesta;
			System.out.println("\n¡Bienvenido al interfaz del LOS POLLOS HERMANOS");
			do {
				System.out.println("\nElige una opción:");
				System.out.println("1. Registrarse");
				System.out.println("2. Login");
				System.out.println("0. Salir");
				try {
					respuesta = Integer.parseInt(reader.readLine());
					LOGGER.info("El usuario elige " + respuesta);
				} catch (NumberFormatException | IOException e) {
					respuesta = -1;
					LOGGER.warning("El usuario no ha introducido un número");
					e.printStackTrace();
				}
				switch(respuesta) {
					case 1:
						registrarse();
						break;
					case 2:
						login();
						break;
					case 0:
						break;
				}
			} while (respuesta != 0);
			userman.disconnect();
			dbman.disconnect();
		}

		private static void registrarse() {
			try {
				System.out.println("Indique su email:");
				String email = reader.readLine();
				System.out.println("Indique su contraseña:");
				String pass = reader.readLine();
				MessageDigest md = MessageDigest.getInstance("MD5");
				md.update(pass.getBytes());
				byte[] hash = md.digest();
				System.out.println("Indique su nombre:");
				String nombre = reader.readLine();
				System.out.println("Indique su numero de telefono:");
				int telefono = Integer.parseInt(reader.readLine());
				System.out.println(userman.getRoles());
				System.out.println("Indique el id del rol:");
				int rolId = Integer.parseInt(reader.readLine());
				Rol rol = userman.getRolById(rolId);
				Usuario usuario = new Usuario(email, hash, rol);
				LOGGER.info(usuario.toString());
				userman.addUsuario(usuario);
				System.out.println("Te has registrado con éxito");
				LOGGER.info(usuario.toString());
				Clientes cliente = new Clientes(usuario.getId(), nombre, telefono, email);
				dbman.addCliente(cliente);
			} catch (IOException e) {
				LOGGER.warning("Error al registrarse");
				e.printStackTrace();
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		private static void login() throws XPathExpressionException, JAXBException, SAXException, ParserConfigurationException {
			try {
				System.out.println("Indique su email:");
				String email = reader.readLine();
				System.out.println("Indique su contraseña:");
				String pass = reader.readLine();
				Usuario usuario = userman.checkPass(email, pass);
				if (usuario == null) {
					System.out.println("Email o contraseña incorrectos");
				} else {
				    cliente = dbman.searchClienteByEmail(usuario.getEmail());
				    System.out.println("Bienvenido " + cliente.getNombre());
					if(usuario.getRol().getNombre().equalsIgnoreCase("admin")) {
						MenuServidor.main(null);
					} else if (usuario.getRol().getNombre().equalsIgnoreCase("usuario")) {
						MenuCliente.main(cliente);
					} else {
						LOGGER.warning("Rol incorrecto");
					}
				}
			} catch (IOException e) {
				LOGGER.severe("Error en el login");
				e.printStackTrace();
			}
		}
		
		

}
