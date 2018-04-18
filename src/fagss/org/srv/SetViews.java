package fagss.org.srv;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import fagss.org.db.DBHelper;
import fagss.org.util.PropertiesMap;

/**
 * Servlet implementation class SetViews
 */
@WebServlet("/setView")
public class SetViews extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SetViews() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		PropertiesMap prop = PropertiesMap.getInstance();
		JSONObject res = new JSONObject();
		DBHelper db = new DBHelper(prop.getValue("DB", "driver"), prop.getValue("DB", "url"), prop.getValue("DB", "user"), prop.getValue("DB", "password"));
		try {
			int queryRes = db.update(prop.getValue("Queries", "Q16"), Integer.parseInt(request.getParameter("id")));
			if (queryRes == 1) {
				res.put("status", 200).put("msg", "Video visto!");
			} else {
				res.put("status", 500).put("msg", "No se pudo efectuar la actualización");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.close();
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
