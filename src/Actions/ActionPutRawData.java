package Actions;

import java.io.IOException;

import org.json.simple.parser.ParseException;

import Client.RawDataClient;

public class ActionPutRawData extends AbstractRawTCPAction {

	ActionPutRawData(byte array[]) {
		mBytes = array;
	}

	@Override
	public String getActionName() {
		return "Put Raw Data...";
	}
	
	public void execute(RawDataClient client) throws IOException, ParseException {
		client.putData(mBytes);
	}

	protected byte[] mBytes;
}
