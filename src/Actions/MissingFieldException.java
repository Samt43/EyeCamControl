package Actions;

public class MissingFieldException extends Exception{
	
    public MissingFieldException(String message) {
        super(message);
    }

    public MissingFieldException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
