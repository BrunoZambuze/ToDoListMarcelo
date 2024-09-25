package DataBase;

public class closeStatementException extends RuntimeException{

	public String msg;
	
	public closeStatementException(String msg) {
		this.msg = msg;
	}
	
	public String getMessage() {
		return this.msg;
	}
	
}
