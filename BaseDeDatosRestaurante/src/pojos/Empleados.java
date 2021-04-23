package pojos;

public class Empleados {
	private int id;
	private String nombre;
	private int salario;
	private int cargoId;
	
	public Empleados() {
		super();
	}
	public Empleados(String nombre, int salario, int cargoId) {
		super();
		this.nombre=nombre;
		this.salario=salario;
		this.cargoId=cargoId;
				
	}
	
	public Empleados(int id, String nombre, int salario, int cargoId) {
		super();
		this.id=id;
		this.nombre=nombre;
		this.salario=salario;
		this.cargoId=cargoId;
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
	public int getSalario() {
		return salario;		
	}
	public void setSalario(int salario) {
			this.salario = salario;
	}
	public int getCargoId() {
		return cargoId;
	}
	public void setCargoId(int cargoId) {
		this.cargoId = cargoId;
	}
	public String toString() {
		String string ="ID: "+ getId()+ "  Nombre: "+ getNombre()+ "  Cargo_id: "+getCargoId()+  "   Sueldo: "+getSalario();
		return string; 
	}
	
}
