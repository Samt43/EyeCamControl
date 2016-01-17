package Client;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;

import org.json.simple.parser.ParseException;

public class RawDataClient extends AbstractTCPClient{
	
	public RawDataClient(String addr,int port) {
		super(addr,port);
	}

	@Override
	public void connect() throws IOException
	{
		super.connect();
        mOut = new OutputStreamWriter(
			     mSocket.getOutputStream(), StandardCharsets.UTF_8);
        mIn = new DataInputStream(mSocket.getInputStream());
	}
	
	public byte[] getData(int size) throws IOException
	{
		byte [] b = new byte[size];
		mIn.readFully(b);
		System.out.println(Integer.toString(size)+ " bytes have been readen successfully !");

		return b;
	}

	// getStatus()
	OutputStreamWriter mOut;
	DataInputStream mIn; 
}