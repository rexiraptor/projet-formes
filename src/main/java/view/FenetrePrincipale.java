package view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import controller.ControleurSouris;
import mode.ModeClassique;
import mode.ModeDifficile;
import mode.ModeJeu;
import model.Jeu;
import session.Joueur;
import session.SessionJeu;
import session.SessionDeuxJoueurs;
import session.TypeSession;
import state.EtatCreationCercle;
import state.EtatCreationRectangle;
import state.EtatDeplacement;
import state.EtatRedimensionnement;
import state.EtatSuppression;
import strategy.GenerationFichierStrategy;

public class FenetrePrincipale extends JFrame {

    private Jeu jeu;
    private SessionJeu session;
    private SessionDeuxJoueurs sessionDeuxJoueurs;

    private final PanneauJeu panneauJeu;
    private final ControleurSouris controleur;

    private JLabel labelScore;
    private JLabel labelManche;
    private JLabel labelGlobal;

    public FenetrePrincipale(Jeu jeu, SessionJeu session) {
        this.jeu = jeu;
        this.session = session;
        this.sessionDeuxJoueurs = null;

        this.panneauJeu = new PanneauJeu(jeu, session);
        this.controleur = new ControleurSouris(jeu, panneauJeu);

        setTitle("Projet Formes");
        setSize(1100, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(new BorderLayout());

        panneauJeu.addMouseListener(controleur);
        panneauJeu.addMouseMotionListener(controleur);

        add(panneauJeu, BorderLayout.CENTER);
        add(creerBarreOutils(), BorderLayout.NORTH);

        controleur.setEtat(new EtatCreationCercle(controleur));

        mettreAJourInfos();
        setVisible(true);
    }

    private JPanel creerBarreOutils() {

        JPanel global = new JPanel(new BorderLayout());

        JPanel ligne1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel ligne2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel ligne3 = new JPanel(new FlowLayout(FlowLayout.LEFT));

        JButton cercle = new JButton("Cercle");
        JButton rectangle = new JButton("Rectangle");
        JButton deplacer = new JButton("Déplacer");
        JButton supprimer = new JButton("Supprimer");
        JButton resize = new JButton("Redimensionner");

        JButton undo = new JButton("Undo");
        JButton redo = new JButton("Redo");
        JButton nouvelle = new JButton("Nouvelle partie");
        JButton valider = new JButton("Valider");

        labelScore = new JLabel("Score : ?");
        labelManche = new JLabel("Manche : ?");
        labelGlobal = new JLabel("Global : ?");

        cercle.addActionListener(e -> controleur.setEtat(new EtatCreationCercle(controleur)));
        rectangle.addActionListener(e -> controleur.setEtat(new EtatCreationRectangle(controleur)));
        deplacer.addActionListener(e -> controleur.setEtat(new EtatDeplacement(controleur)));
        supprimer.addActionListener(e -> controleur.setEtat(new EtatSuppression(controleur)));
        resize.addActionListener(e -> controleur.setEtat(new EtatRedimensionnement(controleur)));

        undo.addActionListener(e -> {
            jeu.getHistorique().undo();
            jeu.notifierObservateurs();
            mettreAJourInfos();
        });

        redo.addActionListener(e -> {
            jeu.getHistorique().redo();
            jeu.notifierObservateurs();
            mettreAJourInfos();
        });

        nouvelle.addActionListener(e -> popupNouvellePartie());
        valider.addActionListener(e -> validerEtAvancer());

        ligne1.add(cercle);
        ligne1.add(rectangle);
        ligne1.add(deplacer);
        ligne1.add(supprimer);
        ligne1.add(resize);

        ligne2.add(undo);
        ligne2.add(redo);
        ligne2.add(nouvelle);
        ligne2.add(valider);

        ligne3.add(labelManche);
        ligne3.add(labelScore);
        ligne3.add(labelGlobal);

        global.add(ligne1, BorderLayout.NORTH);
        global.add(ligne2, BorderLayout.CENTER);
        global.add(ligne3, BorderLayout.SOUTH);

        return global;
    }

    private Integer demanderNombreManches() {
        while (true) {
            String saisie = JOptionPane.showInputDialog(
                    this,
                    "Nombre de manches :",
                    "10"
            );

            if (saisie == null) {
                return null;
            }

            try {
                int valeur = Integer.parseInt(saisie.trim());

                if (valeur > 0) {
                    return valeur;
                }

                JOptionPane.showMessageDialog(
                        this,
                        "Veuillez entrer un entier strictement positif.",
                        "Erreur",
                        JOptionPane.ERROR_MESSAGE
                );

            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(
                        this,
                        "Veuillez entrer un nombre valide.",
                        "Erreur",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        }
    }

    private void popupNouvellePartie() {

        String[] modes = {"Aléatoire", "Fichier", "2 joueurs"};

        int choixMode = JOptionPane.showOptionDialog(
                this,
                "Choisissez le mode de jeu :",
                "Nouvelle partie",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                modes,
                modes[0]
        );

        if (choixMode == JOptionPane.CLOSED_OPTION) return;

        String[] difficulte = {"Classique", "Difficile"};

        int choixDiff = JOptionPane.showOptionDialog(
                this,
                "Choisissez la difficulté :",
                "Nouvelle partie",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                difficulte,
                difficulte[0]
        );

        if (choixDiff == JOptionPane.CLOSED_OPTION) return;

        Integer nbManches = demanderNombreManches();
        if (nbManches == null) return;

        ModeJeu mode = (choixDiff == 1)
                ? new ModeDifficile()
                : new ModeClassique();

        if (choixMode == 2) {

            String j1 = JOptionPane.showInputDialog(this, "Nom joueur 1 :", "Joueur 1");
            if (j1 == null || j1.isBlank()) j1 = "Joueur 1";

            String j2 = JOptionPane.showInputDialog(this, "Nom joueur 2 :", "Joueur 2");
            if (j2 == null || j2.isBlank()) j2 = "Joueur 2";

            SessionDeuxJoueurs s = new SessionDeuxJoueurs(
                    new Joueur(j1),
                    new Joueur(j2),
                    mode,
                    nbManches * 2
            );

            remplacerSessionDeuxJoueurs(s);
            return;
        }

        SessionJeu s;

        if (choixMode == 1) {
            s = new SessionJeu(
                    mode,
                    nbManches,
                    TypeSession.FICHIER,
                    new GenerationFichierStrategy("dist/ensembles_formes.ser")
            );
        } else {
            s = new SessionJeu(mode, nbManches);
        }

        s.demarrerSession();
        remplacerSession(s);
    }

    private void validerEtAvancer() {

        double w = panneauJeu.getWidth();
        double h = panneauJeu.getHeight();

        if (sessionDeuxJoueurs != null) {

            sessionDeuxJoueurs.validerPhase(w, h);

            if (sessionDeuxJoueurs.estTerminee()) {
                JOptionPane.showMessageDialog(this,
                        "Fin\n"
                                + sessionDeuxJoueurs.getJoueur1().getNom() + " : "
                                + sessionDeuxJoueurs.getJoueur1().getScoreGlobal() + "\n"
                                + sessionDeuxJoueurs.getJoueur2().getNom() + " : "
                                + sessionDeuxJoueurs.getJoueur2().getScoreGlobal());
            } else {
                remplacerJeu(sessionDeuxJoueurs.getJeuCourant());
            }

            mettreAJourInfos();
            return;
        }

        session.terminerManche(w, h);

        if (session.estTerminee()) {
            JOptionPane.showMessageDialog(this,
                    "Score final : " + session.calculerScoreGlobal());
            mettreAJourInfos();
            return;
        }

        session.demarrerMancheSuivante();
        remplacerJeu(session.getJeuCourant());
    }

    private void remplacerJeu(Jeu j) {
        this.jeu = j;
        controleur.setJeu(j);
        panneauJeu.setJeu(j);
        j.notifierObservateurs();
        mettreAJourInfos();
    }

    private void remplacerSession(SessionJeu s) {
        this.session = s;
        this.sessionDeuxJoueurs = null;
        panneauJeu.setSession(s);
        remplacerJeu(s.getJeuCourant());
    }

    private void remplacerSessionDeuxJoueurs(SessionDeuxJoueurs s) {
        this.sessionDeuxJoueurs = s;
        this.session = null;
        panneauJeu.setSessionDeuxJoueurs(s);
        remplacerJeu(s.getJeuCourant());
    }

    private void mettreAJourInfos() {

        if (sessionDeuxJoueurs != null) {
            labelManche.setText(
                    "Cycle " + sessionDeuxJoueurs.getCycleCourant()
                            + " / " + sessionDeuxJoueurs.getNombreCycles()
            );
            labelScore.setText("Phase : " + sessionDeuxJoueurs.getPhaseLabel());
            labelGlobal.setText(
                    sessionDeuxJoueurs.getJoueur1().getNom() + " : "
                            + String.format("%.2f", sessionDeuxJoueurs.getJoueur1().getScoreGlobal())
                            + " | "
                            + sessionDeuxJoueurs.getJoueur2().getNom() + " : "
                            + String.format("%.2f", sessionDeuxJoueurs.getJoueur2().getScoreGlobal())
            );
            return;
        }

        labelManche.setText("Manche " + session.getMancheCourante());
        labelScore.setText("Score " + jeu.calculerScore());
        labelGlobal.setText("Global " + session.calculerScoreGlobal());
    }
}
