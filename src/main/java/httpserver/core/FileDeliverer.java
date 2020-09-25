package httpserver.core;

import httpserver.core.protocol.HttpResponse;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Logger;

import static httpserver.core.protocol.HttpConstants.HEADER_CONTENT_TYPE;

/**
 * The class FileDeliverer is responsible for the delivery of static file content.
 */
public class FileDeliverer {

	private static final Logger logger = Logger.getLogger(FileDeliverer.class.getName());

	public static void deliverFile(String path, HttpResponse response) throws IOException {
		if (path.endsWith("/")) {
			path += System.getProperty("index.file");
		}
		Path filePath = Paths.get(System.getProperty("document.root"), path);
		if (!Files.exists(filePath) || !Files.isRegularFile(filePath)) {
		    throw new FileNotFoundException("Path or File does not exist!");
		}
		logger.info("Delivering file " + filePath);
		response.addHeader(HEADER_CONTENT_TYPE, Files.probeContentType(filePath));
		response.writeBody(Files.readAllBytes(filePath));
	}
}
