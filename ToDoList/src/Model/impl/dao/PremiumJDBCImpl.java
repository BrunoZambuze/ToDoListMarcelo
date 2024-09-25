package Model.impl.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import DataBase.acessoNegadoException;
import DataBase.queryFailedException;
import Model.dao.FactoryTarefaDao;
import Model.dao.IContaTarefa;
import Model.dao.ITarefa;
import Model.entities.Premium;
import Model.entities.Standard;
import Model.entities.Tarefa;

public class PremiumJDBCImpl<E extends Premium> implements IContaTarefa<E>{

	private static Connection connection;
	
	public PremiumJDBCImpl(Connection connection) {
		this.connection = connection;
	}
	
	@Override
	public void insert(E conta, Tarefa tarefa) throws queryFailedException{
		try {
			ITarefa iTarefa = FactoryTarefaDao.getInstanceTarefa();
			iTarefa.insertTarefa(conta, tarefa);
		} catch(SQLException e) {
			e.getStackTrace();
			System.out.println(e.getMessage());
		}
	}

	@Override					//Qual a diferença entre tratar a exceção no próprio método ou jogar a responsabilidade para quem chamou o método "throws";
	public void deleteById(E conta, Integer id) throws NullPointerException, acessoNegadoException{
		if(conta instanceof Premium) {
			if(id != null && id > 0) {
				try {
					ITarefa iTarefa = FactoryTarefaDao.getInstanceTarefa();
					Tarefa tarefa = iTarefa.selectTarefaById(id);
					if(tarefa.getIdPremium().equals(conta.getId())) {
						iTarefa.deleteTarefa(tarefa);
					} else {
						throw new acessoNegadoException("Acesso negado. Usuário está tentando deletar algo fora da sua conta!");
					}
				} catch (SQLException e) {
					e.printStackTrace();
					System.out.println(e.getMessage());
				} catch (queryFailedException e) {
					e.printStackTrace();
					System.out.println(e.getMessage());
				}
			} else {
				throw new NullPointerException("Erro: Id não deve ser nulo ou menor que 0");
			}
		}
	}

	@Override
	public void concluirTarefa(E conta, Integer id) {
		if(conta instanceof Premium) {
			if(id != null && id > 0) {
				try {
					ITarefa iTarefa = FactoryTarefaDao.getInstanceTarefa();
					Tarefa tarefa = iTarefa.selectTarefaById(id);
					if(tarefa.getIdPremium().equals(conta.getId())) {
						if(!tarefa.getIsConcluida()) {
							iTarefa.concluirTarefa(tarefa);
						}
					} else {
						throw new acessoNegadoException("Acesso negado. Usuário está tentando alterar algo fora da sua conta!");
					}
				} catch (SQLException e) {
					e.printStackTrace();
					System.out.println(e.getMessage());
				} catch(queryFailedException e) {
					e.printStackTrace();
					System.out.println(e.getMessage());
				} catch(acessoNegadoException e) {
					e.printStackTrace();
					System.out.println(e.getMessage());
				}
			} else {
				throw new NullPointerException("Erro: Id não deve ser nulo ou menor que 0");
			}
		}
	}

	@Override
	public void update(E conta, Tarefa tarefa) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Tarefa findById(E conta, Integer id) throws queryFailedException {
		
		try {
			ITarefa iTarefa = FactoryTarefaDao.getInstanceTarefa();
			Tarefa tarefa = iTarefa.selectTarefaById(id);
			if(conta instanceof Premium) {
				if(conta.getId().equals(tarefa.getIdPremium())) {
					return tarefa;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		return null;
	}

	@Override
	public List<Tarefa> findAll(E conta) {
		List<Tarefa> listaTarefas = new ArrayList<>();
		try {
			ITarefa iTarefa = FactoryTarefaDao.getInstanceTarefa();
			listaTarefas = iTarefa.findAll();
			if(conta instanceof Premium) {		
				Predicate<Tarefa> filterTarefas = tarefa -> { return tarefa.getIdPremium().equals(conta.getId()); };
				listaTarefas = listaTarefas.stream()
										   .filter(filterTarefas)
										   .collect(Collectors.toList());
			}
		} catch(SQLException e) {
			e.getStackTrace();
			System.out.println(e.getMessage());
		}
		return listaTarefas;
	}

	@Override
	public List<Tarefa> findIsConcluida(E conta){
		List<Tarefa> listaTarefasConcluidas = new ArrayList<>();
		try {
			ITarefa iTarefa = FactoryTarefaDao.getInstanceTarefa();
			listaTarefasConcluidas = iTarefa.findAll();
			if(conta instanceof Premium) {
				Predicate<Tarefa> filterIsEquals = tarefa -> { return tarefa.getIdPremium().equals(conta.getId()); };
				Predicate<Tarefa> filterIsConcluida = tarefa -> { return tarefa.getIsConcluida() == true; }; //<----- Alterando para 'false' temos uma lista de tarefas
				listaTarefasConcluidas = listaTarefasConcluidas.stream()									// que ainda não foram concluídas.
														       .filter(filterIsEquals)
														       .filter(filterIsConcluida)
														       .collect(Collectors.toList());
			}
		} catch(SQLException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		return listaTarefasConcluidas;
	}

}
