package kz.epam.darling.command;

import kz.epam.darling.model.Language;
import kz.epam.darling.model.Message;
import kz.epam.darling.model.Profile;
import kz.epam.darling.model.User;
import kz.epam.darling.dao.MessageDAO;
import kz.epam.darling.dao.ProfileDAO;
import kz.epam.darling.dao.UserDAO;

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
            boolean same = false;
            for (Message msg : dialogs.values()) {
                if (msg.getReceiverId() == message.getSenderId() && msg.getSenderId() == message.getReceiverId()) {
                    same = true;
                }
            }
            if (! same) {
                int id = message.getSenderId() == receiver.getId() ? message.getReceiverId() : message.getSenderId();
                User user = UserDAO.findById(id, language.getId());
                Profile profile = ProfileDAO.findByUserId(user.getId(), language.getId());
                user.setProfile(profile);
                dialogs.put(user, message);
            }
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
