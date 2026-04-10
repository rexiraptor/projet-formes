package state;

import controller.ControleurSouris;
import model.Point;

public interface EtatInteraction {
    void mousePressed(ControleurSouris controleur, Point p);
    void mouseDragged(ControleurSouris controleur, Point p);
    void mouseReleased(ControleurSouris controleur, Point p);
    void mouseClicked(ControleurSouris controleur, Point p);
}
