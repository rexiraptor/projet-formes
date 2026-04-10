package strategy;

import java.awt.Color;
import java.util.Random;

import model.Cercle;
import model.GroupeFormes;
import model.Point;
import model.RectangleForme;


public class GenerationAleatoireStrategy implements GenerationStrategy {
    private final int nombreFormes;
    private final double largeurZone;
    private final double hauteurZone;
    private final Random random = new Random();

    public GenerationAleatoireStrategy(int nombreFormes, double largeurZone, double hauteurZone) {
        this.nombreFormes = nombreFormes;
        this.largeurZone = largeurZone;
        this.hauteurZone = hauteurZone;
    }

    @Override
    public GroupeFormes generer() {
        GroupeFormes groupe = new GroupeFormes();

        for (int i = 0; i < nombreFormes; i++) {
            if (random.nextBoolean()) {
                double rayon = 20 + random.nextInt(60);

                double x = rayon + random.nextDouble() * (largeurZone - 2 * rayon);
                double y = rayon + random.nextDouble() * (hauteurZone - 2 * rayon);

                groupe.ajouterForme(new Cercle(new Point(x, y), rayon, Color.RED));
            } else {
                double largeur = 40 + random.nextInt(120);
                double hauteur = 40 + random.nextInt(120);

                double x = random.nextDouble() * (largeurZone - largeur);
                double y = random.nextDouble() * (hauteurZone - hauteur);

                groupe.ajouterForme(new RectangleForme(new Point(x, y), largeur, hauteur, Color.RED));
            }
        }

        return groupe;
    }
}
