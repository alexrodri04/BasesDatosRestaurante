package pojos;

public class Jefes {

	private int id;
	private String nombre;
	
	public Jefes() {
		super();
	}
	
	public Jefes(int id, String nombre) {
		this.id=id;
		this.nombre=nombre;
	}
	
	public Jefes(int id) {
		this.id=id;
	}
	
	public Jefes(String nombre) {
		this.nombre=nombre;
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
	public String toString() {
		String string ="ID: "+ getId()+ " Nombre: "+ getNombre();
		return string;
	}
	
	
}
