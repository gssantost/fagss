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
	private HashMap<String, Properties> map;
	private static PropertiesMap propertyMapInstance = null;
	
	private PropertiesMap() throws IOException {
		this.configDir = new File(System.getProperty("catalina.home"), "conf");
		this.map = new HashMap<String, Properties>();
		this.map.put("DB", createProperty("config.properties"));
		this.map.put("Queries", createProperty("queries.properties"));
		this.map.put("Messages", createProperty("messages.properties"));
	}
	
	public static PropertiesMap getInstance() {
		if (propertyMapInstance == null) {
			synchronized (PropertiesMap.class) {
				if (propertyMapInstance == null) {
					try {
						propertyMapInstance = new PropertiesMap();
					} catch (IOException e) {
						System.out.println("Error al crear el Mapa de Propiedades!");
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
		return propertyMapInstance;
	}

	public String getValue(String key, String value) throws IOException {
		for (HashMap.Entry<String, Properties> entry : map.entrySet()) {
			if (entry.getKey().equals(key)) {
				return entry.getValue().getProperty(value);
			}
		}
		return "false";
	}
	
	private Properties createProperty(String propFile) throws IOException {
		this.prop = new Properties();
		this.in = new FileInputStream(new File(this.configDir, propFile));
		System.out.println("Mapping : " + this.configDir.toString() + "\\" + propFile);
		prop.load(in);
		return prop;
	}
}
