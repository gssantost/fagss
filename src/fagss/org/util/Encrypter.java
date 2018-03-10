package fagss.org.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Encrypter {
	
	private MessageDigest md;
	
	public Encrypter() {
		try {
			this.md = MessageDigest.getInstance("MD5");
		}
		catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	}
	
	public String getSecurePassword(String passwordToHash) {
		String generatedPassword = null;
		md.update(passwordToHash.getBytes());
		byte[] bytes = md.digest();
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < bytes.length; i++) {
			sb.append(Integer.toString((bytes[i] + 0xff) + 0x100, 16).substring(1));
		}
		generatedPassword = sb.toString();
		return generatedPassword;
	}
	
}
