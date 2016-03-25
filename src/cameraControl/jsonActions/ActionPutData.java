package cameraControl.jsonActions;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import cameraControl.client.JSONMessage;

public class ActionPutData extends AbstractJSONAction {

	public ActionPutData(String name) {
		super(name);

		mJsonMessage.setMessageType(1286);
	}
	
	String getMd5(byte[] array)
	{
		String hashtext = new String();
		try {
			MessageDigest m = MessageDigest.getInstance("MD5");
			m.reset();
			m.update(array);
			byte[] digest = m.digest();
			BigInteger bigInt = new BigInteger(1,digest);
			hashtext = bigInt.toString(16);
			// Now we need to zero pad it if you actually want the full 32 chars.
			while(hashtext.length() < 32 ){
			  hashtext = "0"+hashtext;
			}
		} catch (NoSuchAlgorithmException e) {
			System.out.println("No md5 algorithm found !");
			e.printStackTrace();
		}
		
		return hashtext;
	}

	public void setData(byte[] arrayToSend)
	{
	    mJsonMessage.setParameter("offset", 0);
	    mJsonMessage.setParameter("md5sum", getMd5(arrayToSend));
	    mJsonMessage.setParameter("size", arrayToSend.length);
	}

	public void setOutPutFilePath(String filePath)
	{
	    mJsonMessage.setParameter("param", filePath);
		
	}
	@Override
	protected
	void parseResponse(JSONMessage msg) {
		
	}

}
