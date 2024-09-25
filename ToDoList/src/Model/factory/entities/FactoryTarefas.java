package Model.factory.entities;

import Model.entities.Tarefa;

public abstract class FactoryTarefas {

	public static Tarefa getInstanceTarefa(Integer id, String nome, String descricao, Boolean isConcluida, Integer id_standard, Integer id_premium) {
		return new Tarefa(id, nome, descricao, isConcluida, id_standard, id_premium);
	}
	
	public static Tarefa getInstanceTarefa(String nome, String descricao) {
		return new Tarefa(nome, descricao);
	}
	
}