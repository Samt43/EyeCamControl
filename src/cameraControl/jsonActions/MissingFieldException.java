package cameraControl.jsonActions;

public class MissingFieldException extends Exception{
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MissingFieldException(String message) {
        super(message);
    }

    public MissingFieldException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
