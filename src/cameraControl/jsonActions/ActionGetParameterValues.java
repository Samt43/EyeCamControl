package cameraControl.jsonActions;

import java.util.ArrayList;

import org.json.simple.JSONArray;

import cameraControl.client.JSONMessage;

public class ActionGetParameterValues extends AbstractJSONAction {

	public ActionGetParameterValues(String name) {
		super(name);
		mJsonMessage.setMessageType(9);
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
	    mJsonMessage.setParameter("param", name);
		
	}

	protected ArrayList<String> mParameterValues = new ArrayList<String>();


}
