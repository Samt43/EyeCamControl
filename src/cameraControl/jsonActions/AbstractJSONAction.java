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
		mActionName = name;
		mErrorsDuringExecution = false;
	}

	public void setHandler(BasicActionHandler handler)
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

	public String getActionName()
	{
		return mActionName;
	}
	
	public void executeCallBack() {
		mHandler.execute(this);
		// TODO Auto-generated method stub
	}

	public void executeErrorsCallBack() {
		System.out.println("Error during execution of " + getActionName());
		System.out.println("Error returned : " + Long.toString(getErrorCode()));
		mHandler.executeErrorCallback(this);
	}

	public void parseErrorsAndResponse(JSONMessage msg)
	{
		parseErrors(msg);
		if (success())
		{
			parseResponse(msg);
		}
	}

	private void parseErrors(JSONMessage msg) {
		// TODO Auto-generated method stub
		long ret = (long) msg.getJSONObject().get("rval");
		if (ret != 0)
		{
			mErrorsDuringExecution = true;
			mErrorNumber = ret;
		}
	}

	public long getErrorCode()
	{
		return mErrorNumber;
	}

	public boolean success()
	{
		return !mErrorsDuringExecution;
	}

	protected abstract void parseResponse(JSONMessage msg);
	protected JSONMessage mJsonMessage = new JSONMessage();
	protected JSONMessage mJsonResponse = new JSONMessage();
	ArrayList<String> mRequieredFields = new ArrayList<String>();
	boolean mNeedToken;
	boolean mErrorsDuringExecution;
	long mErrorNumber = 0;
	protected String mActionName;
	BasicActionHandler mHandler;

}
