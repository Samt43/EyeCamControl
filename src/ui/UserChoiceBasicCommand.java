package ui;

import cameraControl.CameraController;
import cameraControl.jsonActions.MissingFieldException;


public class UserChoiceBasicCommand extends UserChoice{
	CameraController mCameraController;
	String mBasicCommand;
	
	public UserChoiceBasicCommand(String description, CameraController controller, String command) {
		super(description);
		mCameraController = controller;
		mBasicCommand = command;
	}

	@Override
	public void execute() throws MissingFieldException {
		mCameraController.executeBasicCommand(mBasicCommand);
	}

}
