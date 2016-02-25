package cameraControl.jsonActions;

import cameraControl.client.JSONMessage;

public interface JSONActionControllerHandler {

	void onActionIsCompleted(AbstractJSONAction a);

	void onNotificactionReceived(JSONMessage m);


}
