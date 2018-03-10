package fagss.org.srv;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import fagss.org.facade.QueryFacade;

/**
 * SERVLET implementation class RegisterUser
 */
@WebServlet("/RegisterUser")
public class Register extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Register() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		JSONObject body = new JSONObject(request.getReader().lines().collect(Collectors.joining(System.lineSeparator())));
		JSONObject json = new JSONObject();
		System.out.println(body);
		if (body.getString("password").trim().equals(body.getString("re-password").trim())) {
			try {
				QueryFacade facade = new QueryFacade();
				String message = facade.addUser(body);
				json.put("status", 200).put("message", message);
				out.print(json);
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			json.put("status", "Oops! Check your password fields!");
			out.print(json);
		}
	}
}
