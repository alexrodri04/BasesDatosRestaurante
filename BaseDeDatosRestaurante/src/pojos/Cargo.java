package pojos;

public class Cargo {

	private int id;
	private String nombre;
	

	public Cargo(int id, String nombre) {
		this.id = id;
		this.nombre = nombre;
	}
	public Cargo(String nombre) {
		this.nombre = nombre;
	}
	public Cargo() {
		super();
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
	}

