package xml;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.InputMismatchException;

import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;
import org.xml.sax.SAXException;


public class MenuXML {

	public static void menuXML(int opcion) throws IOException, JAXBException, XPathExpressionException, SAXException, ParserConfigurationException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		try {
			switch(opcion) {

			case 1:
				while (true) {
					try {
					System.out.println("Sobre que tabla desea realizar el Marshalling: Empleados o Cargos");
					String nombre = reader.readLine();
					if (nombre.equalsIgnoreCase("empleados")) {
						XML.marshallingEmpleados();
						break;
					}
					else if (nombre.equalsIgnoreCase("cargos")){
						XML.marshallingCargos();
						break;
					}
					}catch(InputMismatchException e) {
						System.out.println(e);
					}
				}
				break;
			case 2:
				while (true) {
					System.out.println("Sobre que tabla desea realizar el Unmarshalling: Empleados o Cargos");
					String eleccion = reader.readLine();
					if (eleccion.equalsIgnoreCase("empleados")) {
						try {
						XML.unmarshallingEmpleados();
						}catch (Exception e) {
							System.out.println("para realizar esta operación debe haber realizado una operacion de Marshalling");
						}
						break;
					}
					else if (eleccion.equalsIgnoreCase("puestos")){
						try {
						XML.unmarshallingCargos();
						}catch(Exception e) {
							System.out.println("para realizar esta operación debe haber realizado una operacion de Marshalling");
						}
						break;
					}	
				}
				break;
			case 3:
				try {
					XSLT.xsltEmpleado();
				}catch(Exception e) {
				System.out.println("Para realizar esta operación debe haber realizado una operacion de Marshalling de Empleado previamente");
				}
				break;

			default:
				System.out.println("Indroduzca un numero valido");
			}
		}catch (InputMismatchException e) {
			System.out.println("Introduzca un numero");
		}

	}

}
