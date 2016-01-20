/**
 * 
 */
package Actions;

import java.io.IOException;

import org.json.simple.parser.ParseException;

import Client.RawDataClient;

/**
 * @author mathieu
 *
 */
public abstract class AbstractRawTCPAction{

	public AbstractRawTCPAction() {
	}

	public abstract String getActionName();

	public void execute(RawDataClient client) throws IOException, ParseException {
        System.out.println("Execute RawData Action "+ getActionName() + "\r\n");
	}
}
