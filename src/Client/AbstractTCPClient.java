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

public abstract class AbstractTCPClient {
	
	public AbstractTCPClient(String addr,int port) {
		mAddr = addr;
		mPort = port;
	}

	public void connect() throws IOException
	{
		try {
			mSocket = new Socket();
			mSocket.setSoTimeout(10000);
		} catch (SocketException e) {
			e.printStackTrace();
		}

		System.out.println("Connecting TCP to eyeCam... : " + mAddr +":"+Integer.toString(mPort));
		mSocket.connect(new InetSocketAddress(mAddr, mPort),5000);
	}

	public boolean isConnected()
	{
		return mSocket.isConnected();
	}

	public void disconnect() throws IOException
	{
		System.out.println("Disconnecting from eyeCam...");
		mSocket.close();
	}

	// getStatus()
	protected Socket mSocket;
	String mAddr;
	int mPort;

}