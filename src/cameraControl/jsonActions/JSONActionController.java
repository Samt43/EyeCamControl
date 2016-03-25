package cameraControl.jsonActions;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

import org.json.simple.parser.ParseException;

import cameraControl.client.ClientObserverInterface;
import cameraControl.client.JSONMessage;
import cameraControl.client.JsonClient;

public class JSONActionController implements ClientObserverInterface{
	public JSONActionController(String addr,int port) throws IOException {
		mJSONClient = new JsonClient(addr, port);
		mJSONClient.addObservers(this);
		mActionFactory = new ActionFactory();
		mIsSessionActive = false;
	}

	public void registerHandler(JSONActionControllerHandler callbackHandler)
	{
		mJSONActionControllerHandler = callbackHandler;
	}

	public boolean executeJSONCommand(String actionName, BasicActionHandler handler) throws MissingFieldException
	{
		return executeJSONCommand(
				createJSONCommand(actionName, handler));
		
	}

	public AbstractJSONAction createJSONCommand(String actionName, BasicActionHandler handler)
	{
		AbstractJSONAction a = mActionFactory.buildAction(actionName);
		a.setHandler(handler);
		return a;
	}

	public boolean executeJSONCommand(AbstractJSONAction action) throws MissingFieldException
	{
		boolean retour = false;
		if (!mIsSessionActive)
		{
			mSessionNumber = startNewSession();
			mIsSessionActive = true;
		}
		try {
			action.setToken(mSessionNumber);
			if (addAction(action))
			{
				action.execute(mJSONClient);
				retour = true;
			}
		} catch (IOException e) {
	        System.out.println("Exception sending the action !");
			e.printStackTrace();
		} catch (ParseException e) {
	        System.out.println("Exception when parsing response !");
			e.printStackTrace();
		}
		return retour;
	}

	private int startNewSession() {
		int retour = -1;

		ActionStartSession a = mActionFactory.buildAction(ActionFactory.StartSession);
		
		try {
			a.execute(mJSONClient);
			retour = a.getToken();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MissingFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		mJSONClient.startAsynchMode();
		return retour;
	}

	public boolean addAction(AbstractJSONAction action)
	{
		boolean retour = false;
		if (!mActionMap.containsKey(action.getType()))
		{	
			mActionMap.put(action.getType(), action);
			retour = true;
		}
		return retour;
	}


	public void initialize() throws IOException
	{
		mJSONClient.connect();
	}
	
	public void clearRessources()
	{
		try {
			mJSONClient.disconnect();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	@Override
	public void newJSONMessageHasBeenReceived(JSONMessage m) {
		System.out.println("newJSONMessageHasBeenReceived");
		Integer type = m.getMessageType();
		if (mActionMap.containsKey(type))
		{
			AbstractJSONAction a = mActionMap.get(m.getMessageType());
			mActionMap.remove(type);
			a.parseErrorsAndResponse(m);

			System.out.println("mJSONActionControllerHandler.onActionIsCompleted(");
			mJSONActionControllerHandler.onActionIsCompleted(a);
		}
		else
		{
			mJSONActionControllerHandler.onNotificactionReceived(m);
		}
	}

	private JsonClient mJSONClient;
	private boolean mIsSessionActive = false;
	private int mSessionNumber = -1;
	private ActionFactory mActionFactory;
	private JSONActionControllerHandler mJSONActionControllerHandler;
	private ConcurrentHashMap<Integer, AbstractJSONAction> mActionMap = new ConcurrentHashMap<Integer, AbstractJSONAction>();

}
