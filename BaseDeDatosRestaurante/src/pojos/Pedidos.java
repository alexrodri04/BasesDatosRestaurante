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
	private int id_repartidor;
	
	public Pedidos() {
		super();
	}
	
	public Pedidos(int cliente_id, Date fecha, float coste, String direccion, Time hora, int id_repartidor) {
		this.cliente_id=cliente_id;
		this.fecha=fecha;
		this.coste=coste;
		this.direccion=direccion;
		this.hora=hora;
		this.id_repartidor=id_repartidor;
	}
	
	public Pedidos(int id,int cliente_id, Date fecha, float coste, String direccion, Time hora, int id_repartidor) {
		this.id=id;
		this.cliente_id=cliente_id;
		this.fecha=fecha;
		this.coste=coste;
		this.direccion=direccion;
		this.hora=hora;
		this.id_repartidor=id_repartidor;
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
	
	public int getRepartidor() {
		return id_repartidor;
	}
	
	public void setRepartidor(int id_repartidor) {
		this.id_repartidor=id_repartidor;
	}
	
	public String toString() {
		String string ="ID: "+ getId()+ " Cliente_id: "+ getClienteId()+ " Fecha: "+getFecha()+ "Coste: "+getCoste()+ "Direccion: "+getDireccion()+"Hora: "+getHora()+"Repartidor: "+getRepartidor()+"\n";
		return string;
	}
}
