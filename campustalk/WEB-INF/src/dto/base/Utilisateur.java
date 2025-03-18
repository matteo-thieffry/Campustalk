package dto.base;

import java.util.Objects;

public class Utilisateur {
    int id;
    String pseudo;
    String mail;
    String password;
    byte[] photoProfil;

    public Utilisateur(int id, String pseudo, String mail, String password, byte[] photoProfil) {
        this.id = id;
        this.pseudo = pseudo;
        this.mail = mail;
        this.password = password;
        this.photoProfil = photoProfil;
    }

    public Utilisateur(String pseudo, String mail, String password, byte[] photoProfil) {
        this(-1, pseudo, mail, password, photoProfil);
    }

    public Utilisateur(String pseudo, String mail, String password) {
        this(-1, pseudo, mail, password, null);
    }

    public Utilisateur(int id, String pseudo, String mail, byte[] photoProfil) {
        this(id, pseudo, mail, null, photoProfil);
    }

    public Utilisateur(int id, String pseudo, String mail) {
        this(id, pseudo, mail, null, null);
    }

    public Utilisateur(String pseudo, String mail, byte[] photoProfil) {
        this(-1, pseudo, mail, photoProfil);
    }

    public Utilisateur() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPassword() {
        return this.password;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Utilisateur that = (Utilisateur) o;
        return getId() == that.getId() && Objects.equals(getPseudo(), that.getPseudo()) && Objects.equals(getMail(), that.getMail()) && Objects.equals(getPassword(), that.getPassword());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getPseudo(), getMail(), getPassword());
    }

    public String toString() {
        return this.getId() + ", " + this.getPseudo() + ", " + this.getMail();
    }

    public byte[] getPhotoProfil() {
        return photoProfil;
    }

    public void setPhotoProfil(byte[] photoProfil) {
        this.photoProfil = photoProfil;
    }
}
