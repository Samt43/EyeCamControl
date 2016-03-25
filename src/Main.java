import java.io.IOException;
import java.util.Scanner;

import cameraControl.CameraController;
import cameraControl.tcpActions.TCPController;

import cameraControl.jsonActions.JSONActionController;
import ui.MainMenu;

/**
 * 
 */

/**
 * @author mathieu
 *
 */
public class Main {
    public static void main(String[] args) throws IOException {
        JSONActionController JSONcontroller = new JSONActionController("192.168.42.1",7878);
        TCPController TCPcontroller = new TCPController("192.168.42.1",8787);
        
        CameraController cameraController = new CameraController(JSONcontroller,TCPcontroller);
        cameraController.initialise();

        MainMenu menu = new MainMenu(cameraController, new Scanner(System.in));

        menu.executeView();
        cameraController.clearRessources();
    }
    
}
