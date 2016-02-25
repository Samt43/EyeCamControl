package cameraControl.tcpActions;

import java.io.IOException;

import cameraControl.client.RawDataClient;

public class ActionPutRawData extends AbstractRawTCPAction {

	ActionPutRawData(byte array[]) {
		mBytes = array;
	}

	@Override
	public String getActionName() {
		return "Put_Raw_Data";
	}
	
	public void execute(RawDataClient client) throws IOException {
		client.putData(mBytes);
	}

	protected byte[] mBytes;
}
