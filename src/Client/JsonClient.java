package Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;

import org.json.simple.parser.ParseException;

public class JsonClient extends AbstractTCPClient{
	
	public JsonClient(String addr,int port) {
		super(addr,port);
	}

	@Override
	public void connect() throws IOException
	{
		super.connect();
        mOut = new OutputStreamWriter(
			     mSocket.getOutputStream(), StandardCharsets.UTF_8);
        mIn = new BufferedReader(new InputStreamReader(mSocket.getInputStream()));
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
		return msg;
	}

	public boolean isConnected()
	{
		return mSocket.isConnected();
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

	// getStatus()
	OutputStreamWriter mOut;
	BufferedReader mIn; 
}