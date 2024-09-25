package Model.dao;

import java.sql.SQLException;

import DataBase.dbConnection;
import Model.impl.dao.PremiumJDBCImpl;
import Model.impl.dao.StandardJDBCImpl;

public abstract class FactoryContaTarefa {

	public static IContaTarefa getInstanceStandard() throws SQLException{
		return new StandardJDBCImpl(dbConnection.getConnection());
	}
	
	public static IContaTarefa getInstancePremium() throws SQLException {
		return new PremiumJDBCImpl(dbConnection.getConnection());
	}
	
}
