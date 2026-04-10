package strategy;

import model.Cercle;
import model.Forme;
import model.GroupeFormes;
import model.Point;
import model.RectangleForme;

import java.awt.Color;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class GenerationFichierStrategy implements GenerationStrategy {
    private final String cheminFichier;
    private List<GroupeFormes> manches;

    public GenerationFichierStrategy(String cheminFichier) {
        this.cheminFichier = cheminFichier;
        this.manches = chargerDepuisFichier();
    }

    @Override
    public GroupeFormes generer() {
        if (manches.isEmpty()) {
            return new GroupeFormes();
        }
        return copierGroupe(manches.get(0));
    }

    public GroupeFormes genererManche(int index) {
        if (index < 0 || index >= manches.size()) {
            return new GroupeFormes();
        }
        return copierGroupe(manches.get(index));
    }

    public void sauvegarder(List<GroupeFormes> groupes) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(cheminFichier))) {
            List<List<FormeSerializable>> donnees = new ArrayList<>();

            for (GroupeFormes groupe : groupes) {
                List<FormeSerializable> liste = new ArrayList<>();
                for (Forme forme : groupe.getFormes()) {
                    if (forme instanceof Cercle cercle) {
                        liste.add(new FormeSerializable(
                                "CERCLE",
                                cercle.getCentre().getX(),
                                cercle.getCentre().getY(),
                                cercle.getRayon(),
                                0
                        ));
                    } else if (forme instanceof RectangleForme rectangle) {
                        liste.add(new FormeSerializable(
                                "RECTANGLE",
                                rectangle.getOrigine().getX(),
                                rectangle.getOrigine().getY(),
                                rectangle.getLargeur(),
                                rectangle.getHauteur()
                        ));
                    }
                }
                donnees.add(liste);
            }

            oos.writeObject(donnees);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    private List<GroupeFormes> chargerDepuisFichier() {
        List<GroupeFormes> resultats = new ArrayList<>();
        File fichier = new File(cheminFichier);

        if (!fichier.exists()) {
            return resultats;
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fichier))) {
            List<List<FormeSerializable>> donnees = (List<List<FormeSerializable>>) ois.readObject();

            for (List<FormeSerializable> liste : donnees) {
                GroupeFormes groupe = new GroupeFormes();

                for (FormeSerializable fs : liste) {
                    if ("CERCLE".equals(fs.type)) {
                        groupe.ajouterForme(new Cercle(
                                new Point(fs.x, fs.y),
                                fs.v1,
                                Color.RED
                        ));
                    } else if ("RECTANGLE".equals(fs.type)) {
                        groupe.ajouterForme(new RectangleForme(
                                new Point(fs.x, fs.y),
                                fs.v1,
                                fs.v2,
                                Color.RED
                        ));
                    }
                }

                resultats.add(groupe);
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return resultats;
    }

    private GroupeFormes copierGroupe(GroupeFormes original) {
        GroupeFormes copie = new GroupeFormes();

        for (Forme forme : original.getFormes()) {
            if (forme instanceof Cercle cercle) {
                copie.ajouterForme(new Cercle(
                        cercle.getCentre().copy(),
                        cercle.getRayon(),
                        Color.RED
                ));
            } else if (forme instanceof RectangleForme rectangle) {
                copie.ajouterForme(new RectangleForme(
                        rectangle.getOrigine().copy(),
                        rectangle.getLargeur(),
                        rectangle.getHauteur(),
                        Color.RED
                ));
            }
        }

        return copie;
    }

    private static class FormeSerializable implements Serializable {
        private static final long serialVersionUID = 1L;

        String type;
        double x;
        double y;
        double v1;
        double v2;

        FormeSerializable(String type, double x, double y, double v1, double v2) {
            this.type = type;
            this.x = x;
            this.y = y;
            this.v1 = v1;
            this.v2 = v2;
        }
    }
}
