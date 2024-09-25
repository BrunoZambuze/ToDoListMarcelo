package Model.dao;

import java.util.List;

import DataBase.queryFailedException;
import Model.entities.Conta;
import Model.entities.Tarefa;

public interface ITarefa {

	void insertTarefa(Conta conta, Tarefa tarefa)  throws queryFailedException;
	void deleteTarefa(Tarefa tarefa) throws queryFailedException;
	void concluirTarefa(Tarefa tarefa) throws queryFailedException;
	Tarefa selectTarefaById(Integer id) throws queryFailedException;
	List<Tarefa> findAll();
	
}
