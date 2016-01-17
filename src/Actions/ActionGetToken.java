package Actions;

import Client.JSONMessage;
import Client.JsonClient;


public class ActionGetToken extends AbstractJSONAction {

	ActionGetToken() {
		mJsonMessage.setMessageType(257);
		setToken(0);
	}

	@Override
	public String getActionName() {
		return "Get Token...";
	}

	public int getToken()
	{
		System.out.println("Access Token : ");
		System.out.println(mToken);
		return mToken;
	}
	
	protected int mToken = -1;

	@Override
	void parseResponse(JSONMessage msg) {
		mToken = Integer.parseInt(msg.getJSONObject().get("param").toString());
		
	}

}
