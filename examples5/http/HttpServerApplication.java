package examples5.http;

import java.io.IOException;
import java.net.InetSocketAddress;

import com.sun.net.httpserver.HttpServer;

public class HttpServerApplication {

	public static void main(String[] args) throws IOException {
		final HttpServer 	server = HttpServer.create();	// <1>
		
		server.bind(new InetSocketAddress(8080), 0);
		server.createContext("/", new MyHttpHandler(server));
		server.start();
	}
}
