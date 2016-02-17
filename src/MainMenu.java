import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import Actions.JSONActionController;


public class MainMenu {

	public void executeView(JSONActionController control) throws IOException
	{
    System.out.println("Welcome to Eye Cam ! ");
    
    Scanner keyboard = new Scanner(System.in);

    boolean exit = false;
    while (!exit) {
        System.out.println("Enter your choice :");
        System.out.println("1 . Start video recording");
        System.out.println("2 . Stop video recording");
        System.out.println("3 . Stop recording and save video");
        System.out.println("4 . Take a photo");
        System.out.println("5 . Take and save photo");
        System.out.println("6 . View parameters");
        System.out.println("7 . Write file essai.txt in /tmp/fuse_d/MISC/");
        System.out.println("8 . Get Battery Level");
        System.out.println("9 . Format Card");
        System.out.println("10 . Exit");
        int myint = keyboard.nextInt();

        switch (myint) {
		case 1:
	        control.startVideo();
			break;
		case 2:
	        control.stopVideo();
			break;
		case 3:
	        control.StopAndSaveVideo(new File("Video.mp4"));
			break;
		case 4:
	        control.takePicture();
			break;
		case 5:
	        control.TakeAndSavePicture(new File("Picture.jpg"));
			break;
		case 6:
			ParametersMenu parametersMenu = new ParametersMenu();
			parametersMenu.executeView(keyboard,control);
			break;
		case 7:
			control.sendDataToCamera(new String("essai").getBytes(), "/tmp/fuse_d/MISC/essai.txt");
			break;
		case 8:
			System.out.println("Battery Level : " + Integer.toString(control.getBatteryLevel().getBatteryLevel()));
			break;
		case 9:
			control.formatCard();
			break;
		case 10:
			exit = true;
			break;
		default:
			break;
		}
		
	}
    System.out.println("End ! ");
    keyboard.close();

	}
}
