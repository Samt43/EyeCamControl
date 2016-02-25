package cameraControl;

import cameraControl.jsonActions.BasicHandler;

public abstract class CameraControllerHandler implements BasicHandler {
	CameraControllerHandler(CameraController cameraController)
	{
		mCameraController = cameraController;
	}

	protected CameraController mCameraController;
}
