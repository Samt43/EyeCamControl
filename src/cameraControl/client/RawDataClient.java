package cameraControl.client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class RawDataClient extends AbstractTCPClient{
	
	public RawDataClient(String addr,int port) {
		super(addr,port);
	}

	@Override
	public void connect() throws IOException
	{
		super.connect();
        mOut = new DataOutputStream(mSocket.getOutputStream());
        mIn = new DataInputStream(mSocket.getInputStream());
	}
	
	public byte[] getData(int size) throws IOException
	{
		byte [] b = new byte[size];
		mIn.readFully(b);
		System.out.println(Integer.toString(size)+ " bytes have been readen successfully !");

		return b;
	}

	public void putData(byte array[]) throws IOException
	{
		mOut.write(array);
		System.out.println(Integer.toString(array.length)+ " bytes have been put successfully !");
	}

	// getStatus()
	DataOutputStream mOut;
	DataInputStream mIn; 
}