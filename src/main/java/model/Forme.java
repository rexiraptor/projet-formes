package model;

import java.awt.Graphics2D;
import java.awt.geom.Area;

public interface Forme {
    double aire();
    boolean contient(Point p);

    boolean intersecte(Forme autre);
    boolean intersecteCercle(Cercle cercle);
    boolean intersecteRectangle(RectangleForme rectangle);

    void deplacer(double dx, double dy);
    void redimensionner(Point ancrage, Point courant);
    void dessiner(Graphics2D g);

    Area toArea();
}
