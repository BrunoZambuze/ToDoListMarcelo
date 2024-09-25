package Model.dao;

import java.sql.SQLException;

import DataBase.dbConnection;
import Model.impl.dao.ContaPremiumUsuarioImpl;
import Model.impl.dao.ContaStandardUsuarioImpl;

public abstract class FactoryContaUsuario {

	public static IContaUsuario getInstanceContaUsuario(Boolean isPremium) throws SQLException{
		if(isPremium) {
			return new ContaPremiumUsuarioImpl(dbConnection.getConnection());
		}
		return new ContaStandardUsuarioImpl(dbConnection.getConnection());
	}
	
}
