package cameraControl.jsonActions;

import cameraControl.client.JSONMessage;


public class ActionFormatCard extends AbstractJSONAction {

	public ActionFormatCard(String name) {
		super(name);
		mJsonMessage.setMessageType(4);
	}

	@Override
	protected
	void parseResponse(JSONMessage msg) {
	}
}
