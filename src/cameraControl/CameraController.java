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
import cameraControl.jsonActions.JSONActionController;
import cameraControl.jsonActions.JSONActionControllerHandler;
import cameraControl.jsonActions.MissingFieldException;
import cameraControl.tcpActions.ActionGetRawData;
import cameraControl.tcpActions.TCPControler;

public class CameraController implements JSONActionControllerHandler {
	
	final JSONActionController mJSONController;
	final TCPControler mTCPController;
	BlockingQueue<AbstractJSONAction> mResponseQueue = new ArrayBlockingQueue<AbstractJSONAction>(1);
	BlockingQueue<String> mNewPhotoQueue = new ArrayBlockingQueue<String>(999);

	public CameraController(JSONActionController jsonController,TCPControler tcpController)
	{
		mJSONController = jsonController;
		mTCPController = tcpController;
		mJSONController.registerHandler(this);
	}

	public void executeBasicCommand(String command)
	{
		executeJSONCommand(command,new CameraControllerHandler(this) {
			@Override
			public void execute(AbstractJSONAction a) {
			}
		});
	}

	@Override
	public void onActionIsCompleted(AbstractJSONAction a) {
		// TODO Auto-generated method stub
		try {
			System.out.println("Put the handler !!!");
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
		case "stop_video_record":

			break;
		case "photo_taken":
			newPhotoHaveBeenTaken(m);
			break;
		default:
			break;
		}
		// TODO Auto-generated method stub

	}


	public void TakeAndSavePicture()
	{
	executeJSONCommand(ActionFactory.TakePicture,new CameraControllerHandler(this) {
		@Override
		public void execute(AbstractJSONAction a) {
			mCameraController.GetAndSavePicture(new File("Picture.jpg"));
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


	protected void GetAndSavePicture(final File file) {
		try {
			//Wait for the photo to be taken
			String path = mNewPhotoQueue.take();
			ActionGetFile a = (ActionGetFile) mJSONController.createJSONCommand(ActionFactory.GetFile, new CameraControllerHandler(this) {
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
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	protected void DownloadAndSaveFile(File file) {
		//mTCPController.executeAction()

	}

	protected void onStartVideoRecording() {
		// TODO Auto-generated method stub
		System.out.println("Video has been started !");

	}

	protected void NewPhotoHasBeenTaken() {
		// TODO Auto-generated method stub
		System.out.println("New Photo has been taken !");
	}

	protected void executeJSONCommand(String command, CameraControllerHandler mainMenuHandler) {
		AbstractJSONAction a = mJSONController.createJSONCommand(command, mainMenuHandler);
		executeJSONCommand(a);
	}

	protected void executeJSONCommand(AbstractJSONAction a) {
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
		a.executeCallBack();
	}
}
