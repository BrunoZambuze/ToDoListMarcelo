package Model.dao;

import DataBase.queryFailedException;
import Model.entities.Conta;
import Model.entities.Standard;

public interface IContaUsuario<E> {

	/*
	 * Não coloquei nenhum tipo de gerenciamento de contas, onde o usuário padrão só tem permissão de adicionar, ou o administrador tem permissão
	 * de remover as contas. Vou deixar que o usuário só possa criar a conta e atualizar alguma informação que deseja
	 */
	void insert(E conta) throws queryFailedException;
	void update(E conta) throws queryFailedException;
	E findById(Integer id) throws queryFailedException;
	
}
