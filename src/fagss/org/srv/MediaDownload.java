package fagss.org.srv;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import fagss.org.facade.QueryFacade;

/**
 * SERVLET implementation class MediaDownload
 */
@WebServlet("/MediaDownload")
public class MediaDownload extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MediaDownload() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//HttpSession session = request.getSession();
		OutputStream out = null;
		FileInputStream in = null;
		QueryFacade facade = new QueryFacade();
		JSONObject json = new JSONObject();
		json.put("id", request.getParameter("id")); //SE TENÍA UN GET-PARAMETER DE NAME PERO SE CAMBIÓ A BÚSQUEDA POR ID
		JSONObject mediaData = facade.getMedia(json);
		System.out.println(mediaData);
		response.setContentType("file");
		response.setHeader("Content-disposition", "attachment; filename=" + mediaData.getString("filename").replace(",", "_"));
		
		try {
			File fileToDownload = new File(mediaData.getString("url"));
			out = response.getOutputStream();
			in = new FileInputStream(fileToDownload);
			byte[] buffer = new byte[4096];
			int length;
			while ((length = in.read(buffer)) > 0) {
				out.write(buffer, 0, length);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			in.close();
			out.flush();
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
