package kz.epam.darling.controller.command;

import kz.epam.darling.model.Language;
import kz.epam.darling.model.Message;
import kz.epam.darling.model.Profile;
import kz.epam.darling.model.User;
import kz.epam.darling.model.dao.MessageDAO;
import kz.epam.darling.model.dao.ProfileDAO;
import kz.epam.darling.model.dao.UserDAO;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class MessagesCommand implements Command {
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        Language language = (Language) request.getAttribute("language");
        Map<User, Message> dialogs = new LinkedHashMap<>();
        User receiver = (User) request.getSession(false).getAttribute("principal");
        List<Message> messages = MessageDAO.findByChat(receiver.getId());
        for (Message message : messages) {
            User user = UserDAO.findById(message.getSenderId(), language.getId());
            Profile profile = ProfileDAO.findByUserId(user.getId(), language.getId());
            user.setProfile(profile);
            dialogs.put(user, message);
        }
        request.setAttribute("dialogs", dialogs);
        try {
            request.getRequestDispatcher("jsp/messages.jsp").forward(request, response);
        } catch (ServletException | IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) {

    }
}
