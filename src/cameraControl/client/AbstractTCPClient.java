package cameraControl.client;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;

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

	protected Socket mSocket;
	String mAddr;
	int mPort;

}