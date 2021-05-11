package pojos;

import java.sql.Date;
import java.util.ArrayList;

public class Pedidos {
	
	private int id;
	private int cliente_id;
	private float coste;
	private String direccion;
	private Date hora;
	private int id_repartidor;
	private ArrayList<Menus> menu;
	
	public Pedidos() {
		super();
	}
	
	public Pedidos(int cliente_id, float coste, String direccion, Date hora, int id_repartidor) {
		this.cliente_id=cliente_id;
		this.coste=coste;
		this.direccion=direccion;
		this.hora=hora;
		this.id_repartidor=id_repartidor;
	}
	
	public Pedidos(int cliente_id, float coste, String direccion, Date hora, int id_repartidor,ArrayList<Menus> menu) {
		this.cliente_id=cliente_id;
		this.coste=coste;
		this.direccion=direccion;
		this.hora=hora;
		this.id_repartidor=id_repartidor;
		this.menu = menu;
	}
	
	public Pedidos(int id,int cliente_id, float coste, String direccion, Date hora, int id_repartidor) {
		this.id=id;
		this.cliente_id=cliente_id;
		this.coste=coste;
		this.direccion=direccion;
		this.hora=hora;
		this.id_repartidor=id_repartidor;
	}
	
	public Pedidos(int id,int cliente_id, float coste, String direccion, Date hora) {
		this.id=id;
		this.cliente_id=cliente_id;
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
		
	public Date getHora() {
		return hora;
	}

	public void setHora(Date hora) {
		this.hora = hora;
	}
	
	public int getRepartidor() {
		return id_repartidor;
	}
	
	public void setRepartidor(int id_repartidor) {
		this.id_repartidor=id_repartidor;
	}
	public ArrayList<Menus> getMenu(){
		return menu;
	}
	public void setMenu(ArrayList<Menus> menu) {
		this.menu = menu;
	}
	
	public String toString() {
		String string ="ID: "+ getId()+ " Cliente_id: "+ getClienteId()+ "Coste: "+getCoste()+ "Direccion: "+getDireccion()+"Hora: "+getHora()+"Repartidor: "+getRepartidor()+"\n";
		return string;
	}
}
