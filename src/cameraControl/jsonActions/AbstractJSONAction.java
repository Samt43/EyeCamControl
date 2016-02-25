/**
 * 
 */
package cameraControl.jsonActions;

import java.io.IOException;
import java.util.ArrayList;

import org.json.simple.parser.ParseException;

import cameraControl.client.JSONMessage;
import cameraControl.client.JsonClient;

/**
 * @author mathieu
 *
 */
public abstract class AbstractJSONAction{

	public AbstractJSONAction(String name) {
		mNeedToken = true;
		mActionName = name;
	}

	public void setHandler(BasicHandler handler)
	{
		mHandler = handler;
	}

	public void setToken(int i)
	{
		mJsonMessage.setParameter("token", i);
	}

	void execute(JsonClient client) throws IOException, ParseException, MissingFieldException {
		checkValidity();
        System.out.println("Execute JSON Action "+ this.getClass().getName() + "\r\n");
        client.send(mJsonMessage);

	}
	
	boolean needToken()
	{
		return mNeedToken;
	}

	public Integer getType() {
		// TODO Auto-generated method stub
		return mJsonMessage.getMessageType();
	}

	protected void checkValidity() throws MissingFieldException
	{
		for (String field : mRequieredFields) {
			if (!mJsonMessage.containsField(field))
			{
				throw new MissingFieldException(field + " is missing");
				//
			}
		}
	}

	public void executeCallBack() {
		mHandler.execute(this);
		// TODO Auto-generated method stub
	}

	abstract void parseResponse(JSONMessage msg);
	protected JSONMessage mJsonMessage = new JSONMessage();
	protected JSONMessage mJsonResponse = new JSONMessage();
	ArrayList<String> mRequieredFields = new ArrayList<String>();
	boolean mNeedToken;
	protected String mActionName;
	BasicHandler mHandler;

}
