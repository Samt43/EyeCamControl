package Actions;

import Client.JSONMessage;


public class ActionGetToken extends AbstractJSONAction {

	ActionGetToken() {
		mJsonMessage.setMessageType(257);
		setToken(0);
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
