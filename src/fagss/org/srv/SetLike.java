package fagss.org.srv;
import fagss.org.db.DBHelper;
import fagss.org.util.PropertiesMap;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;

/**
 * Servlet implementation class SetLike
 */
@WebServlet("/SetLike")
public class SetLike extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SetLike() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();
		JSONObject json = (JSONObject) session.getAttribute("session");
		JSONObject data = new JSONObject();
		data.put("id_user", json.getInt("id")).put("media_id", Integer.parseInt(request.getParameter("id")));
		JSONObject res = new JSONObject();
		int queryRes;
		PropertiesMap prop = PropertiesMap.getInstance();
		
		try {
			DBHelper db = new DBHelper(prop.getValue("DB", "driver"), prop.getValue("DB", "url"), prop.getValue("DB", "user"), prop.getValue("DB", "password"));
			ResultSet rs = db.execute(prop.getValue("Queries", "Q18"), data.getInt("media_id"), data.getInt("id_user"));
			
			if (!rs.next()) {
				rs.close();
				queryRes = db.update(prop.getValue("Queries", "Q12"), data.getInt("media_id"), data.getInt("id_user"));
				if (queryRes == 1) {
					res.put("message", "Tú LIKE! se ha añadido a la base de datos.");
				} else {
					res.put("message", "No se ha podido realizar el LIKE!");
				}
			} else {
				queryRes = db.update(prop.getValue("Queries", "Q19"), data.getInt("media_id"), data.getInt("id_user"));
				if (queryRes == 1) {
					res.put("message", "Ahora LIKE!");
				} else {
					res.put("message", "No se ha podido realizar el LIKE");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		out.print(res);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
