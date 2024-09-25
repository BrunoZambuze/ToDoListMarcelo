package Application;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import DataBase.factoryException;
import DataBase.queryFailedException;
import Model.dao.FactoryContaUsuario;
import Model.dao.IContaUsuario;
import Model.entities.Conta;
import Model.entities.Tarefa;
import Model.factory.entities.FactoryConta;

public class Program {

	public static void main(String[] args) {
		
		/*
		 * !! NO ARQUIVO "connection.properties" ALTERE A SENHA ATUAL PELA SENHA DO SEU BANCO DE DADOS
		 */
		
		//CRIAR UMA CONTA PREMIUM = "true" OU NÃO "false"
		/*
		 * Dependendo da conta criada o programa se comporta de maneira diferente.
		 * O programa não permite que você selecione uma conta Standard e utilize o mecanismo de uma conta Premium.
		 * Nós não podemos filtrar uma conta Premium se nosso plano é Standard e vice-versa
		 */
		Boolean isContaPremium = false;

		try {
			//CRIAR USUARIO (Precisa descomentar a exceção factoryException, caso contrário pode comentar dnv)
//			Conta conta = FactoryConta.getInstanceConta(null, "Bruno", "Silva", "bruno@hotmail.com", "123456789", isContaPremium);
			IContaUsuario<Conta> iContaUsuario = FactoryContaUsuario.getInstanceContaUsuario(isContaPremium);
//			iContaUsuario.insert(conta);
			
			//PEGAR CONTA DO USUÁRIO JÁ EXISTENTE
			Conta conta2 = iContaUsuario.findById(69);
			System.out.println(conta2);
			
			//UPDATE CONTA USUÁRIO
//			conta2.setNome("Bruno");
//			iContaUsuario.update(conta2);
//			System.out.println(conta2);
			
			//TAREFAS
//			Tarefa tarefa1 = FactoryTarefas.getInstanceTarefa("Passear com o cachorro", "Lazer");
//			conta2.insertTarefa(tarefa1);
			List<Tarefa> listaTarefas = new ArrayList<>();
//			listaTarefas = conta2.findAll();
//			for(Tarefa tarefa: listaTarefas) {
//				System.out.println(tarefa);
//			}
			
			//FILTRAR TAREFA POR ID
//			Tarefa tarefa = conta2.findById(26);
//			System.out.println(tarefa);
			
			//DELETAR TAREFA POR ID
//			conta2.deleteById(25);
			
			//COLOCAR TAREFA COMO CONCLUÍDA
//			conta2.concluirTarefa(26);
			
			//FILTRAR TODAS AS TAREFAS CONCLUIDAS
//			listaTarefas = conta2.findIsConcluida();
//			listaTarefas.stream().forEach(System.out::println);
			
		} catch(SQLException e) {
			e.getStackTrace();
			System.out.println(e.getMessage());
		} catch(queryFailedException e) {
			e.getStackTrace();
			System.out.println(e.getMessage());
//		} catch(factoryException e) {
//			System.out.println(e.getMessage());
		}
		
	}
	
}
