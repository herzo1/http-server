package httpserver.application.controller;

import httpserver.application.model.Profile;
import httpserver.application.model.User;
import httpserver.core.protocol.HttpRequest;
import httpserver.core.protocol.HttpResponse;
import httpserver.framework.RequestHandler;
import httpserver.framework.Session;
import httpserver.framework.SessionManager;

public class ProfileHandler implements RequestHandler {
    private static final String COLOR = "color";
    private static final String FOOD = "food";
    private static final String MUSIC = "music";

    private static final String FILE = "profile.html";


    @Override
    public String handleRequest(HttpRequest request, HttpResponse response) {
        Session session = SessionManager.getSession(request, response);
        if (session == null) {
            response.addParameter("message", "");
            return "login.html";
        }

        User user = (User) session.getData("user");
        Profile profile = (Profile) session.getData("profile");

        if (request.isPost()) {
            profile.setColor(request.getParameter(COLOR));
            profile.setFood(request.getParameter(FOOD));
            profile.setMusic(request.getParameter(MUSIC));
        }

        response.addParameter(COLOR, profile.getColor());
        response.addParameter(FOOD, profile.getFood());
        response.addParameter(MUSIC, profile.getMusic());
        return FILE;
    }
}
