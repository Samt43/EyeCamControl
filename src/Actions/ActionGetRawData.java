package Actions;

import java.io.IOException;
import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import Client.JSONMessage;
import Client.JsonClient;
import Client.RawDataClient;
import Model.Parameter;

public class ActionGetRawData extends AbstractRawTCPAction {

	ActionGetRawData(int lenght) {
		mLenght = lenght;
	}

	@Override
	public String getActionName() {
		return "Get Raw Data...";
	}
	
	public void execute(RawDataClient client) throws IOException, ParseException {
		mBytes = client.getData(mLenght);
	}

	public byte[] getBytes()
	{
		return mBytes;
	}

	protected int mLenght;
	protected byte[] mBytes;
}
