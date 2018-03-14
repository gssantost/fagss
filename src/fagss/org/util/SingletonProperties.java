package fagss.org.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class SingletonProperties {
	
	private static SingletonProperties instance = null;
	private final String src = "C:\\Users\\Giovanny Santos\\ee-workspace\\Fagss\\src\\fagss\\org\\config.properties";
	private Properties p;
	
	private SingletonProperties() {
		this.p = new Properties();
		try {
			p.load(new FileInputStream(new File(src)));
		}
		catch (IOException e) {
			System.out.println("ERROR AL CARGAR EL FICHERO " + e);
			e.printStackTrace();
		}
	}
	
	public static SingletonProperties getInstance() {
		if (instance == null) {
			instance = new SingletonProperties();
		}
		return instance;
	}
	
	public String getProperty(String key) {
		return this.p.getProperty(key);
	}

}
