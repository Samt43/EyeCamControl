package cameraControl.jsonActions;

import cameraControl.client.JSONMessage;

public class ActionGetFile extends AbstractJSONAction {

	public ActionGetFile(String name) {
		super(name);
		mFilePath = "TO SET";
		mJsonMessage.setMessageType(1285);
	    mJsonMessage.setParameter("offset", 0);
	    mJsonMessage.setParameter("fetch_size", 0);
	}

	@Override
	protected
	void parseResponse(JSONMessage msg) {
		mRemaining_Size = Integer.parseInt(msg.getJSONObject().get("rem_size").toString());
		mTotal_Size = Integer.parseInt(msg.getJSONObject().get("size").toString());
	}
	
	public void setFilePath(String filePath)
	{
	    mJsonMessage.setParameter("param", filePath);
	}

	public int getRemaining_Size()
	{
		return mRemaining_Size;
	}
	
	public int getTotal_Size()
	{
		return mTotal_Size;
	}
	protected String mFilePath;
	protected int mRemaining_Size;
	protected int mTotal_Size;

}
