package kz.epam.darling;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class NoCommand implements Command {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.getWriter().println("From 'execute()' method of 'NoCommand' class...");
    }
}
