package fagss.org.util;

import java.io.File;

public class DirectoryManager {
	
	public static void createStoragePath() {
		/*BUSCAMOS EL PATH USER.HOME (Ej. C:\\Users\\Giovanny Santos) y le agregamos el nombre de nuestro directorio para almacenar contenido del sitio*/
		File folder = new File(System.getProperty("user.home") + "\\fagss-storage");
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
