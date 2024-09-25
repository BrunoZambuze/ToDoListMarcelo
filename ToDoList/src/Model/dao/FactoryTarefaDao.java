package Model.dao;

import java.sql.SQLException;

import DataBase.dbConnection;
import Model.impl.dao.TarefaImpl;

public abstract class FactoryTarefaDao {

	public static ITarefa getInstanceTarefa() throws SQLException{
		return new TarefaImpl(dbConnection.getConnection());
	}
	
}
