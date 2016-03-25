package ui;

import java.util.ArrayList;
import java.util.Scanner;


import cameraControl.CameraController;
import cameraControl.CameraControllerHandler;
import cameraControl.cameraModel.Parameter;
import cameraControl.jsonActions.AbstractJSONAction;
import cameraControl.jsonActions.ActionFactory;
import cameraControl.jsonActions.ActionGetParameters;


public class ParametersMenu {

	boolean exit = false;

	public void executeView(final Scanner keyboard, final CameraController controller)
	{
		while (!exit) {
			ActionGetParameters a;
			controller.executeJSONCommand(ActionFactory.GetParameters,new CameraControllerHandler() {

				@Override
				public void executeErrorCallback(AbstractJSONAction abstractJSONAction) {
					// TODO Auto-generated method stub
					System.out.println("Error, we exit");
					exit = true;
				}

				@Override
				public void execute(AbstractJSONAction a) {
					ActionGetParameters p = (ActionGetParameters) a;
					printParameters(p.getParameters());

					System.out.println("Enter your choice :");
					System.out.println("1 . View Specific parameter");
					System.out.println("10 . Exit");
					int myint = keyboard.nextInt();

					switch (myint) {
					case 1:
						System.out.println("Enter the parameter number");
						int n = keyboard.nextInt();
						if (n >=0 && n < p.getParameters().size())
						{
							EditParameter v = new EditParameter();
							v.executeView(p.getParameters().get(n), keyboard, controller);
						}
						break;
					case 10:
						exit = true;
						break;
					default:
						break;
					}
				}


			});

		}}

	protected void printParameters(ArrayList<Parameter> list)
	{
		int i = 0;
		for (Parameter p : list) {
			System.out.print(i);
			System.out.println(". "+p.toString());
			i++;
		}
	}
}
