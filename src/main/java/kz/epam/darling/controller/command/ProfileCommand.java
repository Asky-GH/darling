package kz.epam.darling.controller.command;

import kz.epam.darling.model.Info;
import kz.epam.darling.model.User;
import kz.epam.darling.model.dao.CountryDAO;
import kz.epam.darling.model.dao.GenderDAO;
import kz.epam.darling.model.dao.InfoDAO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;

public class ProfileCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger(ProfileCommand.class.getName());


    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        try {
            request.getRequestDispatcher("jsp/profile.jsp").forward(request, response);
        } catch (ServletException | IOException e) {
            LOGGER.error(e);
        }
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) {
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String gender = request.getParameter("gender");
        String birthday = request.getParameter("birthday");
        String countryName = request.getParameter("countryName");
        try {
            if (firstName.isEmpty() || lastName.isEmpty() || gender == null || birthday.isEmpty() || countryName.isEmpty()) {
                request.setAttribute("errorMessage", "All fields are required!");
                request.getRequestDispatcher("jsp/profile.jsp").forward(request, response);
            } else {
                User user = (User) request.getSession(false).getAttribute("user");
                Info info = new Info();
                info.setFirstName(firstName);
                info.setLastName(lastName);
                info.setGender(GenderDAO.findByName(gender));
                info.setBirthday(Date.valueOf(birthday));
                info.setCountry(CountryDAO.findByName(countryName));
                info.setUserId(user.getId());
                InfoDAO.create(info);
                user.setInfo(info);
                response.sendRedirect(request.getContextPath() + "/profile");
            }
        } catch (ServletException | IOException e) {
            LOGGER.error(e);
        }
    }
}
