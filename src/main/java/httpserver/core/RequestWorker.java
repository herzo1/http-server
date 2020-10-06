package httpserver.core;

import httpserver.core.protocol.HttpConstants;
import httpserver.core.protocol.HttpRequest;
import httpserver.core.protocol.HttpResponse;
import httpserver.framework.FrontController;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Logger;

import static httpserver.core.protocol.HttpConstants.*;

/**
 * The class RequestWorker is responsible for the processing of HTTP requests.
 */
public class RequestWorker implements Runnable {

	private static final Logger LOGGER = Logger.getLogger(RequestWorker.class.getName());

	private Socket socket;

	public RequestWorker(Socket socket) {
		this.socket = socket;
	}

	public void run() {
		try (InputStream inputStream = socket.getInputStream(); OutputStream outputStream = socket.getOutputStream()) {
		    HttpRequest request = new HttpRequest(inputStream);
		    HttpResponse response = new HttpResponse(outputStream);
		    request.parse();
		    processRequest(request, response);
		    response.send();
		} catch (IOException ex) {
			LOGGER.severe(ex.toString());
		} finally {
			try {
				socket.close();
				LOGGER.info("Connection to " + socket.getInetAddress() + " closed");
			} catch (IOException ex) {
				LOGGER.warning(ex.toString());
			}
		}
	}

	private void processRequest(HttpRequest request, HttpResponse response) throws IOException {
		boolean processed = FileDeliverer.deliverFile(request.getPath(), response);

		if (!processed) {
			try {
				processed = FrontController.processRequest(request, response);
			} catch (RuntimeException re) {
				LOGGER.severe(re.toString());
				re.printStackTrace();
				response.setStatus(STATUS_INTERNAL_SERVER_ERROR);
				response.writeBody("<html><body><h1>" + STATUS_INTERNAL_SERVER_ERROR + "</h1></body></html>");
				processed = true;
			}
		}
		if (!processed) {
			response.setStatus(STATUS_NOT_FOUND);
			response.addHeader(HEADER_CONTENT_TYPE, MIME_TYPE_TEXT_HTML);
			response.writeBody("<html><header><meta charset='UTF-8'/></header>");
			response.writeBody("<body><h1>" + STATUS_NOT_FOUND + "</h1></body><html>");
		}
	}
}
