package Actions;

import java.util.ArrayList;

import org.json.simple.JSONArray;

import Client.JSONMessage;

public class ActionGetParameterValues extends AbstractJSONAction {

	public ActionGetParameterValues(String parameterName) {
		mParameterName = parameterName;
		mJsonMessage.setMessageType(9);
	    mJsonMessage.setParameter("param", parameterName);
	}

	@Override
	void parseResponse(JSONMessage msg) {
		JSONArray array = (JSONArray)msg.getJSONObject().get("options");
		for (Object object : array) {
			
			String key = (String)object;

			mParameterValues.add(key);
		}

	}
	
	public ArrayList<String> getParameters()
	{
		return mParameterValues;
	}
	
	public void setParameterName(String name) {
		mParameterName = name;
		
	}

	protected ArrayList<String> mParameterValues = new ArrayList<String>();
	protected String mParameterName;


}
