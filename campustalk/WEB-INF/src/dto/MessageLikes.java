package dto;

import dto.base.Message;
import dto.base.Utilisateur;

public class MessageLikes {
    private Message message;
    private Utilisateur auteur;
    private int nbLikes;
    private boolean liked;

    public MessageLikes(Message message, Utilisateur auteur, int nbLikes, boolean liked) {
        this.message = message;
        this.auteur = auteur;
        this.nbLikes = nbLikes;
        this.liked = liked;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public Utilisateur getAuteur() {
        return auteur;
    }

    public void setAuteur(Utilisateur auteur) {
        this.auteur = auteur;
    }

    public int getNbLikes() {
        return nbLikes;
    }

    public void setNbLikes(int nbLikes) {
        this.nbLikes = nbLikes;
    }

    public boolean isLiked() {
        return liked;
    }

    public void setLiked(boolean liked) {
        this.liked = liked;
    }
}
