package Utility;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class PropertyFile {
	PropertyResources propReader = new PropertyResources();
	private String PROPERTY_CONFIG_PROPERTIES_QA = "configQa.properties";
	private String PROPERTY_CONFIG_PROPERTIES_PROD = "configProd.properties";
	private String PROPERTY_CONFIG_PROPERTIES_DEV = "configDev.properties";
	private Properties prop;

	String environment = propReader.getProp().getProperty("environment").toString().trim();
	String qaEnvironment = "qa";
	String prodEnvironment = "prod";
	String devEnvironment = "dev";

	public PropertyFile() {
		if (environment.equals(qaEnvironment)) {
			FileInputStream ip = null;
			try {
				prop = new Properties();
				ip = new FileInputStream(PROPERTY_CONFIG_PROPERTIES_QA);

				prop.load(ip);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (ip != null) {
					try {
						ip.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		} else if (environment.equals(prodEnvironment)) {
			FileInputStream ip = null;
			try {
				prop = new Properties();
				ip = new FileInputStream(PROPERTY_CONFIG_PROPERTIES_PROD);

				prop.load(ip);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (ip != null) {
					try {
						ip.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}

		} else if (environment.equals(devEnvironment)) {
			FileInputStream ip = null;
			try {
				prop = new Properties();
				ip = new FileInputStream(PROPERTY_CONFIG_PROPERTIES_DEV);

				prop.load(ip);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (ip != null) {
					try {
						ip.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}

		}

	}

	public void getProperty(String element) {
		getProp().get("element").toString().trim();
	}

	public Properties getProp() {
		return prop;
	}

}
