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
System.out.println("1. Registrarse");
System.out.println("2. Mostrar Menu");

try {
respuesta=Integer.parseInt(reader.readLine());
LOGGER.info("El usuario elige " + respuesta);
} catch (NumberFormatException | IOException e) {
respuesta = -1;
LOGGER.warning("El usuario no ha introducido un número");
e.printStackTrace();
}

switch(respuesta) {

case 1:
addCliente();
break;
case 2:
mostrarMenu();
break;
case 3:
break;
case 4:
break;
case 5:
break;
case 6:
break;
case 7:
break;
case 8:
break;
case 9:
break;
}

} while (respuesta != 0);
dbman.disconnect();
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

private static void mostrarMenu() {
List<Menus> menus = dbman.searchMenu();
System.out.println("\nMenu: \n");
for(Menus menu : menus) {
System.out.println(menu);
}
}

private static void añadirAlPedido() {
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