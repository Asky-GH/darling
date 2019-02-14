package kz.epam.darling.controller.command;

import kz.epam.darling.model.Message;
import kz.epam.darling.model.dao.MessageDAO;
import kz.epam.darling.util.JsonSender;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class RefreshCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger(RefreshCommand.class.getName());


    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) {

    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) {
        int senderId = Integer.parseInt(request.getParameter("senderId"));
        int receiverId = Integer.parseInt(request.getParameter("receiverId"));
        List<Message> messages = MessageDAO.findNew(senderId, receiverId);
        if (messages.size() > 0) {
            for (Message message : messages) {
                message.setStatusId(2);
                MessageDAO.update(message);
            }
            try {
                JsonSender.send(response, messages);
            } catch (IOException e) {
                LOGGER.error(e);
            }
        }
    }
}
