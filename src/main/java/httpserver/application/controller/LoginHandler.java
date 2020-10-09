package httpserver.application.controller;

import httpserver.core.protocol.HttpRequest;
import httpserver.core.protocol.HttpResponse;
import httpserver.framework.RequestHandler;
import httpserver.framework.Session;
import httpserver.framework.SessionManager;

public class LoginHandler implements RequestHandler {

    @Override
    public String handleRequest(HttpRequest request, HttpResponse response) {
        if (request.isGet()) {
            response.addParameter("message", "");
            return "login.html";
        }

        String username = request.getParameter("name");
        String password = request.getParameter("password");
        try {
            User user = UserManager.authenticate(username, password);
            Session session = SessionManager.createSession(request, response);
            session.addData("user", user);
            return "profile.html";
        } catch (Exception e) {
            response.addParameter("message", e.getMessage());
            return "login.html";
        }
    }
}
