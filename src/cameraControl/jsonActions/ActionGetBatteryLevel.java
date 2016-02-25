package cameraControl.jsonActions;

import cameraControl.client.JSONMessage;

public class ActionGetBatteryLevel extends AbstractJSONAction {

	public ActionGetBatteryLevel(String name) {
		super(name);
		mJsonMessage.setMessageType(13);
	}

	public int getBatteryLevel()
	{
		return mBatteryPourcentage;
	}
	
	protected int mBatteryPourcentage = -1;

	@Override
	void parseResponse(JSONMessage msg) {
		mBatteryPourcentage = Integer.parseInt(msg.getJSONObject().get("param").toString());
	}
}
