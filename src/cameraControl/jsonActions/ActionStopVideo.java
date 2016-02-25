package cameraControl.jsonActions;

import cameraControl.client.JSONMessage;


public class ActionStopVideo extends AbstractJSONAction {

	public ActionStopVideo(String name) {
		super(name);
		mJsonMessage.setMessageType(514);
	}

	@Override
	void parseResponse(JSONMessage msg) {
		// TODO Auto-generated method stub

	}

	public String getVideoPath()
	{
		return mVideoPath;
	}
	
	protected String mVideoPath = "none";
}
