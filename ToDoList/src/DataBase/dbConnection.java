package DataBase;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public abstract class dbConnection {

	private static Connection connection = null;
	
	public static Connection getConnection() throws SQLException{
		if (connection == null) {
			try {
				Properties properties = getProperties();
				String url = properties.getProperty("banco.url");
				String user = properties.getProperty("banco.user");
				String password = properties.getProperty("banco.password");
				
				return DriverManager.getConnection(url, user, password);
			} catch(IOException e) {
				e.getStackTrace();
				System.out.println("Erro de conexão: Properties | " + e.getMessage());
			}
		}
		return connection;
	}
	
	private static Properties getProperties() throws IOException{
		Properties properties = new Properties();
		String path = "/connection.properties";
		properties.load(dbConnection.class.getResourceAsStream(path));
		return properties;
	}
	
	public static void closeConnection() {
		if(connection != null) {
			try {
				connection.close();
			} catch(SQLException e) {
				throw new closeConnectionException("Erro ao fechar a conexão!");
			}
		}
	}
	
	public static void closeResultSet(ResultSet result) {
		if(result != null) {
			try {
				result.close();
			} catch(SQLException e) {
				throw new closeResultSetException("Erro ao fechar o ResultSet!");
			}
		}
	}
	
	public static void closeStatement(Statement stmt) {
		if(stmt != null) {
			try {
				stmt.close();
			} catch(SQLException e) {
				throw new closeStatementException("Erro ao fechar o Statement!");
			}
		}
	}
	
}
