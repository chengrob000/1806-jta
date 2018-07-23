package project01Servlets;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;

import project01Dao.RequestDaoImpl;
import project01Services.RequestService;

/**
 * Servlet implementation class AdditionalFilesServlet
 */
public class AdditionalFilesServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	   private boolean isMultipart;
	   private String filePath;
	   private int maxFileSize = 50 * 1024;
	   private int maxMemSize = 4 * 1024;
	   private File file ;
	   private Map<String,String> map = new HashMap<String,String>();
	   private String fileName;
	   private RequestService reqSer = new RequestService();
	   final static Logger logger = Logger.getLogger(AdditionalFilesServlet.class);

       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdditionalFilesServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	       throw new ServletException("GET method used with " +
	       getClass( ).getName( )+": POST method required.");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		   logger.info("Uploading an addtional file");
	      // Check that we have a file upload request
	      isMultipart = ServletFileUpload.isMultipartContent(request);
	      response.setContentType("text/html");
	      String reqId= "";
	      java.io.PrintWriter out = response.getWriter( );

	      
	      DiskFileItemFactory factory = new DiskFileItemFactory();
	      
	      // maximum size that will be stored in memory
	      factory.setSizeThreshold(maxMemSize);
	   
	      // Location to save data that is larger than maxMemSize.
	      factory.setRepository(new File("c:\\temp"));

	      // Create a new file upload handler
	      ServletFileUpload upload = new ServletFileUpload(factory);
	   
	      // maximum file size to be uploaded.
	      upload.setSizeMax( maxFileSize );
	      
	      logger.info("Attempting to upload. .");
	      try { 
	          // Parse the request to get file items.
	          List fileItems = upload.parseRequest(request);
	 	
	          // Process the uploaded file items
	          Iterator i = fileItems.iterator();

	          out.println("<html>");
	          out.println("<head>");
	          out.println("<title>Servlet upload</title>");  
	          out.println("</head>");
	          out.println("<body>");
	    
	          while ( i.hasNext () ) {
	             FileItem fi = (FileItem)i.next();
	             if ( !fi.isFormField () ) {
	                // Get the uploaded file parameters
	         		HttpSession session = request.getSession();
	                String fieldName = fi.getFieldName();
	                fileName = (String)session.getAttribute("username")+"_"+reqId+"_"+fi.getName();
	                String contentType = fi.getContentType();
	                boolean isInMemory = fi.isInMemory();
	                long sizeInBytes = fi.getSize();
	                
	             
	                // Write the file
	                file = new File( "c:\\temp\\" + fileName) ;
	                fi.write( file ) ;
	                RequestDaoImpl reqDao = new RequestDaoImpl();
	                reqDao.createAdditionalFiles(reqId, fileName);
	                out.println("Additional File Added!");
	             }
	             else {reqId = fi.getString();}
	          }
	          out.println("</body>");
	          out.println("</html>");
	          } catch(Exception ex) {
	             logger.error("Unable to upload");
	          }
	}

}
