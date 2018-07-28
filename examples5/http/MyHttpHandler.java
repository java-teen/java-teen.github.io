package examples5.http;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

public class MyHttpHandler implements HttpHandler {
	private final HttpServer 	server;			// <1>
	private volatile File 		curDir;
	
	public MyHttpHandler(final HttpServer server) {
		this.server = server;
		this.curDir = new File("c:/testdir");
	}

	private File getCurDir() {
		return curDir;
	}
	
	@Override
	public void handle(final HttpExchange exchange) throws IOException {
		final URI 	uri = exchange.getRequestURI();			// <2>
		boolean		stopServer = false;

		try(final OutputStream 	os = exchange.getResponseBody()) {
			switch (uri.getPath()) {
			  	case "/"	:				// <3>
		  			try{final byte[]		answer = fillPage(null); 
			  			
						exchange.sendResponseHeaders(200, answer.length);
						os.write(answer);
			  		} catch (Exception exc) {
			  			exc.printStackTrace();
			  		}
			  		break;
			  	case "/favicon.ico"	:		// <4>
					exchange.sendResponseHeaders(404, 0);
			  		break;
			  	case "/index.css"	:		// <5>
			  		try(final InputStream	is = this.getClass().getResourceAsStream("index.css")) {
				  		final byte[]		cssContent = loadContent(is);
				  		
						exchange.sendResponseHeaders(200, cssContent.length);
						os.write(cssContent);
			  		}
			  		break;
			  	case "/index.html"	:		// <6>
			  		final Map<String,String>	keys = new HashMap<>();
			  		
			  		for (String item : uri.getQuery().split("\\&")) {	// <7>
			  			final String[]	keyValue = item.split("\\=");
			  			
			  			keys.put(keyValue[0],keyValue[1]);
			  		}
			
			  		File	file2Open = null;
			  		
			  		switch (keys.get("mode") != null ? keys.get("mode") : "") {	// <8>
			  			case "save" :	// <9>
			  				final String	contentType = exchange.getRequestHeaders().getFirst("Content-type");
			  				
			  				if (contentType != null) {
				  				final String	boundary = contentType.substring(contentType.lastIndexOf("boundary=")+"boundary=".length()); 
				  				final Map<String,String>	extracted = loadMultipartContent(exchange.getRequestBody(),"--"+boundary);

				  				try(final InputStream	bais = new ByteArrayInputStream(extracted.get("textarea").getBytes())) {
					  				
				  					Files.copy(bais, new File(curDir,keys.get("name")).toPath(), StandardCopyOption.REPLACE_EXISTING);
				  				}
			  				}
			  				break;
			  			case "exit" :	// <10>
			  				stopServer = true;
			  				break;
			  			case "up" :		// <11>
			  				curDir = getCurDir().getAbsoluteFile().getParentFile();
			  				break;
			  			case "into" :	// <12>
			  				curDir = new File(getCurDir(),keys.get("name"));
			  				break;
			  			case "open" :	// <13>
			  				file2Open = new File(curDir,keys.get("name"));
			  				break;
			  			default :
			  				break;
			  		}

			  		try{final byte[]		answer = fillPage(file2Open);	// <14> 
		  			
						exchange.sendResponseHeaders(200, answer.length);	// <15>
						os.write(answer);
			  		} catch (Exception exc) {
			  			exc.printStackTrace();
			  		}
			  		break;
			  	default :
					exchange.sendResponseHeaders(404, 0);
			  		break;
			}
			os.flush();
		} catch (Exception exc) {
			exc.printStackTrace();
		}
		
		if (stopServer) {
			server.stop(0);
		}
	}

	
	private byte[] fillPage(final File file2Open) throws IOException {	// <16>
  		try(final InputStream	is = this.getClass().getResourceAsStream("index.html")) {
  			return String.format(new String(loadContent(is)),getCurDir().getAbsolutePath(),file2Open == null ? "" : file2Open.getName(),getCurDir().isDirectory() ? getDirectoryContent(getCurDir()) : "",file2Open == null ? "" : loadFileContent(file2Open)).getBytes(); 
  		}
	}

	private static String getDirectoryContent(final File dir) {			// <17>
		final StringBuilder	sb = new StringBuilder();
		
		for(File f : dir.listFiles()) {
			if (f.isDirectory()) {
				sb.append("<tr><td><a href=\"index.html?mode=into&name=").append(f.getName()).append("\">&gt;").append(f.getName()).append("</a></td></tr>");
			}
		}
		for(File f : dir.listFiles()) {
			if (f.isFile()) {
				sb.append("<tr><td><a href=\"index.html?mode=open&name=").append(f.getName()).append("\">&nbsp;").append(f.getName()).append("</a></td></tr>");
			}
		}
		return sb.toString();
	}
	
	private static String loadFileContent(final File file) throws IOException {	// <18>
		try(final InputStream	is = new FileInputStream(file)) {
			return escapeContent(loadContent(is));
		}
	}
	
	private static String escapeContent(final byte[] content) {		// <19>
		final StringBuilder		sb = new StringBuilder(new String(content));
		int location;
		
		location = 0;
		while ((location = sb.indexOf("&", location)) >= 0) {
			sb.delete(location, location+1).insert(location,"&amp;");
			location++;
		}
		
		location = 0;
		while ((location = sb.indexOf("<",location)) >= 0) {
			sb.delete(location, location+1).insert(location,"&lt;");
			location++;
		}
		
		location = 0;
		while ((location = sb.indexOf(">", location)) >= 0) {
			sb.delete(location, location+1).insert(location,"&gt;");
			location++;
		}
		return sb.toString();
	}
	
	private static byte[] loadContent(final InputStream is) throws IOException {	// <20>
		try(final ByteArrayOutputStream	baos = new ByteArrayOutputStream()) {
			final byte[]				buffer = new byte[8192];
			int							len;
			
			while ((len = is.read(buffer)) > 0) {
				baos.write(buffer,0,len);
			}
			baos.flush();
			return baos.toByteArray();
		}
	}
	
	private static Map<String,String> loadMultipartContent(final InputStream content, final String boundary) throws IOException {
		final Map<String,String>	result = new HashMap<>();
		final StringBuilder			data = new StringBuilder();
		String						name = null, str;
		
		try(final Reader	rdr = new InputStreamReader(content);		 // <21>
			final BufferedReader	brdr = new BufferedReader(rdr)) {
			
			if ((str = brdr.readLine()) == null) {
				throw new EOFException(); 
			}
			else if (!str.equals(boundary)){
				throw new EOFException(); 
			}
			
			while (!str.endsWith("--")) {
				if ((str = brdr.readLine()) == null) {
					throw new EOFException(); 
				}
				else {
					name = str.split("\\\"")[1];
				}
				
				if ((str = brdr.readLine()) == null) {
					throw new EOFException(); 
				}
				
				for(;;)
					{if ((str = brdr.readLine()) == null) {
						throw new EOFException(); 
					}
					else if (str.startsWith(boundary)){
						break; 
					}
					else {
						data.append(str).append('\n');
					}
				}
				
				result.put(name, data.toString());
				name = null;
			}
		} catch (EOFException exc) {
			if (name != null) {
				result.put(name, data.toString());
			}
		}
		
		return result;
	}
}
