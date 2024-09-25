package Model.impl.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import DataBase.dbConnection;
import DataBase.queryFailedException;
import Model.dao.ITarefa;
import Model.entities.Conta;
import Model.entities.Standard;
import Model.entities.Tarefa;
import Model.factory.entities.FactoryTarefas;

public class TarefaImpl implements ITarefa {

private static Connection connection;
	
	public TarefaImpl(Connection connection) {
		this.connection = connection;
	}

	
	@Override
	public void insertTarefa(Conta conta, Tarefa tarefa) throws queryFailedException {
		
		PreparedStatement stmt = null;
		ResultSet result = null;
		String sqlStandard = "INSERT INTO tarefas (nome, descricao, fk_id_standard)"
				+ " VALUES (?, ?, ?)";
		String sqlPremium = "INSERT INTO tarefas (nome, descricao, fk_id_standard, fk_id_premium)"
				+ "VALUES (?, ?, null, ?)";
		try {
			if(conta instanceof Standard) {				
				stmt = connection.prepareStatement(sqlStandard, Statement.RETURN_GENERATED_KEYS);
			}
			else {
				stmt = connection.prepareStatement(sqlPremium, Statement.RETURN_GENERATED_KEYS);
			}
			stmt.setString(1, tarefa.getNome());
			stmt.setString(2, tarefa.getDescricao());
			stmt.setInt(3, conta.getId());
			if(stmt.executeUpdate() > 0) {
				result = stmt.getGeneratedKeys();
				if(result.next()) {
					int id = result.getInt(1);
					tarefa.setId(id);
				}
			} else {
				throw new queryFailedException("Erro ao inserir tarefa!");
			}
		} catch(SQLException e) {
			e.getStackTrace();
			System.out.println(e.getMessage());
		} finally {
			dbConnection.closeResultSet(result);
			dbConnection.closeStatement(stmt);
		}
		
	}
	
	@Override
	public void deleteTarefa(Tarefa tarefa) throws queryFailedException{
		PreparedStatement stmt = null;
		String sql = "DELETE FROM tarefas WHERE id = ?";
		try {
			stmt = connection.prepareStatement(sql);
			stmt.setInt(1, tarefa.getId());
			if(stmt.executeUpdate() < 0) {
				throw new queryFailedException("Erro ao remover tarefa!");
			}			
		} catch (queryFailedException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		} catch(SQLException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		} finally {
			dbConnection.closeStatement(stmt);
		}
	}
	
	@Override
	public void concluirTarefa(Tarefa tarefa) throws queryFailedException{
		PreparedStatement stmt = null;
		String sql = "UPDATE tarefas SET is_finalizada = true WHERE tarefas.id = ?";
		try {
			stmt = connection.prepareStatement(sql);
			stmt.setInt(1, tarefa.getId());
			if(stmt.executeUpdate() < 0) {
				throw new queryFailedException("Erro ao concluir a tarefa!");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		} finally {
			dbConnection.closeStatement(stmt);
		}
	}
	
	@Override
	public Tarefa selectTarefaById(Integer id) throws queryFailedException{
		PreparedStatement stmt = null;
		ResultSet result = null;
		Tarefa tarefa = null;
		String sql = "SELECT * FROM tarefas WHERE id = ?";
		try {
			
			stmt = connection.prepareStatement(sql);
			stmt.setInt(1, id);
			result = stmt.executeQuery();
			if(result.next()) {
				tarefa = getTarefaLogicaStatic(result);
				
				return tarefa;
			}
			throw new queryFailedException("Erro ao selecionar a tarefa com id: " + id);
		} catch (SQLException e) {
			e.getStackTrace();
			System.out.println(e.getMessage());
		} finally {
			dbConnection.closeResultSet(result);
			dbConnection.closeStatement(stmt);
		}
		return null;
	}
	
	@Override
	public List<Tarefa> findAll() {
		Statement stmt = null;
		ResultSet result = null;
		List<Tarefa> listaTarefas = new ArrayList<>();
		String sql = "SELECT id, nome, descricao, is_finalizada, fk_id_standard, fk_id_premium FROM tarefas";
		try {
			stmt = connection.createStatement();
			result = stmt.executeQuery(sql);
			if(result.next()) {
				do {
					Tarefa tarefa = getTarefaLogicaStatic(result);
					listaTarefas.add(tarefa);
				} while(result.next());
			}
			if(listaTarefas.isEmpty()) {
				System.out.println("Lista vazia!");
				return Collections.emptyList();
			}
			return listaTarefas;
		} catch(SQLException e) {
			e.getStackTrace();
			System.out.println(e.getMessage());
		} finally {
			dbConnection.closeResultSet(result);
			dbConnection.closeStatement(stmt);
		}
		System.out.println("Lista vazia!");
		return Collections.emptyList();
	}
	
	/*
	 * Lógica para filtrar uma tarefa. Criei essa lógica pois quero usar esse método para retornar os valores das chaves foreing's Keys de seu 
	 * respectivo tipo, Standard ou Premium.
	 * Dessa maneira eu posso fazer a veirificação e reutilizar essa função em outros métodos, sem precisar ficar reescrevendo.
	 */
	private static Tarefa getTarefaLogicaStatic(ResultSet result) throws SQLException {
		Integer foreignKeyStandard = 0;
		Integer foreignKeyPremium = 0;
		Boolean isFinalizada = false;
		foreignKeyStandard = result.getInt("fk_id_standard");
		Integer resultsFinalizada = result.getInt("is_finalizada");
		if(foreignKeyStandard == 0) {						
			foreignKeyPremium = result.getInt("fk_id_premium");
		}
		if(resultsFinalizada == 1) {
			isFinalizada = true;
		}		
		Tarefa tarefa = FactoryTarefas.getInstanceTarefa(result.getInt("id"),
														 result.getString("nome"),
														 result.getString("descricao"),
														 isFinalizada,
														 foreignKeyStandard,
														 foreignKeyPremium);
		return tarefa;
	}
	
}
