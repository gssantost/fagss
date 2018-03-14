package fagss.org.srv;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import org.json.JSONObject;

import fagss.org.facade.QueryFacade;

/**
 * SERVLET implementation class MediaUpload
 */
@MultipartConfig
@WebServlet("/MediaUpload")
public class MediaUpload extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public MediaUpload() {
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
		HttpSession session = request.getSession();
		Part file = request.getPart("file");
		InputStream filecontent = file.getInputStream();
		OutputStream os = null;
		String path = System.getProperty("user.home") + "\\fazt-storage";
		PrintWriter out = response.getWriter();
		JSONObject json = (JSONObject) session.getAttribute("session");
		JSONObject data = new JSONObject();
		JSONObject resolve;
		System.out.println(json.toString());

		try {
			if (!session.isNew()) {
				String fileName = this.getFileName(file);
				File folder = new File(path +"\\" + json.getString("username"));
				path = path + "\\" + json.getString("username") + "\\" + fileName;
				
				if (!folder.exists()) {
					folder.mkdir();
				}

				os = new FileOutputStream(path);
				int read = 0;
				byte[] bytes = new byte[1024];

				while ((read = filecontent.read(bytes)) != -1) {
					os.write(bytes, 0, read);
				}
				
				data.put("url", path);
				data.put("name", request.getParameter("name"));
				data.put("description", request.getParameter("description"));
				data.put("filename", fileName);
				data.put("username", json.getString("username"));
				QueryFacade facade = new QueryFacade();
				resolve = facade.upload(data);
				out.print(resolve);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (filecontent != null) {
				filecontent.close();
			}
			if (os != null) {
				os.close();
			}
		}
	}

	// ÉSTA FUNCIÓN 
	private String getFileName(Part file) {
		for (String content :  file.getHeader("content-disposition").split(";")) {
			if (content.trim().startsWith("filename")) {
				return content.substring(content.indexOf('=') + 1).trim().replace("\"", "");
			}
		}
		return null;
	}
}

