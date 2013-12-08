
public class EndGameException extends Exception {
	
	private static final long serialVersionUID = 1L;
	public String exception;
	
	public EndGameException(String s) {
		super(s);
		exception = s;
	}
	
	public EndGameException() {
		exception = "Unknown Exception";
	}
}
