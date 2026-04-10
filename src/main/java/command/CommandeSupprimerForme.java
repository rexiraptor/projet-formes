package command;

import model.Forme;
import model.Jeu;

public class CommandeSupprimerForme implements Commande {
    private final Jeu jeu;
    private final Forme forme;

    public CommandeSupprimerForme(Jeu jeu, Forme forme) {
        this.jeu = jeu;
        this.forme = forme;
    }

    @Override
    public void executer() {
        jeu.supprimerFormeJoueur(forme);
    }

    @Override
    public void annuler() {
        jeu.ajouterFormeJoueur(forme);
    }
}
