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
        System.out.println("1 . Take a photo");
        System.out.println("2 . Take and save photo");
        System.out.println("3 . View parameters");
        System.out.println("4 . Write file essai.txt in /tmp/fuse_d/MISC/");
        System.out.println("10 . Exit");
        int myint = keyboard.nextInt();

        switch (myint) {
		case 1:
	        control.takePicture();
			break;
		case 2:
	        control.TakeAndSavePicture(new File("Picture.jpg"));
			break;
		case 3:
			ParametersMenu parametersMenu = new ParametersMenu();
			parametersMenu.executeView(keyboard,control);
			break;
		case 4:
			control.sendDataToCamera(new String("essai").getBytes(), "/tmp/fuse_d/MISC/essai.txt");
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
