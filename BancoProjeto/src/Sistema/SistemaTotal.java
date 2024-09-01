package Sistema;
import User.Usuario;
import java.util.ArrayList;
import java.util.Collection;

public class SistemaTotal {

	final ArrayList<Usuario> listaUsuarios = new ArrayList<>();
	
	public SistemaTotal() {};
	
	public void adicionarUsuarioSistema(Usuario usuario) {
		if(usuario == null) {
			throw new NullPointerException("Erro ao adicionar usuario ao sistema");
		}
		listaUsuarios.add(usuario);
	}
	
	public ArrayList<Usuario> getUsuariosCadastradosNoSistema(){
		return this.listaUsuarios;
	}
	
	public void imprimirUsuariosCadastrados() {
		listaUsuarios.stream().forEach(usuario -> System.out.println(usuario.exibirDadosTotal() + "\n"));
	}
}