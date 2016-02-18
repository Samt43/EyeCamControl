package Actions;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.simple.parser.ParseException;

import Threads.CameraListener;

import sun.misc.IOUtils;

import Client.JsonClient;
import Client.RawDataClient;

public class JSONActionController {
	public JSONActionController(String addr,int port) throws IOException {
		mJSONClient = new JsonClient(addr, port);
		mRawDataClient = new RawDataClient(addr, 8787);
		mActionFactory = new ActionFactory();
	}

	public ActionGetRawData getFile(String path)
	{
		ActionGetRawData r = null;
		ActionGetFile actionGet = new ActionGetFile(path);
		try {
			executeJSONCommand(actionGet);

			r = new ActionGetRawData(actionGet.getRemaining_Size());
			executeRawDataAction(r);
			
			ActionListen listen = new ActionListen();
			executeJSONCommand(listen);
		} catch (MissingFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return r;
	}
	
	public void sendDataToCamera(byte data[], String outputPath)
	{
		try {
		ActionPutData a = new ActionPutData(data, outputPath);
		executeJSONCommand(a);
		ActionPutRawData p = new ActionPutRawData(data);
		executeRawDataAction(p);
		ActionListen listen = new ActionListen();
		executeJSONCommand(listen);
		} catch (MissingFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void TakeAndSavePicture(File file)
	{
		ActionTakePicture p;
		try {
			p = (ActionTakePicture) executeJSONCommand(createJSONCommand(ActionFactory.TakePicture));
			ActionGetRawData f = getFile(p.getPicturePath());
			save(file,f.getBytes());
		} catch (MissingFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void StopAndSaveVideo(File file)
	{
		ActionStopVideo a;
		try {
			a = (ActionStopVideo) executeJSONCommand(
					createJSONCommand(ActionFactory.StopVideo));
			ActionGetRawData f = getFile(a.getVideoPath());
			save(file,f.getBytes());
		} catch (MissingFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public AbstractJSONAction executeJSONCommand(String actionName) throws MissingFieldException
	{
		return executeJSONCommand(
				createJSONCommand(actionName));
		
	}

	public AbstractJSONAction createJSONCommand(String actionName)
	{
		return mActionFactory.buildAction(actionName);
	}

	public AbstractJSONAction executeJSONCommand(AbstractJSONAction action) throws MissingFieldException
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

		return action;
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
		//mCameraListener.
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
	private ActionFactory mActionFactory;
	private CameraListener mCameraListener;

}
