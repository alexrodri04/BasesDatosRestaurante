package pojos;

public class Clientes {
	
	private int id;
	private String nombre;
	private int telefono;
	private String email;
	
	public Clientes() {
		super();
	}
	
	public Clientes(int id, String nombre, int telefono, String email) {
		this.id=id;
		this.nombre=nombre;
		this.telefono=telefono;
		this.email=email;
	}
	public Clientes(int id, String nombre,  String email) {
		this.id=id;
		this.nombre=nombre;
		this.email=email;
	}
	
	public Clientes(String nombre, int telefono, String email) {
		this.nombre=nombre;
		this.telefono=telefono;
		this.email=email;
	}
	
	public Clientes(int id, int telefono, String email) {
		this.id=id;
		this.telefono=telefono;
		this.email=email;
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
	public int getTelefono() {
		return telefono;
	}
	public void setTelefono(int telefono) {
		this.telefono = telefono;;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	 @Override
	    public int hashCode() {
	        final int prime = 31;
	        int result = 1;
	        result = prime * result + id;
	        return result;
	    }
	 @Override
	    public boolean equals(Object obj) {
	        if (this == obj)
	            return true;
	        if (obj == null)
	            return false;
	        if (getClass() != obj.getClass())
	            return false;
	        Clientes other = (Clientes) obj;
	        if (id != other.id)
	            return false;
	        return true;
	    }
	public String toString() {
		String string ="ID: "+ getId()+ " Nombre: "+ getNombre()+ " Telefono: "+getTelefono()+ " Email: "+getEmail();
		return string;
	}

}
