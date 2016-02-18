package Actions;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import Client.JSONMessage;

public class ActionPutData extends AbstractJSONAction {

	ActionPutData(byte arrayToSend[],String outputPath) {
		mOutputFilePath = outputPath;
		mJsonMessage.setMessageType(1286);
	    mJsonMessage.setParameter("param", mOutputFilePath);
	    mJsonMessage.setParameter("offset", 0);
	    mJsonMessage.setParameter("md5sum", getMd5(arrayToSend));
	    mJsonMessage.setParameter("size", arrayToSend.length);
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

	@Override
	void parseResponse(JSONMessage msg) {
		
	}

	protected String mOutputFilePath;

}
