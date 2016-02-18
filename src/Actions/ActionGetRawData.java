package Actions;

import java.io.IOException;

import org.json.simple.parser.ParseException;

import Client.RawDataClient;

public class ActionGetRawData extends AbstractRawTCPAction {

	public ActionGetRawData(int lenght) {
		mLenght = lenght;
	}

	@Override
	public String getActionName() {
		return "Get_Raw_Data";
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
