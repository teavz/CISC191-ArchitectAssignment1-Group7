package edu.sdccd.cisc191.template;

import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

@WebServlet(name = "FileUploadServlet", urlPatterns = { "/fileuploadservlet" })
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 1,    // 1MB
        maxFileSize = 1024 * 1024 * 10,         // 10 MB
        maxRequestSize = 1024 * 1024 * 100      //100MB
)

public class FileUploadServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

        public void doPost(HttpServletRequest request, HttpsServletResponse response) throws ServletException, IOException {

        }
}
