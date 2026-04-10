package command;

import model.Cercle;
import model.Forme;
import model.Point;
import model.RectangleForme;

public class CommandeDeplacerForme implements Commande {
    private final Forme forme;
    private final Point anciennePosition;
    private final Point nouvellePosition;

    public CommandeDeplacerForme(Forme forme, Point anciennePosition, Point nouvellePosition) {
        this.forme = forme;
        this.anciennePosition = anciennePosition.copy();
        this.nouvellePosition = nouvellePosition.copy();
    }

    @Override
    public void executer() {
        appliquer(nouvellePosition);
    }

    @Override
    public void annuler() {
        appliquer(anciennePosition);
    }

    private void appliquer(Point p) {
        if (forme instanceof Cercle cercle) {
            cercle.setCentre(p.copy());
        } else if (forme instanceof RectangleForme rectangle) {
            rectangle.setOrigine(p.copy());
        }
    }
}
