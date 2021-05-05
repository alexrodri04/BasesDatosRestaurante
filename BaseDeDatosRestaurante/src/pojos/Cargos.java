package pojos;

public class Cargos {
	
	private int id;
	private String nombre;
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
