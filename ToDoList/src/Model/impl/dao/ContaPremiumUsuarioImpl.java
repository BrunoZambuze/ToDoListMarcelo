package Model.impl.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import DataBase.dbConnection;
import DataBase.factoryException;
import DataBase.queryFailedException;
import Model.dao.IContaUsuario;
import Model.entities.Premium;
import Model.factory.entities.FactoryConta;

/*
 * Como IContaStandardUsuario está recebendo uma superClasse Conta, ao "prender" o ContaPremiumUsuarioImpl à classe Premium utilizando <E extends Premium>,
 * daria o efeito de que a classe atual é do tipo Premium mas estamos utilizando métodos da superClasse Conta, causando um efeito de polimorfismo.
 * Minha lógica está certa? 
 */

public class ContaPremiumUsuarioImpl<E extends Premium> implements IContaUsuario<E>{

	private static Connection connection;
	
	public ContaPremiumUsuarioImpl(Connection connection) {
		this.connection = connection;
	}

	@Override
	public void insert(E conta) throws queryFailedException {
		/*
		 * Eu queria reutilizar o método dessa classe novamente, mas não consegui pensar em algo para poder reaproveitar os códigos da classe ContastandardUsuarioImpl
		 */
		PreparedStatement stmt = null;
		ResultSet result = null;
		String sql_tabela_standard = "INSERT INTO standard (nome, sobrenome, email, senha) VALUES (?, ?, ?, ?)";
		String sql_tabela_premium = "INSERT INTO premium (fk_id_standard) VALUES (?)";
		String sql_tabela_pagamentos = "INSERT INTO pagamentos (valor, data_pagamento, fk_id) VALUES (?, ?, ?)";
		try {
			stmt = connection.prepareStatement(sql_tabela_standard, Statement.RETURN_GENERATED_KEYS);
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
					stmt = connection.prepareStatement(sql_tabela_premium);
					stmt.setInt(1, conta.getId());
					int rowsAffectedPremium = stmt.executeUpdate();
					if(rowsAffectedPremium < 0) {
						throw new queryFailedException("Não foi possível inserir o id na tabela premium!");
					}
					stmt = connection.prepareStatement(sql_tabela_pagamentos);
					stmt.setDouble(1, conta.getPagamento().getValor());
					stmt.setDate(2, new java.sql.Date(conta.getPagamento().getDataPagamento().getTime()));
					stmt.setInt(3, conta.getId());
					int rowsAffectedPagamentos = stmt.executeUpdate();
					if(rowsAffectedPagamentos < 0) {
						throw new queryFailedException("Não foi possível inserir os dados na tabela pagamentos");
					}
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
	public E findById(Integer id) throws queryFailedException {
		PreparedStatement stmt = null;
		ResultSet result = null;
		E contaPremium = null;
		String sql = "SELECT standard.id, standard.nome, standard.sobrenome, standard.email, standard.senha FROM standard "
				+ "INNER JOIN premium ON premium.fk_id_standard = standard.id WHERE standard.id = ?";
		try {
			stmt = connection.prepareStatement(sql);
			stmt.setInt(1, id);
			result = stmt.executeQuery();
			if(result.next()) {
				Boolean isContaPremium = true;
				contaPremium = (E) FactoryConta.getInstanceConta(result.getInt("id"),
																	   result.getString("nome"),
																	   result.getString("sobrenome"),
																	   result.getString("email"),
																	   result.getString("senha"), 
																	   isContaPremium);
			} else {
				throw new queryFailedException("Conta não encontrada!");
			}
			if(contaPremium == null) {
				throw new NullPointerException("Erro, não foi possível filtrar a conta");
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
		return contaPremium;
	}
	
}
