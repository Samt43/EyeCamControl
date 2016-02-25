package ui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import cameraControl.CameraController;
import cameraControl.jsonActions.ActionFactory;
import cameraControl.jsonActions.MissingFieldException;


public class MainMenu {

	ArrayList<UserChoice> mArrayChoices;
	CameraController mCameraController;
	Scanner mInputKeyboard;

	public MainMenu(CameraController cameraController, Scanner inputKeyboard) {
		
		mInputKeyboard = inputKeyboard;
		mArrayChoices = new ArrayList<UserChoice>();
		mCameraController = cameraController;

		mArrayChoices.add(new UserChoiceBasicCommand("Start video recording",mCameraController,ActionFactory.StartVideo));
		mArrayChoices.add(new UserChoiceBasicCommand("Stop video recording",mCameraController,ActionFactory.StopVideo));
		mArrayChoices.add(new UserChoiceBasicCommand("Take a photo",mCameraController,ActionFactory.TakePicture));
		mArrayChoices.add(new UserChoiceBasicCommand("Get Battery Level",mCameraController,ActionFactory.GetBatteryLevel));
		mArrayChoices.add(new UserChoiceBasicCommand("Format Card",mCameraController,ActionFactory.FormatCard));

		mArrayChoices.add(new UserChoice("Take and save photo"){
			public void execute() throws MissingFieldException
			{
				mCameraController.TakeAndSavePicture();
			}
		});
	}

			

	/*

		mArrayChoices.add(new UserChoice("Stop recording and save video"){
			public void execute()
			{mController.StopAndSaveVideo(new File("Video.mp4"));}});

		mArrayChoices.add(new UserChoice("View parameters",this){
			public void execute() throws MissingFieldException
			{
				ParametersMenu parametersMenu = new ParametersMenu();
				parametersMenu.executeView(mInputKeyboard,mController);
				mController.executeJSONCommand(ActionFactory.GetParameters);}});
		mArrayChoices.add(new UserChoice("Write file essai.txt in /tmp/fuse_d/MISC/"){
			public void execute()
			{mController.sendDataToCamera(new String("essai").getBytes(), "/tmp/fuse_d/MISC/essai.txt");}});
	 */


	public void executeView() throws IOException
	{
		System.out.println("Welcome to Eye Cam ! ");

		boolean exit = false;
		while (!exit) {
			int choiceNumber = 0;
			for (UserChoice choice : mArrayChoices) {
				System.out.println(Integer.toString(choiceNumber)+". "+choice.getDescription());
				choiceNumber = choiceNumber+1;
			}

			System.out.println("Enter your choice or press " + Integer.toString(mArrayChoices.size()) + " to exit :");
			int myint = mInputKeyboard.nextInt();
			if (0<= myint && myint < mArrayChoices.size())
			{
				try {
					mArrayChoices.get(myint).execute();
				} catch (MissingFieldException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (myint == mArrayChoices.size())
			{
				exit = true;
			}
		}

		System.out.println("End ! ");
		mInputKeyboard.close();

	}
}
