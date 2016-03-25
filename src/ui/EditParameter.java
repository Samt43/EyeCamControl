package ui;

import java.util.ArrayList;
import java.util.Scanner;

import cameraControl.CameraController;
import cameraControl.cameraModel.Parameter;
import cameraControl.jsonActions.AbstractJSONAction;
import cameraControl.jsonActions.ActionChangeParameterValue;
import cameraControl.jsonActions.ActionFactory;
import cameraControl.jsonActions.ActionGetParameterValues;
import cameraControl.jsonActions.BasicActionHandler;

public class EditParameter {
	boolean exit = false;
	void executeView(final Parameter p, final Scanner keyboard, final CameraController controller)
	{
		while (!exit) {
			System.out.println("Parameter : " + p.toString());
			System.out.println("Possible values : ");
			ActionGetParameterValues a = (ActionGetParameterValues) controller.createJSONCommand(ActionFactory.GetParameterValues, new BasicActionHandler() {
				@Override
				public void executeErrorCallback(AbstractJSONAction abstractJSONAction) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void execute(AbstractJSONAction a) {
					ActionGetParameterValues ac = (ActionGetParameterValues) a;
					printParameterValues(ac.getParameters());
					System.out.println("Enter your choice :");
					System.out.println("1 . Change Value");
					System.out.println("10 . Exit");
					int myint = keyboard.nextInt();

					switch (myint) {
					case 1:
						System.out.println("Enter the new value number");
						int n = keyboard.nextInt();
						if (n >=0 && n < ac.getParameters().size())
			        {
			        	changeParameter(p,ac.getParameters().get(n),controller);
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

			a.setParameterName(p.getName());
			controller.executeJSONCommand(a);
		}
	}

	public void changeParameter(Parameter p, String newValue, CameraController controller)
	{
		
		ActionChangeParameterValue a = (ActionChangeParameterValue) controller.createJSONCommand(ActionFactory.ChangeParameterValue, new BasicActionHandler() {
			
			@Override
			public void executeErrorCallback(AbstractJSONAction abstractJSONAction) {
				System.out.println("Failed to change the parameter");
				
			}

			@Override
			public void execute(AbstractJSONAction a) {
				System.out.println("Changed with Success !!");
				exit = true;
				
			}
		});
		a.setParameterName(p.getName());
		a.setParameterValue(newValue);
		controller.executeJSONCommand(a);
	}

	protected void printParameterValues(ArrayList<String> list)
	{
		int i = 0;
		for (String p : list) {
			System.out.print(i);
			System.out.println(". "+ p);
			i++;
		}
	}
}
