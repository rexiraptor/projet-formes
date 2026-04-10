package state;

import command.CommandeSupprimerForme;
import controller.ControleurSouris;
import model.Forme;
import model.Point;

public class EtatSuppression extends EtatInteractionAbstrait {
    public EtatSuppression(ControleurSouris controleur) {
        super(controleur);
    }

    @Override
    public void mousePressed(ControleurSouris controleur, Point p) {
    }

    @Override
    public void mouseDragged(ControleurSouris controleur, Point p) {
    }

    @Override
    public void mouseReleased(ControleurSouris controleur, Point p) {
    }

    @Override
    public void mouseClicked(ControleurSouris controleur, Point p) {
        Forme forme = getJeu().getFormesJoueur().trouverForme(p);
        if (forme != null) {
            getJeu().getHistorique().executerCommande(new CommandeSupprimerForme(getJeu(), forme));
        }
    }
}
