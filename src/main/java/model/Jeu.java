package model;

import java.util.ArrayList;
import java.util.List;

import command.HistoriqueCommandes;
import mode.ModeJeu;
import observer.Observable;
import observer.Observateur;
import strategy.GenerationStrategy;
import strategy.ScoreStrategy;

public class Jeu implements Observable {
    private GroupeFormes formesReference;
    private GroupeFormes formesJoueur;
    private ModeJeu modeJeu;
    private final HistoriqueCommandes historique;
    private final List<Observateur> observateurs;

    private double largeurZone;
    private double hauteurZone;

    public Jeu(ModeJeu modeJeu) {
        this.modeJeu = modeJeu;
        this.formesReference = new GroupeFormes();
        this.formesJoueur = new GroupeFormes();
        this.historique = new HistoriqueCommandes();
        this.observateurs = new ArrayList<>();
        this.largeurZone = 1;
        this.hauteurZone = 1;
        demarrerPartie();
    }

    public void demarrerPartie() {
        this.formesJoueur = new GroupeFormes();
        this.historique.vider();
        genererFormesReference();
        notifierObservateurs();
    }

    public void ajouterFormeJoueur(Forme forme) {
        if (formesJoueur.getFormes().size() < modeJeu.getNombreFormesMax()) {
            formesJoueur.ajouterForme(forme);
            notifierObservateurs();
        }
    }

    public void supprimerFormeJoueur(Forme forme) {
        formesJoueur.supprimerForme(forme);
        notifierObservateurs();
    }

    public void viderFormesJoueur() {
        this.formesJoueur = new GroupeFormes();
        this.historique.vider();
        notifierObservateurs();
    }

    public void setDimensionsZone(double largeurZone, double hauteurZone) {
        this.largeurZone = largeurZone;
        this.hauteurZone = hauteurZone;
    }

    public double calculerScore() {
        ScoreStrategy scoreStrategy = modeJeu.getScoreStrategy();
        return scoreStrategy.calculerScore(formesReference, formesJoueur, largeurZone, hauteurZone);
    }

    public double calculerScore(double largeurZone, double hauteurZone) {
        ScoreStrategy scoreStrategy = modeJeu.getScoreStrategy();
        return scoreStrategy.calculerScore(formesReference, formesJoueur, largeurZone, hauteurZone);
    }

    public void genererFormesReference() {
        GenerationStrategy generationStrategy = modeJeu.getGenerationStrategy();
        this.formesReference = generationStrategy.generer();
        notifierObservateurs();
    }

    public void setFormesReference(GroupeFormes reference) {
        this.formesReference = reference;
        this.formesJoueur = new GroupeFormes();
        this.historique.vider();
        notifierObservateurs();
    }

    public void setModeJeu(ModeJeu modeJeu) {
        this.modeJeu = modeJeu;
        demarrerPartie();
    }

    public ModeJeu getModeJeu() {
        return modeJeu;
    }

    public GroupeFormes getFormesReference() {
        return formesReference;
    }

    public GroupeFormes getFormesJoueur() {
        return formesJoueur;
    }

    public HistoriqueCommandes getHistorique() {
        return historique;
    }

    @Override
    public void ajouterObservateur(Observateur observateur) {
        observateurs.add(observateur);
    }

    @Override
    public void retirerObservateur(Observateur observateur) {
        observateurs.remove(observateur);
    }

    @Override
    public void notifierObservateurs() {
        for (Observateur observateur : observateurs) {
            observateur.mettreAJour();
        }
    }
}
