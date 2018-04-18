package fagss.org.srv;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONObject;

import fagss.org.db.DBHelper;
import fagss.org.facade.QueryFacade;
import fagss.org.util.PropertiesMap;

/**
 * Servlet implementation class SetComment
 */
@WebServlet("/Comment")
public class SetComment extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SetComment() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();
		PropertiesMap prop = PropertiesMap.getInstance();
		JSONArray commentData = null;
		JSONObject res = new JSONObject();
		try {
			DBHelper db = new DBHelper(prop.getValue("DB", "driver"), prop.getValue("DB", "url"), prop.getValue("DB", "user"), prop.getValue("DB", "password"));
			commentData = db.executeQuery(prop.getValue("Queries", "Q15"), Integer.parseInt(request.getParameter("id")));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if (!session.isNew()) {
			JSONObject userData = (JSONObject) session.getAttribute("session");
			System.out.println(userData);
			res.put("status", 200).put("commentInfo", commentData).put("typeInfo", userData.getInt("typeUser")); //LE ENVÍO EL TYPEUSER DE LA SESIÓN (2 O 3 / USER O ADMIN)
		} else {
			res.put("status", 200).put("commentInfo", commentData).put("typeInfo", 1); //LE INDICO EL TYPE_ID 1 (GUEST)
			session.invalidate();
		}
		out.print(res);
	}
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		PrintWriter out = response.getWriter();
		JSONObject userData = (JSONObject) session.getAttribute("session");
		JSONObject data = new JSONObject();
		JSONObject reqBody = new JSONObject(request.getReader().lines().collect(Collectors.joining(System.lineSeparator())));
		System.out.println(reqBody);
		System.out.println(userData);
		data.put("media_id", Integer.parseInt(reqBody.getString("media_id")));	
		data.put("id_user", userData.getInt("id"));
		data.put("comment", reqBody.getString("comment"));
		data.put("username", userData.getString("username"));
		QueryFacade facade= new QueryFacade();
		JSONObject resolve;
		resolve = facade.setComment(data);
		out.print(resolve);
		System.out.println(resolve);
	}

}
