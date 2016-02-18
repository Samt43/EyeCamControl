import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import Actions.ActionFactory;
import Actions.ActionGetBatteryLevel;
import Actions.JSONActionController;
import Actions.MissingFieldException;


public class MainMenu {

	ArrayList<UserChoice> mArrayChoices;
	final JSONActionController mController;
	Scanner mInputKeyboard;

	public MainMenu(JSONActionController controller, Scanner inputKeyboard) {
		mController = controller;
		mInputKeyboard = inputKeyboard;

		mArrayChoices = new ArrayList<UserChoice>();
		mArrayChoices.add(new UserChoice("Start video recording"){
			public void execute() throws MissingFieldException
			{mController.executeJSONCommand(ActionFactory.StartVideo);}});

		mArrayChoices.add(new UserChoice("Stop video recording"){
			public void execute() throws MissingFieldException
			{mController.executeJSONCommand(ActionFactory.StopVideo);}});

		mArrayChoices.add(new UserChoice("Stop recording and save video"){
			public void execute()
			{mController.StopAndSaveVideo(new File("Video.mp4"));}});

		mArrayChoices.add(new UserChoice("Take a photo"){
			public void execute() throws MissingFieldException
			{mController.executeJSONCommand(ActionFactory.TakePicture);}});

		mArrayChoices.add(new UserChoice("Take and save photo"){
			public void execute()
			{mController.TakeAndSavePicture(new File("Picture.jpg"));}});

		mArrayChoices.add(new UserChoice("View parameters"){
			public void execute() throws MissingFieldException
			{
				ParametersMenu parametersMenu = new ParametersMenu();
				parametersMenu.executeView(mInputKeyboard,mController);
				mController.executeJSONCommand(ActionFactory.GetParameters);}});

		mArrayChoices.add(new UserChoice("Get Battery Level"){
			public void execute() throws MissingFieldException
			{
				ActionGetBatteryLevel a = (ActionGetBatteryLevel) mController.executeJSONCommand(ActionFactory.GetBatteryLevel);
				System.out.println("Battery Level : " + Integer.toString(a.getBatteryLevel()));
			}});

		mArrayChoices.add(new UserChoice("Format Card"){
			public void execute() throws MissingFieldException
			{mController.executeJSONCommand(ActionFactory.FormatCard);}});

		mArrayChoices.add(new UserChoice("Write file essai.txt in /tmp/fuse_d/MISC/"){
			public void execute()
			{mController.sendDataToCamera(new String("essai").getBytes(), "/tmp/fuse_d/MISC/essai.txt");}});
	}

	public void executeView() throws IOException
	{
		System.out.println("Welcome to Eye Cam ! ");

		boolean exit = false;
		while (!exit) {
			int choiceNumber = 0;
			for (UserChoice choice : mArrayChoices) {
				choiceNumber = choiceNumber+1;
				System.out.println(Integer.toString(choiceNumber)+". "+choice.getDescription());
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
