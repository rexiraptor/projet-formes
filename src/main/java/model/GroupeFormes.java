package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GroupeFormes {
    private final List<Forme> formes = new ArrayList<>();

    public void ajouterForme(Forme forme) {
        formes.add(forme);
    }

    public void supprimerForme(Forme forme) {
        formes.remove(forme);
    }

    public List<Forme> getFormes() {
        return Collections.unmodifiableList(formes);
    }

    public Forme trouverForme(Point p) {
        for (int i = formes.size() - 1; i >= 0; i--) {
            if (formes.get(i).contient(p)) {
                return formes.get(i);
            }
        }
        return null;
    }
}
