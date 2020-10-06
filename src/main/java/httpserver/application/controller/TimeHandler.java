package httpserver.application.controller;

import httpserver.core.protocol.HttpRequest;
import httpserver.core.protocol.HttpResponse;
import httpserver.framework.RequestHandler;

import java.time.LocalDateTime;

public class TimeHandler implements RequestHandler {

    @Override
    public String handleRequest(HttpRequest request, HttpResponse response) {
        LocalDateTime dateTime = LocalDateTime.now();
        response.addParameter("time", dateTime.toString());
        return "time.html";
    }
}
