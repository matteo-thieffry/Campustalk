package dto;

import dto.base.Utilisateur;

public class UtilisateurParticipation {
    Utilisateur utilisateur;
    boolean participe;

    public UtilisateurParticipation(Utilisateur utilisateur, boolean participe) {
        this.utilisateur = utilisateur;
        this.participe = participe;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public int getId() {
        return utilisateur.getId();
    }

    public String getPseudo() {
        return utilisateur.getPseudo();
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    public boolean isParticipe() {
        return participe;
    }

    public void setParticipe(boolean participe) {
        this.participe = participe;
    }
}
