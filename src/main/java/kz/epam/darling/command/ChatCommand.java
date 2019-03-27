package kz.epam.darling.command;

import kz.epam.darling.constant.Attribute;
import kz.epam.darling.constant.Constant;
import kz.epam.darling.constant.Parameter;
import kz.epam.darling.constant.View;
import kz.epam.darling.model.Language;
import kz.epam.darling.model.Message;
import kz.epam.darling.model.User;
import kz.epam.darling.dao.MessageDAO;
import kz.epam.darling.dao.UserDAO;
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
        try {
            int receiverId;
            try {
                receiverId = Integer.parseInt(request.getParameter(Parameter.ID));
            } catch (NumberFormatException e) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
                return;
            }
            Language language = (Language) request.getAttribute(Attribute.LANGUAGE);
            User receiver = UserDAO.findById(receiverId, language.getId());
            User sender = (User) request.getSession().getAttribute(Attribute.PRINCIPAL);
            List<Message> messages = MessageDAO.findByParticipants(sender.getId(), receiverId);
            for (Message message : messages) {
                if (message.getStatusId() == Constant.UNREAD_MESSAGE_STATUS_ID && message.getReceiverId() == sender.getId()) {
                    message.setStatusId(Constant.READ_MESSAGE_STATUS_ID);
                    MessageDAO.update(message);
                }
            }
            request.setAttribute(Attribute.MESSAGES, messages);
            request.setAttribute(Attribute.RECEIVER, receiver);
            request.getRequestDispatcher(View.CHAT).forward(request, response);
        } catch (ServletException | IOException e) {
            LOGGER.error(e);
        }
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) {
        User sender = (User) request.getSession().getAttribute(Attribute.PRINCIPAL);
        int receiverId = Integer.parseInt(request.getParameter(Parameter.ID));
        String text = request.getParameter(Parameter.TEXT);
        if (text.trim().isEmpty()) {
            request.setAttribute(Attribute.TEXT_ERROR, "key.chatPageEmptyTextError");
        } else if (text.length() > Constant.MESSAGE_MAX_LENGTH) {
            request.setAttribute(Attribute.TEXT_ERROR, "key.chatPageTooLongTextError");
            request.setAttribute(Attribute.TEXT, text);
        } else {
            Message message = new Message();
            message.setText(text);
            message.setSenderId(sender.getId());
            message.setReceiverId(receiverId);
            MessageDAO.create(message);
            try {
                response.sendRedirect(request.getContextPath() + "/chat?id=" + receiverId);
                return;
            } catch (IOException e) {
                LOGGER.error(e);
            }
        }
        doGet(request, response);
    }
}
