package pojos;

public class Menus {
	private int id;
	private String plato;
	private float precio;
	
	public Menus() {
		super();
	}
	
	public Menus(int id, String plato, int precio) {
		this.id=id;
		this.plato=plato;
		this.precio=precio;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public String getPlato() {
		return plato;
	}

	public void setPlato(String plato) {
		this.plato = plato;
	}
	
	public float getPrecio() {
		return precio;
	}

	public void setPrecio(int precio) {
		this.precio = precio;
	}
	
	@Override
	public String toString() {
		return "Plato [id=" + id + ", plato=" + plato + " precio=" + "precio]";
	}
}
