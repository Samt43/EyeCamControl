package cameraControl;

import cameraControl.jsonActions.AbstractJSONAction;
import cameraControl.jsonActions.BasicActionHandler;

public abstract class CameraControllerHandler implements BasicActionHandler {
	@Override
	public void executeErrorCallback(AbstractJSONAction abstractJSONAction) {
		System.out.println("ErrorCallback not implemented");
		
	}
}
