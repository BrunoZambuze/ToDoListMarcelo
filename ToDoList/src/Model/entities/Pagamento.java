package Model.entities;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class Pagamento {

	private Integer id;
	private final Double valor;
	private Date data_pagamento;
	
	public Pagamento() {
		this.valor = 19.90;
		setDataPagamento();
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public void setDataPagamento() {
		LocalDateTime localDate = LocalDateTime.now();
		localDate = localDate.plusMonths(1);
		Date dataAtual = Date.from(localDate.atZone(ZoneId.systemDefault()).toInstant());
		this.data_pagamento = dataAtual;
	}
	
	public Integer getId() {
		return this.id;
	}
	
	public Double getValor() {
		return this.valor;
	}
	
	public Date getDataPagamento() {
		return this.data_pagamento;
	}
	
}
