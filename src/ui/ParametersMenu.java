package ui;

import java.util.ArrayList;
import java.util.Scanner;


import cameraControl.cameraModel.Parameter;
import cameraControl.jsonActions.JSONActionController;


public class ParametersMenu {
	
	public void executeView(Scanner keyboard, JSONActionController controller)
	{

		boolean exit = false;
		while (!exit) {
			/*
			ActionGetParameters a;
			try {
				a = (ActionGetParameters) controller.executeJSONCommand(ActionFactory.GetParameters);
				printParameters(a.getParameters());

				System.out.println("Enter your choice :");
				System.out.println("1 . View Specific parameter");
				System.out.println("10 . Exit");
				int myint = keyboard.nextInt();

				switch (myint) {
				case 1:
					System.out.println("Enter the parameter number");
					int n = keyboard.nextInt();
					if (n >=0 && n < a.getParameters().size())
					{
						viewParameter(a.getParameters().get(n),keyboard,controller);
					}
					break;
				case 10:
					exit = true;
					break;
				default:
					break;
				}
			} catch (MissingFieldException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
				*/
		}
	}

	void printParameters(ArrayList<Parameter> list)
	{
		int i = 0;
		for (Parameter p : list) {
			System.out.print(i);
			System.out.println(". "+p.toString());
			i++;
		}
	}

	void printParameterValues(ArrayList<String> list)
	{
		int i = 0;
		for (String p : list) {
			System.out.print(i);
			System.out.println(". "+ p);
			i++;
		}
	}
	
	void viewParameter(Parameter p, Scanner keyboard, JSONActionController controller)
	{
		/*
		boolean exit = false;
		while (!exit) {
			System.out.println("Parameter : " + p.toString());
			System.out.println("Possible values : ");
			ActionGetParameterValues a = (ActionGetParameterValues) controller.createJSONCommand(ActionFactory.GetParameterValues);
			a.setParameterName(p.getName());

			try {
				controller.executeJSONCommand(a);
			} catch (MissingFieldException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			printParameterValues(a.getParameters());
			System.out.println("Enter your choice :");
			System.out.println("1 . Change Value");
			System.out.println("10 . Exit");
			int myint = keyboard.nextInt();

			switch (myint) {
			case 1:
				System.out.println("Enter the new value number");
				int n = keyboard.nextInt();
				if (n >=0 && n < a.getParameters().size())
	        {
	        	changeParameter(p,a.getParameters().get(n),controller);
	        }
				break;
			case 10:
				exit = true;
				break;
			default:
				break;
			}
		}
		*/}
	
	public void changeParameter(Parameter p, String newValue, JSONActionController controller)
	{
		/*
		ActionChangeParameterValue a = (ActionChangeParameterValue) controller.createJSONCommand(ActionFactory.ChangeParameterValue);
		a.setParameterName(p.getName());
		a.setParameterValue(newValue);

		try {
			controller.executeJSONCommand(a);
		} catch (MissingFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (a.getValueChanged())
		{
			System.out.println("Success !!");
		}
		else
		{
			System.out.println("Failure");
		}
		*/
	}
}
