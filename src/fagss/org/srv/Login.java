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

import org.json.JSONObject;

import fagss.org.facade.QueryFacade;

/**
 * SERVLET implementation class Login
 */
@WebServlet("/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Login() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();
		JSONObject json = new JSONObject();
		
		if (session.isNew()) {
			json.put("status", 200).put("message", "session not started");
			session.invalidate();
		} else {
			json.put("status", 200).put("message", "session started").put("session", session.getAttribute("session"));
		}
		
		out.print(json);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();
		JSONObject reqBody = new JSONObject(request.getReader().lines().collect(Collectors.joining(System.lineSeparator())));
		QueryFacade facade = new QueryFacade();
		JSONObject data = facade.checkUser(reqBody);
		JSONObject json = new JSONObject();
		
		if (data.getBoolean("readyState")) {
			if (session.isNew()) {
				storeSession(data, session);
				json.put("status", 200).put("res", "session stored").put("session", session.getAttribute("session"));
			} else {
				json.put("status", 200).put("res", "ya existe un usuario en sesion");
			}
			out.print(json);
		} else {
			out.print(data);
		}
	}
	
	private void storeSession(JSONObject dataToStore, HttpSession session) {
		if (dataToStore == null) {
			session.setAttribute("session", dataToStore);
		} else {
			session.setAttribute("session", dataToStore);
		}
	}

}

