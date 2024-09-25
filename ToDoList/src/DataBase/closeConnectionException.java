package DataBase;

public class closeConnectionException extends RuntimeException{
	
	public String msg;
	
	public closeConnectionException(String msg) {
		this.msg = msg;
	}
	
	public String getMessage() {
		return this.msg;
	}
	
}
