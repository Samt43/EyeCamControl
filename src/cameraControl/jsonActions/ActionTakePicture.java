package cameraControl.jsonActions;

import cameraControl.client.JSONMessage;


public class ActionTakePicture extends AbstractJSONAction {

	public ActionTakePicture(String name) {
		super(name);
		mJsonMessage.setMessageType(769);
	}

	@Override
	protected
	void parseResponse(JSONMessage msg) {
		// TODO Auto-generated method stub

	}

	protected String mPicturePath = "none";
}
