package state;

import java.awt.Color;

import command.CommandeAjouterForme;
import controller.ControleurSouris;
import model.Cercle;
import model.Point;

public class EtatCreationCercle extends EtatInteractionAbstrait {
    private Point depart;

    public EtatCreationCercle(ControleurSouris controleur) {
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
            double rayon = depart.distanceTo(pBorne);

            Cercle preview = new Cercle(depart.copy(), rayon, new Color(0, 0, 255, 120));

            double largeurZone = controleur.getPanneauJeu().getWidth();
            double hauteurZone = controleur.getPanneauJeu().getHeight();
            preview.limiterRayonDansZone(largeurZone, hauteurZone);

            controleur.getPanneauJeu().setFormePreview(preview);
        }
    }

    @Override
    public void mouseReleased(ControleurSouris controleur, Point p) {
        if (depart != null) {
            Point pBorne = bornerPoint(controleur, p);
            double rayon = depart.distanceTo(pBorne);

            if (rayon > 2) {
                Cercle cercle = new Cercle(depart.copy(), rayon, Color.BLUE);

                double largeurZone = controleur.getPanneauJeu().getWidth();
                double hauteurZone = controleur.getPanneauJeu().getHeight();
                cercle.limiterRayonDansZone(largeurZone, hauteurZone);

                getJeu().getHistorique().executerCommande(
                        new CommandeAjouterForme(getJeu(), cercle)
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
