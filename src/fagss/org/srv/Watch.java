package fagss.org.srv;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fagss.org.facade.QueryFacade;

/**
 * SERVLET implementation class PlayVideo
 */
@WebServlet("/watch")
public class Watch extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Watch() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ServletOutputStream out = response.getOutputStream();
		QueryFacade facade = new QueryFacade();
		String url = facade.getVideo(Integer.parseInt(request.getParameter("id")));
		System.out.println(url);
		InputStream in = new FileInputStream(url);
		String mime = "video/mp4";
		byte[] bytes = new byte[1024];
		
		response.setContentType(mime);
		
		try {
			int bytesRead;
			while ((bytesRead = in.read(bytes)) != 1) {
				out.write(bytes, 0, bytesRead);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			in.close();
			out.close();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
