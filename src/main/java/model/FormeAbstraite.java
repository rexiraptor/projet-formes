package model;

import java.awt.Color;

public abstract class FormeAbstraite implements Forme {
    private Color couleur;
    private boolean selectionnee;

    public FormeAbstraite(Color couleur) {
        this.couleur = couleur;
        this.selectionnee = false;
    }

    public Color getCouleur() {
        return couleur;
    }

    public void setCouleur(Color couleur) {
        this.couleur = couleur;
    }

    public boolean isSelectionnee() {
        return selectionnee;
    }

    public void setSelectionnee(boolean selectionnee) {
        this.selectionnee = selectionnee;
    }
}
