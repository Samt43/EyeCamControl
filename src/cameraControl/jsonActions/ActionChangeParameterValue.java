package cameraControl.jsonActions;

import cameraControl.client.JSONMessage;

public class ActionChangeParameterValue extends AbstractJSONAction {

	public ActionChangeParameterValue(String name) {
		super(name);
		mJsonMessage.setMessageType(2);
	}

	@Override
	protected
	void parseResponse(JSONMessage msg) {

	}

	public void setParameterName(String name) {
	    mJsonMessage.setParameter("type", name);
	}

	public void setParameterValue(String newValue) {
		mJsonMessage.setParameter("param", newValue);
	}

}
