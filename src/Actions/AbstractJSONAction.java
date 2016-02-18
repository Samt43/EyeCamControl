/**
 * 
 */
package Actions;

import java.io.IOException;
import java.util.ArrayList;

import org.json.simple.parser.ParseException;

import Client.JSONMessage;
import Client.JsonClient;

/**
 * @author mathieu
 *
 */
public abstract class AbstractJSONAction{

	public AbstractJSONAction() {
		mNeedToken = true;
	}

	public void setToken(int i)
	{
		mJsonMessage.setParameter("token", i);
	}

	void execute(JsonClient client) throws IOException, ParseException, MissingFieldException {
		checkValidity();
        System.out.println("Execute JSON Action "+ this.getClass().getName() + "\r\n");
        client.send(mJsonMessage);
        mJsonResponse = client.getResponse();
        parseResponse(mJsonResponse);

	}
	
	boolean needToken()
	{
		return mNeedToken;
	}

	private void checkValidity() throws MissingFieldException
	{
		for (String field : mRequieredFields) {
			if (!mJsonMessage.containsField(field))
			{
				throw new MissingFieldException(field + " is missing");
				//
			}
		}
	}

	abstract void parseResponse(JSONMessage msg);
	protected JSONMessage mJsonMessage = new JSONMessage();
	protected JSONMessage mJsonResponse = new JSONMessage();
	ArrayList<String> mRequieredFields = new ArrayList<String>();
	boolean mNeedToken;
}
