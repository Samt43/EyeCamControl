package ui;

import cameraControl.jsonActions.MissingFieldException;

public abstract class UserChoice {
	public UserChoice(String description)
	{
		mDescription = description;
	}
	public String getDescription()
	{
		return mDescription;
	}

	abstract public void execute() throws MissingFieldException;

	String mDescription;
}
