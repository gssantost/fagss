package fagss.org.db;

import java.sql.ResultSet;
import java.text.MessageFormat;

import org.json.JSONObject;

import fagss.org.util.Encrypter;
import fagss.org.util.PropertiesMap;

public class User {
	
	private DBHelper db;
	private ResultSet rs;
	private JSONObject res;
	
	public User() {
		this.db = null;
		this.rs = null;
		this.res = new JSONObject();
	}
	
	public JSONObject insert(JSONObject json) {
		int row;
		try {
			PropertiesMap p = PropertiesMap.getInstance();
			db = new DBHelper(p.getValue("DB", "driver"), p.getValue("DB", "url"), p.getValue("DB", "user"), p.getValue("DB", "password"));
			rs = db.execute(p.getValue("Queries", "Q3"), json.getString("user"));
			
			if (!rs.next()) {
				rs.close();
				String securedPassword = Encrypter.getSecurePassword(json.getString("password"));
				row = db.update(p.getValue("Queries", "Q2"), json.getString("name"), json.getString("lastname"), json.getString("user"), securedPassword, json.getString("email"));
				if (row==1) {
					res.put("message", MessageFormat.format(p.getValue("Messages", "m3"), 0)).put("status", 200);
				} else {
					res.put("message", MessageFormat.format(p.getValue("Messages", "m4"), 0)).put("status", 500);
				}
			} else {
				res.put("message", MessageFormat.format(p.getValue("Messages", "m3"), 0)).put("status", 500);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				db.close();
			} catch (Exception e) {
				System.out.println("An error occured while trying to close PreparedStatement: " + e);
			}
		}
		return res;
	}

	public JSONObject check(JSONObject json) {
		try {
			PropertiesMap p = PropertiesMap.getInstance();
			String passwordAsHash = Encrypter.getSecurePassword(json.getString("password"));
			db = new DBHelper(p.getValue("DB", "driver"), p.getValue("DB", "url"), p.getValue("DB", "user"), p.getValue("DB", "password"));
			rs = db.execute(p.getValue("Queries", "Q4"), json.getString("username"), passwordAsHash);
			
			if (!rs.next()) {
				res.put("message", MessageFormat.format(p.getValue("Messages", "m1"), 0)).put("status", 500);
			} else {
				res.put("username", rs.getString(1)).put("typeUser", rs.getInt(2)).put("name", rs.getString(3)).put("lastname", rs.getString(4)).put("id", rs.getInt(5));
				res.put("message", MessageFormat.format(p.getValue("Messages", "m2"), res.getString("username"))).put("status", 200);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				db.close();
			} catch (Exception e) {
				e.printStackTrace();
			}	
		}
		return res; //RETORNO UNA RESPUESTA DE TIPO JSON INCLUYENDO UN MENSAJE, O LOS DATOS PARA LA SESIÓN + UN MENSAJE
					//ENVÍO UN FLAG PARA INDICAR SI SE PUEDE O NO INICIAR UNA SESIÓN HTTP
	}
}