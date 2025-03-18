package dto.base;

public class Aime {
    int idUtilisateur;
    int idMessage;

    public Aime(int idUtilisateur, int idMessage) {
        this.idUtilisateur = idUtilisateur;
        this.idMessage = idMessage;
    }

    public int getIdUtilisateur() {
        return idUtilisateur;
    }

    public void setIdUtilisateur(int idUtilisateur) {
        this.idUtilisateur = idUtilisateur;
    }

    public int getIdMessage() {
        return idMessage;
    }

    public void setIdMessage(int idMessage) {
        this.idMessage = idMessage;
    }
}
