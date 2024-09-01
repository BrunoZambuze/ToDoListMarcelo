package account;
import User.Usuario;
import java.util.Random;
import User.Usuario;
import Sistema.SistemaTotal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.*;
import java.util.stream.Collector;

public abstract class Conta {

	private Usuario usuario; 
	private SistemaTotal sistemaTotal;
	private Random gerador = new Random();
	private String numeroConta;
	private String proprietario;
	private double saldoConta; //Por padrão recebe 0
	
	protected Conta(Usuario usuario, SistemaTotal sistemaTotal){
		try {
			this.sistemaTotal = sistemaTotal;
			setUsuario(usuario);
			setProprietario();
			setSaldoConta(0.0);
			setNumeroConta();
		} catch(Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
	}
	
	protected String getNumeroConta() {
		return this.numeroConta;
	}
	
	protected void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	protected Usuario getUsuario() {
		return this.usuario;
	}
	
	protected double getSaldoConta() {
		return this.saldoConta;
	}
	
	protected void setSaldoConta(double valor) {
		this.saldoConta = valor;
	}
	
	protected String getProprietario() {
		return usuario.getNome() + " " + usuario.getSobrenome();
	}
	
	//Preencher o nome do proprietario da conta automaticamente
	private void setProprietario() {
		this.proprietario = this.getProprietario();
	}

	@Override
	public int hashCode() {
		return Objects.hash(numeroConta, numeroContaString, proprietario);
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
		return Objects.equals(numeroConta, other.numeroConta)
				&& Objects.equals(numeroContaString, other.numeroContaString)
				&& Objects.equals(proprietario, other.proprietario);
	}

	//Gerar um numero da conta aleatório de até 11 digitos
	String numeroContaString = ""; //Eu posso fazer isso? Jogar a variável para fora do corpo. O predicado isNumeroExistente estava pedindo para que eu colocasse 
	private void setNumeroConta() { //a variável numeroContaString como 'final'. Pesquisei e descobri que era por contade de ela estar em um escopo interno. Por isso retirei,
		int[] numero = new int[12]; //mas não tenho certeza se é exatamente por isso que deu erro.
		
		boolean jaExiste;
		do {
			for(int i = 0; i < 12; i++) {
				numero[i] = gerador.nextInt(9);
				numeroContaString = numeroContaString.concat(Integer.toString(numero[i]));
			}
			Predicate<Usuario> isNumeroExistente = usuario -> {	return numeroContaString.equals(usuario.getConta().getNumeroConta()); };
			ArrayList<Usuario> listaUsuario = this.sistemaTotal.getUsuariosCadastradosNoSistema();
			this.numeroConta = numeroContaString;
			jaExiste = listaUsuario.stream().anyMatch(isNumeroExistente);
			if(jaExiste) {
				numeroContaString = "";
			}
		}while(jaExiste);
		if(numeroConta == null) {
			throw new NullPointerException("Erro: Número da conta não foi criado");
		}
	}
	
	/**
	 * 
	 * @param numero
	 * @return boolean, verificando se o nome está correto
	 * @NullPointerException caso o valor passado seja nulo
	 * @IllegalArgumentException caso tenha numeros
	 */
	protected boolean verificarNumeroNome(String nome) { // Eu poderia deixar esse método mais simples reutilizando o método de verificarNumeros, mas não sei
		if(nome == null) {								// se é viável eu usar um método da classe Usuario aqui na classe Conta. Também tem o fato desse método
													   // estar privado, eu só conseguiria reutiliza-lo se deixasse público, por questões de segurança deixei private.
			throw new NullPointerException("Erro: Nome de referência não poder ficar nulo");
		}
		for(int i = 0; i < nome.length(); i++) {
			char caractere = nome.charAt(i);
			if(caractere >= '0' && caractere <= '9') {
				throw new IllegalArgumentException("Erro: Por favor, retire os números");
			}else if((caractere >= 'a' && caractere <= 'z') || (caractere >= 'A' && caractere <= 'Z')) {
				continue;
			}
		}		
		return true;
	}
	
	//Método para verificar se existe alguma conta com o nome que o usuário passou
	/**
	 * @param nome
	 * @NullPointerException Caso o nome esteja nulo
	 * @IllegalArgumentException Se o usuário querer transferir para sua própria conta
	 */
	protected Conta verificarSeContaExiste(String nome) {
		if(nome == null) {
			throw new NullPointerException("Erro: Nome não pode ser nulo");
		}
		if(nome.equals(this.getProprietario())) {
			throw new IllegalArgumentException("Erro: Não é possível transferir para a própria conta!");
		}
		ArrayList<Usuario> listaUsuario = this.sistemaTotal.getUsuariosCadastradosNoSistema();
		Predicate<Usuario> isContaExistente = usuario -> { return nome.equals(usuario.getConta().getProprietario());};
		Conta contaEncontrada = listaUsuario.stream()
											.filter(isContaExistente)
											.map(Usuario::getConta)
											.findAny()
											.orElseThrow(() -> new NullPointerException("Conta não encontrada"));
		return contaEncontrada;
	}
	
	/**
	 * 
	 * @param nome
	 * @param valor
	 * @throws NullPointerException
	 * @throws IllegalArgumentException Caso o valor da transferência seja negativo
	 */
	public void transferir(String nome, double valor) throws NullPointerException, IllegalArgumentException {
		if(valor < 0) {
			throw new IllegalArgumentException("Erro: Valor inserido para transferência inválido!");
		}
		this.verificarNumeroNome(nome);
		Conta contaEncontrada = this.verificarSeContaExiste(nome);
		this.sacar(valor);
		contaEncontrada.depositar(valor);
	}
	public abstract void sacar(double valor);
	public abstract void depositar(double valor);
	public abstract String exibirDadosConta();
	
}