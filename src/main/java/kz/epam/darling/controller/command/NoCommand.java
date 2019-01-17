package kz.epam.darling.controller.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

// TODO think about handling not existing routes
public class NoCommand implements Command {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.getWriter().println("From 'execute()' method of 'NoCommand' class...");
    }
}
