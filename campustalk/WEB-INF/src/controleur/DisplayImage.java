package controleur;

import dao.MessageDao;
import dao.UtilisateurDao;
import dto.base.Message;
import dto.base.Utilisateur;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

@WebServlet("/display_image")
public class DisplayImage extends HttpServlet {
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String paramId = req.getParameter("idImage");
        String profil = req.getParameter("profil");
        if (paramId == null) {
            sendBlackImage(res);
            return;
        }

        if (profil.equals("false")) {
            int idMessage;
            try {
                idMessage = Integer.parseInt(paramId);
            } catch (NumberFormatException e) {
                sendBlackImage(res);
                return;
            }
            MessageDao messageDao = new MessageDao();
            Message message = messageDao.findMessageById(idMessage);
            if (message == null || message.getContenuImage() == null) {
                sendBlackImage(res);
                return;
            }
            res.setContentType("image/png");
            res.getOutputStream().write(message.getContenuImage());
        } else if (profil.equals("true")) {
            int idUtilisateur;
            try {
                idUtilisateur = Integer.parseInt(paramId);
            } catch (NumberFormatException e) {
                sendBlackImage(res);
                return;
            }
            UtilisateurDao utilisateurDao = new UtilisateurDao();
            Utilisateur utilisateur = utilisateurDao.find(idUtilisateur);
            if (utilisateur == null || utilisateur.getPhotoProfil() == null) {
                sendBlackImage(res);
                return;
            }

            res.setContentType("image/png");
            res.getOutputStream().write(utilisateur.getPhotoProfil());
        }
    }

    private void sendBlackImage(HttpServletResponse res) throws IOException {
        BufferedImage blackImage = new BufferedImage(100, 100, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = blackImage.createGraphics();
        g2d.setColor(Color.BLACK);
        g2d.fillRect(0, 0, 100, 100);
        g2d.dispose();

        res.setContentType("image/png");
        ImageIO.write(blackImage, "png", res.getOutputStream());
    }
}