package fagss.org.srv;
import fagss.org.facade.*;
import java.io.IOException;
import java.io.PrintWriter;

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
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		PrintWriter out= response.getWriter();
		HttpSession session= request.getSession();
		JSONObject json= (JSONObject) session.getAttribute("session");
		JSONObject data=new JSONObject();
		JSONObject resolve;
		data.put("id_user", json.getInt("id")).put("media_id", Integer.parseInt(request.getParameter("id")));
		QueryFacade facade= new QueryFacade();
		resolve=facade.likes(data);
		out.print(resolve);
		System.out.println(resolve);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out= response.getWriter();
		HttpSession session= request.getSession();
		JSONObject json= (JSONObject) session.getAttribute("session");
		JSONObject data=new JSONObject();
		JSONObject resolve;
		data.put("username", json.getString("username"));
		QueryFacade facade= new QueryFacade();
		resolve=facade.likes(data);
		out.print(resolve);
		System.out.println(resolve);


	}

}
