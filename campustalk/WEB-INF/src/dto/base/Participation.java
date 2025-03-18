
package dto.base;

public class Participation {
    private int idUtilisateur;
    private int idDiscussion;
    private boolean administrateur;

    public Participation(int idUtilisateur, int idDiscussion, boolean administrateur) {
        this.idUtilisateur = idUtilisateur;
        this.idDiscussion = idDiscussion;
        this.administrateur = administrateur;
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

    public boolean isAdministrateur() {
        return administrateur;
    }

    public void setAdministrateur(boolean administrateur) {
        this.administrateur = administrateur;
    }
}
