package Main;
import DAO.FabricaConexao;
import java.sql.Connection;
import java.sql.SQLException;

public class Principal {

	public static void main(String[] args) {
		
		try{
			Connection conexao = FabricaConexao.getConexao();
			FabricaConexao.criarTabelas();
		} catch(SQLException e) {
			System.out.println(e.getMessage());
		}
		
	}
	
}
