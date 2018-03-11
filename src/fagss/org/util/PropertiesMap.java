package fagss.org.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Properties;

public class PropertiesMap {
	
	private File configDir;
	private Properties prop;
	private InputStream in;
	private HashMap<String, String> map;
	
	public PropertiesMap() {
		configDir = new File(System.getProperty("catalina.home"), "conf"); //CATALIA.HOME
		prop = new Properties();
		map = new HashMap<String, String>();
		map.put("DB", "config.properties");
		map.put("Queries", "queries.properties");
		map.put("Messages", "messages.properties");
	}
	 
	public String getValue(String key, String value) throws IOException {
		
		for (HashMap.Entry<String, String> entry : map.entrySet()) {
			if (entry.getKey().equals(key)) {
				in = new FileInputStream(new File(configDir, entry.getValue()));
				prop.load(in);
			}
		}
		return prop.getProperty(value);
	}
	
}
