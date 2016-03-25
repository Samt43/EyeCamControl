package ui;

import java.io.File;
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
		mArrayChoices.add(new UserChoice("Stop video recording and save"){
			public void execute() throws MissingFieldException
			{
				mCameraController.StopVideoRecodingAndSave(new File("video.mp4"));
			}
		});
		mArrayChoices.add(new UserChoiceBasicCommand("Take a photo",mCameraController,ActionFactory.TakePicture));
		mArrayChoices.add(new UserChoiceBasicCommand("Get Battery Level",mCameraController,ActionFactory.GetBatteryLevel));
		mArrayChoices.add(new UserChoiceBasicCommand("Format Card",mCameraController,ActionFactory.FormatCard));

		mArrayChoices.add(new UserChoice("Take and save photo"){
			public void execute() throws MissingFieldException
			{
				mCameraController.TakeAndSavePicture(new File("Picture.jpg"));
			}
		});
		mArrayChoices.add(new UserChoice("Write file essai.txt in /tmp/fuse_d/MISC/"){
			public void execute() throws MissingFieldException
			{
				mCameraController.sendDataToCamera("/tmp/fuse_d/MISC/essai.txt", new String("essai").getBytes());
			}
		});
		mArrayChoices.add(new UserChoice("View, modify parameters"){
			public void execute() throws MissingFieldException
			{
				ParametersMenu m = new ParametersMenu();
				m.executeView(mInputKeyboard,mCameraController);
			}
		});
	}

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

		System.out.println("Will now disconnect and close ! ");
		mInputKeyboard.close();

	}
}
