package kz.epam.darling.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface Command {
    default void execute(HttpServletRequest request, HttpServletResponse response) {
        String method = request.getMethod();
        if (method.equals("GET")) {
            doGet(request, response);
        } else {
            doPost(request, response);
        }
    }

    void doGet(HttpServletRequest request, HttpServletResponse response);
    void doPost(HttpServletRequest request, HttpServletResponse response);
}
