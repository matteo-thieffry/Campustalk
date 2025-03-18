package controleur;

import dao.UtilisateurDao;
import dto.Data;
import dto.base.Utilisateur;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import org.apache.commons.text.StringEscapeUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

@WebServlet("/modify_user")
@MultipartConfig(maxFileSize = 1024 * 1024 * 1000)
public class ModifyUser extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Data data = (Data) session.getAttribute("userData");

        if (data == null) {
            res.sendRedirect("login.html");
            return;
        }

        Utilisateur currentUtilisateur = data.getUtilisateur();
        Utilisateur modifyUtilisateur = this.getToUpdateUser(req);

        mergeUsers(modifyUtilisateur, currentUtilisateur);
        boolean modified = new UtilisateurDao().modify(currentUtilisateur.getId(), modifyUtilisateur);

        if (modified) {
            Utilisateur current = data.getUtilisateur();
            if (modifyUtilisateur.getPseudo() != null) current.setPseudo(modifyUtilisateur.getPseudo());
            if (modifyUtilisateur.getMail() != null) current.setMail(modifyUtilisateur.getMail());
            if (modifyUtilisateur.getPhotoProfil() != null) current.setPhotoProfil(modifyUtilisateur.getPhotoProfil());
            session.setAttribute("userData", data);
        }

        res.sendRedirect("home");
    }

    private Utilisateur getToUpdateUser(HttpServletRequest req) throws IOException, ServletException {
        Utilisateur utilisateur = new Utilisateur();
        String mail = StringEscapeUtils.escapeHtml4(req.getParameter("mail"));
        String pseudo = StringEscapeUtils.escapeHtml4(req.getParameter("pseudo"));
        Part filePart = req.getPart("photo_profil");

        if (pseudo != null && !pseudo.isBlank()) {
            utilisateur.setPseudo(pseudo);
        }
        if (mail != null && !mail.isBlank()) {
            utilisateur.setMail(mail);
        }
        if (isImagePart(filePart)) {
            utilisateur.setPhotoProfil(extractImageBytes(filePart));
        }

        return utilisateur;
    }

    private boolean isImagePart(Part filePart) {
        return filePart != null && filePart.getSize() > 0 && filePart.getContentType() != null && filePart.getContentType().startsWith("image/");
    }

    private byte[] extractImageBytes(Part filePart) throws IOException {
        try (InputStream inputStream = filePart.getInputStream()) {
            BufferedImage image = ImageIO.read(inputStream);
            if (image != null) {
                try (InputStream imgStream = filePart.getInputStream()) {
                    return imgStream.readAllBytes();
                }
            }
        }
        return null;
    }

    private void mergeUsers(Utilisateur toUpdate, Utilisateur current) {
        if (toUpdate.getPseudo() == null) toUpdate.setPseudo(current.getPseudo());
        if (toUpdate.getMail() == null) toUpdate.setMail(current.getMail());
        if (toUpdate.getPhotoProfil() == null) toUpdate.setPhotoProfil(current.getPhotoProfil());
    }
}
