package controleur;

import dao.MessageDao;
import dto.Data;
import dto.base.Message;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import org.apache.commons.text.StringEscapeUtils;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@WebServlet("/send_message")
@MultipartConfig(maxFileSize = 1024 * 1024 * 1000)
public class SendMessage extends HttpServlet {
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String texte_message = StringEscapeUtils.escapeHtml4(req.getParameter("message"));
        String paramIdDiscussion = req.getParameter("idDiscussion");
        String paramDays = StringEscapeUtils.escapeHtml4(req.getParameter("nbDays"));
        String paramHours = StringEscapeUtils.escapeHtml4(req.getParameter("nbHours"));
        String paramMinutes = StringEscapeUtils.escapeHtml4(req.getParameter("nbMinutes"));
        Part filePart = req.getPart("image");

        int idDiscussion;
        try {
            idDiscussion = Integer.parseInt(paramIdDiscussion);
        } catch (NumberFormatException e) {
            res.sendRedirect("home");
            return;
        }
        Timestamp expiration = this.getExpirationTimestamp(new String[]{paramDays, paramHours, paramMinutes});

        byte[] imageData = null;
        if (filePart != null && filePart.getSize() > 0) {
            InputStream inputStream = filePart.getInputStream();
            imageData = inputStream.readAllBytes();
        }

        HttpSession session = req.getSession();
        Data data = (Data) session.getAttribute("userData");
        Message message = new Message(expiration, texte_message, imageData, data.getUtilisateur().getId(), idDiscussion);
        MessageDao messageDao = new MessageDao();
        messageDao.sendMessage(message);

        res.sendRedirect("home?idDisc=" + idDiscussion);
    }

    private Timestamp getExpirationTimestamp(String[] strValues) {
        Integer nbDays = this.convertValues(strValues[0]);
        Integer nbHours = this.convertValues(strValues[1]);
        Integer nbMinutes = this.convertValues(strValues[2]);

        if (nbDays == null && nbHours == null && nbMinutes == null) {
            return null;
        }
        LocalDateTime expirationDate = LocalDateTime.now();
        if (nbDays != null) {
            expirationDate = expirationDate.plusDays(nbDays);
        }
        if (nbHours != null) {
            expirationDate = expirationDate.plusHours(nbHours);
        }
        if (nbMinutes != null) {
            expirationDate = expirationDate.plusMinutes(nbMinutes);
        }
        return Timestamp.valueOf(expirationDate);
    }

    private Integer convertValues(String strValue) {
        if (strValue == null || strValue.isEmpty()) {
            return null;
        }
        try {
            return Integer.parseInt(strValue);
        } catch (NumberFormatException e) {
            return null;
        }
    }
}

