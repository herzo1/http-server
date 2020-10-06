package httpserver.application.controller;

import httpserver.core.protocol.HttpRequest;
import httpserver.core.protocol.HttpResponse;
import httpserver.framework.RequestHandler;

public class HelloHandler implements RequestHandler {
    private static final String HELLO_FORM = "hello-form.html";
    private static final String HELLO_RESPONSE = "hello-response.html";
    private static final String NOT_FOUND = "not-found.html";

    @Override
    public String handleRequest(HttpRequest request, HttpResponse response) {
        if (request.isGet()) {
            return HELLO_FORM;
        } else if (request.isPost()) {
            String name = request.getParameter("name");
            if (name == null || name.isEmpty()) {
                name = "anybody";
            }
            response.addParameter("name", name);
            return HELLO_RESPONSE;
        } else {
            return NOT_FOUND;
        }
    }
}
