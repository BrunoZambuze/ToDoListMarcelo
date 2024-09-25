package Model.entities;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import DataBase.acessoNegadoException;
import DataBase.queryFailedException;
import Model.dao.FactoryContaTarefa;
import Model.dao.IContaTarefa;

public class Premium extends Conta{

	private Pagamento pagamento;
	private static final String TIPO_CONTA = "Premium";
	private IContaTarefa<Premium> iContaTarefa;
	
	public Premium(Integer id, String nome, String sobrenome, String email, String senha) {
		super(id, nome, sobrenome, email, senha);
		this.pagamento = new Pagamento();
		try {
			iContaTarefa = FactoryContaTarefa.getInstancePremium();
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
	}
	
	public Pagamento getPagamento() {
		return this.pagamento;
	}
	
	public String getFatura() {
		return "Valor: R$ " + pagamento.getValor() + "\nData Pagamento: " ;
	}

	@Override
	public void insertTarefa(Tarefa tarefa) {
		try {
			iContaTarefa.insert(this, tarefa);
		} catch (queryFailedException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
	}

	@Override
	public void concluirTarefa(Integer id) {
		
		iContaTarefa.concluirTarefa(this, id);
		
	}
	
	@Override
	public void deleteById(Integer id) {
		try {
			iContaTarefa.deleteById(this, id);
		} catch(NullPointerException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		} catch(acessoNegadoException e) {
			System.out.println(e.getMessage());
		}
	}
	
	@Override
	public Tarefa findById(Integer id) {
		try {
			Tarefa tarefa = iContaTarefa.findById(this, id);
			return tarefa;
		} catch (queryFailedException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		return null;
	}
	
	@Override
	public List<Tarefa> findAll(){
		List<Tarefa> listaTarefas = new ArrayList<>();
		try {
			listaTarefas = iContaTarefa.findAll(this);
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
		return listaTarefas;
	}
	
	@Override
	public List<Tarefa> findIsConcluida() {
		List<Tarefa> listaIsConcluida = new ArrayList<>();
		try {
			listaIsConcluida = iContaTarefa.findIsConcluida(this);
			if(!listaIsConcluida.isEmpty()) {
				return listaIsConcluida;
			}
			System.out.println("Lista vazia!");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		return Collections.emptyList();
	}
	
	public String toString() {
		return String.format("Tipo: %s\nNome: %s\nSobrenome: %s\nEmail: %s\nValor: R$ %.2f\nData Pagamento: %tF%n", this.TIPO_CONTA, super.getNome(), super.getSobrenome(),
																						super.getEmail(), this.pagamento.getValor(), this.getPagamento().getDataPagamento());
	}
	
}