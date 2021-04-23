package pojos;

import java.sql.Date;
import java.sql.Time;

public class Pedidos {
	
	private int id;
	private int cliente_id;
	private Date fecha;
	private float coste;
	private String direccion;
	private Time hora;
	private Empleados repartidor;
	
	public Pedidos() {
		super();
	}
	
	public Pedidos(int cliente_id, Date fecha, float coste, String direccion, Time hora, Empleados repartidor) {
		this.cliente_id=cliente_id;
		this.fecha=fecha;
		this.coste=coste;
		this.direccion=direccion;
		this.hora=hora;
		this.repartidor=repartidor;
	}
	
	public Pedidos(int id,int cliente_id, Date fecha, float coste, String direccion, Time hora, Empleados repartidor) {
		this.id=id;
		this.cliente_id=cliente_id;
		this.fecha=fecha;
		this.coste=coste;
		this.direccion=direccion;
		this.hora=hora;
		this.repartidor=repartidor;
	}
	
	public Pedidos(int id,int cliente_id, Date fecha, float coste, String direccion, Time hora) {
		this.id=id;
		this.cliente_id=cliente_id;
		this.fecha=fecha;
		this.coste=coste;
		this.direccion=direccion;
		this.hora=hora;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public int getClienteId() {
		return cliente_id;
	}

	public void setClienteId(int cliente_id) {
		this.cliente_id = cliente_id;
	}
	
	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date horario) {
		this.fecha = horario;
	}
	
	public float getCoste() {
		return coste;
	}

	public void setCoste(float coste) {
		this.coste = coste;
	}
	
	public String getDireccion() {
		return direccion;
	}
	
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
		
	public Time getHora() {
		return hora;
	}

	public void setHora(Time hora) {
		this.hora = hora;
	}
	
	public Empleados getRepartidor() {
		return repartidor;
	}
	
	public void setRepartidor(Empleados repartidor) {
		this.repartidor=repartidor;
	}

}
