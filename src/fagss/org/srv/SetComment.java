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
 * Servlet implementation class SetComment
 */
@WebServlet("/SetComment")
public class SetComment extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SetComment() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		PrintWriter out = response.getWriter();
		JSONObject json = (JSONObject) session.getAttribute("session");
		JSONObject data = new JSONObject();
		JSONObject resolve;
		data.put("media_id", Integer.parseInt(request.getParameter("id")));
		data.put("id_user", json.getInt("id"));
		data.put("comment", request.getParameter("comment"));
		data.put("username", json.getString("username"));
		QueryFacade facade= new QueryFacade();
		resolve=facade.comment(data);
		out.print(resolve);
		System.out.println(resolve);
	}

}
