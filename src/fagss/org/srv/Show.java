package fagss.org.srv;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONObject;

import fagss.org.db.DBHelper;
import fagss.org.util.PropertiesMap;

/**
 * Servlet implementation class Show
 */
@WebServlet("/show")
public class Show extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Show() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();
		PropertiesMap prop = PropertiesMap.getInstance();
		JSONObject res = new JSONObject();
		JSONArray data = new JSONArray();
		DBHelper db = new DBHelper(prop.getValue("DB", "driver"), prop.getValue("DB", "url"), prop.getValue("DB", "user"), prop.getValue("DB", "password"));
		data = db.executeQuery(prop.getValue("Queries", "Q17"), "null");
		db.close();
		
		if (!session.isNew()) {
			JSONObject userData = (JSONObject) session.getAttribute("session");
			System.out.println(userData);
			res.put("status", 200).put("mediaInfo", data).put("typeInfo", userData.getInt("typeUser")); //LE ENVÍO EL TYPEUSER DE LA SESIÓN (2 O 3 / USER O ADMIN)
		} else {
			res.put("status", 200).put("mediaInfo", data).put("typeInfo", 1); //LE INDICO EL TYPE_ID 1 (GUEST)
			session.invalidate();
		}
		out.print(res);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
