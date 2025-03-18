
package dto.base;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Arrays;

public class Message {
    private int id;
    private Timestamp dateHeure;
    private Timestamp expiration;
    private String contenuTexte;
    private byte[] contenuImage;
    private int idUtilisateur;
    private int idDiscussion;

    public Message(int id, Timestamp dateHeure, Timestamp expiration, String contenuTexte, byte[] contenuImage, int idUtilisateur, int idDiscussion) {
        this.id = id;
        this.dateHeure = dateHeure;
        this.expiration = expiration;
        this.contenuTexte = contenuTexte;
        this.contenuImage = contenuImage;
        this.idUtilisateur = idUtilisateur;
        this.idDiscussion = idDiscussion;
    }

    public Message(int id, Timestamp dateHeure, Timestamp expiration, String contenuTexte, int idUtilisateur, int idDiscussion) {
        this(id, dateHeure, expiration, contenuTexte, null, idUtilisateur, idDiscussion);
    }

    public Message(Timestamp expiration, String contenuTexte, byte[] contenuImage, int idUtilisateur, int idDiscussion) {
        this(-1, Timestamp.valueOf(LocalDateTime.now()), expiration, contenuTexte, contenuImage, idUtilisateur, idDiscussion);
    }

    public Message(Timestamp expiration, String contenuTexte, int idUtilisateur, int idDiscussion) {
        this(expiration, contenuTexte, null, idUtilisateur, idDiscussion);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Timestamp getDateHeure() {
        return dateHeure;
    }

    public void setDateHeure(Timestamp dateHeure) {
        this.dateHeure = dateHeure;
    }

    public String getContenuTexte() {
        return contenuTexte;
    }

    public void setContenuTexte(String contenuTexte) {
        this.contenuTexte = contenuTexte;
    }

    public int getIdUtilisateur() {
        return idUtilisateur;
    }

    public void setIdUtilisateur(int idUtilisateur) {
        this.idUtilisateur = idUtilisateur;
    }

    public int getIdDiscussion() {
        return idDiscussion;
    }

    public void setIdDiscussion(int idDiscussion) {
        this.idDiscussion = idDiscussion;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", dateHeure=" + dateHeure +
                ", contenuTexte='" + contenuTexte + '\'' +
                ", contenuImage=" + Arrays.toString(contenuImage) +
                ", idUtilisateur=" + idUtilisateur +
                ", idDiscussion=" + idDiscussion +
                '}';
    }

    public byte[] getContenuImage() {
        return contenuImage;
    }

    public void setContenuImage(byte[] contenuImage) {
        this.contenuImage = contenuImage;
    }

    public Timestamp getExpiration() {
        return expiration;
    }

    public void setExpiration(Timestamp expiration) {
        this.expiration = expiration;
    }
}
