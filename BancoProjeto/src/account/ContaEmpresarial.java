package account;
import Sistema.SistemaTotal;
import User.Usuario;

public class ContaEmpresarial extends Conta{

	static final private String tipoConta = "Empresarial";
	public ContaEmpresarial(Usuario usuario, SistemaTotal sistemaTotal) {
		super(usuario, sistemaTotal);
	}
	
	protected static String getTipoConta() {
		return tipoConta;
	}
	
	@Override
	public void sacar(double valor) {
		if(super.getSaldoConta() > 0 && valor > 0 && valor <= 6000.00) {
			if(super.getSaldoConta() - valor >= -500) {
				double saldoConta = super.getSaldoConta();
				saldoConta -= valor;
				super.setSaldoConta(saldoConta);
			}else {
				throw new IllegalArgumentException("Erro: Não foi possível sacar a quantia. O saldo ultrapassou o limite de 500 reais negativos");
			}
		}else {
			throw new IllegalArgumentException("Erro: Algo deu errado");
		}
	}
	
	@Override
	public void depositar(double valor) {
		if(valor > 0) {
			double valorConta = super.getSaldoConta();
			setSaldoConta(valorConta += valor);
		}else {
			throw new IllegalArgumentException("Erro: Não é possível depositar valor negativo");
		}
	}
	
	@Override
	public String exibirDadosConta() {
		return String.format("Tipo: %s\nProprietário: %s\nNúmero da conta: %s\nSaldo: R$ %.2f", 
				this.getTipoConta(), super.getProprietario(), super.getNumeroConta(), super.getSaldoConta());
	}
}
