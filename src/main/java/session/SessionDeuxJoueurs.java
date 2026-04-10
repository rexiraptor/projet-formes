package session;

import java.awt.Color;

import mode.ModeJeu;
import model.Cercle;
import model.Forme;
import model.GroupeFormes;
import model.Jeu;
import model.RectangleForme;

public class SessionDeuxJoueurs {
    private final Joueur joueur1;
    private final Joueur joueur2;

    private Joueur dessinateur;
    private Joueur reproducteur;

    private Jeu jeuCourant;
    private final ModeJeu modeJeu;

    private int mancheCourante;
    private final int nombreManches;

    private boolean phaseDessin;

    public SessionDeuxJoueurs(Joueur joueur1, Joueur joueur2, ModeJeu modeJeu, int nombreManches) {
        this.joueur1 = joueur1;
        this.joueur2 = joueur2;
        this.modeJeu = modeJeu;
        this.nombreManches = nombreManches;

        this.dessinateur = joueur1;
        this.reproducteur = joueur2;

        this.mancheCourante = 1;
        this.phaseDessin = true;

        this.jeuCourant = new Jeu(modeJeu);
        this.jeuCourant.setFormesReference(new GroupeFormes());
    }

    public void validerPhase(double largeurZone, double hauteurZone) {
        if (phaseDessin) {
            GroupeFormes reference = copierGroupeVersRouge(jeuCourant.getFormesJoueur());
            jeuCourant.setFormesReference(reference);
            jeuCourant.viderFormesJoueur();
            phaseDessin = false;
        } else {
            double score = jeuCourant.calculerScore(largeurZone, hauteurZone);
            reproducteur.ajouterScore(score);

            inverserRoles();
            mancheCourante++;

            if (!estTerminee()) {
                jeuCourant = new Jeu(modeJeu);
                jeuCourant.setFormesReference(new GroupeFormes());
                phaseDessin = true;
            }
        }
    }

    private void inverserRoles() {
        Joueur temp = dessinateur;
        dessinateur = reproducteur;
        reproducteur = temp;
    }

    public boolean estTerminee() {
        return mancheCourante > nombreManches;
    }

    public int getCycleCourant() {
        return (mancheCourante + 1) / 2;
    }

    public int getNombreCycles() {
        return nombreManches / 2;
    }

    public String getPhaseLabel() {
        return phaseDessin ? "Dessin" : "Reproduction";
    }

    public Jeu getJeuCourant() {
        return jeuCourant;
    }

    public Joueur getDessinateur() {
        return dessinateur;
    }

    public Joueur getReproducteur() {
        return reproducteur;
    }

    public Joueur getJoueur1() {
        return joueur1;
    }

    public Joueur getJoueur2() {
        return joueur2;
    }

    public boolean isPhaseDessin() {
        return phaseDessin;
    }

    public int getMancheCourante() {
        return mancheCourante;
    }

    public int getNombreManches() {
        return nombreManches;
    }

    private GroupeFormes copierGroupeVersRouge(GroupeFormes original) {
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
}
