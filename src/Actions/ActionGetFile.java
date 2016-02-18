package Actions;

import java.io.IOException;
import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import Client.JSONMessage;
import Client.JsonClient;
import Model.Parameter;

public class ActionGetFile extends AbstractJSONAction {

	public ActionGetFile(String filePath) {
		mFilePath = filePath;
		mJsonMessage.setMessageType(1285);
	    mJsonMessage.setParameter("param", mFilePath);
	    mJsonMessage.setParameter("offset", 0);
	    mJsonMessage.setParameter("fetch_size", 0);
	}

	@Override
	void parseResponse(JSONMessage msg) {
		mRemaining_Size = Integer.parseInt(msg.getJSONObject().get("rem_size").toString());
		mTotal_Size = Integer.parseInt(msg.getJSONObject().get("size").toString());
	}
	
	void parseResponse2(JSONMessage msg) {

	}

	public int getRemaining_Size()
	{
		return mRemaining_Size;
	}
	
	public int getTotal_Size()
	{
		return mTotal_Size;
	}
	protected String mFilePath;
	protected int mRemaining_Size;
	protected int mTotal_Size;

}
