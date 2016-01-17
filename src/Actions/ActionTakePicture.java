package Actions;

import java.io.IOException;

import org.json.simple.parser.ParseException;

import com.sun.security.ntlm.Client;

import Client.JSONMessage;
import Client.JsonClient;


public class ActionTakePicture extends AbstractJSONAction {

	ActionTakePicture() {
		mJsonMessage.setMessageType(769);
	}

	@Override
	public String getActionName() {
		return "Take Picture";
	}

	@Override
	void parseResponse(JSONMessage msg) {
		// TODO Auto-generated method stub

	}

	void parseResponse2(JSONMessage msg) {
		mPicturePath = msg.getJSONObject().get("param").toString();
	}

	public String getPicturePath()
	{
		return mPicturePath;
	}
	
	@Override
	void execute(JsonClient client) throws IOException, ParseException {

		super.execute(client);

		try {
			JSONMessage jsonResponse2 = client.getResponse();
			parseResponse2(jsonResponse2);
		} catch (IOException e) {
			System.out.println("Exception receiving path photo taken !");
			e.printStackTrace();
		}
	}
	
	protected String mPicturePath = "none";
}
