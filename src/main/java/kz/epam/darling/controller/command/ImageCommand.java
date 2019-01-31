package kz.epam.darling.controller.command;

import kz.epam.darling.model.dao.ImageDAO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Blob;
import java.sql.SQLException;

public class ImageCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger(ImageCommand.class.getName());


    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        int image_id = Integer.parseInt(request.getParameter("id"));
        Blob image = ImageDAO.findById(image_id);
        try {
            byte[] bytes = image.getBytes(1, (int) image.length());
            response.setContentType("image/gif");
            OutputStream os = response.getOutputStream();
            os.write(bytes);
            os.flush();
            os.close();
        } catch (SQLException | IOException e) {
            LOGGER.error(e);
        }
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) {

    }
}
