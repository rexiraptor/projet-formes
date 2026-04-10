package state;

import java.awt.Color;

import command.CommandeAjouterForme;
import controller.ControleurSouris;
import model.Point;
import model.RectangleForme;

public class EtatCreationRectangle extends EtatInteractionAbstrait {
    private Point depart;

    public EtatCreationRectangle(ControleurSouris controleur) {
        super(controleur);
    }

    private Point bornerPoint(ControleurSouris controleur, Point p) {
        double largeurZone = controleur.getPanneauJeu().getWidth();
        double hauteurZone = controleur.getPanneauJeu().getHeight();

        double x = Math.max(0, Math.min(p.getX(), largeurZone));
        double y = Math.max(0, Math.min(p.getY(), hauteurZone));

        return new Point(x, y);
    }

    @Override
    public void mousePressed(ControleurSouris controleur, Point p) {
        depart = bornerPoint(controleur, p);
    }

    @Override
    public void mouseDragged(ControleurSouris controleur, Point p) {
        if (depart != null) {
            Point pBorne = bornerPoint(controleur, p);

            Point origine = new Point(
                    Math.min(depart.getX(), pBorne.getX()),
                    Math.min(depart.getY(), pBorne.getY())
            );

            double largeur = Math.abs(pBorne.getX() - depart.getX());
            double hauteur = Math.abs(pBorne.getY() - depart.getY());

            RectangleForme preview = new RectangleForme(
                    origine,
                    largeur,
                    hauteur,
                    new Color(0, 0, 255, 120)
            );

            controleur.getPanneauJeu().setFormePreview(preview);
        }
    }

    @Override
    public void mouseReleased(ControleurSouris controleur, Point p) {
        if (depart != null) {
            Point pBorne = bornerPoint(controleur, p);

            Point origine = new Point(
                    Math.min(depart.getX(), pBorne.getX()),
                    Math.min(depart.getY(), pBorne.getY())
            );

            double largeur = Math.abs(pBorne.getX() - depart.getX());
            double hauteur = Math.abs(pBorne.getY() - depart.getY());

            if (largeur > 2 && hauteur > 2) {
                RectangleForme rectangle = new RectangleForme(origine, largeur, hauteur, Color.BLUE);
                getJeu().getHistorique().executerCommande(
                        new CommandeAjouterForme(getJeu(), rectangle)
                );
            }

            controleur.getPanneauJeu().clearFormePreview();
            depart = null;
        }
    }

    @Override
    public void mouseClicked(ControleurSouris controleur, Point p) {
    }
}
