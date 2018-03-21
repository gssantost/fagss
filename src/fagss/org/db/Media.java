package fagss.org.db;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Calendar;

import org.json.JSONArray;
import org.json.JSONObject;

import fagss.org.util.PropertiesMap;

public class Media {
	
	private Connection con = null;
	private PreparedStatement pstm = null;
	private ResultSet rs = null;
	private ResultSetMetaData rsmd = null;
	
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
	
	public JSONArray getUserMedia(String query, Object... val) {
		JSONArray res = new JSONArray();
		PropertiesMap prop = new PropertiesMap();
		
		try {
			Class.forName(prop.getValue("DB", "driver"));
			con = DriverManager.getConnection(prop.getValue("DB", "url"), prop.getValue("DB", "user"), prop.getValue("DB", "password"));
			pstm = con.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			
			for (int i = 0; i < val.length; i++) {
				pstm.setObject(i + 1, val[i]);
			}
			
			rs = pstm.executeQuery();
			rsmd = rs.getMetaData();
			rs.beforeFirst();
			while (rs.next()) {
				JSONObject row = new JSONObject();
				for (int i = 1; i <= rsmd.getColumnCount(); i++) {
					row.put(rsmd.getColumnLabel(i), rs.getObject(i));
				}
				res.put(row);
			}
			
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
	
	//AHORA RETORNA LA MEDIA POR ID
	public JSONObject getMedia(JSONObject json) {
		JSONObject res = new JSONObject();
		PropertiesMap prop = new PropertiesMap();
		
		try {
			Class.forName(prop.getValue("DB", "driver"));
			con = DriverManager.getConnection(prop.getValue("DB", "url"), prop.getValue("DB", "user"), prop.getValue("DB", "password"));
			pstm = con.prepareStatement(prop.getValue("Queries", "Q7"));
			pstm.setInt(1, json.getInt("id"));
			rs = pstm.executeQuery();
			
			while (rs.next()) {
				res.put("url", rs.getString(1)).put("filename", rs.getString(2));
			}
			
		} catch (ClassNotFoundException | IOException | SQLException e) {
			e.printStackTrace();
			res.put("message", "no se ha podido descargar");
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
	
	public String getVideo(int id) {
		String res = null;
		PropertiesMap prop = new PropertiesMap();
		
		try {
			Class.forName(prop.getValue("DB", "driver"));
			con = DriverManager.getConnection(prop.getValue("DB", "url"), prop.getValue("DB", "user"), prop.getValue("DB", "password"));
			pstm = con.prepareStatement(prop.getValue("Queries", "Q9"));
			pstm.setInt(1, id);
			rs = pstm.executeQuery();
			while (rs.next()) {
				res = rs.getString(1);
			}
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
