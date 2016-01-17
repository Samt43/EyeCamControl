package Actions;

import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import Client.JSONMessage;
import Client.JsonClient;
import Model.Parameter;

public class ActionChangeParameterValue extends AbstractJSONAction {

	ActionChangeParameterValue(String parameterName, String newValue) {
		mJsonMessage.setMessageType(2);
	    mJsonMessage.setParameter("type", parameterName);
	    mJsonMessage.setParameter("param", newValue);
	    success = false;
	}

	@Override
	public String getActionName() {
		return "Change Parameter";
	}

	@Override
	void parseResponse(JSONMessage msg) {
		Long ret = (Long) msg.getJSONObject().get("rval");
		if (ret == 0)
		{
			success = true;
		}
	}
	
	public boolean getValueChanged()
	{
		return success;
	}
	
	protected boolean success;

}
