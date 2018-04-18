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
 * Servlet implementation class RemoveMyComment
 */
@WebServlet("/removeMyComment")
public class RemoveMyComment extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RemoveMyComment() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();
		JSONObject res = null;
		int mediaId = Integer.parseInt(request.getParameter("id"));
		int commentId = Integer.parseInt(request.getParameter("comment_id"));
		
		if (!session.isNew()) {
			JSONObject userData = (JSONObject) session.getAttribute("session");
			QueryFacade facade = new QueryFacade();
			String mediaUrl = facade.getVideo(mediaId);
			System.out.println(mediaUrl);
			if (userData.getInt("typeUser") == 2 || userData.getInt("typeUser") == 3) {
				JSONObject json = new JSONObject();
				json.put("id_user", userData.getInt("id")).put("media_id", mediaId).put("comment_id", commentId);
				if (facade.isUserComment(json)) {
					res = facade.deleteComment(json);
					res.put("status", 200);
				} else {
					res = new JSONObject();
					res.put("status", 403).put("msg", "Éste video no está asociado a su usuario");
				}
			} else {
				res = new JSONObject();
				res.put("status", 200).put("msg", "Usuario no posee suficientes requerimientos para ejecutar ésta acción");
			}
		} else {
			res = new JSONObject();
			res.put("status", 500).put("msg", "Debe poseer una sesión para ésta opción");
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
