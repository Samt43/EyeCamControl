package Actions;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import Client.JsonClient;

public class ActionFactory {
	public ActionFactory() {
		mAvailableActions = new ArrayList<String>();
		mAvailableActions.add(ActionTakePicture.class.getName());
		mAvailableActions.add(ActionGetParameters.class.getName());

	}

	ArrayList<String> getAvailableActions()
	{
		return mAvailableActions;
	}
	
	@SuppressWarnings("unchecked")
	public <T extends AbstractJSONAction> T buildAction(String className)
	{
		Object instance = null;
		if (mAvailableActions.contains(className))
		{
			try {
				Class<?> clazz = Class.forName(className);
				Constructor<?> constructor = clazz.getConstructor();
				instance = constructor.newInstance();
			} catch (InstantiationException | IllegalAccessException
					| IllegalArgumentException | InvocationTargetException | ClassNotFoundException | NoSuchMethodException | SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		return (T) instance;
	}

	private ArrayList<String> mAvailableActions;
}
