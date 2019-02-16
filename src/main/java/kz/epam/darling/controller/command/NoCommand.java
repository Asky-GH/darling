package kz.epam.darling.controller.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class NoCommand implements Command {
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        try {
            response.getWriter().println("From 'doGet()' method of 'NoCommand' class...");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) {
        try {
            response.getWriter().println("From 'doPost()' method of 'NoCommand' class...");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
