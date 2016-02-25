/**
 * 
 */
package cameraControl.tcpActions;

import java.io.IOException;

import cameraControl.client.RawDataClient;

/**
 * @author mathieu
 *
 */
public abstract class AbstractRawTCPAction{

	public AbstractRawTCPAction() {
	}

	public abstract String getActionName();

	public void execute(RawDataClient client) throws IOException {
        System.out.println("Execute RawData Action "+ getActionName() + "\r\n");
	}
}
