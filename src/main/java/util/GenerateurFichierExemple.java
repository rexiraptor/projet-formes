package util;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import model.Cercle;
import model.GroupeFormes;
import model.Point;
import model.RectangleForme;
import strategy.GenerationFichierStrategy;

public class GenerateurFichierExemple {
    public static void main(String[] args) {
        List<GroupeFormes> groupes = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            GroupeFormes groupe = new GroupeFormes();

            groupe.ajouterForme(new Cercle(
                    new Point(120 + i * 15, 100 + i * 10),
                    35 + i * 2,
                    Color.RED
            ));

            groupe.ajouterForme(new RectangleForme(
                    new Point(280 + i * 12, 180),
                    70 + i * 2,
                    55 + i * 3,
                    Color.RED
            ));

            if (i % 2 == 0) {
                groupe.ajouterForme(new Cercle(
                        new Point(500, 250 + i * 5),
                        25 + i,
                        Color.RED
                ));
            } else {
                groupe.ajouterForme(new RectangleForme(
                        new Point(480, 220 + i * 4),
                        60,
                        40 + i * 2,
                        Color.RED
                ));
            }

            groupes.add(groupe);
        }

        GenerationFichierStrategy generation = new GenerationFichierStrategy("dist/ensembles_formes.ser");
        generation.sauvegarder(groupes);

    }
}
