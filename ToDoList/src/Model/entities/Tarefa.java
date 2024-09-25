package Model.entities;

import java.util.Objects;

public class Tarefa {

	private Integer id;
	private String nome;
	private String descricao;
	private Boolean isConcluida;
	private Integer id_standard;
	private Integer id_premium;
	
	public Tarefa(Integer id, String nome, String descricao, Boolean isConcluida, Integer id_standard, Integer id_premium) {
		if(id_premium != null) {
			setIdPremium(id_premium);
		} else {
			this.id_premium = null;
		}
		if(id_standard != null) {
			setIdStandard(id_standard);
		} else {
			this.id_standard = id_standard;
		}
		if(isConcluida == false) {
			this.isConcluida = false;
		} else {
			setIsConcluida(isConcluida);
		}
		setId(id);
		setNome(nome);
		setDescricao(descricao);
	}
	
	public Tarefa(String nome, String descricao) {
		this.id = null;
		setNome(nome);
		setDescricao(descricao);
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	public void setIsConcluida(Boolean isConcluida) {
		this.isConcluida = isConcluida;
	}
	
	public void setIdPremium(Integer id) {
		this.id_premium = id;
	}
	
	public void setIdStandard(Integer id) {
		this.id_standard = id;
	}
	
	public Integer getId() {
		return this.id;
	}
	
	public String getNome() {
		return this.nome;
	}
	
	public String getDescricao() {
		return this.descricao;
	}
	
	public Boolean getIsConcluida() {
		return this.isConcluida;
	}
	
	public Integer getIdStandard() {
		return this.id_standard;
	}
	
	public Integer getIdPremium() {
		return this.id_premium;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(id, id_premium, id_standard);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Tarefa other = (Tarefa) obj;
		return Objects.equals(id, other.id) && Objects.equals(id_premium, other.id_premium)
				&& Objects.equals(id_standard, other.id_standard);
	}

	public String toString() {
		return "id: " + this.getId() + "\nNome: " + this.getNome() + "\nDescrição: " + this.getDescricao() + "\nConcluída?: " + this.getIsConcluida() + "\n"
				+ "id_standard: " + this.getIdStandard() + "\nid_premium: " + this.getIdPremium() + "\n";
	}
	
}
