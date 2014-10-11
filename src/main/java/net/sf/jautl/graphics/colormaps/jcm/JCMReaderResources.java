package net.sf.jautl.graphics.colormaps.jcm;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

public class JCMReaderResources extends JCMReader {
	public JCMReaderResources(String resourceName) {
		super(loadJSON(resourceName));
	}
	
	public JCMReaderResources(InputStream is) {
		super(loadJSON(is));
	}
	
	private static JSONObject loadJSON(String resourceName) {
		InputStream is = JCMReaderResources.class.getClassLoader().getResourceAsStream(resourceName);
		
		return (is == null) ? null : loadJSON(is);
	}
	
	private static JSONObject loadJSON(InputStream is) {
		JSONObject jo = null;

		InputStreamReader isr = null;
		try {
			isr = new InputStreamReader(is);
			JSONTokener tokener = new JSONTokener(isr);
			jo = new JSONObject(tokener);
		} catch (JSONException e) {
		} finally {
			try {
				isr.close();
				is = null;
			} catch (IOException e) {
			} finally {
				isr = null;
			}
		}

		return jo;
	}
}
