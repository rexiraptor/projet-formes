package strategy;

import model.GroupeFormes;

public interface ScoreStrategy {
    double calculerScore(GroupeFormes reference, GroupeFormes joueur, double largeurZone, double hauteurZone);
}
