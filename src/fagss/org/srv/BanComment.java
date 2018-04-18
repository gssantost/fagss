package fagss.org.srv;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;

import fagss.org.facade.QueryFacade;

/**
 * Servlet implementation class BanComment
 */
@WebServlet("/BanComment")
public class BanComment extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BanComment() {
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
		JSONObject data = new JSONObject();
		JSONObject res = null;
		
		if (!session.isNew()) {
			JSONObject userData = (JSONObject) session.getAttribute("session");
			if (userData.getInt("typeUser") == 3) {
				data.put("media_id", request.getParameter("id")).put("comment_id", request.getParameter("comment_id"));
				QueryFacade facade = new QueryFacade();
				res = facade.banComment(data);
				res.put("status", 200);
			} else {
				res = new JSONObject();
				res.put("status", 403).put("msg", "Usuario no posee suficientes requerimientos para ejecutar ésta acción");
			}
		} else {
			res = new JSONObject();
			res.put("status", 500).put("msg", "Debe poseer una sesión para ésta opción");			
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
