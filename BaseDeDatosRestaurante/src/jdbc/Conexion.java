package jdbc;
import java.sql.Connection;
	import java.sql.DriverManager;
	import java.sql.SQLException;
	
public class Conexion {
	
	public static Connection connect() {
			try {
				Class.forName("org.sqlite.JDBC");
				Connection c =DriverManager.getConnection("JDBC:sqlite:./db/parque.db");
				c.createStatement().execute("Pragma foreign_keys=ON");
				System.out.println("Database connection openned");
				return c;
			} catch (Exception e) {
				e.printStackTrace();
				return null;
				
			}
			
		}

		public static void disconnect(Connection c) throws SQLException {
			
			c.close();
			System.out.println("DB connection closed");
			
		}
		
}
