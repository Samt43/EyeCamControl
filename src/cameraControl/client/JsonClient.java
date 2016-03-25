package cameraControl.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import org.json.simple.parser.ParseException;

public class JsonClient extends AbstractTCPClient{
	
	public JsonClient(String addr,int port) {
		super(addr,port);
		mAsynchMode = false;
	}

	public void startAsynchMode()
	{
		try {
			mSocket.setSoTimeout(0);
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		mSocketListener.start();
		mAsynchMode = true;
	}

	@Override
	public void connect() throws IOException
	{
		super.connect();
        mOut = new OutputStreamWriter(
			     mSocket.getOutputStream(), StandardCharsets.UTF_8);
        mIn = new BufferedReader(new InputStreamReader(mSocket.getInputStream()));
        mSocketListener = new CameraListener(this);
        
	}

	public void send(JSONMessage msg) throws IOException
	{
        System.out.println("Sending "+ msg.getRawContent() + "\r\n");

        mOut.write(msg.getRawContent(),0,msg.getSize());
        mOut.flush();
        
	}

	public JSONMessage getResponse() throws IOException, ParseException {
		String resp = readJson();
		System.out.println("Message received : " + resp);
		JSONMessage msg = new JSONMessage();
		msg.setRawContent(resp);

		if (mAsynchMode)
		{
			NotifyObservers(msg);
		}

		return msg;
	}

	public boolean isConnected()
	{
		return mSocket.isConnected();
	}

	// observers should be added before connect.
	public void addObservers(ClientObserverInterface anObserver)
	{
		mObservers.add(anObserver);
		
	}

	void NotifyObservers(JSONMessage msg)
	{
		for (ClientObserverInterface i : mObservers) {
			i.newJSONMessageHasBeenReceived(msg);
		}
	}

	protected String readJson() throws IOException
	{
		String jsonStr = new String();
		int counter = 0;
		char[] c = new char[1];

		do {
			mIn.read(c, 0, 1);
			if (c[0] == '{')
			{
				counter++;
			}
			if (c[0] == '}')
			{
				counter--;
			}
			jsonStr += c[0];
		} while (counter != 0);

		return jsonStr;
	}

	@Override
	public void disconnect() throws IOException
	{
		super.disconnect();
		
	}
	// getStatus()
	OutputStreamWriter mOut;
	BufferedReader mIn; 
	CameraListener mSocketListener;
	Boolean mAsynchMode;
	ArrayList<ClientObserverInterface> mObservers = new ArrayList<ClientObserverInterface>();

}