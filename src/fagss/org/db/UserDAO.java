package fagss.org.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;

import org.json.JSONObject;

import fagss.org.util.Encrypter;
import fagss.org.util.SingletonProperties;

public class UserDAO {
	
	private Connection con;
	private PreparedStatement pstm;
	private ResultSet rs;
	
	public UserDAO() {
		this.con = null;
		this.pstm = null;
		this.rs = null;
	}
	
	public JSONObject insert(JSONObject json) {
		JSONObject res = new JSONObject();
		int row;
		//String message = null;
		
		try {
			SingletonProperties sp = SingletonProperties.getInstance();
			Class.forName(sp.getProperty("db.driver"));
			con = DriverManager.getConnection(sp.getProperty("db.url"), sp.getProperty("db.user"), sp.getProperty("db.password"));
			pstm = con.prepareStatement(sp.getProperty("Q3"));
			pstm.setString(1, json.getString("user"));
			rs = pstm.executeQuery();
			
			if (!rs.next()) {
				rs.close();
				pstm.close();
				Encrypter en = new Encrypter();
				String securedPassword = en.getSecurePassword(json.getString("password"));
				pstm = con.prepareStatement(sp.getProperty("Q2"));
				pstm.setString(1, json.getString("name"));
				pstm.setString(2, json.getString("lastname"));
				pstm.setString(3, json.getString("user"));
				pstm.setString(4, securedPassword);
				pstm.setString(5, json.getString("email"));
				row = pstm.executeUpdate();
				if (row==1) {
					//System.out.println(MessageFormat.format(sp.getProperty("m3"), 0));
					res.put("message", MessageFormat.format(sp.getProperty("m3"), 0)).put("status", 200);
				} else {
					//System.out.println(MessageFormat.format(sp.getProperty("m4"), 0));
					res.put("message", MessageFormat.format(sp.getProperty("m4"), 0)).put("status", 500);
				}
			} else {
				//System.out.println(MessageFormat.format(sp.getProperty("m5"), 0));
				res.put("message", MessageFormat.format(sp.getProperty("m5"), 0)).put("status", 500);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				pstm.close();
				con.close();
			} catch (Exception e) {
				System.out.println("An error occured while trying to close PreparedStatement: " + e);
			}
		}
		
		return res;
	}

	public JSONObject check(JSONObject json) {
		JSONObject res = new JSONObject();
		
		try {
			SingletonProperties sp = SingletonProperties.getInstance();
			Class.forName(sp.getProperty("db.driver"));
			con = DriverManager.getConnection(sp.getProperty("db.url"), sp.getProperty("db.user"), sp.getProperty("db.password"));
			Encrypter en = new Encrypter();
			String passwordAsHash = en.getSecurePassword(json.getString("password"));
			pstm = con.prepareStatement(sp.getProperty("Q4"));
			pstm.setString(1, json.getString("username"));
			pstm.setString(2, passwordAsHash);
			rs = pstm.executeQuery();
			
			if (!rs.next()) {
				res.put("message", MessageFormat.format(sp.getProperty("m1"), 0)).put("readyState", false);
			} else {
				res.put("username", rs.getString(1)).put("typeUser", rs.getInt(2)).put("name", rs.getString(3)).put("lastname", rs.getString(4));
				res.put("message", MessageFormat.format(sp.getProperty("m2"), res.getString("username"))).put("readyState", true);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				pstm.close();
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}	
		}
		return res; //RETORNO UNA RESPUESTA DE TIPO JSON INCLUYENDO UN MENSAJE, O LOS DATOS PARA LA SESIÓN + UN MENSAJE
					//ENVÍO UN FLAG PARA INDICAR SI SE PUEDE O NO INICIAR UNA SESIÓN HTTP
	}
}