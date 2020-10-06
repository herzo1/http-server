package httpserver.framework;

import httpserver.core.protocol.HttpRequest;
import httpserver.core.protocol.HttpResponse;

public interface RequestHandler {
    public String handleRequest(HttpRequest request, HttpResponse response);
}
