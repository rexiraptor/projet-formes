package command;

import model.Cercle;
import model.Forme;
import model.RectangleForme;

public class CommandeRedimensionnerForme implements Commande {
    private final Forme forme;
    private final double ancienneValeur1;
    private final double ancienneValeur2;
    private final double nouvelleValeur1;
    private final double nouvelleValeur2;

    public CommandeRedimensionnerForme(Forme forme,
                                       double ancienneValeur1,
                                       double ancienneValeur2,
                                       double nouvelleValeur1,
                                       double nouvelleValeur2) {
        this.forme = forme;
        this.ancienneValeur1 = ancienneValeur1;
        this.ancienneValeur2 = ancienneValeur2;
        this.nouvelleValeur1 = nouvelleValeur1;
        this.nouvelleValeur2 = nouvelleValeur2;
    }

    @Override
    public void executer() {
        appliquer(nouvelleValeur1, nouvelleValeur2);
    }

    @Override
    public void annuler() {
        appliquer(ancienneValeur1, ancienneValeur2);
    }

    private void appliquer(double v1, double v2) {
        if (forme instanceof Cercle cercle) {
            cercle.setRayon(v1);
        } else if (forme instanceof RectangleForme rectangle) {
            rectangle.setLargeur(v1);
            rectangle.setHauteur(v2);
        }
    }
}
