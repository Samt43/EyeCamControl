package Actions;

import Client.JSONMessage;


public class ActionGetBatteryLevel extends AbstractJSONAction {

	public ActionGetBatteryLevel() {
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
