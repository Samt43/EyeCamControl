package cameraControl.jsonActions;

import java.io.IOException;

import org.json.simple.parser.ParseException;

import cameraControl.client.JSONMessage;
import cameraControl.client.JsonClient;


public class ActionStartSession extends AbstractJSONAction {

	public ActionStartSession(String name) {
		super(name);
		mJsonMessage.setMessageType(257);
		setToken(0);
	}

	public int getToken()
	{
		System.out.println("Access Token : ");
		System.out.println(mToken);
		return mToken;
	}
	
	@Override
	protected
	void parseResponse(JSONMessage msg) {
		mToken = Integer.parseInt(msg.getJSONObject().get("param").toString());
		
	}
	
	void execute(JsonClient client) throws IOException, ParseException, MissingFieldException {
		super.execute(client);
		// We process this action synchronously.
        parseResponse(client.getResponse());
	}

	protected int mToken = -1;

}
