package kz.epam.darling.controller.command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public interface Command {
    default void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException,
                                                                                            InterruptedException,
                                                                                            SQLException, ClassNotFoundException {
        String method = request.getMethod();
        if (method.equals("GET")) {
            doGet(request, response);
        } else {
            doPost(request, response);
        }
    }

    void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException;
    void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException,
                                                                                    InterruptedException, SQLException,
                                                                                    ClassNotFoundException;
}
