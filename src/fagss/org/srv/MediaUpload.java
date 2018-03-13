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

/**
 * Servlet implementation class MediaUpload
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
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		    HttpSession session = request.getSession();
		    Part file =request.getPart("file");
			//Collection <Part> form = request.getParts();
			InputStream filecontent = file.getInputStream();
			OutputStream os = null;
			String path ="C:\\Users\\Fabiola\\Desktop\\test";
			PrintWriter out = response.getWriter();
			JSONObject message =new JSONObject();
		
			
			try {
				if (!session.isNew()) {
					String fileName = this.getFileName(file);
					File folder =new File(path +"\\" +session.getAttribute("email"));
					path =path +"\\" + session.getAttribute("email") + "\\" +fileName;
					if(!folder.exists())
						folder.mkdir();
				os = new FileOutputStream(path);
				int read = 0;
				byte[] bytes = new byte[1024];
	
				while ((read = filecontent.read(bytes)) != -1) {
					os.write(bytes, 0, read);
				}
				message.put("status",200);
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
			
			out.print("AQUIII");
			}
	
	
	// Esta funcion permite obtener el nombre del archivo
	private String getFileName(Part file) {
		for (String content :  file.getHeader("content-disposition").split(";")) {
			if (content.trim().startsWith("filename")) {
				return content.substring(content.indexOf('=') + 1).trim().replace("\"", "");
			}
		}
		return null;
	}
}

