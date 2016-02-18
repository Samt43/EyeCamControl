package Actions;

import java.io.IOException;

import org.json.simple.parser.ParseException;

import com.sun.security.ntlm.Client;

import Client.JSONMessage;
import Client.JsonClient;


public class ActionStopVideo extends AbstractJSONAction {

	public ActionStopVideo() {
		mJsonMessage.setMessageType(514);
	}

	@Override
	void parseResponse(JSONMessage msg) {
		// TODO Auto-generated method stub

	}

	public String getVideoPath()
	{
		return mVideoPath;
	}

	void parseResponse2(JSONMessage msg) {
		mVideoPath = msg.getJSONObject().get("param").toString();
	}
	
	@Override
	void execute(JsonClient client) throws IOException, ParseException, MissingFieldException {

		super.execute(client);

		try {
			JSONMessage jsonResponse2 = client.getResponse();
			parseResponse2(jsonResponse2);
		} catch (IOException e) {
			System.out.println("Exception receiving path photo taken !");
			e.printStackTrace();
		}
	}
	protected String mVideoPath = "none";
}
