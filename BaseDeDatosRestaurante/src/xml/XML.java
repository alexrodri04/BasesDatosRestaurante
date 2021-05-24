package xml;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import pojos.Cargos;
import pojos.Empleados;

public class XML {
	
	public static void marshallingEmpleados() throws JAXBException{
		
		Empleados empleado = new Empleados(1,"Alejandro",1000,1);
		JAXBContext jaxbC = JAXBContext.newInstance(Empleados.class);
		Marshaller jaxbM = jaxbC.createMarshaller();
		jaxbM.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,Boolean.TRUE);
		File XMLfile = new File("Empleado.xml");
		jaxbM.marshal(empleado, XMLfile);
		jaxbM.marshal(empleado, System.out);
	}
	
	public static void unmarshallingEmpleados() throws JAXBException {
		JAXBContext jaxbC = JAXBContext.newInstance(Empleados.class);
		Unmarshaller jaxbU = jaxbC.createUnmarshaller();
		File XMLfile2 = new File("Empleado.xml");
		Empleados empleado2 = (Empleados)jaxbU.unmarshal(XMLfile2);
		System.out.println(empleado2);
	}
	
	public static void marshallingCargos() throws JAXBException{
		Cargos cargo = new Cargos(1,"Informatico",1);
		JAXBContext jaxbC = JAXBContext.newInstance(Cargos.class);
		Marshaller jaxbM = jaxbC.createMarshaller();
		jaxbM.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,Boolean.TRUE);
		File XMLfile = new File("Puesto.xml");
		jaxbM.marshal(cargo, XMLfile);
		jaxbM.marshal(cargo, System.out);
	}
	
public static void unmarshallingCargos() throws JAXBException {
		JAXBContext jaxbC = JAXBContext.newInstance(Cargos.class);
		Unmarshaller jaxbU = jaxbC.createUnmarshaller();
		File XMLfile2 = new File("Puesto.xml");
		Cargos cargo2 = (Cargos)jaxbU.unmarshal(XMLfile2);
		System.out.println(cargo2);
	}

}
