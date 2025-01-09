package Utility;

import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class ReadMetaData {
	
	
	static Map<String,Object> cache;
	
	@SuppressWarnings("unchecked")
	public static Map<String,Object> getMetdataCache() {
		if(null ==cache) {
			cache = new HashMap<String, Object>();
			JSONParser parser = new JSONParser();
			try {
				Object obj = parser.parse(new FileReader(System.getProperty("user.dir") + "/data/testMetaData"));
				JSONObject jsonObject = (JSONObject) obj;
				cache = jsonObject;
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			
		}
		return cache;
	}
}