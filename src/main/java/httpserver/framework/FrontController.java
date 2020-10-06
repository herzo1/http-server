package httpserver.framework;

import httpserver.core.protocol.HttpRequest;
import httpserver.core.protocol.HttpResponse;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Logger;

public class FrontController {
    private static final String ROUTE_FILE = "config/routes.properties";
    private static final Logger logger = Logger.getLogger(FrontController.class.getName());

    public static boolean processRequest(HttpRequest request, HttpResponse response) throws IOException {
        RequestHandler handler = getRequestHandler(request.getPath());
        if (handler == null) return false;
        logger.info("Dispatching request to " + handler.getClass().getName());
        String templateName = handler.handleRequest(request, response);
        if (templateName != null) {
            ResponseRenderer.renderResponse(templateName, response);
        }
        return true;
    }

    private static RequestHandler getRequestHandler(String path) throws IOException {
        Properties routes = new Properties();
        routes.load(new FileInputStream(ROUTE_FILE));
        String classname = routes.getProperty(path);
        try {
            Class clazz = Class.forName(classname);
            return (RequestHandler) clazz.getDeclaredConstructor().newInstance();
        } catch (ReflectiveOperationException e) {
            logger.warning(e.toString());
            return null;
        }
    }
}
