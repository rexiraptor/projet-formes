import javax.swing.SwingUtilities;

import mode.ModeClassique;
import model.Jeu;
import session.SessionJeu;
import view.FenetrePrincipale;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            SessionJeu session = new SessionJeu(new ModeClassique(), 10);
            session.demarrerSession();
            Jeu jeu = session.getJeuCourant();
            new FenetrePrincipale(jeu, session);
        });
    }
}
