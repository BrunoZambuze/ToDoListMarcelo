package Model.entities;

import java.util.List;
import java.util.Objects;

public abstract class Conta {

	private Integer id;
	private String nome;
	private String sobrenome;
	private String email;
	private String senha;
	
	public Conta(Integer id, String nome, String sobrenome, String email, String senha) {
		setId(id);
		setNome(nome);
		setSobrenome(sobrenome);
		setEmail(email);
		setSenha(senha);
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public void setSobrenome(String sobrenome) {
		this.sobrenome = sobrenome;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public void setSenha(String senha) {
		this.senha = senha;
	}
	
	public Integer getId() {
		return this.id;
	}
	
	public String getNome() {
		return this.nome;
	}
	
	public String getSobrenome() {
		return this.sobrenome;
	}
	
	public String getEmail() {
		return this.email;
	}
	
	public String getSenha() {
		return this.senha;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Conta other = (Conta) obj;
		return Objects.equals(id, other.id);
	}

	public abstract void insertTarefa(Tarefa tarefa);
	public abstract void deleteById(Integer id);
	public abstract void concluirTarefa(Integer id);
//	public abstract void update(Tarefa tarefa);
	public abstract Tarefa findById(Integer id);
	public abstract List<Tarefa> findAll();
	public abstract List<Tarefa> findIsConcluida();
	
}