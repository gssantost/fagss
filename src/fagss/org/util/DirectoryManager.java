package fagss.org.util;

import java.io.File;

public class DirectoryManager {
	
	public static void createStoragePath() {
		File folder = new File(System.getProperty("user.home") + "\\fazt-storage");
		if (!folder.exists()) {
			if (folder.mkdir()) {
				System.out.println("Directorio de almacenamiento creado satisfactoriamente");
			} else {
				System.out.println("Hubo un error al intentar crear el directorio");
			}
		} else {
			System.out.println("El directorio ya existe");
		}
	}

}
