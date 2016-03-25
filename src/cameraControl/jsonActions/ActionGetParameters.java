package cameraControl.jsonActions;

import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import cameraControl.cameraModel.Parameter;
import cameraControl.client.JSONMessage;

public class ActionGetParameters extends AbstractJSONAction {

	public ActionGetParameters(String name) {
		super(name);
		mJsonMessage.setMessageType(3);
	}

	@Override
	protected
	void parseResponse(JSONMessage msg) {
		JSONArray array = (JSONArray)msg.getJSONObject().get("param");
		for (Object object : array) {
			
			String key = (String)((JSONObject)object).keySet().iterator().next();
			String value = (String)((JSONObject)object).get(key);
			
			mParameterList.add(new Parameter(key, value));
		}

	}
	
	public ArrayList<Parameter> getParameters()
	{
		return mParameterList;
	}
	
	protected ArrayList<Parameter> mParameterList = new ArrayList<Parameter>();

}
