package kz.epam.darling.controller.command.admin;

import kz.epam.darling.controller.command.Command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AdminCommand implements Command {
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        try {
            request.getRequestDispatcher("jsp/admin/admin.jsp").forward(request, response);
        } catch (ServletException | IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) {

    }
}
