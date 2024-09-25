package Model.factory.entities;

import DataBase.factoryException;
import Model.entities.Conta;
import Model.entities.Premium;
import Model.entities.Standard;

public abstract class FactoryConta {

	public static Conta getInstanceConta(Integer id, String nome, String sobrenome, String email, String senha, Boolean isPremium) throws factoryException {
		if(isPremium == true) {
			return new Premium(id, nome, sobrenome, email, senha);
		}
		return new Standard(id, nome, sobrenome, email, senha);
	}
	
}
