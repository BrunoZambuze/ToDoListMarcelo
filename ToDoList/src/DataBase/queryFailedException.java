package DataBase;

public class queryFailedException extends Exception{

	private String msg;
	
	public queryFailedException(String msg) {
		this.msg = msg;
	}
	
	public String getMessage() {
		return this.msg;
	}
	
}
