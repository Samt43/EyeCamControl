package Actions;

import java.io.IOException;

import org.json.simple.parser.ParseException;

import com.sun.security.ntlm.Client;

import Client.JSONMessage;
import Client.JsonClient;


public class ActionStartVideo extends AbstractJSONAction {

	ActionStartVideo() {
		mJsonMessage.setMessageType(513);
	}

	@Override
	public String getActionName() {
		return "Start Video";
	}

	@Override
	void parseResponse(JSONMessage msg) {
		// TODO Auto-generated method stub

	}

	void parseResponse2(JSONMessage msg) {
		//
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
}
