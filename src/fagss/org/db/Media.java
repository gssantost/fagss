package fagss.org.db;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.json.JSONArray;
import org.json.JSONObject;

import fagss.org.util.PropertiesMap;

public class Media {

	private ResultSet rs = null;
	private JSONObject res = new JSONObject();
	private DBHelper db;
	private PropertiesMap prop = PropertiesMap.getInstance();

	public JSONObject setMedia(JSONObject json) {
		int queryRes;
		try {
			db = new DBHelper(prop.getValue("DB", "driver"), prop.getValue("DB", "url"), prop.getValue("DB", "user"),
					prop.getValue("DB", "password"));
			queryRes = db.update(prop.getValue("Queries", "Q5"), json.getString("url"), json.getString("name"),
					json.getString("filename"), json.getString("description"), json.getString("username"));
			if (queryRes == 1) {
				res.put("message", "Su video ha sido agregado!");
			} else {
				res.put("message", "no se ha podido insertar");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			db.close();
		}
		return res;
	}

	public JSONArray getAllVideos(JSONObject json) {
		JSONArray res = new JSONArray();
		try {
			db = new DBHelper(prop.getValue("DB", "driver"), prop.getValue("DB", "url"), prop.getValue("DB", "user"),
					prop.getValue("DB", "password"));
			res = db.executeQuery(prop.getValue("Queries", "Q8"), json.getString("username"));
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			db.close();
		}
		return res;
	}

	// AHORA RETORNA LA MEDIA POR ID
	public JSONObject getMedia(JSONObject json) {
		try {
			db = new DBHelper(prop.getValue("DB", "driver"), prop.getValue("DB", "url"), prop.getValue("DB", "user"),
					prop.getValue("DB", "password"));
			rs = db.execute(prop.getValue("Queries", "Q7"), json.getInt("id"));
			while (rs.next()) {
				res.put("url", rs.getString(1)).put("filename", rs.getString(2));
			}
		} catch (IOException | SQLException e) {
			e.printStackTrace();
			res.put("message", "no se ha podido descargar");
		} finally {
			db.close();
		}
		return res;
	}

	public String getVideo(int id) {
		String res = null;
		try {
			db = new DBHelper(prop.getValue("DB", "driver"), prop.getValue("DB", "url"), prop.getValue("DB", "user"),
					prop.getValue("DB", "password"));
			rs = db.execute(prop.getValue("Queries", "Q9"), id);
			while (rs.next()) {
				res = rs.getString(1);
			}
		} catch (IOException | SQLException e) {
			e.printStackTrace();
		} finally {
			db.close();
		}
		return res;
	}

	public JSONObject removeVideo(int id) {
		PreparedStatement remLikes = null;
		PreparedStatement remComments = null;
		PreparedStatement remVideo = null;
		Connection con = null;

		try {
			Class.forName(prop.getValue("DB", "driver"));
			con = DriverManager.getConnection(prop.getValue("DB", "url"), prop.getValue("DB", "user"),
					prop.getValue("DB", "password"));
			con.setAutoCommit(false);
			remLikes = con.prepareStatement(prop.getValue("Queries", "Q22"));
			remComments = con.prepareStatement(prop.getValue("Queries", "Q23"));
			remVideo = con.prepareStatement(prop.getValue("Queries", "Q24"));
			remLikes.setInt(1, id);
			remLikes.executeUpdate();
			remComments.setInt(1, id);
			remComments.executeUpdate();
			remVideo.setInt(1, id);
			remVideo.executeUpdate();
			con.commit();
			res.put("message", "Video eliminado");
		} catch (ClassNotFoundException | IOException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				//con.setAutoCommit(true);
				if (remLikes != null) {
					remLikes.close();
				}
				if (remComments != null) {
					remComments.close();
				}
				if (remVideo != null) {
					remVideo.close();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return res;
	}
	
	public boolean isUserVideo(JSONObject json) {
		boolean q = false;
		try {
			db = new DBHelper(prop.getValue("DB", "driver"), prop.getValue("DB", "url"), prop.getValue("DB", "user"), prop.getValue("DB", "password"));
			rs = db.execute(prop.getValue("Queries", "Q25"), json.getInt("id_user"), json.getInt("media_id"));
			if (rs.next()) {
				q = true;
			}
		} catch (IOException | SQLException e) {
			e.printStackTrace();
		} finally {
			db.close();
		}
		return q;
	}

}
