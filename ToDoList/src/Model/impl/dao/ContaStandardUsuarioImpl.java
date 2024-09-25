package Model.impl.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import DataBase.dbConnection;
import DataBase.factoryException;
import DataBase.queryFailedException;
import Model.dao.IContaUsuario;
import Model.entities.Conta;
import Model.entities.Standard;
import Model.factory.entities.FactoryConta;

public class ContaStandardUsuarioImpl<E extends Standard> implements IContaUsuario<E>{

	private static Connection connection;
	
	public ContaStandardUsuarioImpl(Connection connection) {
		this.connection = connection;
	}
	
	@Override
	public void insert(E conta) throws queryFailedException{
		
		PreparedStatement stmt = null;
		ResultSet result = null;
		String sql = "INSERT INTO standard (nome, sobrenome, email, senha) VALUES (?, ?, ?, ?)";
		try {
			stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			stmt.setString(1, conta.getNome());
			stmt.setString(2, conta.getSobrenome());
			stmt.setString(3, conta.getEmail());
			stmt.setString(4, conta.getSenha());
			int rowsAffected = stmt.executeUpdate();
			if(rowsAffected > 0) {
				result = stmt.getGeneratedKeys();
				if(result.next()) {
					Integer id = result.getInt(1);
					conta.setId(id);
				}
			}else {
				throw new queryFailedException("Erro ao inserir a conta do usuário ao banco de dados!");				
			}
		} catch (SQLException e) {
			e.getStackTrace();
			System.out.println(e.getMessage());
		} finally {
			dbConnection.closeStatement(stmt);
			dbConnection.closeResultSet(result);
		}
	}

	@Override
	public void update(E conta) throws queryFailedException {
		PreparedStatement stmt = null;
		ResultSet result = null;
		String sql = "UPDATE standard SET nome = ?, sobrenome = ?, email = ?, senha = ? WHERE standard.id = ?";
		try {
			stmt = connection.prepareStatement(sql);
			stmt.setString(1, conta.getNome());
			stmt.setString(2, conta.getSobrenome());
			stmt.setString(3, conta.getEmail());
			stmt.setString(4, conta.getSenha());
			stmt.setInt(5, conta.getId());
			if(stmt.executeUpdate() < 0) {
				throw new queryFailedException("Erro ao atualizar os dados!");
			}
		} catch(SQLException e) {
			e.getStackTrace();
			System.out.println(e.getMessage());
		} finally {
			dbConnection.closeStatement(stmt);
			dbConnection.closeResultSet(result);
		}
	}

	@Override
	public E findById(Integer id) throws queryFailedException{
		PreparedStatement stmt = null;
		ResultSet result = null;
		E contaStandard = null;
		String sql = "SELECT id, nome, sobrenome, email, senha FROM standard WHERE id = ?";
		String sqlPremium = "SELECT id FROM pagamentos WHERE fk_id = ?";
		try {
			stmt = connection.prepareStatement(sql);
			stmt.setInt(1, id);
			result = stmt.executeQuery();
			if(result.next()) {
				Boolean isContaPremium = false;
				contaStandard = (E) FactoryConta.getInstanceConta(result.getInt("id"),
																	   result.getString("nome"),
																	   result.getString("sobrenome"),
																	   result.getString("email"),
																	   result.getString("senha"), 
																	   isContaPremium);
			} else {
				throw new queryFailedException("Conta não encontrada!");
			}
			if(contaStandard == null) {
				throw new NullPointerException("Erro, não foi possível filtrar a conta");
			}
			stmt = connection.prepareStatement(sqlPremium);
			stmt.setInt(1, id);
			result = stmt.executeQuery();
			if(result.next()) {
				throw new queryFailedException("Não foi possível filtrar a conta! ID selecionado pertence a uma conta Premium!");
			}
		} catch(SQLException e) {
			e.getStackTrace();
			System.out.println(e.getMessage());
		} catch(factoryException e) {
			e.getStackTrace();
			System.out.println(e.getMessage());
		} finally {
			dbConnection.closeResultSet(result);
			dbConnection.closeStatement(stmt);
		}
		return contaStandard;
	}
	
}
