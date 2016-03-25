package cameraControl;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import cameraControl.client.JSONMessage;
import cameraControl.jsonActions.AbstractJSONAction;
import cameraControl.jsonActions.ActionFactory;
import cameraControl.jsonActions.ActionGetFile;
import cameraControl.jsonActions.ActionPutData;
import cameraControl.jsonActions.BasicActionHandler;
import cameraControl.jsonActions.JSONActionController;
import cameraControl.jsonActions.JSONActionControllerHandler;
import cameraControl.jsonActions.MissingFieldException;
import cameraControl.tcpActions.ActionGetRawData;
import cameraControl.tcpActions.ActionPutRawData;
import cameraControl.tcpActions.TCPController;

public class CameraController implements JSONActionControllerHandler {
	
	final JSONActionController mJSONController;
	final TCPController mTCPController;
	BlockingQueue<AbstractJSONAction> mResponseQueue = new ArrayBlockingQueue<AbstractJSONAction>(1);
	BlockingQueue<String> mNewPhotoQueue = new ArrayBlockingQueue<String>(999);
	BlockingQueue<String> mNewVideoQueue = new ArrayBlockingQueue<String>(999);

	public CameraController(JSONActionController jsonController,TCPController tcpController)
	{
		mJSONController = jsonController;
		mTCPController = tcpController;
		mJSONController.registerHandler(this);
	}

	public void initialise() throws IOException
	{
        mJSONController.initialize();
        mTCPController.initialize();
	}

	public void clearRessources()
	{
        mJSONController.clearRessources();
        mTCPController.clearRessources();
	}

	public void executeBasicCommand(String command)
	{
		executeJSONCommand(command,new CameraControllerHandler() {
			@Override
			public void execute(AbstractJSONAction a) {
			}
		});
	}

	@Override
	public void onActionIsCompleted(AbstractJSONAction a) {
		// This method is called when we receive a completed action. (listener Thread)
		try {
			mResponseQueue.put(a);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void onNotificactionReceived(JSONMessage m) {
		System.out.println("handle Notification !");
		System.out.println(m.getJSONObject().toJSONString());
		switch (m.getJSONObject().get("type").toString()) {
		case "start_video_record":

			break;
		case "video_record_complete":
			newVideoHaveBeenTaken(m);
			break;
		case "photo_taken":
			newPhotoHaveBeenTaken(m);
			break;
		default:
			break;
		}
		// TODO Auto-generated method stub

	}

	public void StopVideoRecodingAndSave(final File f)
	{
		mNewVideoQueue.clear();
		executeJSONCommand(ActionFactory.StopVideo,new CameraControllerHandler() {
		@Override
		public void execute(AbstractJSONAction a) {
			//Wait for the photo to be taken
			String path;
			try {
				path = mNewVideoQueue.take();
				GetAndSaveFile(path, f);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}});
	}

	public void TakeAndSavePicture(final File f)
	{
		mNewPhotoQueue.clear();
		executeJSONCommand(ActionFactory.TakePicture,new CameraControllerHandler() {
		@Override
		public void execute(AbstractJSONAction a) {
			//Wait for the photo to be taken
			String path;
			try {
				path = mNewPhotoQueue.take();
				GetAndSaveFile(path, f);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}});
	}

	private void newPhotoHaveBeenTaken(JSONMessage m) {
		String path  = m.getJSONObject().get("param").toString();
		try {
			mNewPhotoQueue.put(path);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void newVideoHaveBeenTaken(JSONMessage m) {
		String path  = m.getJSONObject().get("param").toString();
		try {
			mNewVideoQueue.put(path);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}



	public void sendDataToCamera(String filePath, final byte[] data)
	{
		ActionPutData a = (ActionPutData) mJSONController.createJSONCommand(ActionFactory.PutData, new CameraControllerHandler() {
			@Override
			public void execute(AbstractJSONAction a) {
				ActionPutRawData p = new ActionPutRawData(data);

				// This action is done synchronously
				mTCPController.executeAction(p);
				
			}});

		a.setOutPutFilePath(filePath);
		a.setData(data);
		executeJSONCommand(a);
	}
	
	protected void GetAndSaveFile(String path, final File file) {
		ActionGetFile a = (ActionGetFile) mJSONController.createJSONCommand(ActionFactory.GetFile, new CameraControllerHandler() {
			@Override
			public void execute(AbstractJSONAction a) {
				ActionGetFile aJSON = (ActionGetFile) a;
				ActionGetRawData f = new ActionGetRawData(aJSON.getTotal_Size());
				// This action is done synchronously
				mTCPController.executeAction(f);
				save(file,f.getBytes());
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
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		});

		a.setFilePath(path);
		executeJSONCommand(a);
	}

	protected void onStartVideoRecording() {
		// TODO Auto-generated method stub
		System.out.println("Video has been started !");
	}

	protected void NewPhotoHasBeenTaken() {
		// TODO Auto-generated method stub
		System.out.println("New Photo has been taken !");
	}

	public AbstractJSONAction createJSONCommand(String actionName, BasicActionHandler handler)
	{
		AbstractJSONAction a = mJSONController.createJSONCommand(actionName, handler);
		return a;
	}
	
	public void executeJSONCommand(String command, BasicActionHandler handler) {
		AbstractJSONAction a = mJSONController.createJSONCommand(command, handler);
		executeJSONCommand(a);
	}

	public void executeJSONCommand(AbstractJSONAction a) {
		// TODO Auto-generated method stub
		try {
			mJSONController.executeJSONCommand(a);
			//Wait that we receive the response.
			executeResponse(mResponseQueue.take());
		} catch (MissingFieldException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void executeResponse(AbstractJSONAction a) {
		if (a.success())
		{
			a.executeCallBack();
		}
		else
		{
			a.executeErrorsCallBack();
		}
	}
}
