package cameraControl.jsonActions;

public interface BasicActionHandler {
	public void execute(AbstractJSONAction a);
	public void executeErrorCallback(AbstractJSONAction abstractJSONAction);

}
