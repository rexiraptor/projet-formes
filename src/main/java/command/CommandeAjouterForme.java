package command;

import model.Forme;
import model.Jeu;

public class CommandeAjouterForme implements Commande {
    private final Jeu jeu;
    private final Forme forme;

    public CommandeAjouterForme(Jeu jeu, Forme forme) {
        this.jeu = jeu;
        this.forme = forme;
    }

    @Override
    public void executer() {
        jeu.ajouterFormeJoueur(forme);
    }

    @Override
    public void annuler() {
        jeu.supprimerFormeJoueur(forme);
    }
}
