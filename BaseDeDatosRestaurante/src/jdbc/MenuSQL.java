package jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import pojos.Menus;

public class MenuSQL {
	private static Connection c;
	final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	public static void añadirMenu(Menus menu) {
		try {
			PreparedStatement prep = c.prepareStatement("INSERT INTO Menus (Plato,Precio) VALUES (?,?)");
			prep.setString(1, menu.getPlato());
			prep.setFloat(2, menu.getPrecio());
			prep.executeUpdate();
			prep.close();
		} catch (SQLException e) {
			LOGGER.severe("Error al insertar el menu: " + menu);
			e.printStackTrace();
		}
	}
	
	public static boolean eliminarMenu(String nombreMenu) {
		boolean existe = false;
		try {
			PreparedStatement prep = c.prepareStatement("DELETE FROM Menus WHERE Plato LIKE ?;");
			prep.setString(1,"%" + nombreMenu + "%");
			int res = prep.executeUpdate();
			if(res > 0)
				existe = true;
			prep.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return existe;
	}

	public static List<Menus> buscarMenu() {
		List<Menus> menus = new ArrayList<Menus>();
		try {
			Statement stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM Menus;");
			while(rs.next()){
				int id = rs.getInt("Id");
				String plato = rs.getString("Plato");
				float precio = rs.getFloat("Precio");
				Menus menu = new Menus(id,plato,precio);
				menus.add(menu);
				LOGGER.fine("Menu: " + menu);
			}
			rs.close();
			stmt.close();
		} catch (SQLException e) {
			LOGGER.severe("Error al hacer un SELECT");
			e.printStackTrace();
		}
		return menus;
	}
	
	public static List<Menus> buscarMenuNombre(String menu) {
		List<Menus> menus = new ArrayList<Menus>();
		try {
			PreparedStatement prep = c.prepareStatement("SELECT * FROM Menus WHERE Plato = ?;");
			prep.setString(1,menu + "");
			ResultSet rs = prep.executeQuery();
			while(rs.next()) {
				int id = rs.getInt("Id");
				String plato = rs.getString("Plato");
				int precio = rs.getInt("Precio");
				Menus menu1 = new Menus (id,plato,precio);
				menus.add(menu1);
				LOGGER.fine("Menu: " + menu1);
			}
			prep.close();
		} catch (SQLException e) {
			LOGGER.severe("Error al hacer un SELECT");
			e.printStackTrace();
		}
		return menus;
	}

	
}
