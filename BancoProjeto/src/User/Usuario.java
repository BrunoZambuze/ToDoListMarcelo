package User;
import account.*;
import Sistema.SistemaTotal;

public class Usuario {

	private String nome;
	private String sobrenome;
	private Conta conta;
	private SistemaTotal sistemaTotal;
	
	public Usuario(String nome, String sobrenome, boolean empresarial, SistemaTotal sistemaTotal) {
		this.sistemaTotal = sistemaTotal;
		setNome(nome);
		setSobrenome(sobrenome);
		setConta(empresarial);
		setConta(conta);	
		setSistemaTotal(sistemaTotal); //Eu tentei relacionar o sistema com usuário pra ele adicionar esse usuário automaticamente, mas na classe usuário
	}								  //não estava conseguindo acessar o método 'adicionarUsuario' pois o sistema não tinha um objeto criado. A única solução
									 //que eu pensei foi em instanciar o objeto do sistema diretamente na main e passar esse objeto como argumento no meu construtor
									//do usuário, assim eu consigo acessar esse objeto diretamente na classe, para aí sim conseguir adicionar "automaticamente".
	
	private void setSistemaTotal(SistemaTotal sistemaTotal) {
		if (sistemaTotal == null) {
			throw new NullPointerException("Erro: Sistema não adicionado");
		}
		this.sistemaTotal.adicionarUsuarioSistema(this);
	}
	
	public SistemaTotal getSistemaTotal() {
		return this.sistemaTotal;
	}
	
	//Polimorfismo
	private void setConta(Conta conta) {
		if(conta == null) {
			throw new NullPointerException("Erro ao criar a conta, está nulo");
		}
		this.conta = conta;
	}
	
	//Verifica se a conta a ser criada é corrente ou empresarial
	private void setConta(Boolean empresarial) { //Coloquei Boolean como Wrapper para poder verificar se ele é nulo.
		if(empresarial == null) {
			throw new NullPointerException("Erro: Por favor, informe o tipo da conta. True: Empresarial | False: Corrente");
		}
		if(empresarial) {
			this.setConta(new ContaEmpresarial(this, this.sistemaTotal));
		}else {
			this.setConta(new ContaCorrente(this, this.sistemaTotal));
		}
	}
	
	public Conta getConta() {
		return this.conta;
	}
	
	public String getNome() {
		return this.nome;
	}
	
	/**
	 * @param nome
	 * @NullPointerException Caso o nome esteja vazio
	 * @IllegalArgumentException Caso o nome tenha algum caractere numérico
	 */
	public void setNome(String nome) {
		if(nome.trim().isEmpty()) { //Verifica se o nome foi preenchido
			throw new NullPointerException("Error: O nome não pode ficar vazio");
		}
		try {
			if(verificarNumeros(nome)) {
				this.nome = nome;
			}
		} catch(IllegalArgumentException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public String getSobrenome() {
		return this.sobrenome;
	}
	
	/**
	 * @param sobrenome
	 * @NullPointerException Caso o sobrenome esteja vazio
	 * @IllegalArgumentException Caso o sobrenome tenha algum caractere numérico
	 */
	public void setSobrenome(String sobrenome) {
		if(sobrenome.trim().isEmpty()) { //Verifica se o sobrenome foi preenchido
			throw new NullPointerException("Error: O sobrenome não pode ficar vazio");
		}
		try {
			if(verificarNumeros(sobrenome)) {
				this.sobrenome = sobrenome;
			}
		} catch(IllegalArgumentException e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * 
	 * @param nome
	 * @return
	 * @IllegalArgumentException Caso o nome tenha algum numero
	 * @IllegalArgumentException Caso o nome tenha algum caractere especial
	 */
	//Verificar se o nome não tem números
	private boolean verificarNumeros(String nome) {
		for(int i = 0; i < nome.length(); i++) {
			char caractere = nome.charAt(i);
			//No Unicode, o 'char' 0 tem o valor numérico de 48 e o 'char' 9 o valor numérico de 57
			//Caso o nome tenha algum valor numérico entre 48 a 57, será caracterizado como número
			if(caractere >= '0' && caractere <= '9') {
				throw new IllegalArgumentException("Erro: Por favor, retire os números");
			}
			if((caractere >= '!' && caractere <= '/') || (caractere >= ':' && caractere <= '@') || 
			   (caractere >= '[' && caractere <= '`') || (caractere >= '{' && caractere <= '}')) {
				throw new IllegalArgumentException("Erro: Por favor, retire os caracteres especiais");
			} else if((caractere >= 'a' && caractere <= 'z') || (caractere >= 'A' && caractere <= 'Z')) {
				continue;
			}
		}		
		return true;
	}
	
	public String exibirDadosTotal() {
		if(this.nome != null && this.sobrenome != null && this.conta != null) {
			return this.conta.exibirDadosConta();
		}
		throw new IllegalStateException("Dados incompletos: nome, sobrenome ou conta estão errados");
	}
	
	
	//MÉTODOS DO SALDO
	public void transferir(String numero, double valor) {
		try {
			this.conta.transferir(numero, valor);
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
		} catch (NullPointerException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public void sacar(double valor) {
		try{
			this.conta.sacar(valor);
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
		} catch (NullPointerException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public void depositar(double valor) {
		try {
			this.conta.depositar(valor);
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
		} catch (NullPointerException e) {
			System.out.println(e.getMessage());
		}
	}
}