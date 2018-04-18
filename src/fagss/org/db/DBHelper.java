package fagss.org.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import org.json.JSONArray;
import org.json.JSONObject;

public class DBHelper {
	
	private Connection con;
	private PreparedStatement pstm;
	private ResultSet rs;
	private ResultSetMetaData rsmd;
	private int res;
	private JSONObject row;
	private JSONArray table;
	
	public DBHelper(String driver, String url, String usr, String pwd) {
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, usr, pwd);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}
	
	//PARA EJECUTAR QUERIES MÁS COMPLETOS COMO SELECTS SIMPLES Y SELECTS DE COMPROBACIONES
	public ResultSet execute(String query, Object... values) {
		try {
			this.pstm = con.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			//SET EVERY VALUE PASSED AS ARGUMENT
			for (int i = 0; i < values.length; i++) {
				this.pstm.setObject(i + 1, values[i]);
			}
			this.rs = this.pstm.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return this.rs;
	}
	
	//PARA OBTENER INFORMACIÓN MÁS COMPLETA DESDE UNA TABLA
	public JSONArray executeQuery(String query, Object... values) {
		try {
			this.pstm = con.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			if (values[0] != "null") {
				for (int i = 0; i < values.length; i++) {
					this.pstm.setObject(i + 1, values[i]);
				}
			}
			this.rs = this.pstm.executeQuery();
		} catch (Exception e) {
			e.getStackTrace();
		} 
		return this.getTable(this.rs);
	}
	
	//FUNCIÓN PARA OBTENER UNA TABLA
	public JSONArray getTable(ResultSet rs) {
		try {
			this.rsmd = rs.getMetaData();
			rs.last();
			table = new JSONArray();
			rs.beforeFirst();
			while (rs.next()) {
				row = new JSONObject();
				for (int i = 1; i <= this.rsmd.getColumnCount(); i++) {
					row.put(rsmd.getColumnLabel(i), rs.getObject(i));
				}
				table.put(row);
			}
		}
		catch (Exception e) {
			e.getStackTrace();
		}
		return table;
	}
	
	//PARA EJECUTAR SENTENCIAS DE INSERT, UPDATE Y DELETE
	public int update(String query, Object... values) {
		try {
			this.pstm = con.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			//SET EVERY VALUE PASSED AS ARGUMENT
			for (int i = 0; i < values.length; i++) {
				this.pstm.setObject(i + 1, values[i]);
			}
				this.res = this.pstm.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		return this.res; 
	}
	
	//PARA CERRAR LA CONEXIÓN
	public void close() {
		try {
			this.con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
