package Actions;

import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import Client.JSONMessage;
import Client.JsonClient;
import Model.Parameter;

public class ActionChangeParameterValue extends AbstractJSONAction {

	public ActionChangeParameterValue() {
		mJsonMessage.setMessageType(2);
	    success = false;
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

	public void setParameterName(String name) {
	    mJsonMessage.setParameter("type", name);
	}

	public void setParameterValue(String newValue) {
		mJsonMessage.setParameter("param", newValue);
	}
	
	protected boolean success;

}
