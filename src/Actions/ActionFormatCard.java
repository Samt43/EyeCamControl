package Actions;

import Client.JSONMessage;


public class ActionFormatCard extends AbstractJSONAction {

	public ActionFormatCard() {
		mJsonMessage.setMessageType(4);
	}

	@Override
	void parseResponse(JSONMessage msg) {
	}
}
