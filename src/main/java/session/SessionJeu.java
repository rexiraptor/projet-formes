package session;

import model.GroupeFormes;
import model.Jeu;
import mode.ModeJeu;
import strategy.GenerationFichierStrategy;

import java.util.ArrayList;
import java.util.List;

public class SessionJeu {
    private final int nombreManches;
    private int mancheCourante;
    private final List<Double> scores;
    private final ModeJeu modeJeu;
    private final TypeSession typeSession;
    private final GenerationFichierStrategy generationFichierStrategy;
    private Jeu jeuCourant;

    public SessionJeu(ModeJeu modeJeu, int nombreManches) {
        this(modeJeu, nombreManches, TypeSession.ALEATOIRE, null);
    }

    public SessionJeu(ModeJeu modeJeu, int nombreManches, TypeSession typeSession,
                      GenerationFichierStrategy generationFichierStrategy) {
        this.modeJeu = modeJeu;
        this.nombreManches = nombreManches;
        this.typeSession = typeSession;
        this.generationFichierStrategy = generationFichierStrategy;
        this.mancheCourante = 0;
        this.scores = new ArrayList<>();
    }

    public void demarrerSession() {
        mancheCourante = 0;
        scores.clear();
        demarrerMancheSuivante();
    }

    public boolean demarrerMancheSuivante() {
        if (mancheCourante >= nombreManches) {
            return false;
        }

        jeuCourant = new Jeu(modeJeu);

        if (typeSession == TypeSession.FICHIER && generationFichierStrategy != null) {
            GroupeFormes reference = generationFichierStrategy.genererManche(mancheCourante);
            jeuCourant.setFormesReference(reference);
        } else {
            jeuCourant.demarrerPartie();
        }

        mancheCourante++;
        return true;
    }

    public void terminerManche(double largeurZone, double hauteurZone) {
        if (jeuCourant != null) {
            double score = jeuCourant.calculerScore(largeurZone, hauteurZone);
            scores.add(score);
        }
    }

    public double calculerScoreGlobal() {
        if (scores.isEmpty()) {
            return 0;
        }

        double somme = 0;
        for (double score : scores) {
            somme += score;
        }

        return somme / scores.size();
    }

    public boolean estTerminee() {
        return scores.size() >= nombreManches;
    }

    public int getNombreManches() {
        return nombreManches;
    }

    public int getMancheCourante() {
        return mancheCourante;
    }

    public List<Double> getScores() {
        return scores;
    }

    public Jeu getJeuCourant() {
        return jeuCourant;
    }
}
