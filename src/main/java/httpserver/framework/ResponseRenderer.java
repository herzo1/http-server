package httpserver.framework;

import httpserver.core.protocol.HttpResponse;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Logger;

import static httpserver.core.protocol.HttpConstants.*;

public class ResponseRenderer {

    private static final Logger logger = Logger.getLogger(ResponseRenderer.class.getName());

    public static void renderResponse(String templateName, HttpResponse response) throws IOException {
        Path path = Paths.get(System.getProperty("template.root"), templateName);
        logger.info("Rendering template " + path);
        String content = new String(Files.readAllBytes(path));
        for (String name : response.getParameterNames()) {
            Object value = response.getParameter(name);
            if (value != null) {
                content = content.replaceAll("\\{" + name + "}", value.toString());
            }
        }
        response.setStatus(STATUS_OK);
        response.addHeader(HEADER_CONTENT_TYPE, MIME_TYPE_TEXT_HTML);
        response.writeBody(content);
    }
}
