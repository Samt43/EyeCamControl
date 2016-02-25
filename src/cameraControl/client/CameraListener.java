package cameraControl.client;

import java.io.IOException;
import org.json.simple.parser.ParseException;


public class CameraListener extends Thread {

	private JsonClient mJSONClient;

	public CameraListener(JsonClient JSONClient)
	{
		mJSONClient = JSONClient;
	}

	public void run() {
		while (mJSONClient.isConnected())
		{
	    try {
			mJSONClient.getResponse();
		} catch (IOException | ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
	}
}
