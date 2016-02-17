package Actions;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.json.simple.parser.ParseException;

import sun.misc.IOUtils;

import Client.JsonClient;
import Client.RawDataClient;

public class JSONActionController {
	public JSONActionController(String addr,int port) throws IOException {
		mJSONClient = new JsonClient(addr, port);
		mRawDataClient = new RawDataClient(addr, 8787);
	}

	public ActionTakePicture takePicture()
	{
		ActionTakePicture action = new ActionTakePicture();
		executeJSONCommand(action);
		
		
		return action;
	}

	public ActionStartVideo startVideo()
	{
		ActionStartVideo action = new ActionStartVideo();
		executeJSONCommand(action);

		return action;
	}

	public ActionStopVideo stopVideo()
	{
		ActionStopVideo action = new ActionStopVideo();
		executeJSONCommand(action);
		System.out.println("URL to the file : " + action.getVideoPath());
		return action;
	}

	public ActionGetRawData getFile(String path)
	{

		ActionGetFile actionGet = new ActionGetFile(path);
		executeJSONCommand(actionGet);

		ActionGetRawData r = new ActionGetRawData(actionGet.getRemaining_Size());
		executeRawDataAction(r);
		
		ActionListen listen = new ActionListen();
		executeJSONCommand(listen);

		return r;
	}

	public ActionGetBatteryLevel getBatteryLevel()
	{
		ActionGetBatteryLevel a = new ActionGetBatteryLevel();
		executeJSONCommand(a);
		return a;
	}

	public ActionFormatCard formatCard()
	{
		ActionFormatCard a = new ActionFormatCard();
		executeJSONCommand(a);
		return a;
	}
	
	public void sendDataToCamera(byte data[], String outputPath)
	{
		ActionPutData a = new ActionPutData(data, outputPath);
		executeJSONCommand(a);
		ActionPutRawData p = new ActionPutRawData(data);
		executeRawDataAction(p);
		ActionListen listen = new ActionListen();
		executeJSONCommand(listen);
	}

	public void TakeAndSavePicture(File file)
	{
		ActionTakePicture p = takePicture();
		ActionGetRawData f = getFile(p.getPicturePath());

		save(file,f.getBytes());
	}

	public void StopAndSaveVideo(File file)
	{
		ActionStopVideo a = stopVideo();
		ActionGetRawData f = getFile(a.getVideoPath());
		save(file,f.getBytes());
	}

	public ActionGetParameters getParameters()
	{
		ActionGetParameters action = new ActionGetParameters();
		executeJSONCommand(action);
		return action;
	}
	
	public ActionGetParameterValues getParameterValues(String param)
	{
		ActionGetParameterValues action = new ActionGetParameterValues(param);
		executeJSONCommand(action);
		return action;
	}
	
	public ActionChangeParameterValue ChangeParameterValue(String param,String newValue)
	{
		ActionChangeParameterValue action = new ActionChangeParameterValue(param,newValue);
		executeJSONCommand(action);
		return action;
	}

	protected void executeJSONCommand(AbstractJSONAction action)
	{
		try {

			if (action.needToken())
			{
				ActionGetToken getToken = new ActionGetToken();
				getToken.execute(mJSONClient);
				int token = getToken.getToken();
				action.setToken(token);
			}
			action.execute(mJSONClient);
		} catch (IOException e) {
	        System.out.println("Exception sending the action !");
			e.printStackTrace();
		} catch (ParseException e) {
	        System.out.println("Exception when parsing response !");
			e.printStackTrace();
		}
	}
	
	protected void executeRawDataAction(AbstractRawTCPAction a)
	{
		try {
			a.execute(mRawDataClient);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public void initialize() throws IOException
	{
		mJSONClient.connect();
		mRawDataClient.connect();
	}
	
	public void clearRessources()
	{
		try {
			mRawDataClient.disconnect();
			mJSONClient.disconnect();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	private void save(File file,byte b[])
	{
		BufferedOutputStream bos = null;

		//create an object of FileOutputStream
		FileOutputStream fos;
		try {
			fos = new FileOutputStream(file);
			bos = new BufferedOutputStream(fos);
			bos.write(b);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private JsonClient mJSONClient;
	private RawDataClient mRawDataClient;

}
