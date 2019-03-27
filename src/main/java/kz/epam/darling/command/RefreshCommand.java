package kz.epam.darling.command;

import kz.epam.darling.constant.Constant;
import kz.epam.darling.constant.Parameter;
import kz.epam.darling.dao.MessageDAO;
import kz.epam.darling.model.Message;
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
        try {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        } catch (IOException e) {
            LOGGER.error(e);
        }
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) {
        int senderId = Integer.parseInt(request.getParameter(Parameter.SENDER_ID));
        int receiverId = Integer.parseInt(request.getParameter(Parameter.RECEIVER_ID));
        List<Message> messages = MessageDAO.findNew(senderId, receiverId);
        if (messages.size() > 0) {
            for (Message message : messages) {
                message.setStatusId(Constant.READ_MESSAGE_STATUS_ID);
                MessageDAO.update(message);
            }
            JsonSender.send(response, messages);
        }
    }
}
