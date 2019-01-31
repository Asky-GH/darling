package kz.epam.darling.controller.command;

import kz.epam.darling.model.Image;
import kz.epam.darling.model.User;
import kz.epam.darling.model.dao.ImageDAO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Blob;
import java.sql.SQLException;

public class ImageCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger(ImageCommand.class.getName());
    private static final long MAX_FILE_SIZE = 1_048_576;


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
        User user = (User) request.getSession(false).getAttribute("user");
        try {
            Part part = request.getPart("image");
            if (part.getSize() <= 0) {
                request.setAttribute("errorMessage", "Choose a file!");
                request.getRequestDispatcher("jsp/profile.jsp").forward(request, response);
            } else {
                if (part.getSize() > MAX_FILE_SIZE) {
                    request.setAttribute("errorMessage", "Maximum file size is 1Mb!");
                    request.getRequestDispatcher("jsp/profile.jsp").forward(request, response);
                } else {
                    Image image = user.getProfile().getImage();
                    image.setUrl(request.getRequestURL() + "?id=" + image.getId());
//                    image.setUser_id(user.getId());
                    ImageDAO.update(image, part);
                    user.getProfile().setImage(image);
                    response.sendRedirect(request.getContextPath() + "/profile");
                }
            }
        } catch (IOException | ServletException e) {
            LOGGER.error(e);
        }
    }
}
