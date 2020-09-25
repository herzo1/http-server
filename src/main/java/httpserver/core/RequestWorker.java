package httpserver.core;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Logger;

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
			Scanner scanner = new Scanner(inputStream);
			PrintWriter writer = new PrintWriter(outputStream);
			processRequest(scanner, writer);
		} catch (IOException ex) {
			LOGGER.severe(ex.toString());
		} finally {
			try {
				socket.close();
				LOGGER.info("Connection to " + socket.getInetAddress() + " closed");
			} catch (IOException ex) {
			}
		}
	}

	private void processRequest(Scanner scanner, PrintWriter writer) throws IOException {
		StringBuilder html = new StringBuilder("<!DOCTYPE html> \n" +
				"<html>\n" +
				"<head>\n" +
				"<title>Request</title>\n" +
				"</head>\n" +
				"<body>\n");


		// scanner has the http-request present
		while(scanner.hasNext()) {
			html.append("<p>");
			html.append(scanner.nextLine());
			html.append("</p>\n");
		}

		html.append("</body>\n" + "</html>");

		writer.println("HTTP/1.1 200 OK"); // status line
		writer.println("Content-Type: text/html");
		writer.println("Content-Length: " + html.length());
		writer.println("Keep-Alive: timeout=5, max=99");
		writer.println("Connection: Keep-Alive");
		writer.println(); // empty line

        writer.print(html);
		writer.flush(); // schickt buffer-inhalt über das netzwerk
	}
}