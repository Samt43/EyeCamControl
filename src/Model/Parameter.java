package Model;

public class Parameter {
	public Parameter(String name,String value) {
		mName = name;
		mValue = value;
	}
	
	public String toString()
	{
		return mName + " -> " + mValue;
	}
	
	public String getName()
	{
		return mName;
	}
	protected String mName;
	protected String mValue;
}
