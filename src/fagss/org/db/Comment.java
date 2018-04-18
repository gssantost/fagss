package fagss.org.db;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.json.JSONObject;

import fagss.org.util.PropertiesMap;

public class Comment {
	
	private DBHelper db;
	private ResultSet rs = null;
	private JSONObject res = new JSONObject();
	PropertiesMap prop = PropertiesMap.getInstance();
	private int queryRes;
	
	public JSONObject setComment(JSONObject json) {
		try {
			this.db = new DBHelper(prop.getValue("DB", "driver"), prop.getValue("DB", "url"), prop.getValue("DB", "user"), prop.getValue("DB", "password"));
			this.queryRes = db.update(prop.getValue("Queries", "Q14"), json.getInt("media_id"), json.getInt("id_user"), json.getString("comment"));
			if (this.queryRes == 1) {
				res.put("msg", "comentario insertado");
			} else {
				res.put("msg", "no se ha podido insertar");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			this.db.close();
		}
		return this.res;
	}
	
	public JSONObject getComment() {
		return res;
	}
	
	public JSONObject banComment(JSONObject json) {
		try {
			this.db = new DBHelper(prop.getValue("DB", "driver"), prop.getValue("DB", "url"), prop.getValue("DB", "user"), prop.getValue("DB", "password"));
			this.queryRes = db.update(prop.getValue("Queries", "Q21"), json.getInt("comment_id"), json.getInt("media_id"));
			if (this.queryRes == 1) {
				res.put("msg", "EL COMENTARIO HA SIDO BANEADO");
			} else {
				res.put("msg", "NO SE HA PODIDO BANEAR EL COMENTARIO");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			this.db.close();
		}
		return this.res;
	}
	
	public JSONObject deleteComment(JSONObject json) {
		try {
			this.db = new DBHelper(prop.getValue("DB", "driver"), prop.getValue("DB", "url"), prop.getValue("DB", "user"), prop.getValue("DB", "password"));
			this.queryRes = db.update(prop.getValue("Queries", "Q26"), json.getInt("comment_id"), json.getInt("media_id"));
			if (this.queryRes == 1) {
				res.put("msg", "HAZ ELIMINADO TU COMENTARIO");
			} else {
				res.put("msg", "NO SE HA PODIDO ELIMINAR TU COMENTARIO");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			this.db.close();
		}
		return this.res;
	}
	
	public boolean isUserComment(JSONObject json) {
		boolean q = false;
		try {
			this.db = new DBHelper(prop.getValue("DB", "driver"), prop.getValue("DB", "url"), prop.getValue("DB", "user"), prop.getValue("DB", "password"));
			this.rs = db.execute(prop.getValue("Queries", "Q27"), json.getInt("id_user"), json.getInt("comment_id"));
			if (rs.next()) {
				q = true;
			}
		} catch (IOException | SQLException e) {
			e.printStackTrace();
		} finally {	
			this.db.close();
		}
		return q;
	}
}
