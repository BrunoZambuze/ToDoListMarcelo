package Main;
import User.Usuario;
import account.Conta;
import Sistema.SistemaTotal;

public class Principal {

	public static void main(String[] args) {				

		try {
			SistemaTotal sistema = new SistemaTotal();
			Usuario user2 = new Usuario("Marcelo", "Gomes", false, sistema);
			Usuario user3 = new Usuario("Bruno", "Silva", true, sistema);
			user2.depositar(1000);
			user3.depositar(100);
			user2.sacar(100);
			user2.transferir("Bruno Silva", 300);
			sistema.imprimirUsuariosCadastrados();
			
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
}

/*
 * CONSIDERAÇÕES FINAIS: 
 * Na transferência, eu queria fazer a verificação utilizando o número da conta e fazendo as validações através desse número, porém como toda vez
 * que o programa é executado ele gera um novo número, iria ocorrer um conflito na hora de verificar esses valores, por isso optei pelo proprietário da conta.
 * 
 * Não consegui reutilizar muitos códigos. Será que criando diversas interfaces funcionais mais simples e juntando todas para construir um pipeline mais robusto compensa?.
 * 
 * Provavelmente eu errei bastante nas exceções.
 * 
 * O próximo passo é pegar os erros desse mini-projeto e melhorar no próximo, mas dessa vez integrando com o banco de dados.
*/
