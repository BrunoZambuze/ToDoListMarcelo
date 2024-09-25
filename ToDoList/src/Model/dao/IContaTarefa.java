package Model.dao;

import java.util.List;

import DataBase.acessoNegadoException;
import DataBase.queryFailedException;
import Model.entities.Conta;
import Model.entities.Tarefa;

/*
 * Coloquei a interface como genérica pois precisei selecionar algumas variáveis que só tinha na classe Premium. Ne a interface fosse do tipo conta, eu não
 * conseguiria acessar essas variáveis utilizando essa mesma interface. Já como generics eu posso "forçar" com que a minha outra classe que implementa dessa interface
 * herde de um tipo específico, realizando assim um polimorfismo entre eles.
 */

public interface IContaTarefa<E extends Conta> {

	void insert(E conta, Tarefa tarefa) throws queryFailedException;
	void deleteById(E conta, Integer id) throws acessoNegadoException;
	void concluirTarefa(E conta, Integer id);
	void update(E conta, Tarefa tarefa);
	Tarefa findById(E conta, Integer id) throws queryFailedException;
	List<Tarefa> findAll(E conta);
	List<Tarefa> findIsConcluida(E conta);
	
}
