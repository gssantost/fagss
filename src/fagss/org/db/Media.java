package fagss.org.db;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Calendar;

import org.json.JSONObject;

import fagss.org.util.PropertiesMap;

public class Media {
	
	private Connection con = null;
	private PreparedStatement pstm = null;
	
	public JSONObject setMedia(JSONObject json) {
		JSONObject res = new JSONObject();
		PropertiesMap prop = new PropertiesMap();
		Calendar c = Calendar.getInstance();
		
		try {
			Class.forName(prop.getValue("DB", "driver"));
			con = DriverManager.getConnection(prop.getValue("DB", "url"), prop.getValue("DB", "user"), prop.getValue("DB", "password"));
			pstm = con.prepareStatement(prop.getValue("Queries", "Q5"));
			pstm.setString(1, json.getString("url"));
			pstm.setString(2, json.getString("name"));
			pstm.setString(3, json.getString("filename"));
			pstm.setString(4, json.getString("description"));
			pstm.setTimestamp(5, new java.sql.Timestamp(c.getTime().getTime()));
			pstm.setString(6, json.getString("username"));
			
			if (pstm.executeUpdate() == 1) {
				res.put("message", "video agregado");
			} else {
				res.put("message", "no se ha podido insertar");
			}
			
		} catch (ClassNotFoundException | IOException | SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				pstm.close();
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return res;
	}
	
	public JSONObject getMedia() {
		JSONObject res = new JSONObject();
		PropertiesMap prop = new PropertiesMap();
		
		try {
			Class.forName(prop.getValue("DB", "driver"));
			con = DriverManager.getConnection(prop.getValue("DB", "url"), prop.getValue("DB", "user"), prop.getValue("DB", "password"));
		} catch (ClassNotFoundException | IOException | SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				pstm.close();
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		
		
		return res;
	}

}
