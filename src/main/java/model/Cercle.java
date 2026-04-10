package model;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;

public class Cercle extends FormeAbstraite {
    private Point centre;
    private double rayon;

    public Cercle(Point centre, double rayon, Color couleur) {
        super(couleur);
        this.centre = centre;
        this.rayon = rayon;
    }

    @Override
    public double aire() {
        return Math.PI * rayon * rayon;
    }

    @Override
    public boolean contient(Point p) {
        return centre.distanceTo(p) <= rayon;
    }

    @Override
    public boolean intersecte(Forme autre) {
        return autre.intersecteCercle(this);
    }

    @Override
    public boolean intersecteCercle(Cercle autreCercle) {
        return centre.distanceTo(autreCercle.getCentre()) <= (rayon + autreCercle.getRayon());
    }

    @Override
    public boolean intersecteRectangle(RectangleForme rectangle) {
        double plusProcheX = Math.max(
                rectangle.getOrigine().getX(),
                Math.min(centre.getX(), rectangle.getOrigine().getX() + rectangle.getLargeur())
        );

        double plusProcheY = Math.max(
                rectangle.getOrigine().getY(),
                Math.min(centre.getY(), rectangle.getOrigine().getY() + rectangle.getHauteur())
        );

        Point pointLePlusProche = new Point(plusProcheX, plusProcheY);
        return centre.distanceTo(pointLePlusProche) <= rayon;
    }

    @Override
    public void deplacer(double dx, double dy) {
        centre.translate(dx, dy);
    }

    @Override
    public void redimensionner(Point ancrage, Point courant) {
        this.rayon = Math.max(1, ancrage.distanceTo(courant));
    }

    @Override
    public void dessiner(Graphics2D g) {
        double x = centre.getX() - rayon;
        double y = centre.getY() - rayon;
        double diametre = 2 * rayon;

        Ellipse2D cercle = new Ellipse2D.Double(x, y, diametre, diametre);

        g.setColor(getCouleur());
        g.fill(cercle);

        g.setColor(Color.BLACK);
        g.draw(cercle);
    }

    @Override
    public Area toArea() {
        double x = centre.getX() - rayon;
        double y = centre.getY() - rayon;
        double diametre = 2 * rayon;
        return new Area(new Ellipse2D.Double(x, y, diametre, diametre));
    }

    public void garderDansZone(double largeurZone, double hauteurZone) {
        double x = centre.getX();
        double y = centre.getY();

        if (x - rayon < 0) {
            x = rayon;
        }
        if (y - rayon < 0) {
            y = rayon;
        }
        if (x + rayon > largeurZone) {
            x = largeurZone - rayon;
        }
        if (y + rayon > hauteurZone) {
            y = hauteurZone - rayon;
        }

        centre.setX(x);
        centre.setY(y);
    }

    public void limiterRayonDansZone(double largeurZone, double hauteurZone) {
        double rayonMax = Math.min(
                Math.min(centre.getX(), largeurZone - centre.getX()),
                Math.min(centre.getY(), hauteurZone - centre.getY())
        );

        if (rayon > rayonMax) {
            rayon = Math.max(1, rayonMax);
        }
    }

    public Point getCentre() {
        return centre;
    }

    public void setCentre(Point centre) {
        this.centre = centre;
    }

    public double getRayon() {
        return rayon;
    }

    public void setRayon(double rayon) {
        this.rayon = rayon;
    }
}
