/**
 * 
 */
package Client;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * @author mathieu
 *
 */
public class JSONMessage extends Message {

	/* (non-Javadoc)
	 * @see Client.Message#getRawContent()
	 */
	@Override
	public String getRawContent() {
		return mJson.toString();
	}

	public void setRawContent(String raw) throws ParseException
	{
		 mJson = (JSONObject) new JSONParser().parse(raw);
	}
	
	public JSONObject getJSONObject() {
		return mJson;
	}

	public void setMessageType(int i)
	{
		setParameter("msg_id", i);
	}

	public void setParameter(String name, String value)
	{
		getJSONObject().put(name, value);
	}

	public void setParameter(String name, int value)
	{
		getJSONObject().put(name, value);
	}

	private JSONObject mJson = new JSONObject();

}
