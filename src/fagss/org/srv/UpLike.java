package fagss.org.srv;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;

import fagss.org.db.DBHelper;
import fagss.org.util.PropertiesMap;

/**
 * Servlet implementation class UpLike
 */
@WebServlet("/UpLike")
public class UpLike extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpLike() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		PrintWriter out = response.getWriter();
		PropertiesMap prop = PropertiesMap.getInstance();
		DBHelper db = null;
		//JSONObject json = new JSONObject();
		JSONArray data = null;
		try {
			db = new DBHelper(prop.getValue("DB", "driver"), prop.getValue("DB", "url"), prop.getValue("DB", "user"), prop.getValue("DB", "password"));
			data = db.executeQuery(prop.getValue("Queries", "Q20"), Integer.parseInt(request.getParameter("id")));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.close();
		}
		System.out.println(data);
		out.print(data);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
