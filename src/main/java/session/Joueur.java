package session;

import java.util.ArrayList;
import java.util.List;

public class Joueur {
    private final String nom;
    private final List<Double> scores;

    public Joueur(String nom) {
        this.nom = nom;
        this.scores = new ArrayList<>();
    }

    public void ajouterScore(double score) {
        scores.add(score);
    }

    public double getScoreGlobal() {
        if (scores.isEmpty()) {
            return 0;
        }

        double somme = 0;
        for (double score : scores) {
            somme += score;
        }
        return somme / scores.size();
    }

    public String getNom() {
        return nom;
    }

    public List<Double> getScores() {
        return scores;
    }
}
