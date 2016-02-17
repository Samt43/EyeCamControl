package Actions;

import Client.JSONMessage;


public class ActionFormatCard extends AbstractJSONAction {

	ActionFormatCard() {
		mJsonMessage.setMessageType(4);
	}

	@Override
	public String getActionName() {
		return "Format Card";
	}

	@Override
	void parseResponse(JSONMessage msg) {
	}
}
