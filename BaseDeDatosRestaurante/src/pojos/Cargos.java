package pojos;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@Entity
@Table(name = "Cargos")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "Cargos")

public class Cargos {
	@Id
	@GeneratedValue(generator="cargos_gen") 
	@TableGenerator(name="cargos_gen", table="sqlite_sequence", pkColumnName="name", 
		valueColumnName="seq", pkColumnValue="Cargos")
	@XmlAttribute
	private int id;
	@XmlElement
	private String nombre;
	@XmlElement
	private int jefe_id;
	
	public Cargos() {
		super();
	}
	
	public Cargos(int id, String nombre, int jefe_id) {
		this.id=id;
		this.nombre=nombre;
		this.jefe_id=jefe_id;
	}
	
	public Cargos(int id, String nombre) {
		this.id=id;
		this.nombre=nombre;
	}
	
	public Cargos(int id, int jefe_id) {
		this.id=id;
		this.jefe_id=jefe_id;
	}
	
	public Cargos(String nombre, int jefe_id) {
		this.nombre=nombre;
		this.jefe_id=jefe_id;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public int getJefe_Id() {
		return jefe_id;
	}
	public void setJefe_Id(int id) {
		this.jefe_id = id;
	}
	public String toString() {
		String string ="ID: "+ getId()+ " Nombre: "+ getNombre()+ " Jefe: "+getJefe_Id();
		return string;
	}
	

}
