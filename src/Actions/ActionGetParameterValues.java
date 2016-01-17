package Actions;

import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import Client.JSONMessage;
import Client.JsonClient;
import Model.Parameter;

public class ActionGetParameterValues extends AbstractJSONAction {

	ActionGetParameterValues(String parameterName) {
		mParameterName = parameterName;
		mJsonMessage.setMessageType(9);
	    mJsonMessage.setParameter("param", parameterName);
	}

	@Override
	public String getActionName() {
		return "Get Parameter";
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
	
	protected ArrayList<String> mParameterValues = new ArrayList<String>();
	protected String mParameterName;

}
