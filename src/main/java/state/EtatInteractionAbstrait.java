package state;

import controller.ControleurSouris;
import model.Jeu;

public abstract class EtatInteractionAbstrait implements EtatInteraction {
    protected ControleurSouris controleur;

    public EtatInteractionAbstrait(ControleurSouris controleur) {
        this.controleur = controleur;
    }

    protected Jeu getJeu() {
        return controleur.getJeu();
    }
}
