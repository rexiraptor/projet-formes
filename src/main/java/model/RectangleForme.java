package model;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;

public class RectangleForme extends FormeAbstraite {
    private Point origine;
    private double largeur;
    private double hauteur;

    public RectangleForme(Point origine, double largeur, double hauteur, Color couleur) {
        super(couleur);
        this.origine = origine;
        this.largeur = largeur;
        this.hauteur = hauteur;
    }

    @Override
    public double aire() {
        return largeur * hauteur;
    }

    @Override
    public boolean contient(Point p) {
        double x = p.getX();
        double y = p.getY();

        return x >= origine.getX()
                && x <= origine.getX() + largeur
                && y >= origine.getY()
                && y <= origine.getY() + hauteur;
    }

    @Override
    public boolean intersecte(Forme autre) {
        return autre.intersecteRectangle(this);
    }

    @Override
    public boolean intersecteCercle(Cercle cercle) {
        return cercle.intersecteRectangle(this);
    }

    @Override
    public boolean intersecteRectangle(RectangleForme autreRectangle) {
        return origine.getX() < autreRectangle.getOrigine().getX() + autreRectangle.getLargeur()
                && origine.getX() + largeur > autreRectangle.getOrigine().getX()
                && origine.getY() < autreRectangle.getOrigine().getY() + autreRectangle.getHauteur()
                && origine.getY() + hauteur > autreRectangle.getOrigine().getY();
    }

    @Override
    public void deplacer(double dx, double dy) {
        origine.translate(dx, dy);
    }

    @Override
    public void redimensionner(Point ancrage, Point courant) {
        this.largeur = Math.max(1, Math.abs(courant.getX() - ancrage.getX()));
        this.hauteur = Math.max(1, Math.abs(courant.getY() - ancrage.getY()));
        this.origine = new Point(
                Math.min(ancrage.getX(), courant.getX()),
                Math.min(ancrage.getY(), courant.getY())
        );
    }

    @Override
    public void dessiner(Graphics2D g) {
        Rectangle2D rectangle = new Rectangle2D.Double(
                origine.getX(),
                origine.getY(),
                largeur,
                hauteur
        );

        g.setColor(getCouleur());
        g.fill(rectangle);

        g.setColor(Color.BLACK);
        g.draw(rectangle);
    }

    @Override
    public Area toArea() {
        return new Area(new Rectangle2D.Double(
                origine.getX(),
                origine.getY(),
                largeur,
                hauteur
        ));
    }

    public void garderDansZone(double largeurZone, double hauteurZone) {
        double x = origine.getX();
        double y = origine.getY();

        if (x < 0) {
            x = 0;
        }
        if (y < 0) {
            y = 0;
        }
        if (x + largeur > largeurZone) {
            x = largeurZone - largeur;
        }
        if (y + hauteur > hauteurZone) {
            y = hauteurZone - hauteur;
        }

        origine.setX(x);
        origine.setY(y);
    }

    public Point getCoinHautGauche() {
        return origine.copy();
    }

    public Point getCoinHautDroit() {
        return new Point(origine.getX() + largeur, origine.getY());
    }

    public Point getCoinBasGauche() {
        return new Point(origine.getX(), origine.getY() + hauteur);
    }

    public Point getCoinBasDroit() {
        return new Point(origine.getX() + largeur, origine.getY() + hauteur);
    }

    public Point getCoinLePlusProche(Point p) {
        Point[] coins = {
                getCoinHautGauche(),
                getCoinHautDroit(),
                getCoinBasGauche(),
                getCoinBasDroit()
        };

        Point plusProche = coins[0];
        double min = p.distanceTo(coins[0]);

        for (int i = 1; i < coins.length; i++) {
            double d = p.distanceTo(coins[i]);
            if (d < min) {
                min = d;
                plusProche = coins[i];
            }
        }

        return plusProche;
    }

    public Point getCoinOppose(Point coin) {
        if (coin.getX() == origine.getX() && coin.getY() == origine.getY()) {
            return getCoinBasDroit();
        }
        if (coin.getX() == origine.getX() + largeur && coin.getY() == origine.getY()) {
            return getCoinBasGauche();
        }
        if (coin.getX() == origine.getX() && coin.getY() == origine.getY() + hauteur) {
            return getCoinHautDroit();
        }
        return getCoinHautGauche();
    }

    public Point getOrigine() {
        return origine;
    }

    public void setOrigine(Point origine) {
        this.origine = origine;
    }

    public double getLargeur() {
        return largeur;
    }

    public void setLargeur(double largeur) {
        this.largeur = largeur;
    }

    public double getHauteur() {
        return hauteur;
    }

    public void setHauteur(double hauteur) {
        this.hauteur = hauteur;
    }
}
