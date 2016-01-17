package Client;

public abstract class Message {

	public abstract String getRawContent();
	public int getSize()
	{
		return getRawContent().length();
		
	}

}
