/**
 * 
 */
package Actions;

import java.io.IOException;

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

	public abstract String getActionName();

	public void setToken(int i)
	{
		mJsonMessage.setParameter("token", i);
	}

	void execute(JsonClient client) throws IOException, ParseException {
        System.out.println("Execute JSON Action "+ getActionName() + "\r\n");
        client.send(mJsonMessage);
		mJsonResponse = client.getResponse();
		parseResponse(mJsonResponse);
	}
	
	boolean needToken()
	{
		return mNeedToken;
	}

	abstract void parseResponse(JSONMessage msg);
	protected JSONMessage mJsonMessage = new JSONMessage();
	protected JSONMessage mJsonResponse = new JSONMessage();
	boolean mNeedToken;
}
