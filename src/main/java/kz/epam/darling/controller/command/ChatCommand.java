package kz.epam.darling.controller.command;

import kz.epam.darling.model.Language;
import kz.epam.darling.model.Message;
import kz.epam.darling.model.User;
import kz.epam.darling.model.dao.MessageDAO;
import kz.epam.darling.model.dao.UserDAO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class ChatCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger(ChatCommand.class.getName());


    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        Language language = (Language) request.getAttribute("language");
        User sender = (User) request.getSession(false).getAttribute("principal");
        int receiverId = Integer.parseInt(request.getParameter("id"));
        User receiver = UserDAO.findById(receiverId, language.getId());
        List<Message> messages = MessageDAO.findByParticipants(sender.getId(), receiverId);
        for (Message message : messages) {
            if (message.getReceiverId() == sender.getId()) {
                message.setStatusId(2);
                MessageDAO.update(message);
            }
        }
        request.setAttribute("messages", messages);
        request.setAttribute("receiver", receiver);
        try {
            request.getRequestDispatcher("jsp/chat.jsp").forward(request, response);
        } catch (ServletException | IOException e) {
            LOGGER.error(e);
        }
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) {
        User sender = (User) request.getSession(false).getAttribute("principal");
        int receiverId = Integer.parseInt(request.getParameter("id"));
        String text = request.getParameter("text");
        Message message = new Message();
        message.setText(text);
        message.setSenderId(sender.getId());
        message.setReceiverId(receiverId);
        MessageDAO.create(message);
        try {
            response.sendRedirect(request.getContextPath() + "/chat?id=" + receiverId);
        } catch (IOException e) {
            LOGGER.error(e);
        }
    }
}
