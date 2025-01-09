package Utility;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyResources {
	private String PROPERTY_CONFIG_PROPERTIES = "/configRead.properties";
	private Properties prop;

	public PropertyResources() {
		try {
			prop = new Properties();
			InputStream ip = PropertyResources.class.getResourceAsStream(PROPERTY_CONFIG_PROPERTIES);
			prop.load(ip);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void getProperty(String element) {
		getProp().get("element").toString().trim();
	}

	public Properties getProp() {
		return prop;
	}

}
