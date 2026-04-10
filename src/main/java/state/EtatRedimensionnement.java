package state;

import command.CommandeRedimensionnerForme;
import controller.ControleurSouris;
import model.Cercle;
import model.Forme;
import model.Point;
import model.RectangleForme;

public class EtatRedimensionnement extends EtatInteractionAbstrait {
    private Forme formeSelectionnee;
    private Point ancrage;
    private double ancienneValeur1;
    private double ancienneValeur2;

    public EtatRedimensionnement(ControleurSouris controleur) {
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
        formeSelectionnee = getJeu().getFormesJoueur().trouverForme(p);

        if (formeSelectionnee instanceof Cercle cercle) {
            ancrage = cercle.getCentre().copy();
            ancienneValeur1 = cercle.getRayon();
            ancienneValeur2 = 0;
        } else if (formeSelectionnee instanceof RectangleForme rectangle) {
            Point coinSelectionne = rectangle.getCoinLePlusProche(p);
            ancrage = rectangle.getCoinOppose(coinSelectionne);
            ancienneValeur1 = rectangle.getLargeur();
            ancienneValeur2 = rectangle.getHauteur();
        }
    }

    @Override
    public void mouseDragged(ControleurSouris controleur, Point p) {
        if (formeSelectionnee != null && ancrage != null) {
            Point pBorne = bornerPoint(controleur, p);
            formeSelectionnee.redimensionner(ancrage, pBorne);

            double largeurZone = controleur.getPanneauJeu().getWidth();
            double hauteurZone = controleur.getPanneauJeu().getHeight();

            if (formeSelectionnee instanceof Cercle cercle) {
                cercle.limiterRayonDansZone(largeurZone, hauteurZone);
            } else if (formeSelectionnee instanceof RectangleForme rectangle) {
                rectangle.garderDansZone(largeurZone, hauteurZone);
            }

            getJeu().notifierObservateurs();
        }
    }

    @Override
    public void mouseReleased(ControleurSouris controleur, Point p) {
        if (formeSelectionnee != null && ancrage != null) {
            Point pBorne = bornerPoint(controleur, p);
            formeSelectionnee.redimensionner(ancrage, pBorne);

            double largeurZone = controleur.getPanneauJeu().getWidth();
            double hauteurZone = controleur.getPanneauJeu().getHeight();

            if (formeSelectionnee instanceof Cercle cercle) {
                cercle.limiterRayonDansZone(largeurZone, hauteurZone);
            } else if (formeSelectionnee instanceof RectangleForme rectangle) {
                rectangle.garderDansZone(largeurZone, hauteurZone);
            }
        }

        if (formeSelectionnee instanceof Cercle cercle) {
            getJeu().getHistorique().enregistrerCommandeExecutee(
                    new CommandeRedimensionnerForme(cercle, ancienneValeur1, 0, cercle.getRayon(), 0)
            );
        } else if (formeSelectionnee instanceof RectangleForme rectangle) {
            getJeu().getHistorique().enregistrerCommandeExecutee(
                    new CommandeRedimensionnerForme(
                            rectangle,
                            ancienneValeur1,
                            ancienneValeur2,
                            rectangle.getLargeur(),
                            rectangle.getHauteur()
                    )
            );
        }

        formeSelectionnee = null;
        ancrage = null;
    }

    @Override
    public void mouseClicked(ControleurSouris controleur, Point p) {
    }
}
