package DAO;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public abstract class FabricaConexao {

	/*
	 * Reservei essa classe somente para criar a conexão e vários métodos para criar as tabelas do banco de dados. Assim o usuário não 
	 * precisará criar a tabela manualmente.
	 */
	
	public static Connection getConexao() throws SQLException {
		try {
		Properties prop = getProperties();
		
		final String url = prop.getProperty("banco.url");
		final String usuario = prop.getProperty("banco.usuario");
		final String senha = prop.getProperty("banco.senha");
		
		return DriverManager.getConnection(url, usuario, senha);
		
		} catch(IOException e) {
			System.out.println("Erro ao encontrar as propriedades da conexão: " + e.getMessage());
			throw new SQLException("Erro: Não foi possível conectar ao banco de dados");
		}
	}
	
	//Pegar a chave e valor do nosso arquivo de conexão externalizado
	private static Properties getProperties() throws IOException {
		Properties prop = new Properties();
		
//		//Obtém o diretório do usuário
//		String userHome = System.getProperty("user.home");
//		
//		//Constrói o caminho da pasta "Documentos"
//		Path documentosPath = Paths.get(userHome, "Documentos");
//		
//		//Converter o caminho para uma string
//		String caminho = documentosPath.toString();
		String caminho = "/conexao.properties";
		
		prop.load(FabricaConexao.class.getResourceAsStream(caminho));
		return prop;
	}
	
	public static void criarTabelas() throws SQLException {
		
		String createTabelaUsuario = "CREATE TABLE IF NOT EXISTS usuarios ("
				+ "id_usuario INT NOT NULL AUTO_INCREMENT PRIMARY KEY,"
				+ "nome_usuario VARCHAR(50) NOT NULL,"
				+ "senha_usuario VARCHAR(50) NOT NULL,"
				+ "fk_id_padrao INT,"
				+ "fk_id_premium INT, "
				+ "FOREIGN KEY (fk_id_premium) REFERENCES conta_premium (id_premium),"
				+ "FOREIGN KEY (fk_id_padrao) REFERENCES conta_padrao (id_padrao)"
				+ ")";
		
		String createTabelaPadrao = "CREATE TABLE IF NOT EXISTS conta_padrao ("
				+ "id_padrao INT NOT NULL AUTO_INCREMENT PRIMARY KEY,"
				+ "nome_tarefa VARCHAR(50) NOT NULL,"
				+ "descricao_tarefa VARCHAR(150)"
				+ ")";
		
		String createTabelaPremium = "CREATE TABLE IF NOT EXISTS conta_premium ("
				+ "id_premium INT NOT NULL AUTO_INCREMENT PRIMARY KEY,"
				+ "nome_tarefa VARCHAR(50) NOT NULL,"
				+ "descricao_tarefa VARCHAR(150)"
				+ ")";
		
		Statement stmt = getConexao().createStatement();
		stmt.execute(createTabelaPremium);
		stmt.execute(createTabelaPadrao);
		stmt.execute(createTabelaUsuario);
		
	}
	
}
