package DataBase;

public class closeResultSetException extends RuntimeException{

	public String msg;
	
	public closeResultSetException(String msg) {
		this.msg = msg;
	}
	
	public String getMessage() {
		return this.msg;
	}
	
}
