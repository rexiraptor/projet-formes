package view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import model.Forme;
import model.Jeu;
import observer.Observateur;
import session.SessionJeu;
import session.SessionDeuxJoueurs;

public class PanneauJeu extends JPanel implements Observateur {
    private Jeu jeu;
    private SessionJeu session;
    private SessionDeuxJoueurs sessionDeuxJoueurs;
    private Forme formePreview;

    public PanneauJeu(Jeu jeu, SessionJeu session) {
        this.jeu = jeu;
        this.session = session;
        this.jeu.ajouterObservateur(this);
        setBackground(Color.WHITE);
    }

    public void setJeu(Jeu nouveauJeu) {
        this.jeu.retirerObservateur(this);
        this.jeu = nouveauJeu;
        this.jeu.ajouterObservateur(this);
        repaint();
    }

    public void setSession(SessionJeu session) {
        this.session = session;
        this.sessionDeuxJoueurs = null;
        repaint();
    }

    public void setSessionDeuxJoueurs(SessionDeuxJoueurs sessionDeuxJoueurs) {
        this.sessionDeuxJoueurs = sessionDeuxJoueurs;
        this.session = null;
        repaint();
    }

    public void setFormePreview(Forme formePreview) {
        this.formePreview = formePreview;
        repaint();
    }

    public void clearFormePreview() {
        this.formePreview = null;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        for (Forme forme : jeu.getFormesReference().getFormes()) {
            forme.dessiner(g2d);
        }

        for (Forme forme : jeu.getFormesJoueur().getFormes()) {
            forme.dessiner(g2d);
        }

        if (formePreview != null) {
            formePreview.dessiner(g2d);
        }

        jeu.setDimensionsZone(getWidth(), getHeight());

        g2d.setColor(Color.BLACK);
        g2d.drawString("Mode : " + jeu.getModeJeu().getNomMode(), 10, 20);

        if (session != null) {
            g2d.drawString("Manche : " + session.getMancheCourante() + " / " + session.getNombreManches(), 10, 40);
            g2d.drawString("Score en direct : " + String.format("%.2f / 100", jeu.calculerScore()), 10, 60);
            g2d.drawString("Score global : " + String.format("%.2f / 100", session.calculerScoreGlobal()), 10, 80);
        }

        if (sessionDeuxJoueurs != null) {
            g2d.drawString(
                    "Cycle : " + sessionDeuxJoueurs.getCycleCourant() + " / " + sessionDeuxJoueurs.getNombreCycles(),
                    10, 40
            );
            g2d.drawString("Phase : " + sessionDeuxJoueurs.getPhaseLabel(), 10, 60);
            g2d.drawString("Dessinateur : " + sessionDeuxJoueurs.getDessinateur().getNom(), 10, 80);
            g2d.drawString("Reproducteur : " + sessionDeuxJoueurs.getReproducteur().getNom(), 10, 100);
            g2d.drawString(
                    sessionDeuxJoueurs.getJoueur1().getNom() + " score : "
                            + String.format("%.2f / 100", sessionDeuxJoueurs.getJoueur1().getScoreGlobal()),
                    10, 120
            );
            g2d.drawString(
                    sessionDeuxJoueurs.getJoueur2().getNom() + " score : "
                            + String.format("%.2f / 100", sessionDeuxJoueurs.getJoueur2().getScoreGlobal()),
                    10, 140
            );
        }
    }

    @Override
    public void mettreAJour() {
        repaint();
    }
}
