



import java.io.IOException;
import java.util.Scanner;

import Actions.JSONActionController;
import Client.JsonClient;

/**
 * 
 */

/**
 * @author mathieu
 *
 */
public class Main {
    public static void main(String[] args) throws IOException {
        JSONActionController controller = new JSONActionController("192.168.42.1",7878);
        controller.initialize();
        MainMenu menu = new MainMenu(controller, new Scanner(System.in));
        menu.executeView();
    }
    
}
