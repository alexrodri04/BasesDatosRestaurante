package jdbc;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import pojos.Empleados;
import pojos.Pedidos;

public class PedidosSQL {
	final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	private static Connection c;
	
	public static void añadirPedido(Pedidos pedido) {
		try {
			PreparedStatement prep = c.prepareStatement("INSERT INTO Pedidos (Cliente_Id, Fecha,Coste, Direccion,Hora,REPARTIDOR_ID) VALUES (?,?,?,?,?,?)");
			prep.setInt(1, pedido.getClienteId());
			prep.setDate(2, pedido.getFecha());
			prep.setFloat(3, pedido.getCoste());
			prep.setString(4, pedido.getDireccion());
			prep.setTime(5,pedido.getHora());
			prep.setInt(6,pedido.getRepartidor());
			prep.executeUpdate();
			prep.close();
		} catch (SQLException e) {
			LOGGER.severe("Error al insertar el pedido: " + pedido);
			e.printStackTrace();
		}
	}
	
	public static List<Pedidos> buscarPedidos() {
		List<Pedidos> pedidos = new ArrayList<Pedidos>();
		try {
			Statement stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM Pedidos;");
			while(rs.next()){
				int id = rs.getInt("Id");
				int cliente_id = rs.getInt("Cliente_Id");
				Date fecha = rs.getDate("Fecha");
				float coste = rs.getFloat("Coste");
				String direccion = rs.getString("Direccion");
				Time hora = rs.getTime("Hora");
				int idEmpleado = rs.getInt("Repartidor_Id");
				Pedidos pedido = new Pedidos(id,cliente_id,fecha,coste,direccion,hora,idEmpleado);
				pedidos.add(pedido);
				LOGGER.fine("Pedido: " + pedido);
			}
			rs.close();
			stmt.close();
		} catch (SQLException e) {
			LOGGER.severe("Error al hacer un SELECT");
			e.printStackTrace();
		}
		return pedidos;
		
	}
	public static void mostarPedidos(Connection c) throws SQLException {
		Statement stmt = c.createStatement();
		String sql = "SELECT * FROM Pedidos";
		ResultSet rs = stmt.executeQuery(sql);
		if(rs != null) {
		while (rs.next()) {
			int id = rs.getInt("Id");
			int cliente_id = rs.getInt("Cliente_Id");
			Date fecha = rs.getDate("Fecha");
			float coste = rs.getFloat("Coste");
			String direccion = rs.getString("Direccion");
			Time hora = rs.getTime("Hora");
			int idEmpleado = rs.getInt("Repartidor_Id");
			Empleados repartidor = EmpleadoSQL.buscarEmpleadosId(idEmpleado);
			Pedidos pedido = new Pedidos(id, cliente_id, fecha, coste, direccion, hora);
			String nombreRepartidor = repartidor.getNombre();
			System.out.println(pedido + "Repartidor: " + nombreRepartidor);
		}
		}
		rs.close();
		stmt.close();
	}
}
