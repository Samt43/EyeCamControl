package Actions;

import java.io.IOException;
import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import Client.JSONMessage;
import Client.JsonClient;
import Model.Parameter;

public class ActionListen extends AbstractJSONAction {

	ActionListen() {
		mNeedToken = false;
	}

	@Override
	public String getActionName() {
		return "Listening";
	}

	@Override
	void parseResponse(JSONMessage msg) {
	
	}
	
	@Override
	void execute(JsonClient client) throws IOException, ParseException {
		try {
			JSONMessage jsonResponse2 = client.getResponse();
			parseResponse(jsonResponse2);
		} catch (IOException e) {
			System.out.println("Exception receiving path photo taken !");
			e.printStackTrace();
		}
	}

}
