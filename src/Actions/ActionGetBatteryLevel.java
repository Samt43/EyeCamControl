package Actions;

import Client.JSONMessage;


public class ActionGetBatteryLevel extends AbstractJSONAction {

	ActionGetBatteryLevel() {
		mJsonMessage.setMessageType(13);
	}

	@Override
	public String getActionName() {
		return "Get Token...";
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
