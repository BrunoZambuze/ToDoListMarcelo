package DataBase;

public class factoryException extends Exception {

	private String msg;
	
	public factoryException(String msg) {
		this.msg = msg;
	}
	
	public String getMessage() {
		return this.msg;
	}
	
}
