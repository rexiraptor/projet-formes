package controller;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import model.Jeu;
import model.Point;
import state.EtatInteraction;
import view.PanneauJeu;

public class ControleurSouris extends MouseAdapter {
    private Jeu jeu;
    private final PanneauJeu panneauJeu;
    private EtatInteraction etatCourant;

    public ControleurSouris(Jeu jeu, PanneauJeu panneauJeu) {
        this.jeu = jeu;
        this.panneauJeu = panneauJeu;
    }

    public void setEtat(EtatInteraction etatCourant) {
        this.etatCourant = etatCourant;
    }

    public Jeu getJeu() {
        return jeu;
    }

    public void setJeu(Jeu jeu) {
        this.jeu = jeu;
    }

    public PanneauJeu getPanneauJeu() {
        return panneauJeu;
    }

    private Point convertir(MouseEvent e) {
        return new Point(e.getX(), e.getY());
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (etatCourant != null) {
            etatCourant.mousePressed(this, convertir(e));
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (etatCourant != null) {
            etatCourant.mouseDragged(this, convertir(e));
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (etatCourant != null) {
            etatCourant.mouseReleased(this, convertir(e));
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (etatCourant != null) {
            etatCourant.mouseClicked(this, convertir(e));
        }
    }
}
