package state;

import command.CommandeDeplacerForme;
import controller.ControleurSouris;
import model.Cercle;
import model.Forme;
import model.Point;
import model.RectangleForme;

public class EtatDeplacement extends EtatInteractionAbstrait {
    private Forme formeSelectionnee;
    private Point precedent;
    private Point positionInitiale;

    public EtatDeplacement(ControleurSouris controleur) {
        super(controleur);
    }

    @Override
    public void mousePressed(ControleurSouris controleur, Point p) {
        formeSelectionnee = getJeu().getFormesJoueur().trouverForme(p);
        precedent = p;
        positionInitiale = extrairePosition(formeSelectionnee);
    }

    @Override
    public void mouseDragged(ControleurSouris controleur, Point p) {
        if (formeSelectionnee != null && precedent != null) {
            double dx = p.getX() - precedent.getX();
            double dy = p.getY() - precedent.getY();

            formeSelectionnee.deplacer(dx, dy);

            double largeurZone = controleur.getPanneauJeu().getWidth();
            double hauteurZone = controleur.getPanneauJeu().getHeight();

            if (formeSelectionnee instanceof Cercle cercle) {
                cercle.garderDansZone(largeurZone, hauteurZone);
            } else if (formeSelectionnee instanceof RectangleForme rectangle) {
                rectangle.garderDansZone(largeurZone, hauteurZone);
            }

            precedent = p;
            getJeu().notifierObservateurs();
        }
    }

    @Override
    public void mouseReleased(ControleurSouris controleur, Point p) {
        if (formeSelectionnee != null && positionInitiale != null) {
            Point nouvellePosition = extrairePosition(formeSelectionnee);
            if (nouvellePosition != null) {
                getJeu().getHistorique().enregistrerCommandeExecutee(
                        new CommandeDeplacerForme(formeSelectionnee, positionInitiale, nouvellePosition)
                );
            }
        }

        formeSelectionnee = null;
        precedent = null;
        positionInitiale = null;
    }

    @Override
    public void mouseClicked(ControleurSouris controleur, Point p) {
    }

    private Point extrairePosition(Forme forme) {
        if (forme instanceof Cercle cercle) {
            return cercle.getCentre().copy();
        }
        if (forme instanceof RectangleForme rectangle) {
            return rectangle.getOrigine().copy();
        }
        return null;
    }
}
