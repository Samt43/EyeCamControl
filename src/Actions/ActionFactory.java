package Actions;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import Client.JsonClient;

public class ActionFactory {
	
	public static String TakePicture = "Take_Picture";
	public static String StopVideo = "Stop_Video";
	public static String StartVideo = "Start_Video";
	public static String GetParameters = "Get_Parameters";
	public static String GetBatteryLevel = "Get_Battery_Level";
	public static String FormatCard = "Format_Card";
	public static String Listen = "Listen";
	public static String GetParameterValues = "Get_Parameter_Values";
	public static String ChangeParameterValue = "Change_Parameter_Value";
	
	public ActionFactory() {
		mAvailableActions = new HashMap<String,Class<?>>();
		mAvailableActions.put(TakePicture,ActionTakePicture.class);
		mAvailableActions.put(StopVideo,ActionStopVideo.class);
		mAvailableActions.put(StartVideo,ActionStartVideo.class);
		mAvailableActions.put(GetParameters,ActionGetParameters.class);
		mAvailableActions.put(GetBatteryLevel,ActionGetBatteryLevel.class);
		mAvailableActions.put(FormatCard,ActionFormatCard.class);
		mAvailableActions.put(GetParameterValues,ActionGetParameterValues.class);
		mAvailableActions.put(ChangeParameterValue,ActionChangeParameterValue.class);
		mAvailableActions.put(Listen,ActionListen.class);
	}

	Map<String,Class<?>> getAvailableActions()
	{
		return mAvailableActions;
	}
	
	@SuppressWarnings("unchecked")
	public <T extends AbstractJSONAction> T buildAction(String actionName)
	{
		Object instance = null;
		if (mAvailableActions.containsKey(actionName))
		{
			try {
				Class<?> clazz = mAvailableActions.get(actionName);
				Constructor<?> constructor = clazz.getConstructor();
				instance = constructor.newInstance();
			} catch (InstantiationException | IllegalAccessException
					| IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		return (T) instance;
	}

	private Map<String,Class<?>> mAvailableActions;
}
