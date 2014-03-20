package userUploader;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jspsmart.File;
import jspsmart.SmartUpload;
import spiders.MyJDBC;

/**
 * 
 * @author nightwolf
 */
public class UpLoadUserHeadImage extends HttpServlet {
	private ServletConfig config = null;
	private String FileName = null;
	private String sPath = "/UploadPhoto";

        /**
         * 
         * @param request
         * @param response
         * @throws ServletException
         * @throws IOException
         */
        public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		SmartUpload mySmartUpload = new SmartUpload();
		mySmartUpload.initialize(config, request, response);
		mySmartUpload.setMaxFileSize(2048 * 1024);
		mySmartUpload.setAllowedFilesList("jpg,gif,png,jpeg,bmp");
		try {
			mySmartUpload.upload();
			File myFile = mySmartUpload.getFiles().getFile(0);
			if (!myFile.isMissing()) {

				Date currTime = new Date();
				SimpleDateFormat formatter2 = new SimpleDateFormat(
						"yyyyMMddhhmmssS", Locale.US);
				FileName = new String((formatter2.format(currTime))
						.getBytes("iso-8859-1"));
				String ext = myFile.getFileExt();
				FileName = FileName + "." + ext;

				myFile.saveAs(sPath + "/" + FileName,
						mySmartUpload.SAVE_VIRTUAL);
                                
			}
			response.sendRedirect("Pages/uploadimage.jsp?Picurl="
					+ FileName + "&step=2");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

        /**
         * 
         * @param config
         * @throws ServletException
         */
        @Override
	public void init(ServletConfig config) throws ServletException {
		this.config = config;
	}

}
