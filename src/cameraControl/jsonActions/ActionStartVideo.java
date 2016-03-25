package cameraControl.jsonActions;

import cameraControl.client.JSONMessage;


public class ActionStartVideo extends AbstractJSONAction {

	public ActionStartVideo(String name) {
		super(name);
		mJsonMessage.setMessageType(513);
	}

	@Override
	protected
	void parseResponse(JSONMessage msg) {
		// TODO Auto-generated method stub

	}
	
}
