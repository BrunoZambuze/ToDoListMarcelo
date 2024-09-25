package DataBase;

public class acessoNegadoException extends Exception{

	private final String msg;
	
	public acessoNegadoException(String msg) {
		this.msg = msg;
	}
	
	public String toString() {
		return this.msg;
	}
	
}
